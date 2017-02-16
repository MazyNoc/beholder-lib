package nu.annat.example;

import java.util.Arrays;

import nu.annat.beholder.presenter.ComponentPresenter;

public class CardData extends ComponentPresenter {
	public CardData(ComponentPresenter... info) {
		getChildren().addAll(Arrays.asList(info));
	}
}
