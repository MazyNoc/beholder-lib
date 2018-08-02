package nu.annat.example;


public class ToastAction {
	final String message;
	final DualLineData data;

	public ToastAction(String message, DualLineData data) {
		this.message = message;
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public DualLineData getData() {
		return data;
	}
}
