package nu.annat.beholder.presenter;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public interface ParentComponentInfo extends ComponentInfo, Iterable<ComponentInfo> {
	boolean add(ComponentInfo componentInfo);

	boolean addAll(Collection<ComponentInfo> componentInfos);

	ComponentInfo get(int index);

	Iterator<ComponentInfo> iterator();

	List<ComponentInfo> subPresenters();
}
