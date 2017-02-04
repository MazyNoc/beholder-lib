package nu.annat.example;

import android.databinding.ViewDataBinding;
import android.view.View;

import nu.annat.beholder.action.ActionHandler;
import nu.annat.beholder.ComponentViewHolder;
import nu.annat.beholder.ViewInformation;
import nu.annat.example.databinding.DualLineLayoutBinding;

public class DualLineComponent extends ComponentViewHolder<DualLineLayoutBinding, DualLineData> {


	public DualLineComponent(ViewInformation viewInformation, ViewDataBinding binding, ActionHandler actionHandler, int layoutId, int reuseId) {
		super(viewInformation, binding, actionHandler, layoutId, reuseId);
	}

	@Override
	protected void prepareBinding(DualLineLayoutBinding binding) {
		binding.setHandler(this);
	}

	@Override
	protected void updateBindings(DualLineLayoutBinding binding, DualLineData presenter) {
		binding.setPresenter(presenter);
	}

	public void onCardClick(View view){
		actionHandler.handle(new ToastAction("Message"));
	}
}
