package nu.annat.beholder.presenter;

import android.databinding.ViewDataBinding;

import nu.annat.beholder.ViewInformation;
import nu.annat.beholder.action.ActionHandler;

public class ComponentData {
	public ViewInformation viewInformation;
	public ViewDataBinding binding;
	public ActionHandler actionHandler;
	public int layoutId;
	public int reuseId;

	public ComponentData(ViewInformation viewInformation, ViewDataBinding binding, ActionHandler actionHandler, int layoutId, int reuseId) {
		this.viewInformation = viewInformation;
		this.binding = binding;
		this.actionHandler = actionHandler;
		this.layoutId = layoutId;
		this.reuseId = reuseId;
	}
}
