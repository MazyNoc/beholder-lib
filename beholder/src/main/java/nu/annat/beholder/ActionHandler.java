package nu.annat.beholder;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class ActionHandler {
	private static final String TAG = ActionHandler.class.getSimpleName();
	private final Handler mainThreadHandler;
	protected Map<Class<? extends Action>, ActionInfo> handlers = new HashMap<>();

	private static class ActionInfo {
		private final boolean forceMainThread;
		private final OnAction onAction;

		public ActionInfo(boolean forceMainThread, OnAction onAction) {
			this.forceMainThread = forceMainThread;
			this.onAction = onAction;
		}
	}

	public ActionHandler() {
		this.mainThreadHandler = new Handler(Looper.getMainLooper());
	}

	public void register(Class<? extends Action> actionClass, OnAction onAction) {
		register(actionClass, onAction, false);
	}

	public void register(Class<? extends Action> actionClass, OnAction onAction, boolean forceMainThread) {
		handlers.put(actionClass, new ActionInfo(forceMainThread, onAction));
	}

	public void handle(final Action action) {
		final ActionInfo actionInfo = handlers.get(action.getClass());
		if (actionInfo == null) {
			Log.d(TAG, "Action " + action.getClass().getName() + " is not registered");
		} else {
			if (actionInfo.forceMainThread && !onMainThread()) {
				mainThreadHandler.post(new Runnable() {
					@Override
					public void run() {
						actionInfo.onAction.executeAction(action);
					}
				});
			} else {
				actionInfo.onAction.executeAction(action);
			}
		}
	}

	private boolean onMainThread() {
		return Thread.currentThread() == Looper.getMainLooper().getThread();
	}
}
