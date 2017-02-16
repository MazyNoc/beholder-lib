package nu.annat.example;

import nu.annat.beholder.presenter.ComponentPresenter;

public class DualLineData extends ComponentPresenter {
	private String header;
	private String body;

	public DualLineData(String header, String body) {
		this.header = header;
		this.body = body;
	}

	public String getHeader() {
		return header;
	}

	public String getBody() {
		return body;
	}
}
