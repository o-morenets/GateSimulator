
public class Wire {
	private Signal signal;
	private String name;
	
	/**
	 * Uses X ("unknown") as the starting Signal value, and also initializes the name
	 */
	public Wire(String name) {
		this.signal = Signal.X;
		this.name = name;
	}

	/**
	 * Returns the string of name, colon, signal value
	 */
	@Override
	public String toString() {
		return name + ":" + signal;
	}
	
	/**
	 * Performs an equality check, ensuring that the name and signal both match
	 */
	@Override public boolean equals(Object other) {
		Wire wire = (Wire) other;
		return name.equals(wire.name) && signal.equals(wire.signal);
	}

	// getters/setters
	
	public Signal getSignal() {
		return signal;
	}

	public void setSignal(Signal signal) {
		this.signal = signal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
