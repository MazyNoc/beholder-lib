package nu.annat.beholder;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nu.annat.beholder.presenter.Presenter;

public abstract class ComponentViewHolder<BINDING extends ViewDataBinding, PRESENTER extends Presenter> extends RecyclerView.ViewHolder {

	protected final BINDING binding;
	protected final ActionHandler actionHandler;
	private final int layoutId;
	private final int reuseId;
	protected final ViewInformation viewInformation;
	private PRESENTER PRESENTER;
	private List<ComponentViewHolder> children;

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

	private void ensureChildren() {
		children = children != null ? children : new ArrayList<ComponentViewHolder>();
	}

	public void addChild(ComponentViewHolder viewHolder) {
		ensureChildren();
		children.add(viewHolder);
	}

	public List<ComponentViewHolder> getChildren() {
		if (BuildConfig.DEBUG) {
			return Collections.unmodifiableList(children);
		} else {
			return children;
		}
	}

	protected void act(final Action action) {
		if(actionHandler!=null){
			actionHandler.executeAction(action);
		}
	}

}
