package nu.annat.beholder.presenter;

import java.util.ArrayList;

import nu.annat.beholder.ComponentFactory;

/**
 * Even if most presenters don't have children, the Android implementation of an empty
 * arrayList is so efficient that we don't have to separate non parents from parents.
 */
public class ComponentPresenter extends ArrayList<ComponentInfo> implements ComponentInfo {

	private final ComponentInfo base;

	public ComponentPresenter() {
		super();
		this.base = this;
	}

	public ComponentPresenter(ComponentInfo impersonate){
		this.base = impersonate;
	}

	@Override
	public int layoutHash() {
		return base.getClass().getCanonicalName().hashCode();
	}

	@Override
	public int deepLayoutHash() {
		int hash = layoutHash();
		if (!isEmpty()) {
			for (ComponentInfo childComponentInfo : this) {
				hash = 31 * hash + childComponentInfo.deepLayoutHash();
			}
		}
		return hash;
	}
}
