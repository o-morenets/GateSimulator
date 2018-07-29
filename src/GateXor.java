import java.util.List;

public class GateXor extends Gate {

	public GateXor(List<Wire> ins, Wire output) {
		super("XOR", ins, output);
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
		Signal currentOut = getOutput().getSignal();
		int numHi = 0;
		int numX = 0;
		
		for (Wire wire : getInputs()) {
			if (wire.getSignal().equals(Signal.HI)) {
				if (++numHi > 1) {
					getOutput().setSignal(Signal.LO);
					return ! currentOut.equals(Signal.LO);
				}
			} else if (wire.getSignal().equals(Signal.X)) {
				numX++;
				if (numHi > 0) {
					getOutput().setSignal(Signal.X);
					return ! currentOut.equals(Signal.X);
				}
			}
		}
		
		if (numHi == 1) {
			getOutput().setSignal(Signal.HI);
			return ! currentOut.equals(Signal.HI);
		} else if (numX == getInputs().size() || numX == 1) {
			getOutput().setSignal(Signal.X);
			return ! currentOut.equals(Signal.X);
		} else {
			getOutput().setSignal(Signal.LO);
			return ! currentOut.equals(Signal.LO);
		}
	}

}
