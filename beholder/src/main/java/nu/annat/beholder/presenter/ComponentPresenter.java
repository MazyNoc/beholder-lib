package nu.annat.beholder.presenter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Even if most presenters don't have children, the Android implementation of an empty
 * arrayList is so efficient that we don't have to separate non parents from parents.
 */
public class ComponentPresenter implements ComponentInfo {

	private final ComponentInfo base;
	private final List<ComponentInfo> children = new ArrayList<>();

	public ComponentPresenter() {
		super();
		this.base = this;
	}

	/**
	 * Convenience constructor when its used as a delegate class (see {@link ObservablePresenter}
	 * @param impersonate, the instance that uses this as a delegate class. Is used for the hash-code calculations
	 */
	public ComponentPresenter(ComponentInfo impersonate) {
		super();
		this.base = impersonate;
	}

	@Override
	public int layoutHash() {
		return base.getClass().getCanonicalName().hashCode();
	}

	@Override
	public int deepLayoutHash() {
		int hash = layoutHash();
		if (!children.isEmpty()) {
			for (ComponentInfo childComponentInfo : children) {
				hash = 31 * hash + childComponentInfo.deepLayoutHash();
			}
		}
		return hash;
	}

	public List<ComponentInfo> getChildren() {
		return children;
	}

	@Override
	public void add(ComponentInfo component) {
		children.add(component);
	}

	@Override
	public void addAll(Collection<ComponentInfo> components) {
		children.addAll(components);
	}

	@Override
	public ComponentInfo get(int index) {
		return children.get(index);
	}

	@Override
	public int size() {
		return children.size();
	}

	@Override
	public Iterator<ComponentInfo> iterator() {
		return children.iterator();
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
}
