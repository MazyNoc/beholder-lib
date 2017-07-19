package nu.annat.example;

import android.databinding.ViewDataBinding;

import nu.annat.beholder.ComponentViewHolder;
import nu.annat.beholder.ViewInformation;
import nu.annat.beholder.action.ActionHandler;
import nu.annat.beholder.presenter.ComponentData;
import nu.annat.example.databinding.SingleLineLayoutBinding;

public class SingleLineComponent extends ComponentViewHolder<SingleLineLayoutBinding, SingleLineData, Object> {


	public SingleLineComponent(ComponentData baseData) {
		super(baseData);
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
