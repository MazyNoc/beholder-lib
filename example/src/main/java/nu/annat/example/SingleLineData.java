package nu.annat.example;

import nu.annat.beholder.presenter.ComponentPresenter;

public class SingleLineData extends ComponentPresenter {
	private String text;

	public SingleLineData(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
