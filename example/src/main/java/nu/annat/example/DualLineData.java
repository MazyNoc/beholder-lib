package nu.annat.example;

import nu.annat.beholder.jsonconverter.PresenterInfo;
import nu.annat.beholder.presenter.ComponentPresenter;

@PresenterInfo
public class DualLineData extends ComponentPresenter {
	private String header;
	private String body;

	public DualLineData() {
		super();
	}

	public DualLineData(String header, String body) {
		super();
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
