package nu.annat.example;

import java.util.Arrays;

import nu.annat.beholder.presenter.AbstractParentComponentInfo;
import nu.annat.beholder.presenter.ComponentInfo;

public class CardData extends AbstractParentComponentInfo {
	public CardData(ComponentInfo... info) {
		addAll(Arrays.asList(info));
	}
}
