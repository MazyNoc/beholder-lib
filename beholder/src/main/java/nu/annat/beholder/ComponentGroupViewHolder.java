package nu.annat.beholder;

import android.databinding.ViewDataBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nu.annat.beholder.presenter.Presenter;

public abstract class ComponentGroupViewHolder<BINDING extends ViewDataBinding, PRESENTER extends Presenter> extends ComponentViewHolder<BINDING, PRESENTER> {

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
