package nu.annat.beholder;

import android.view.ViewGroup;

import java.util.List;

public interface ComponentGroup {
	ViewGroup getChildArea();
	List<ComponentViewHolder> getChildren();

	void addChild(int index, ComponentViewHolder componentViewHolder);
	void addChild(ComponentViewHolder componentViewHolder);
	void removeAll();

	void remove(ComponentViewHolder holder);
}
