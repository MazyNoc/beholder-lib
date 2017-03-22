package nu.annat.example;

import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import nu.annat.beholder.ComponentGroupViewHolder;
import nu.annat.beholder.ViewInformation;
import nu.annat.beholder.action.ActionHandler;
import nu.annat.example.databinding.CardBinding;

public class CardComponent extends ComponentGroupViewHolder<CardBinding, CardData, Object> {

	public CardComponent(ViewInformation viewInformation, ViewDataBinding binding, ActionHandler actionHandler, int layoutId, int reuseId) {
		super(viewInformation, binding, actionHandler, layoutId, reuseId);
	}

	@Override
	protected void prepareView() {
		binding.setViewInfo(viewInformation);
	}

	@Override
	public ViewGroup getChildArea() {
		return binding.childArea;
	}
}
