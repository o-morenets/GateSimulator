
public class ExceptionLogicParameters extends RuntimeException {
	private boolean inputsRelated;
	private int expected, found;
	
	/**
	 * constructor that assigns to each field
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
