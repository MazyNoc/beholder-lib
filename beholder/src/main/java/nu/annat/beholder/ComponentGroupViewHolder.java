package nu.annat.beholder;

import android.databinding.ViewDataBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nu.annat.beholder.action.ActionHandler;
import nu.annat.beholder.presenter.ComponentData;
import nu.annat.beholder.presenter.ComponentInfo;

public abstract class ComponentGroupViewHolder<BINDING extends ViewDataBinding, PRESENTER extends ComponentInfo, ACTION> extends ComponentViewHolder<BINDING, PRESENTER, ACTION> implements ComponentGroup {

	protected List<ComponentViewHolder> children = new ArrayList<>();

	public ComponentGroupViewHolder(ComponentData baseData) {
		super(baseData);
	}

	public void addChild(int index, ComponentViewHolder componentViewHolder) {
		children.add(index, componentViewHolder);
		getChildArea().addView(componentViewHolder.itemView, index);
		onChildAdded(componentViewHolder);
	}

	public void addChild(ComponentViewHolder componentViewHolder) {
		children.add(componentViewHolder);
		getChildArea().addView(componentViewHolder.itemView);
		onChildAdded(componentViewHolder);
	}

	protected void onChildAdded(ComponentViewHolder componentViewHolder){
		// not used here
	}

	public void removeAll() {
		getChildArea().removeAllViews();
		children.clear();
	}

	public void remove(ComponentViewHolder holder) {
		getChildArea().removeView(holder.itemView);
		children.remove(holder);
	}

	public List<ComponentViewHolder> getChildren() {
		if (BuildConfig.DEBUG) {
			return Collections.unmodifiableList(children);
		} else {
			return children;
		}
	}
}
