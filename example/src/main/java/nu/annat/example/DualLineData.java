package nu.annat.example;

public class DualLineData extends BaseData {
	private String header;
	private String body;

	public DualLineData(String header, String body) {
		this.header = header;
		this.body = body;
	}

	public String getHeader() {
		return header;
	}

	public String getBody() {
		return body;
	}
}
