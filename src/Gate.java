import java.util.ArrayList;
import java.util.List;

public abstract class Gate implements Logic {
	private List<Wire> inputs;
	private Wire output;
	private String name;
	
	/**
	 * The obvious constructor (instantiate all fields)
	 */
	public Gate(String name, List<Wire> ins, Wire out) {
		if (ins == null || ins.isEmpty())
			throw new ExceptionLogicParameters(true, 1, 0);

		this.inputs = ins;
		this.output = out;
		this.name = name;
	}
	
	/**
	 * Feed these signals to the input wires.
	 */
	@Override
	public void feed(List<Signal> inSigs) {
		if (inSigs.size() != inputs.size())
			throw new ExceptionLogicParameters(true, inputs.size(), inSigs.size());
		
		for (int i = 0; i < inputs.size(); i++) {
			inputs.get(i).setSignal(inSigs.get(i));
		}
	}
	
	/**
	 * Same notion, but obtain the Signal values out of this string via Signal.fromString.
	 */
	@Override
	public void feed(String signalsStr) {
		feed(Signal.fromString(signalsStr));
	}
	
	/**
	 * Read the single signal value from the output wire, and return it as a single value in a List.
	 * (It requires that a list be returned because we got this method from the Logic interface,
	 * and that will be used by more than just one-output gates).
	 */
	@Override
	public List<Signal> read() {
		return new ArrayList<Signal>() {
			{
				add(output.getSignal());
			}
		};
	}
	
	/**
	 * Combine the Logic methods into a convenient single call: feed the given inSigs, propagate them through, and return the results of read.
	 */
	@Override
	public List<Signal> inspect(List<Signal> inSigs) {
		feed(inSigs);
		propagate();
		return read();
	}
	
	/**
	 * Identical to the previous inspect() method but pass a String of signals in rather than a list.
	 */
	@Override
	public String inspect(String inStr) {
		feed(inStr);
		propagate();
		return output.getSignal().toString();
	}
	
	/**
	 * Include the name and then use the List definition's own toString implementations
     * to include the inputs and output values
	 */
	@Override public String toString() {
		return name + "( " + inputs + " | " + output + " )";
	}
	
	/**
	 * Performs an equality check, ensuring that the inputs, output, and name are the same values
	 */
	@Override
	public boolean equals(Object other) {
		Gate o = (Gate) other;
		return inputs.equals(o.inputs) && output.equals(o.output) && name.equals(o.name);
	}

	// getters/setters
	
	public List<Wire> getInputs() {
		return inputs;
	}

	public void setInputs(List<Wire> inputs) {
		this.inputs = inputs;
	}

	public Wire getOutput() {
		return output;
	}

	public void setOutput(Wire output) {
		this.output = output;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
