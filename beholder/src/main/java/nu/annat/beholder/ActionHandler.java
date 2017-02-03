package nu.annat.beholder;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class ActionHandler {
	private static final String TAG = ActionHandler.class.getSimpleName();
	private final Handler mainThreadHandler;
	protected Map<Class<?>, ActionInfo> handlers = new HashMap<>();

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

	public <T> void register(Class<? extends T> actionClass, OnAction<T> onAction) {
		register(actionClass, onAction, false);
	}

	public <T> void register(Class<? extends T> actionClass, OnAction<T> onAction, boolean forceMainThread) {
		handlers.put(actionClass, new ActionInfo(forceMainThread, onAction));
	}

	public void handle(final Object action) {
		final ActionInfo actionInfo = handlers.get(action.getClass());
		if (actionInfo == null) {
			Log.d(TAG, "Action " + action.getClass().getName() + " is not registered");
		} else {
			if (actionInfo.forceMainThread && !onMainThread()) {
				mainThreadHandler.post(new Runnable() {
					@Override
					public void run() {
						actionInfo.onAction.execute(action);
					}
				});
			} else {
				actionInfo.onAction.execute(action);
			}
		}
	}

	private boolean onMainThread() {
		return Thread.currentThread() == Looper.getMainLooper().getThread();
	}
}
