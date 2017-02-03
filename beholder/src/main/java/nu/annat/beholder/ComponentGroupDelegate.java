package nu.annat.beholder;

import android.view.ViewGroup;

import java.util.List;

public class ComponentGroupDelegate implements ComponentGroup {
	@Override
	public ViewGroup getChildArea() {
		return null;
	}

	@Override
	public List<ComponentViewHolder> getChildren() {
		return null;
	}

	@Override
	public void addChild(ComponentViewHolder componentViewHolder) {

	}

	@Override
	public void removeAll() {

	}
}
