package nu.annat.beholder.presenter;

import android.databinding.BaseObservable;

import java.util.List;

/**
 * Presenter extending from databindings BaseObservable
 */
public class ObservablePresenter extends BaseObservable implements ComponentInfo {
	private ComponentPresenter delegate = new ComponentPresenter(this);

	@Override
	public int layoutHash() {
		return delegate.layoutHash();
	}

	@Override
	public int deepLayoutHash() {
		return delegate.deepLayoutHash();
	}

	@Override
	public List<ComponentInfo> getChildren() {
		return delegate.getChildren();
	}
}
