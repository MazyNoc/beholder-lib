package nu.annat.beholder.action;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class ActionHandler<ACTION> {
	private static final String TAG = ActionHandler.class.getSimpleName();
	private final Handler mainThreadHandler;
	private final ActionHandler<ACTION> delegate;
	protected Map<Class<? extends ACTION>, ActionInfo<? extends ACTION>> handlers = new HashMap<>();

	private static class ActionInfo<ACTION> {
		private final boolean forceMainThread;
		private final OnAction<ACTION> onAction;

		public ActionInfo(boolean forceMainThread, OnAction<ACTION> onAction) {
			this.forceMainThread = forceMainThread;
			this.onAction = onAction;
		}
	}

	public ActionHandler() {
		this(null);
	}

	public ActionHandler(ActionHandler<ACTION> delegate) {
		this.mainThreadHandler = new Handler(Looper.getMainLooper());
		this.delegate = delegate;
	}

	public <T extends ACTION> void register(Class<T> actionClass, OnAction<T> onAction) {
		register(actionClass, onAction, false);
	}

	public <T extends ACTION> void register(Class<T> actionClass, OnAction<T> onAction, boolean forceMainThread) {
		handlers.put(actionClass, new ActionInfo<T>(forceMainThread, onAction));
	}

	public void handle(final ACTION action) {
		final ActionInfo actionInfo = handlers.get(action.getClass());
		if (actionInfo == null) {
			if (delegate != null) {
				delegate.handle(action);
			} else {
				Log.w(TAG, "Action " + action.getClass().getName() + " is not registered");
			}
		} else {
			if (actionInfo.forceMainThread && !isOnMainThread()) {
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

	private boolean isOnMainThread() {
		return Thread.currentThread() == Looper.getMainLooper().getThread();
	}
}
