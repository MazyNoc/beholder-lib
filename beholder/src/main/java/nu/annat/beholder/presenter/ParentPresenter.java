package nu.annat.beholder.presenter;

import java.util.Collection;
import java.util.List;

public interface ParentPresenter extends Presenter {
	boolean add(Presenter presenter);
	boolean addAll(Collection<Presenter> presenters);
	List<Presenter> subPresenters();
}
