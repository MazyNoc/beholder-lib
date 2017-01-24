package nu.annat.beholder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nu.annat.beholder.presenter.ParentPresenterOld;
import nu.annat.beholder.presenter.Presenter;

public class ComponentFactory {

	private static final String TAG = ComponentFactory.class.getSimpleName();
	protected Map<Class<? extends Presenter>, ComponentInfo> components = new HashMap<>();


	public ComponentFactory() {
	}

	public ComponentFactory(ComponentInfo... componentInfos) {
		this();
		registerComponents(componentInfos);
	}

	public ComponentFactory(Collection<ComponentInfo> componentInfos) {
		this();
		registerComponents(componentInfos);
	}

	public void registerComponent(ComponentInfo componentInfo) {
		components.put(componentInfo.presenter, componentInfo);
	}

	public void registerComponents(ComponentInfo... componentInfos) {
		registerComponents(Arrays.asList(componentInfos));
	}

	public void registerComponents(Collection<ComponentInfo> componentInfos) {
		for (ComponentInfo componentInfo : componentInfos) {
			registerComponent(componentInfo);
		}
	}

	public ComponentViewHolder createDeep(int order, Class<? extends Presenter> presenterClass, Presenter presenter, ViewGroup root, boolean force, boolean bind, ActionHandler actionHandler) {
		ComponentViewHolder holder = createView(order, presenterClass, presenter, root, actionHandler);
		if (bind) holder.setData(presenter, force);
		if (presenter instanceof ParentPresenterOld && holder instanceof ComponentGroup) {
			ParentPresenterOld parentPresenterOld = (ParentPresenterOld) presenter;
			ViewGroup contentGroup = ((ComponentGroup) holder).getChildArea();
			int childOrder = 0;
			for (final Presenter component : parentPresenterOld) {
				ComponentViewHolder deep = createDeep(childOrder++, component.getClass(), component, contentGroup, force, true, actionHandler);
				holder.addChild(deep);
				contentGroup.addView(deep.itemView);
			}
		}
		return holder;
	}

	protected ComponentViewHolder createView(int order, Class<? extends Presenter> presenterClass, Presenter presenter, ViewGroup root, ActionHandler actionHandler) {
		ComponentInfo it = getIt(presenterClass);
		int layoutId = presenter.layoutHash();
		int reuseId = presenter.deepLayoutHash();
		if (it == null) throw new RuntimeException("Can't find data for " + presenterClass.getName());
		LayoutInflater inflater = LayoutInflater.from(root.getContext());
		ViewDataBinding inflate = DataBindingUtil.inflate(inflater, it.layout, root, false);
		if (inflate == null) throw new RuntimeException("Can't inflate view for " + presenterClass.getName());

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

	public void bindDeep(ComponentViewHolder holder, Presenter presenter, boolean force) {
		if (holder.getReuseId() != presenter.deepLayoutHash()) {
			throw new RuntimeException(String.format("Presenter does not fit the layout, holder = %d, presenter = %d", holder.getLayoutId(), presenter.layoutHash()));
		}
		holder.setData(presenter, force);
		if (presenter instanceof ParentPresenterOld && holder instanceof ComponentGroup) {
			ParentPresenterOld parentPresenterOld = (ParentPresenterOld) presenter;
			ViewGroup contentGroup = ((ComponentGroup) holder).getChildArea();
			if (holder.getLayoutId() == presenter.layoutHash()) {
				List<ComponentViewHolder> children = holder.getChildren();
				for (int i = 0; i < children.size(); i++) {
					bindDeep(children.get(i), parentPresenterOld.get(i), force);
				}
			}
		}
	}

	protected ComponentInfo getIt(Class<? extends Presenter> presenterClass) {
		ComponentInfo componentInfo = components.get(presenterClass);
		return componentInfo;
	}

	public <T extends ComponentViewHolder> T create(Presenter presenter, ViewGroup root, ActionHandler actionHandler) {
		return (T) createDeep(0, presenter.getClass(), presenter, root, false, true, actionHandler);
	}

	public <T extends ComponentViewHolder> T createReusable(Presenter presenter, ViewGroup root, ActionHandler actionHandler) {
		return (T) createDeep(0, presenter.getClass(), presenter, root, false, false, actionHandler);
	}

	public <T extends ComponentViewHolder> T createReusable(Class<? extends Presenter> presenterClass, ViewGroup root, ActionHandler actionHandler) {
		return (T) createDeep(0, presenterClass, null, root, false, false, actionHandler);
	}

	public static class ComponentInfo {
		public Class<? extends ComponentViewHolder> viewHolder;
		public int layout;
		public Class<? extends Presenter> presenter;

		public ComponentInfo(Class<? extends ComponentViewHolder> viewHolder, int layout, Class<? extends Presenter> presenter) {
			this.layout = layout;
			this.presenter = presenter;
			this.viewHolder = viewHolder;
		}
	}
}
