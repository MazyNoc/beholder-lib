package nu.annat.example;

import android.view.View;

import nu.annat.beholder.ComponentViewHolder;
import nu.annat.beholder.presenter.ComponentData;
import nu.annat.example.databinding.DualLineLayoutBinding;

public class DualLineComponent extends ComponentViewHolder<DualLineLayoutBinding, DualLineData, Object> {

	public DualLineComponent(ComponentData baseData) {
		super(baseData);
	}

	@Override
	protected void prepareView() {
		binding.setHandler(this);
	}

	@Override
	protected void prepareData() {
		binding.setViewInfo(viewInformation);
		binding.setPresenter(presenter);
	}

	public void onCardClick(View view) {
		act(new ToastAction(getPresenter().getHeader(), presenter));
	}
}
