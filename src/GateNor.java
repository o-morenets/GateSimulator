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
			if (wire.getSignal().equals(Signal.HI)) {
				ans = ! currentOut.equals(Signal.LO);
				getOutput().setSignal(Signal.LO);
				return ans;
			}
		}
		
		for (Wire wire : getInputs()) {
			if (wire.getSignal().equals(Signal.X)) {
				ans = ! currentOut.equals(Signal.X);
				getOutput().setSignal(Signal.X);
				return ans;
			}
		}
		
		ans = ! currentOut.equals(Signal.HI);
		getOutput().setSignal(Signal.HI);
		return ans;
	}

}
