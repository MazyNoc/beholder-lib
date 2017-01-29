package nu.annat.beholder.presenter;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AbstractComponentInfoGroup  implements ComponentInfoGroup, List<ComponentInfo> {

	ComponentInfoGroupDelegate delegate = new ComponentInfoGroupDelegate(this);

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return delegate.contains(o);
	}

	@NonNull
	@Override
	public Iterator<ComponentInfo> iterator() {
		return delegate.iterator();
	}

	@NonNull
	@Override
	public Object[] toArray() {
		return delegate.toArray();
	}

	@NonNull
	@Override
	public <T> T[] toArray(T[] a) {
		return delegate.toArray(a);
	}

	@Override
	public boolean add(ComponentInfo componentInfo) {
		return delegate.add(componentInfo);
	}

	@Override
	public boolean remove(Object o) {
		return delegate.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return delegate.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends ComponentInfo> c) {
		return delegate.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends ComponentInfo> c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
	}

	@Override
	public void clear() {

	}

	@Override
	public ComponentInfo get(int index) {
		return delegate.get(index);
	}

	@Override
	public ComponentInfo set(int index, ComponentInfo element) {
		return null;
	}

	@Override
	public void add(int index, ComponentInfo element) {

	}

	@Override
	public ComponentInfo remove(int index) {
		return null;
	}

	@Override
	public int indexOf(Object o) {
		return 0;
	}

	@Override
	public int lastIndexOf(Object o) {
		return 0;
	}

	@Override
	public ListIterator<ComponentInfo> listIterator() {
		return null;
	}

	@NonNull
	@Override
	public ListIterator<ComponentInfo> listIterator(int index) {
		return null;
	}

	@NonNull
	@Override
	public List<ComponentInfo> subList(int fromIndex, int toIndex) {
		return null;
	}

	@Override
	public int layoutHash() {
		return delegate.layoutHash();
	}

	@Override
	public int deepLayoutHash() {
		return delegate.deepLayoutHash();
	}
}
