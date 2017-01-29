package nu.annat.beholder;


public interface OnAction<T extends  Action> {
	<R> R executeAction(T action);
}
