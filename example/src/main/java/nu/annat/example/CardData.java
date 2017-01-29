package nu.annat.example;

import java.util.Arrays;

import nu.annat.beholder.presenter.AbstractComponentInfoGroup;
import nu.annat.beholder.presenter.ComponentInfo;

public class CardData extends AbstractComponentInfoGroup {
	public CardData(ComponentInfo... info) {
		addAll(Arrays.asList(info));
	}
}
