package nu.annat.beholder;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class BeholderBindingExtensions {

	private static final String TAG = "BeholderBindingExtensions";

	@BindingAdapter("adapter")
	public static void setAdapter(RecyclerView view, BeholderAdapter<?> adapter) {
		view.swapAdapter(adapter, false);
	}

}
