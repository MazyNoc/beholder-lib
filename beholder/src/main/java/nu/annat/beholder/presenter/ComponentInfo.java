package nu.annat.beholder.presenter;

import android.os.Bundle;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nu.annat.beholder.BeholderAdapter;

/**
 * This interface ensures that all layouts can get a unique value to allow for reusing the same layout
 * in a Recyclerview or own framework.
 *
 * Since the list of children can change during the components lifetime, the calls cant be static.
 *
 */
public interface ComponentInfo extends Iterable<ComponentInfo> {

	/**
	 * @return hash for current layout
	 */
	int layoutHash();

	/**
	 * @return hash for the total deep layout
	 */
	int deepLayoutHash();

	List<ComponentInfo> getChildren();

	void add(ComponentInfo component);

	void addAll(Collection<ComponentInfo> components);

	ComponentInfo get(int index);

	int size();

	Iterator<ComponentInfo> iterator();

	long itemId();

	void saveStates(Bundle extras);

	void restoreStates(Bundle extras);

	BeholderAdapter<?> getChildAdapter();

	void setChildAdapter(BeholderAdapter<?> adapter);
}
