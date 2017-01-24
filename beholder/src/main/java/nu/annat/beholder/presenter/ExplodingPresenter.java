package nu.annat.beholder.presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ExplodingPresenter extends AbstractParentPresenter {

	protected final ArrayList<Presenter> preparedItems = new ArrayList<>();

	public ExplodingPresenter() {

	}

	public ExplodingPresenter(Collection<Presenter> children) {
		this();
		super.addAll(children);
	}

	public ExplodingPresenter(Presenter... children) {
		this();
		super.addAll(Arrays.asList(children));
	}

	public List<Presenter> explode() {
		preparedItems.clear();
		prepare(subPresenters());
		return preparedItems;
	}

	protected void clearPrepared() {
		preparedItems.clear();
	}

	protected void addPrepared(Presenter presenter) {
		preparedItems.add(presenter);
	}

	protected void prepare(List<Presenter> presenters) {
		for (Presenter presenter : presenters) {
			preparedItems.add(presenter);
		}
	}
}


