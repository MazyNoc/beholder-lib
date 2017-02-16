package nu.annat.example;

import java.util.ArrayList;
import java.util.Collection;

import nu.annat.beholder.presenter.ComponentPresenter;


public class CardWrapper extends ArrayList<ComponentPresenter> {

	@Override
	public int size() {
		return super.size()+2;
	}

//	@Override
	public Collection<ComponentPresenter> get() {
		ArrayList<ComponentPresenter> list = new ArrayList<>();
		list.add(new CardData());
		for (ComponentPresenter componentInfo : this) {
			list.add(new CardData(componentInfo));
		}
		list.add(new CardData());
		return list;
	}
}
