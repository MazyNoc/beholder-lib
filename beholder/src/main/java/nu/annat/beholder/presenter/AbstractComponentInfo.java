package nu.annat.beholder.presenter;

public class AbstractComponentInfo implements ComponentInfo {
	private final ComponentInfoDelegate delegate;

	public AbstractComponentInfo() {
		delegate = new ComponentInfoDelegate(this);
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
