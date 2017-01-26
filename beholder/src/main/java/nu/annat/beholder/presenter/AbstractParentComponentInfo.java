package nu.annat.beholder.presenter;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class AbstractParentComponentInfo implements ParentComponentInfo {

	ParentComponentInfoDelegate delegate = new ParentComponentInfoDelegate(this);

	@Override
	public boolean add(ComponentInfo componentInfo) {
		return delegate.add(componentInfo);
	}

	@Override
	public boolean addAll(Collection<ComponentInfo> componentInfos) {
		return delegate.addAll(componentInfos);
	}

	@Override
	public ComponentInfo get(int index) {
		return delegate.get(index);
	}

	@Override
	public Iterator<ComponentInfo> iterator() {
		return delegate.iterator();
	}

	@Override
	public List<ComponentInfo> subPresenters() {
		return delegate.subComponentInfos;
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
