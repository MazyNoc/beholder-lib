package nu.annat.beholder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.List;

import nu.annat.beholder.action.ActionHandler;
import nu.annat.beholder.presenter.ComponentInfo;

/**
 * Helper to use components directly as RecycleView item objects.
 *
 *
 *
 */
public class BeholderAdapter extends RecyclerView.Adapter<ComponentViewHolder> {
	private static final String TAG = BeholderAdapter.class.getSimpleName();

	private final ActionHandler actionHandler;
	private final List<ComponentInfo> data;
	private ComponentFactory factory;
	private SparseArray<ComponentInfo> cachedPresenters = new SparseArray<>();

	public BeholderAdapter(ComponentFactory factory, List<ComponentInfo> data, ActionHandler actionHandler) {
		this.factory = factory;
		this.data = data;
		this.actionHandler = actionHandler;
	}

	@Override
	public ComponentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return factory.createReusable(cachedPresenters.get(viewType), parent, actionHandler);
	}

	@Override
	public int getItemViewType(int position) {
		ComponentInfo componentInfo = getItem(position);
		int deepLayoutHash = componentInfo.deepLayoutHash();
		cachedPresenters.put(deepLayoutHash, componentInfo);
		return deepLayoutHash;
	}

	protected ComponentInfo getItem(int position) {
		return data.get(position);
	}

	public List<ComponentInfo> getData() {
		return data;
	}

	@Override
	public void onBindViewHolder(ComponentViewHolder holder, int position) {
		factory.bindDeep(holder, getItem(position), true);
	}

	@Override
	public int getItemCount() {
		return data == null ? 0 : data.size();
	}

	public void close() {
		factory = null;
	}
}
