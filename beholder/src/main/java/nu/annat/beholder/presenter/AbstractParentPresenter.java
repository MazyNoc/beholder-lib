package nu.annat.beholder.presenter;

import java.util.Collection;
import java.util.List;

public class AbstractParentPresenter implements ParentPresenter {

	ParentPresenterDelegate delegate = new ParentPresenterDelegate(this);

	@Override
	public boolean add(Presenter presenter) {
		return delegate.add(presenter);
	}

	@Override
	public boolean addAll(Collection<Presenter> presenters) {
		return delegate.addAll(presenters);
	}

	@Override
	public List<Presenter> subPresenters() {
		return delegate.subPresenters;
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
