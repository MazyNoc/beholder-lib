package nu.annat.beholder.presenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ParentPresenterDelegate implements ParentPresenter {
	PresenterDelegate delegate = new PresenterDelegate(this);
	List<Presenter> subPresenters = new ArrayList<>();

	private final Object parent;

	public ParentPresenterDelegate(Object parent) {
		this.parent = parent;
	}

	@Override
	public boolean add(Presenter presenter) {
		if (presenter instanceof ExplodingPresenter) {
			for (Presenter subPresenter : ((ExplodingPresenter) presenter).explode()) {
				subPresenters.add(subPresenter);
			}
			return true;
		}
		return subPresenters.add(presenter);
	}

	@Override
	public boolean addAll(Collection<Presenter> presenters) {
		for (Presenter presenter : presenters) {
			add(presenter);
		}
		return true;
	}

	@Override
	public List<Presenter> subPresenters() {
		return subPresenters;
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
		for (Presenter childPresenter : subPresenters) {
			hash = 31 * hash + childPresenter.deepLayoutHash();
		}
//			lastModCount = modCount;
//			lastDeepHash = hash;
//		}
//		return lastDeepHash;
		return hash;
	}
}
