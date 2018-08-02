package nu.annat.example;

import java.util.Arrays;

import nu.annat.beholder.presenter.ComponentInfo;
import nu.annat.beholder.presenter.ComponentPresenter;

public class CardData extends ComponentPresenter {
	public CardData(ComponentInfo... info) {
		getChildren().addAll(Arrays.asList(info));
	}
}
