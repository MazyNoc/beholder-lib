package nu.annat.beholder.presenter;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import nu.annat.beholder.ViewInformation;
import nu.annat.beholder.action.ActionHandler;

public class ComponentData {
	public ViewInformation viewInformation;
	public ViewDataBinding binding;
	public LifecycleOwner lifecycleOwner;
	public ActionHandler actionHandler;
	public int layoutId;
	public int reuseId;

//	public ComponentData(ViewInformation viewInformation, ViewDataBinding binding, ActionHandler actionHandler, int layoutId, int reuseId) {
//		this.viewInformation = viewInformation;
//		this.binding = binding;
//		this.actionHandler = actionHandler;
//		this.layoutId = layoutId;
//		this.reuseId = reuseId;
//	}

	public ComponentData(ViewInformation viewInformation, ViewDataBinding binding, LifecycleOwner lifecycleOwner, ActionHandler actionHandler, int layoutId, int reuseId) {
		this.viewInformation = viewInformation;
		this.binding = binding;
		this.lifecycleOwner = lifecycleOwner;
		this.actionHandler = actionHandler;
		this.layoutId = layoutId;
		this.reuseId = reuseId;
	}
}
