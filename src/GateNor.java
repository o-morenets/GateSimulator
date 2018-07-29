import java.util.List;

public class GateNor extends Gate {

	public GateNor(List<Wire> ins, Wire output) {
		super("NOR", ins, output);
	}

	/**
	 * Performs an equality check, ensuring that the inputs, output, and name are the same values.
	 */
	@Override
	public boolean equals(Object other) {
		Gate o = (Gate) other;
		return getInputs().equals(o.getInputs()) && getOutput().equals(o.getOutput()) && getName().equals(o.getName());
	}
	
	@Override
	public boolean propagate() {
		boolean ans;
		Signal currentOut = getOutput().getSignal();
		
		for (Wire wire : getInputs()) {
			if (wire.getSignal() == Signal.HI) {
				ans = currentOut != Signal.LO;
				getOutput().setSignal(Signal.LO);
				return ans;
			}
		}
		
		for (Wire wire : getInputs()) {
			if (wire.getSignal() == Signal.X) {
				ans = currentOut != Signal.X;
				getOutput().setSignal(Signal.X);
				return ans;
			}
		}

		getOutput().setSignal(Signal.HI);
		return ! currentOut.equals(Signal.HI);
	}

}
