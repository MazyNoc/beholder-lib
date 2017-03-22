package nu.annat.beholder;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import nu.annat.beholder.action.ActionHandler;
import nu.annat.beholder.presenter.ComponentInfo;

public abstract class ComponentViewHolder<BINDING extends ViewDataBinding, PRESENTER extends ComponentInfo, ACTION> extends RecyclerView.ViewHolder {

	protected final BINDING binding;
	protected final ActionHandler actionHandler;
	protected final ViewInformation viewInformation;
	private final int layoutId;
	private final int reuseId;
	protected PRESENTER presenter;

	// debug constructor
	private ComponentViewHolder(View view) {
		super(view);
		binding = null;
		actionHandler = null;
		layoutId = 0;
		reuseId = 0;
		viewInformation = null;
	}

	public ComponentViewHolder(ViewInformation viewInformation, final ViewDataBinding binding, ActionHandler actionHandler, int layoutId, int reuseId) {
		super(binding.getRoot());
		this.layoutId = layoutId;
		this.actionHandler = actionHandler;
		this.reuseId = reuseId;
		//noinspection unchecked
		this.binding = (BINDING) binding;
		this.viewInformation = viewInformation;
		prepareView();
	}


	public PRESENTER getPresenter() {
		return presenter;
	}

	public void setData(PRESENTER presenter, boolean force) {
		this.presenter = presenter;
		prepareData();
		if (force) {
			binding.executePendingBindings();
		}
	}

	/**
	 * Called once when the view is created. use it to bind view specific things like action callbacks and @link {@link ViewInformation}
	 */
	protected void prepareView() {
	}

	/**
	 * Called every time the data is set. use it to bind data from the presenter to the view
	 */
	protected void prepareData() {
	}

	public int getLayoutId() {
		return layoutId;
	}

	public int getReuseId() {
		return reuseId;
	}

	public ViewInformation getViewInformation() {
		return viewInformation;
	}

	public ActionHandler getActionHandler() {
		return actionHandler;
	}

	protected void act(final ACTION action) {
		if (actionHandler != null) {
			actionHandler.handle(action);
		}
	}

}
