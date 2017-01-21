package nu.annat.beholder.presenter;

public class AbstractPresenter implements Presenter {
	private final PresenterDelegate delegate;

	public AbstractPresenter() {
		delegate = new PresenterDelegate(this);
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
