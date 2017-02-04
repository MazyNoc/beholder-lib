package nu.annat.example;

import android.databinding.ViewDataBinding;

import nu.annat.beholder.action.ActionHandler;
import nu.annat.beholder.ComponentViewHolder;
import nu.annat.beholder.ViewInformation;
import nu.annat.example.databinding.SingleLineLayoutBinding;

public class SingleLineComponent extends ComponentViewHolder<SingleLineLayoutBinding, SingleLineData> {

	public SingleLineComponent(ViewInformation viewInformation, ViewDataBinding binding, ActionHandler actionHandler, int layoutId, int reuseId) {
		super(viewInformation, binding, actionHandler, layoutId, reuseId);
	}

	@Override
	protected void prepareBinding(SingleLineLayoutBinding binding) {

	}

	@Override
	protected void updateBindings(SingleLineLayoutBinding binding, SingleLineData presenter) {
		binding.setPresenter(presenter);
	}
}
