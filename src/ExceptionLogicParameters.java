
public class ExceptionLogicParameters extends RuntimeException {
	private boolean inputsRelated; // when true, indicates this parameters issue was for the inputs to some Logic structure
	                                // (and not to the outputs)
	private int expected, found; // How many were expected, and how many were found?
	
	/**
	 * Constructor that assigns to each field
	 */
	public ExceptionLogicParameters(boolean inputsRelated, int expected, int found) {
		this.inputsRelated = inputsRelated;
		this.expected = expected;
		this.found = found;
	}

	@Override public String toString() {
        return "Wrong input. Expected: " + expected + ", found: " + found;
	}

	// getters/setters:

	public boolean getInputsRelated() {
		return inputsRelated;
	}

	public void setInputsRelated(boolean inputsRelated) {
		this.inputsRelated = inputsRelated;
	}

	public int getExpected() {
		return expected;
	}

	public void setExpected(int expected) {
		this.expected = expected;
	}

	public int getFound() {
		return found;
	}

	public void setFound(int found) {
		this.found = found;
	}
	
}
