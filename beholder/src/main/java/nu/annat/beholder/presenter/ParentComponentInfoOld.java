package nu.annat.beholder.presenter;

import java.util.ArrayList;
import java.util.Collection;

public class ParentComponentInfoOld extends ArrayList<ComponentInfo> implements ComponentInfo {

	protected int lastModCount = 0;
	private int lastDeepHash = 0;

	ParentComponentInfoOld() {
		super();
	}

	public ParentComponentInfoOld(Collection<ComponentInfo> children) {
		this();
		for (ComponentInfo child : children) {
			add(child);
		}
	}

	public ParentComponentInfoOld(ComponentInfo... children) {
		this();
		for (ComponentInfo child : children) {
			add(child);
		}
	}

	@Override
	public void add(int index, ComponentInfo componentInfo) {
		if (componentInfo instanceof ExplodingComponentInfo) {
			super.addAll(index, ((ExplodingComponentInfo) componentInfo).explode());
		}
		super.add(index, componentInfo);
	}

	@Override
	public boolean add(ComponentInfo componentInfo) {
		if (componentInfo instanceof ExplodingComponentInfo) {
			return super.addAll(((ExplodingComponentInfo) componentInfo).explode());
		}
		return super.add(componentInfo);
	}

	@Override
	public boolean addAll(Collection<? extends ComponentInfo> c) {
		if (anyExploding(c)) {
			for (ComponentInfo componentInfo : c) {
				add(componentInfo);
			}
		}
		return super.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends ComponentInfo> c) {
		if (anyExploding(c)) {
			for (ComponentInfo componentInfo : c) {
				add(index++, componentInfo);
			}
		}
		return super.addAll(index, c);
	}

	private boolean anyExploding(Collection<? extends ComponentInfo> c) {
		for (ComponentInfo componentInfo : c) {
			if (componentInfo instanceof ExplodingComponentInfo) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int layoutHash() {
		int hash = this.getClass().getName().hashCode();
		return hash;
	}

	@Override
	public int deepLayoutHash() {
		if (lastModCount != modCount) {
			int hash = layoutHash();
			for (ComponentInfo childComponentInfo : this) {
				hash = 31 * hash + childComponentInfo.deepLayoutHash();
			}
			lastModCount = modCount;
			lastDeepHash = hash;
		}
		return lastDeepHash;
	}
}
