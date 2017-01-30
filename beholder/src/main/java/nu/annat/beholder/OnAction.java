package nu.annat.beholder;

public interface OnAction<T extends Action> {
	void executeAction(T action);
}
