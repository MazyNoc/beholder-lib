package nu.annat.beholder.presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ExplodingComponentInfo extends AbstractParentComponentInfo {

	protected final ArrayList<ComponentInfo> preparedItems = new ArrayList<>();

	public ExplodingComponentInfo() {

	}

	public ExplodingComponentInfo(Collection<ComponentInfo> children) {
		this();
		super.addAll(children);
	}

	public ExplodingComponentInfo(ComponentInfo... children) {
		this();
		super.addAll(Arrays.asList(children));
	}

	public List<ComponentInfo> explode() {
		preparedItems.clear();
		prepare(subPresenters());
		return preparedItems;
	}

	protected void clearPrepared() {
		preparedItems.clear();
	}

	protected void addPrepared(ComponentInfo componentInfo) {
		preparedItems.add(componentInfo);
	}

	protected void prepare(List<ComponentInfo> componentInfos) {
		for (ComponentInfo componentInfo : componentInfos) {
			preparedItems.add(componentInfo);
		}
	}
}


