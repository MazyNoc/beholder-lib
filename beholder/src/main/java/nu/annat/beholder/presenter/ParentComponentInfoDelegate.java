package nu.annat.beholder.presenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ParentComponentInfoDelegate implements ParentComponentInfo {
	ComponentInfoDelegate delegate = new ComponentInfoDelegate(this);
	List<ComponentInfo> subComponentInfos = new ArrayList<>();

	private final Object parent;

	public ParentComponentInfoDelegate(Object parent) {
		this.parent = parent;
	}

	@Override
	public boolean add(ComponentInfo componentInfo) {
		if (componentInfo instanceof ExplodingComponentInfo) {
			for (ComponentInfo subComponentInfo : ((ExplodingComponentInfo) componentInfo).explode()) {
				subComponentInfos.add(subComponentInfo);
			}
			return true;
		}
		return subComponentInfos.add(componentInfo);
	}

	@Override
	public boolean addAll(Collection<ComponentInfo> componentInfos) {
		for (ComponentInfo componentInfo : componentInfos) {
			add(componentInfo);
		}
		return true;
	}

	@Override
	public List<ComponentInfo> subPresenters() {
		return subComponentInfos;
	}

	@Override
	public int layoutHash() {
		int hash = parent.getClass().getName().hashCode();
		return hash;
	}

	@Override
	public int deepLayoutHash() {
		//if (lastModCount != modCount) {
		int hash = layoutHash();
		for (ComponentInfo childComponentInfo : subComponentInfos) {
			hash = 31 * hash + childComponentInfo.deepLayoutHash();
		}
//			lastModCount = modCount;
//			lastDeepHash = hash;
//		}
//		return lastDeepHash;
		return hash;
	}
}
