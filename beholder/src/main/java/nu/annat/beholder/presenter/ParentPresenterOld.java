package nu.annat.beholder.presenter;

import java.util.ArrayList;
import java.util.Collection;

public class ParentPresenterOld extends ArrayList<Presenter> implements Presenter {

	protected int lastModCount = 0;
	private int lastDeepHash = 0;

	ParentPresenterOld() {
		super();
	}

	public ParentPresenterOld(Collection<Presenter> children) {
		this();
		for (Presenter child : children) {
			add(child);
		}
	}

	public ParentPresenterOld(Presenter... children) {
		this();
		for (Presenter child : children) {
			add(child);
		}
	}

	@Override
	public void add(int index, Presenter presenter) {
		if (presenter instanceof ExplodingPresenter) {
			super.addAll(index, ((ExplodingPresenter) presenter).explode());
		}
		super.add(index, presenter);
	}

	@Override
	public boolean add(Presenter presenter) {
		if (presenter instanceof ExplodingPresenter) {
			return super.addAll(((ExplodingPresenter) presenter).explode());
		}
		return super.add(presenter);
	}

	@Override
	public boolean addAll(Collection<? extends Presenter> c) {
		if (anyExploding(c)) {
			for (Presenter presenter : c) {
				add(presenter);
			}
		}
		return super.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Presenter> c) {
		if (anyExploding(c)) {
			for (Presenter presenter : c) {
				add(index++, presenter);
			}
		}
		return super.addAll(index, c);
	}

	private boolean anyExploding(Collection<? extends Presenter> c) {
		for (Presenter presenter : c) {
			if (presenter instanceof ExplodingPresenter) {
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
			for (Presenter childPresenter : this) {
				hash = 31 * hash + childPresenter.deepLayoutHash();
			}
			lastModCount = modCount;
			lastDeepHash = hash;
		}
		return lastDeepHash;
	}
}
