package nu.annat.beholder;

import android.databinding.BaseObservable;

/**
 * The ViewInformation differs from ComponentInfo based presenters in the way its assigned to the view.
 * A componentInfo presenter @link {@link nu.annat.beholder.presenter.ComponentPresenter}, @link {@link nu.annat.beholder.presenter.ObservablePresenter}
 * carries the data that should be presented, so in a list of 100 items, you have 100 instances of presenters.
 * <p>
 * The ViewInformation is tied to a specific View and is reused when the view is reused. This can be used to hold values that are important for the
 * layout of the view, like the child place (order) of the specific view.
 * <p>
 * As an example, if you have a LinearLayout as the parent view you might want to know if this particular view is the first child and if so hide a
 * top delimiter on this view.
 * You can also add indentation for the depth if that is added to the ViewInformation
 */
public class ViewInformation extends BaseObservable {
	private final int depth;
	private final int order;

	public ViewInformation(int depth, int order) {
		this.depth = depth;
		this.order = order;
	}

	public int getOrder() {
		return order;
	}

	public boolean isFirst() {
		return order == 0;
	}

	public int getDepth() {
		return depth;
	}
}
