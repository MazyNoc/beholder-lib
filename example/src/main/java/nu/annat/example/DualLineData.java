package nu.annat.example;

import androidx.databinding.Bindable;
import nu.annat.beholder.jsonconverter.PresenterInfo;
import nu.annat.beholder.presenter.ObservablePresenter;

@PresenterInfo
public class DualLineData extends ObservablePresenter {
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

	@Bindable
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
		notifyPropertyChanged(BR.body);
	}

	public String getHeader() {
		return header;
	}
}
