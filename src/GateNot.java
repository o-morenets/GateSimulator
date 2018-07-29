import java.util.ArrayList;

public class GateNot extends Gate {

    public GateNot(Wire input, Wire output) {
        super("NOT", one(input), output);
    }

    // Helper method to create an ArrayList of one thing so that Java's
    // stupid "super() must be first line" rule can be honored
    public static <T> ArrayList<T> one(T x) {
        ArrayList<T> a = new ArrayList<T>();
        a.add(x);
        return a;
    }

    /**
     * Performs an equality check, ensuring that the inputs, output, and name
     * are the same values.
     */
    @Override
    public boolean equals(Object other) {
        Gate o = (Gate) other;
        return getInputs().equals(o.getInputs()) && getOutput().equals(o.getOutput()) && getName().equals(o.getName());
    }

    @Override
    public boolean propagate() {
        Signal currentOut = getOutput().getSignal();
        Signal newOut = getInputs().get(0).getSignal().invert();
        getOutput().setSignal(newOut);

        return !currentOut.equals(newOut);
    }

}
