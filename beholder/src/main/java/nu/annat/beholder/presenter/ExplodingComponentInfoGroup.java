package nu.annat.beholder.presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ExplodingComponentInfoGroup extends AbstractComponentInfoGroup {

	protected final ArrayList<ComponentInfo> preparedItems = new ArrayList<>();

	public ExplodingComponentInfoGroup() {

	}

	public ExplodingComponentInfoGroup(Collection<ComponentInfo> children) {
		this();
		super.addAll(children);
	}

	public ExplodingComponentInfoGroup(ComponentInfo... children) {
		this();
		super.addAll(Arrays.asList(children));
	}

	public List<ComponentInfo> explode() {
		preparedItems.clear();
		prepare(this);
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


