package nu.annat.example;

import nu.annat.beholder.Action;


public class ToastAction extends Action {
	final String message;

	public ToastAction(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
