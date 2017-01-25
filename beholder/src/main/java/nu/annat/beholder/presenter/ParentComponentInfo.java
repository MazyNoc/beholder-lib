package nu.annat.beholder.presenter;

import java.util.Collection;
import java.util.List;

public interface ParentComponentInfo extends ComponentInfo {
	boolean add(ComponentInfo componentInfo);
	boolean addAll(Collection<ComponentInfo> componentInfos);
	List<ComponentInfo> subPresenters();
}
