package nu.annat.beholder;

import java.util.HashMap;
import java.util.Map;

public class ActionHandler {
	protected Map<Class<? extends Action>, OnAction> handlers = new HashMap<>();
	public void register(Class<? extends Action> actionClass, OnAction onAction){
		handlers.put(actionClass, onAction);
	}

	public void handle(Action action){
		OnAction onAction = handlers.get(action.getClass());
		if(onAction == null){

		} else {
			onAction.executeAction(action);
		}
	}
}
