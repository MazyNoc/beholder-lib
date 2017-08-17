package nu.annat.beholder;

public class Stats {

	private final String name;
	private final long start;

	public static Stats start(String name) {
		return new Stats(name);
	}

	public static Stats start() {
		return new Stats("");
	}

	public Stats(String name) {
		this.name = name;
		this.start = System.currentTimeMillis();
	}

	public String stop() {
		return ("BeholderStats: " + name + ": " + (System.currentTimeMillis() - this.start));
	}

	public String stop(String name) {
		return ("BeholderStats: " + name + ": " + (System.currentTimeMillis() - this.start));
	}


}
