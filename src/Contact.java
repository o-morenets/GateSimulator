import java.util.List;

public class Contact implements Logic {
	private Wire in, out;
	private boolean inbound;
	
	/**
	 * The obvious constructor (initialize all fields).
	 */
	public Contact(Wire in, Wire out, boolean inbound) {
		this.in = in;
		this.out = out;
		this.inbound = inbound;
	}
	
	@Override
	public void feed(List<Signal> inSignals) {
		// TODO Auto-generated method stub
	}

	@Override
	public void feed(String inSignals) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean propagate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Signal> read() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Combine the Logic methods into a convenient single call: feed the given inSigs, propagate them through, and return the results of read.
	 */
	@Override
	public List<Signal> inspect(List<Signal> inputs) {
		feed(inputs);
		propagate();
		return read();
	}

	/**
	 * Identical to the previous inspect() method but pass a String of signals in rather than a list.
	 */
	@Override
	public String inspect(String inputs) {
		feed(inputs);
		propagate();
		return out.getSignal().toString();
	}

	@Override public String toString() {
		String nameIn = in.getName();
		String nameOut = out.getName();
		
		if (nameIn.equals(nameOut)) {
			return in.toString();
		} else {
			if (inbound) {
				nameOut = "(" + nameOut + ")";
			} else {
				nameIn = "(" + nameIn + ")";
			}
			return nameIn + nameOut + ":" + in.getSignal();
		}
	}
	
	@Override public boolean equals(Object o) {
		Contact other = (Contact) o;
		return in.equals(other.in) && out.equals(other.out) && inbound == other.inbound;
	}

	// getters/setters:
		
	public Wire getIn() {
		return in;
	}

	public void setIn(Wire in) {
		this.in = in;
	}

	public Wire getOut() {
		return out;
	}

	public void setOut(Wire out) {
		this.out = out;
	}

	public boolean getInbound() {
		return inbound;
	}

	public void setInbound(boolean inbound) {
		this.inbound = inbound;
	}
	
}
