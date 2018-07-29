
public class ExceptionLogicMalformedSignal extends RuntimeException {
	private char bad; // the offending character
	private String msg; // the entire message
	
	/**
	 * constructor that assigns to each field
	 */
	public ExceptionLogicMalformedSignal(char bad, String msg) {
		this.bad = bad;
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		return msg;
	}

	// getters/setters:

	public char getBad() {
		return bad;
	}

	public void setBad(char bad) {
		this.bad = bad;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
