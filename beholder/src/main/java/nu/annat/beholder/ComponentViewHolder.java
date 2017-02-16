package nu.annat.beholder;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import nu.annat.beholder.action.ActionHandler;
import nu.annat.beholder.presenter.ComponentInfo;

public abstract class ComponentViewHolder<BINDING extends ViewDataBinding, PRESENTER extends ComponentInfo> extends RecyclerView.ViewHolder {

	protected final BINDING binding;
	protected final ActionHandler actionHandler;
	protected final ViewInformation viewInformation;
	private final int layoutId;
	private final int reuseId;
	private PRESENTER PRESENTER;

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
		this.binding = (BINDING) binding;
		this.viewInformation = viewInformation;
		prepareBinding(this.binding);
	}

	/**
	 * Called once when the view is created. use it to bind view specific things like action callbacks and @link {@link ViewInformation}
	 * @param binding
	 */
	protected abstract void prepareBinding(final BINDING binding);

	public PRESENTER getPresenter() {
		return PRESENTER;
	}

	public void setData(PRESENTER PRESENTER, boolean force) {
		this.PRESENTER = PRESENTER;
		updateBindings(binding, PRESENTER);
		if (force) {
			binding.executePendingBindings();
		}
	}

	protected abstract void updateBindings(BINDING binding, final PRESENTER presenter);

	public int getLayoutId() {
		return layoutId;
	}

	public int getReuseId() {
		return reuseId;
	}

	protected void act(final Object action) {
		if (actionHandler != null) {
			actionHandler.handle(action);
		}
	}

}
