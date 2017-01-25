package nu.annat.beholder;

import android.databinding.ViewDataBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nu.annat.beholder.presenter.ComponentInfo;

public abstract class ComponentGroupViewHolder<BINDING extends ViewDataBinding, PRESENTER extends ComponentInfo> extends ComponentViewHolder<BINDING, PRESENTER> {

	private List<ComponentViewHolder> children = new ArrayList<>();

	public ComponentGroupViewHolder(ViewInformation viewInformation, ViewDataBinding binding, ActionHandler actionHandler, int layoutId, int reuseId) {
		super(viewInformation, binding, actionHandler, layoutId, reuseId);
	}

	public List<ComponentViewHolder> getChildren() {
		if (BuildConfig.DEBUG) {
			return Collections.unmodifiableList(children);
		} else {
			return children;
		}
	}
}
