package nu.annat.beholder.presenter;

import android.widget.LinearLayout;

import java.util.Iterator;
import java.util.List;

/**
 * This interface ensures that all layouts can get a unique value to allow for reusing the same layout
 * in a Recyclerview or own framework.
 *
 * Since the list of children can change during the components lifetime, the calls cant be static.
 *
 */
public interface ComponentInfo  {

	List<ComponentInfo> getChildren();

	/**
	 * @return hash for current layout
	 */
	int layoutHash();

	/**
	 * @return hash for the total deep layout
	 */
	int deepLayoutHash();
}
