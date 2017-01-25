package nu.annat.beholder.presenter;

public class ComponentInfoDelegate implements ComponentInfo {

	private final Object parent;

	public ComponentInfoDelegate(Object parent) {
		this.parent = parent;
	}

	@Override
	public int layoutHash() {
		int hash = parent.getClass().getName().hashCode();
		return hash;
	}

	@Override
	public int deepLayoutHash() {
		int hash = layoutHash();
		return hash;
	}
}
