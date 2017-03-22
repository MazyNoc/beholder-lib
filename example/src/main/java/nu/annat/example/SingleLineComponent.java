package nu.annat.example;

import android.databinding.ViewDataBinding;

import nu.annat.beholder.ComponentViewHolder;
import nu.annat.beholder.ViewInformation;
import nu.annat.beholder.action.ActionHandler;
import nu.annat.example.databinding.SingleLineLayoutBinding;

public class SingleLineComponent extends ComponentViewHolder<SingleLineLayoutBinding, SingleLineData, Object> {

	public SingleLineComponent(ViewInformation viewInformation, ViewDataBinding binding, ActionHandler actionHandler, int layoutId, int reuseId) {
		super(viewInformation, binding, actionHandler, layoutId, reuseId);
	}

	@Override
	protected void prepareView() {
		binding.setViewInfo(viewInformation);
	}

	@Override
	protected void prepareData() {
		binding.setPresenter(presenter);
	}
}
