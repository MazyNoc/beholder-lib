package nu.annat.beholder;

import android.databinding.BaseObservable;

public class ViewInformation extends BaseObservable {
	private int order;

	public ViewInformation(int order) {
		this.order = order;
	}

	public int getOrder() {
		return order;
	}

	public boolean isFirst() {
		return order == 0;
	}
}
