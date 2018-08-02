package nu.annat.beholder;

import androidx.recyclerview.widget.RecyclerView;

public interface ComponentAdapterGroup {
	RecyclerView getRecyclerView();

	boolean hasStableIds();
}
