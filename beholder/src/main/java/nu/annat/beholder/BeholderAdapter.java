package nu.annat.beholder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.List;

import nu.annat.beholder.presenter.Presenter;

public class BeholderAdapter extends RecyclerView.Adapter<ComponentViewHolder> {
	private static final String TAG = BeholderAdapter.class.getSimpleName();

	private final ActionHandler actionHandler;
	private final List<Presenter> items;
	private ComponentFactory factory;
	private SparseArray<Presenter> cachedPresenters = new SparseArray<>();

	public BeholderAdapter(ComponentFactory factory, List<Presenter> items, ActionHandler actionHandler) {
		this.factory = factory;
		this.items = items;
		this.actionHandler = actionHandler;
	}

	@Override
	public ComponentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return factory.createReusable(cachedPresenters.get(viewType), parent, actionHandler);
	}

	@Override
	public int getItemViewType(int position) {
		Presenter presenter = items.get(position);
		int deepLayoutHash = presenter.deepLayoutHash();
		cachedPresenters.put(deepLayoutHash, presenter);
		return deepLayoutHash;
	}

	@Override
	public void onBindViewHolder(ComponentViewHolder holder, int position) {
		factory.bindDeep(holder, items.get(position), true);
	}

	@Override
	public int getItemCount() {
		return items == null ? 0 : items.size();
	}

	public void close() {
		factory = null;
	}
}
