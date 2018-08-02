package nu.annat.beholder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.databinding.ViewDataBinding;
import nu.annat.beholder.presenter.ComponentData;
import nu.annat.beholder.presenter.ComponentInfo;

public abstract class ComponentAdapterViewHolder<BINDING extends ViewDataBinding, PRESENTER extends ComponentInfo, ACTION> extends ComponentViewHolder<BINDING, PRESENTER, ACTION> implements ComponentAdapterGroup {

	protected List<ComponentInfo> children = new ArrayList<>();

	public ComponentAdapterViewHolder(ComponentData baseData) {
		super(baseData);
	}

	protected void dataChanged() {
		if (presenter.getAdapter() != null) {
			presenter.getAdapter().notifyDataSetChanged();
		}

	}

	public void addChild(int index, ComponentInfo componentInfo) {
		children.add(index, componentInfo);
		dataChanged();
		onChildAdded(componentInfo);
	}

	public void addChild(ComponentInfo componentInfo) {
		children.add(componentInfo);
		dataChanged();
		onChildAdded(componentInfo);
	}

	protected void onChildAdded(ComponentInfo componentInfo) {
		// not used here
	}

	public void removeAll() {
		children.clear();
		dataChanged();
	}

	public void remove(ComponentInfo componentInfo) {
		children.remove(componentInfo);
		dataChanged();
	}

	public List<ComponentInfo> getChildren() {
		if (BuildConfig.DEBUG) {
			return Collections.unmodifiableList(children);
		} else {
			return children;
		}
	}
}
