package nu.annat.beholder;

import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import nu.annat.beholder.action.ActionHandler;
import nu.annat.beholder.presenter.ComponentInfo;

/**
 * Helper to use components directly as RecycleView item objects.
 */
public class BeholderAdapter< T extends ComponentInfo> extends RecyclerView.Adapter<ComponentViewHolder> {
	private static final String TAG = BeholderAdapter.class.getSimpleName();

	protected final ActionHandler actionHandler;
	protected final List<T> data;
	protected final LifecycleOwner lifecycleOwner;
	protected final int baseDepth;
	protected ComponentFactory factory;
	protected SparseArray<ComponentInfo> cachedPresenters = new SparseArray<>();

	public BeholderAdapter(ComponentFactory factory, List<T> data, ActionHandler actionHandler) {
		this(factory, 0, data, actionHandler, null, false);
	}

	public BeholderAdapter(ComponentFactory factory, List<T> data, ActionHandler actionHandler, boolean hasStableIds) {
		this(factory, 0, data, actionHandler, null, hasStableIds);
	}

	public BeholderAdapter(ComponentFactory factory, int baseDepth, List<T> data, ActionHandler actionHandler, LifecycleOwner lifecycleOwner, boolean hasStableIds) {
		this.factory = factory;
		this.data = data;
		this.actionHandler = actionHandler;
		this.baseDepth = baseDepth;
		this.lifecycleOwner = lifecycleOwner;
		setHasStableIds(hasStableIds);
	}

	@Override
	public ComponentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return factory.createReusable(cachedPresenters.get(viewType), this.baseDepth, parent, lifecycleOwner, actionHandler);
	}

	@Override
	public int getItemViewType(int position) {
		ComponentInfo componentInfo = getItem(position);
		int deepLayoutHash = componentInfo.deepLayoutHash();
		cachedPresenters.put(deepLayoutHash, componentInfo);
		return deepLayoutHash;
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).itemId();
	}

	protected ComponentInfo getItem(int position) {
		return data.get(position);
	}

	public List<T> getData() {
		return data;
	}

	@Override
	public void onBindViewHolder(ComponentViewHolder holder, int position) {
		factory.bindDeep(holder, position, getItem(position), true);
	}

	@Override
	public void onViewAttachedToWindow(ComponentViewHolder holder) {
		super.onViewAttachedToWindow(holder);
		holder.attached();
	}

	@Override
	public void onViewDetachedFromWindow(ComponentViewHolder holder) {
		super.onViewDetachedFromWindow(holder);
		holder.detached();
	}

	@Override
	public int getItemCount() {
		return data == null ? 0 : data.size();
	}

	public void close() {
		factory = null;
	}
}
