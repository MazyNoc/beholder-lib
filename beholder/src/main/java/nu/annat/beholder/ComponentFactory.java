package nu.annat.beholder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nu.annat.beholder.action.ActionHandler;
import nu.annat.beholder.presenter.ComponentInfo;

public class ComponentFactory {

	private static final String TAG = ComponentFactory.class.getSimpleName();
	protected Map<Class<? extends ComponentInfo>, Component> components = new HashMap<>();
	private List<WeakReference<ComponentViewHolder>> activeComponents = new LinkedList<>();

	public interface ViewHolderConstructor {
		ComponentViewHolder create(ViewInformation vi, ViewDataBinding vdb, ActionHandler ah, int layoutId, int reuseId);
	}

	public static class Component {
		public ViewHolderConstructor vhc;
		public Class<? extends ComponentViewHolder> viewHolder;
		public int layout;
		public Class<? extends ComponentInfo> presenter;

		public Component(Class<? extends ComponentInfo> presenter, Class<? extends ComponentViewHolder> viewHolder, int layout) {
			this.layout = layout;
			this.presenter = presenter;
			this.viewHolder = viewHolder;
		}

		public Component(Class<? extends ComponentInfo> presenter, ViewHolderConstructor viewHolderConstructor, int layout) {
			this.layout = layout;
			this.presenter = presenter;
			this.vhc = viewHolderConstructor;
		}

	}

	public static Bundle saveStates(List<ComponentInfo> componentInfos, Bundle bundle) {
		saveStates("", bundle, bundle, componentInfos);
		return bundle;
	}

	private static void saveStates(String prefix, Bundle root, Bundle savedComponents, List<ComponentInfo> componentInfos) {
		int index = 0;
		for (ComponentInfo componentInfo : componentInfos) {
			String uniqueId = prefix + ":" + String.valueOf(componentInfo.layoutHash()) + ":" + index;
			Bundle bundle = new Bundle();
			componentInfo.saveStates(bundle);
			savedComponents.putBundle(uniqueId, bundle);
			saveStates(uniqueId, root, bundle, componentInfo.getChildren());
			index++;
		}
	}

	public static void restoreStates(Bundle bundle, List<ComponentInfo> componentInfos) {
		restoreStates("", bundle, bundle, componentInfos);
	}

	private static void restoreStates(String prefix, Bundle root, Bundle bundle, List<ComponentInfo> componentInfos) {
		int index = 0;
		for (ComponentInfo componentInfo : componentInfos) {
			String uniqueId = prefix + ":" + String.valueOf(componentInfo.layoutHash()) + ":" + index;
			Bundle objectBundle = bundle.getBundle(uniqueId);
			if (objectBundle != null) {
				componentInfo.restoreStates(objectBundle);
				restoreStates(uniqueId, root, objectBundle, componentInfo.getChildren());
			}
			index++;
		}
	}

	public static void print(List<ComponentInfo> ComponentInfoList) {
		print(ComponentInfoList, 0);
	}

	private static void print(List<ComponentInfo> ComponentInfoList, int level) {
		String s = "";
		for (int i = 0; i < level; i++) {
			s += "    ";
		}

		for (ComponentInfo componentInfo : ComponentInfoList) {
			System.out.println(level + s + componentInfo.getClass().getSimpleName());
			if (componentInfo instanceof ComponentInfo) {
				print(componentInfo.getChildren(), level + 1);
			}

		}
	}

	public ComponentFactory() {
	}

	public ComponentFactory(Component... components) {
		this();
		registerComponents(components);
	}

	public ComponentFactory(Collection<Component> components) {
		this();
		registerComponents(components);
	}

	public void registerComponent(Component component) {
		components.put(component.presenter, component);
	}

	public void registerComponents(Component... components) {
		registerComponents(Arrays.asList(components));
	}

	public void registerComponents(Collection<Component> components) {
		for (Component ComponentInfo : components) {
			registerComponent(ComponentInfo);
		}
	}

	public ComponentViewHolder createDeep(int depth, int order, Class<? extends ComponentInfo> presenterClass, ComponentInfo componentInfo, ViewGroup root, boolean force, boolean bindPresenter, ActionHandler actionHandler) {
		ComponentViewHolder holder = createView(depth, order, presenterClass, componentInfo, root, actionHandler);
		if (bindPresenter) holder.setData(componentInfo, force);
		if (!componentInfo.getChildren().isEmpty() && holder instanceof ComponentGroup) {
			ComponentGroup componentGroup = (ComponentGroup) holder;
			int childOrder = 0;
			depth++;
			for (final ComponentInfo component : componentInfo.getChildren()) {
				ComponentViewHolder deep = createDeep(depth, childOrder++, component.getClass(), component, componentGroup.getChildArea(), force, true, actionHandler);
				componentGroup.addChild(deep);
			}
		}
		return holder;
	}

	protected ComponentViewHolder createView(int depth, int order, Class<? extends ComponentInfo> presenterClass, ComponentInfo componentInfo, ViewGroup root, ActionHandler actionHandler) {
		Component it = getIt(presenterClass);
		int layoutId = componentInfo.layoutHash();
		int reuseId = componentInfo.deepLayoutHash();
		if (it == null) {
			throw new RuntimeException(presenterClass.getName() + " is not registered as a component");
		}
		LayoutInflater inflater = LayoutInflater.from(root.getContext());
		ViewDataBinding inflate = DataBindingUtil.inflate(inflater, it.layout, root, false);
		if (inflate == null) {
			throw new RuntimeException("Can't inflate view for " + presenterClass.getName());
		}

		ViewInformation viewInformation = new ViewInformation(depth, order);


		if (it.vhc != null) {
			return it.vhc.create(viewInformation, inflate, actionHandler, layoutId, reuseId);
		}
		// until java 8, reflection
		try {
			Constructor<? extends ComponentViewHolder> constructor = it.viewHolder.getConstructor(ViewInformation.class, ViewDataBinding.class, ActionHandler.class, int.class, int.class);
			ComponentViewHolder componentViewHolder = constructor.newInstance(viewInformation, inflate, actionHandler, layoutId, reuseId);
			activeComponents.add(new WeakReference<>(componentViewHolder));
			return componentViewHolder;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<ComponentViewHolder> getActiveComponents() {
		compactActiveList();
		List<ComponentViewHolder> active = new ArrayList<>();
		for (WeakReference<ComponentViewHolder> activeComponent : activeComponents) {
			ComponentViewHolder componentViewHolder = activeComponent.get();
			if (componentViewHolder != null) {
				active.add(componentViewHolder);
			}
		}
		return active;
	}

	public void compactActiveList() {
		Iterator<WeakReference<ComponentViewHolder>> iterator = activeComponents.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().get() == null) {
				iterator.remove();
			}
		}
	}

	public void update(ComponentViewHolder holder, boolean force) {
		ViewGroup vg = ((ViewGroup) holder.itemView);
		//updateDeep(null, holder, holder.getPresenter(), force);
		// simple solution
		if (holder instanceof ComponentGroupViewHolder) {
			ComponentGroupViewHolder groupViewHolder = (ComponentGroupViewHolder) holder;
			groupViewHolder.removeAll();
			ViewInformation viewInformation = groupViewHolder.getViewInformation();
			ActionHandler actionHandler = groupViewHolder.getActionHandler();
			ComponentInfo presenter = groupViewHolder.getPresenter();
			ViewGroup parentVG = (ViewGroup) groupViewHolder.getChildArea();
			int depth = viewInformation.getDepth() + 1;
			int order = 0;
			for (ComponentInfo componentInfo : presenter) {
				groupViewHolder.addChild(createDeep(depth, order++, componentInfo.getClass(), componentInfo, parentVG, force, true, actionHandler));
			}
		}
	}

	public void updateDeep(ComponentGroupViewHolder parent, ComponentViewHolder holder, ComponentInfo componentInfo, boolean force) {
		if (holder.getLayoutId() != componentInfo.layoutHash()) {
			Log.d(TAG, "ID missmatch, recreate");
			ViewInformation viewInformation = holder.getViewInformation();
			parent.remove(holder);
			ViewGroup parentVG = (ViewGroup) parent.itemView;
			ComponentViewHolder newHolder = createDeep(viewInformation.getDepth(), viewInformation.getOrder(), componentInfo.getClass(), componentInfo, parentVG, force, true, holder.getActionHandler());
			parent.addChild(newHolder);
		} else {
			Log.d(TAG, "ID match, update data");
			holder.setData(componentInfo, force);
		}

		if (holder instanceof ComponentGroupViewHolder) {
			ComponentGroupViewHolder groupViewHolder = (ComponentGroupViewHolder) holder;
			List<ComponentViewHolder> holders = new ArrayList<>(groupViewHolder.getChildren());
			int max = Math.max(holders.size(), componentInfo.size());
			for (int i = 0; i < max; i++) {
				if (i >= holders.size()) {
					Log.d(TAG, "more presenters than views, create new");
					ViewInformation viewInformation = holder.getViewInformation();
					ComponentInfo componentInfo1 = componentInfo.get(i);
					ComponentViewHolder newComponent = createDeep(viewInformation.getDepth(), viewInformation.getOrder(), componentInfo1.getClass(), componentInfo1, (ViewGroup) groupViewHolder.itemView, force, true, groupViewHolder.getActionHandler());
					groupViewHolder.addChild(i, newComponent);
				} else if (i >= componentInfo.size()) {
					Log.d(TAG, "more views than presenters, remove");
					groupViewHolder.remove(holders.get(i));
				} else {
					Log.d(TAG, "update child");
					updateDeep(groupViewHolder, holders.get(i), componentInfo.get(i), force);
				}
			}
		}
	}


	public void bindDeep(ComponentViewHolder holder, ComponentInfo componentInfo, boolean force) {
		if (holder.getReuseId() != componentInfo.deepLayoutHash()) {
			throw new RuntimeException(String.format("Component does not fit the layout, holder = %d, componentInfo = %d", holder.getLayoutId(), componentInfo.layoutHash()));
		}

		holder.setData(componentInfo, force);
		if (componentInfo instanceof ComponentInfo && holder instanceof ComponentGroup) {
			ComponentGroup componentGroup = (ComponentGroup) holder;
			if (holder.getLayoutId() == componentInfo.layoutHash()) {
				List<ComponentViewHolder> children = componentGroup.getChildren();
				List<ComponentInfo> componentInfoList = componentInfo.getChildren();
				if (children != null) {
					for (int i = 0; i < children.size(); i++) {
						bindDeep(children.get(i), componentInfoList.get(i), force);
					}
				}
			}
		}
	}

	protected Component getIt(Class<? extends ComponentInfo> presenterClass) {
		return components.get(presenterClass);
	}

	public <T extends ComponentViewHolder> T create(ComponentInfo componentInfo, ViewGroup root, ActionHandler actionHandler) {
		return (T) createDeep(0, 0, componentInfo.getClass(), componentInfo, root, false, true, actionHandler);
	}

	public <T extends ComponentViewHolder> T createReusable(ComponentInfo componentInfo, ViewGroup root, ActionHandler actionHandler) {
		return (T) createDeep(0, 0, componentInfo.getClass(), componentInfo, root, false, false, actionHandler);
	}

	public <T extends ComponentViewHolder> T createReusable(Class<? extends ComponentInfo> presenterClass, ViewGroup root, ActionHandler actionHandler) {
		return (T) createDeep(0, 0, presenterClass, null, root, false, false, actionHandler);
	}

	public Collection<Class<? extends ComponentInfo>> getRegisteredPresenters() {
		return components.keySet();
	}
}
