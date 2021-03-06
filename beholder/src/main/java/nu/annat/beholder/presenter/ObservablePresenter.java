package nu.annat.beholder.presenter;

import android.os.Bundle;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import androidx.databinding.BaseObservable;
import androidx.recyclerview.widget.RecyclerView;
import nu.annat.beholder.BeholderAdapter;

/**
 * Presenter extending from databindings BaseObservable
 */
public class ObservablePresenter extends BaseObservable implements ComponentInfo {
	private ComponentPresenter delegate = new ComponentPresenter(this);


	@Override
	public int layoutHash() {
		return delegate.layoutHash();
	}

	@Override
	public int deepLayoutHash() {
		return delegate.deepLayoutHash();
	}

	@Override
	public List<ComponentInfo> getChildren() {
		return delegate.getChildren();
	}

	@Override
	public void add(ComponentInfo component) {
		delegate.add(component);
	}

	@Override
	public void addAll(Collection<ComponentInfo> components) {
		delegate.addAll(components);
	}

	@Override
	public ComponentInfo get(int index) {
		return delegate.get(index);
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public Iterator<ComponentInfo> iterator() {
		return delegate.iterator();
	}

	@Override
	public long itemId() {
		return RecyclerView.NO_ID;
	}

	@Override
	public void saveStates(Bundle extras) {

	}

	@Override
	public void restoreStates(Bundle extras) {

	}

	@Override
	public BeholderAdapter<?> getChildAdapter() {
		return delegate.getChildAdapter();
	}

	@Override
	public void setChildAdapter(BeholderAdapter<?> adapter) {
		delegate.setChildAdapter(adapter);
	}
}
