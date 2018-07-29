import java.util.List;

public class GateOr extends Gate {

    public GateOr(List<Wire> ins, Wire output) {
        super("OR", ins, output);
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
                ans = currentOut != Signal.HI;
                getOutput().setSignal(Signal.HI);
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

        getOutput().setSignal(Signal.LO);
        return currentOut != Signal.LO;
    }

}
