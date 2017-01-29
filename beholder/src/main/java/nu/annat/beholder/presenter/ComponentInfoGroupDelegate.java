package nu.annat.beholder.presenter;

import java.util.ArrayList;

public class ComponentInfoGroupDelegate extends ArrayList<ComponentInfo> implements ComponentInfoGroup {
	ComponentInfoDelegate delegate = new ComponentInfoDelegate(this);

	private final ComponentInfo parent;

	public ComponentInfoGroupDelegate(ComponentInfo parent) {
		this.parent = parent;
	}

	@Override
	public boolean add(ComponentInfo componentInfo) {
		if (componentInfo instanceof ExplodingComponentInfoGroup) {
			for (ComponentInfo subComponentInfo : ((ExplodingComponentInfoGroup) componentInfo).explode()) {
				add(subComponentInfo);
			}
			return true;
		}
		return super.add(componentInfo);
	}

	@Override
	public ComponentInfo set(int index, ComponentInfo element) {
		if (element instanceof ExplodingComponentInfoGroup) {
			remove(index);
			add(index, element);
			return element;
		} else {
			return super.set(index, element);
		}
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
		for (ComponentInfo childComponentInfo : this) {
			hash = 31 * hash + childComponentInfo.deepLayoutHash();
		}
//			lastModCount = modCount;
//			lastDeepHash = hash;
//		}
//		return lastDeepHash;
		return hash;
	}
}
