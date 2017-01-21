package nu.annat.beholder.presenter;

public interface Presenter {

	/**
	 * @return hash for current presenter
	 */
	int layoutHash();

	/**
	 * @return hash for the total layout
	 */
	int deepLayoutHash();
}
