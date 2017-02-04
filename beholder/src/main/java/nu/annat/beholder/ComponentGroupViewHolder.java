package nu.annat.beholder;

import android.databinding.ViewDataBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nu.annat.beholder.action.ActionHandler;
import nu.annat.beholder.presenter.ComponentInfo;

public abstract class ComponentGroupViewHolder<BINDING extends ViewDataBinding, PRESENTER extends ComponentInfo> extends ComponentViewHolder<BINDING, PRESENTER> implements ComponentGroup {

	protected List<ComponentViewHolder> children = new ArrayList<>();

	public ComponentGroupViewHolder(ViewInformation viewInformation, ViewDataBinding binding, ActionHandler actionHandler, int layoutId, int reuseId) {
		super(viewInformation, binding, actionHandler, layoutId, reuseId);
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

	public List<ComponentViewHolder> getChildren() {
		if (BuildConfig.DEBUG) {
			return Collections.unmodifiableList(children);
		} else {
			return children;
		}
	}
}
