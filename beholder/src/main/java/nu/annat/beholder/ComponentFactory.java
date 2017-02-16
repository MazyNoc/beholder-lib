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

import nu.annat.beholder.action.ActionHandler;
import nu.annat.beholder.presenter.ComponentInfo;

public class ComponentFactory {

	private static final String TAG = ComponentFactory.class.getSimpleName();
	protected Map<Class<? extends ComponentInfo>, Component> components = new HashMap<>();

	public static class Component {
		public Class<? extends ComponentViewHolder> viewHolder;
		public int layout;
		public Class<? extends ComponentInfo> presenter;

		public Component(Class<? extends ComponentInfo> presenter, Class<? extends ComponentViewHolder> viewHolder, int layout) {
			this.layout = layout;
			this.presenter = presenter;
			this.viewHolder = viewHolder;
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

	public ComponentViewHolder createDeep(int depth, int order, Class<? extends ComponentInfo> presenterClass, ComponentInfo componentInfo, ViewGroup root, boolean force, boolean bind, ActionHandler actionHandler) {
		ComponentViewHolder holder = createView(depth, order, presenterClass, componentInfo, root, actionHandler);
		if (bind) holder.setData(componentInfo, force);
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
}
