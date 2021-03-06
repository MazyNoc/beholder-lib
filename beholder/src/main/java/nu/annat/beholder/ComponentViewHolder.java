package nu.annat.beholder;

import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import nu.annat.beholder.action.ActionHandler;
import nu.annat.beholder.presenter.ComponentData;
import nu.annat.beholder.presenter.ComponentInfo;

public abstract class ComponentViewHolder<BINDING extends ViewDataBinding, PRESENTER extends ComponentInfo, ACTION> extends RecyclerView.ViewHolder {

	protected final BINDING binding;
	protected final ActionHandler actionHandler;
	private final int layoutId;
	private final int reuseId;
	protected LifecycleOwner lifecycleOwner;
	protected ViewInformation viewInformation;
	protected PRESENTER presenter;
	private boolean attached;

	// debug constructor
	private ComponentViewHolder(View view) {
		super(view);
		binding = null;
		actionHandler = null;
		layoutId = 0;
		reuseId = 0;
		viewInformation = null;
	}

	public ComponentViewHolder(ComponentData baseData) {
		super(baseData.binding.getRoot());
		this.layoutId = baseData.layoutId;
		this.actionHandler = baseData.actionHandler;
		this.reuseId = baseData.reuseId;
		//noinspection unchecked
		this.binding = (BINDING) baseData.binding;
		this.viewInformation = baseData.viewInformation;
		this.lifecycleOwner = baseData.lifecycleOwner;
		prepareView();
	}


	public PRESENTER getPresenter() {
		return presenter;
	}

	public void setData(PRESENTER presenter, boolean force) {
		if (this.presenter != null) {
			releasePresenter(this.presenter);
		}
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

	/**
	 * Called every time the data released. can be used to save states and so on.
	 */
	protected void releasePresenter(PRESENTER presenter) {

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

	public void setViewInformation(ViewInformation viewInformation) {
		this.viewInformation = viewInformation;
	}

	public ActionHandler getActionHandler() {
		return actionHandler;
	}

	protected void act(final ACTION action) {
		if (actionHandler != null) {
			actionHandler.handle(action);
		}
	}

	void attached() {
		this.attached = true;
		onAttached();
	}

	protected void onAttached() {

	}


	void detached() {
		this.attached = false;
		onDetached();
	}

	protected void onDetached() {

	}

	public boolean isAttached() {
		return attached;
	}
}
