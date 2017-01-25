package nu.annat.beholder.presenter;

/**
 * This interface ensures that all layouts can get a unique value to allow for reusing the same layout
 * in a Recyclerview or own framework.
 *
 * Since the list of children can change during the components lifetime, the calls cant be static.
 *
 */
public interface ComponentInfo {

	/**
	 * @return hash for current layout
	 */
	int layoutHash();

	/**
	 * @return hash for the total layout
	 */
	int deepLayoutHash();
}
