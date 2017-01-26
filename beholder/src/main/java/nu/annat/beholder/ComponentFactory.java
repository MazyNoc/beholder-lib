package nu.annat.beholder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nu.annat.beholder.presenter.ComponentInfo;
import nu.annat.beholder.presenter.ParentComponentInfo;

public class ComponentFactory {

	private static final String TAG = ComponentFactory.class.getSimpleName();
	protected Map<Class<? extends ComponentInfo>, Component> components = new HashMap<>();

	public static class Component {
		public Class<? extends ComponentViewHolder> viewHolder;
		public int layout;
		public Class<? extends ComponentInfo> presenter;

		public Component(Class<? extends ComponentViewHolder> viewHolder, int layout, Class<? extends ComponentInfo> presenter) {
			this.layout = layout;
			this.presenter = presenter;
			this.viewHolder = viewHolder;
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
		for (Component componentInfo : components) {
			registerComponent(componentInfo);
		}
	}

	public ComponentViewHolder createDeep(int order, Class<? extends ComponentInfo> presenterClass, ComponentInfo componentInfo, ViewGroup root, boolean force, boolean bind, ActionHandler actionHandler) {
		ComponentViewHolder holder = createView(order, presenterClass, componentInfo, root, actionHandler);
		if (bind) holder.setData(componentInfo, force);
		if (componentInfo instanceof ParentComponentInfo && holder instanceof ComponentGroup) {
			ParentComponentInfo parentComponentInfo = (ParentComponentInfo) componentInfo;
			ViewGroup contentGroup = ((ComponentGroup) holder).getChildArea();
			int childOrder = 0;
			for (final ComponentInfo component : parentComponentInfo) {
				ComponentViewHolder deep = createDeep(childOrder++, component.getClass(), component, contentGroup, force, true, actionHandler);
				holder.addChild(deep);
				contentGroup.addView(deep.itemView);
			}
		}
		return holder;
	}

	protected ComponentViewHolder createView(int order, Class<? extends ComponentInfo> presenterClass, ComponentInfo componentInfo, ViewGroup root, ActionHandler actionHandler) {
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

		ViewInformation viewInformation = new ViewInformation(order);

		// until java 8, reflection
		try {
			Constructor<? extends ComponentViewHolder> constructor = it.viewHolder.getConstructor(ViewInformation.class, ViewDataBinding.class, ActionHandler.class, int.class, int.class);
			return constructor.newInstance(viewInformation, inflate, actionHandler, layoutId, reuseId);
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

	public void bindDeep(ComponentViewHolder holder, ComponentInfo componentInfo, boolean force) {
		if (holder.getReuseId() != componentInfo.deepLayoutHash()) {
			throw new RuntimeException(String.format("Component does not fit the layout, holder = %d, componentInfo = %d", holder.getLayoutId(), componentInfo.layoutHash()));
		}
		holder.setData(componentInfo, force);
		if (componentInfo instanceof ParentComponentInfo && holder instanceof ComponentGroup) {
			ParentComponentInfo parentPresenterOld = (ParentComponentInfo) componentInfo;
			ViewGroup contentGroup = ((ComponentGroup) holder).getChildArea();
			if (holder.getLayoutId() == componentInfo.layoutHash()) {
				List<ComponentViewHolder> children = holder.getChildren();
				for (int i = 0; i < children.size(); i++) {
					bindDeep(children.get(i), parentPresenterOld.get(i), force);
				}
			}
		}
	}

	protected Component getIt(Class<? extends ComponentInfo> presenterClass) {
		Component componentInfo = components.get(presenterClass);
		return componentInfo;
	}

	public <T extends ComponentViewHolder> T create(ComponentInfo componentInfo, ViewGroup root, ActionHandler actionHandler) {
		return (T) createDeep(0, componentInfo.getClass(), componentInfo, root, false, true, actionHandler);
	}

	public <T extends ComponentViewHolder> T createReusable(ComponentInfo componentInfo, ViewGroup root, ActionHandler actionHandler) {
		return (T) createDeep(0, componentInfo.getClass(), componentInfo, root, false, false, actionHandler);
	}

	public <T extends ComponentViewHolder> T createReusable(Class<? extends ComponentInfo> presenterClass, ViewGroup root, ActionHandler actionHandler) {
		return (T) createDeep(0, presenterClass, null, root, false, false, actionHandler);
	}
}
