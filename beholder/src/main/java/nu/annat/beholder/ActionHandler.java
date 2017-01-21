package nu.annat.beholder;


public interface ActionHandler {
	<T> T executeAction(Action action);
}
