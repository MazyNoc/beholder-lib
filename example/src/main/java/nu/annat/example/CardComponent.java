package nu.annat.example;

import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import nu.annat.beholder.ActionHandler;
import nu.annat.beholder.ComponentGroup;
import nu.annat.beholder.ComponentViewHolder;
import nu.annat.beholder.ViewInformation;
import nu.annat.example.databinding.CardBinding;

public class CardComponent extends ComponentViewHolder<CardBinding, CardData> implements ComponentGroup {

	public CardComponent(ViewInformation viewInformation, ViewDataBinding binding, ActionHandler actionHandler, int layoutId, int reuseId) {
		super(viewInformation, binding, actionHandler, layoutId, reuseId);
	}

	@Override
	protected void prepareBinding(CardBinding binding) {

	}

	@Override
	protected void updateBindings(CardBinding binding, CardData cardData) {

	}


	@Override
	public ViewGroup getChildArea() {
		return binding.childArea;
	}
}
