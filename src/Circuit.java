import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Circuit implements Logic {
	private List<Logic> components; // The list of logical blocks that are wired together in this circuit.
	private List<Contact> inputs, outputs; // The connections to the outside world.
	private List<Wire> innerWires; // A convenient place to store all known wires while constructing a circuit.
	private List<String> importables; //  the names of circuits that were announced on the IMPORT line, if any.
	private String name; // the circuit's name (part of the file name).
	
	/**
	 * The obvious constructor that obediently plugs in all arguments to the fields.
	 */
	public Circuit (String circuitName, List<Logic> components, List<Contact> inputs, List<Contact> outputs, List<Wire> innerWires, List<String> importables) {
		this.name = circuitName;
		this.components = components;
		this.inputs = inputs;
		this.outputs = outputs;
		this.innerWires = innerWires;
		this.importables = importables;
	}
	
	/**
	 * An involved constructor.
	 */
	public Circuit(String circuitName) throws IOException {
		// initialize all fields, first thing (including empty lists).
		this.name = circuitName;
		this.components = new ArrayList<>();
		this.inputs = new ArrayList<>();
		this.outputs = new ArrayList<>();
		this.innerWires = new ArrayList<>();
		this.importables = new ArrayList<>();
		
		// Get a scanner via getCircuitScanner, to read through the file.
		Scanner sc = getCircuitScanner(circuitName);

		// Call parseImportLine if appropriate, to find and add items to the importables list.
		String line = "";
		if (sc.hasNextLine()) {
			line = sc.nextLine();
			String[] tokens = line.split(" ");
			if (tokens[0].equalsIgnoreCase("IMPORT")) {
				parseImportLine(sc.nextLine());
				sc.nextLine(); // read empty line
			}
        }

        // Call parseContactsLine, to find and add all Contact values to inputs and outputs.
        line = sc.nextLine();
        parseContactsLine(line);
		sc.nextLine(); // read empty line
		
		// Repeatedly call parseComponentLine, as necessary, to successfully create each requested gate or sub-circuit,
		// hook it up to the overall circuit as needed (via hookUp), and add it to the components list.
		// (This list can contain gates and circuits, as they are all implementors of Logic).
		while(sc.hasNextLine()) {
			line = sc.nextLine();
			parseComponentLine(line);
			// TODO
		}
	}
	
	// Helpers for the Constructor
	
	/**
	 * Converts something like "halfadder" into the filename "samples/halfadder.txt", opens the file,
	 * creates a Scanner attached to that file, and returns the Scanner.
	 */
	public Scanner getCircuitScanner(String circuitName) throws IOException {
		return new Scanner(new File("samples/" + circuitName + ".txt"));
	}
	
	/**
	 *  Parses the entire line from the file that definitely contains the IMPORT keyword
	 *  and one or more circuit names after it (all separated by single spaces).
	 *  Updates the importables field.
	 */
	public void parseImportLine(String line) {
		String[] tokens = line.split(" ");
		for (int i = 1; i < tokens.length; i++) {
			importables.add(tokens[i]);
		}
	}

	/**
	 * Given the entire line from the file that names the input and output wires, create those new wires and add them to innerWires;
	 * for each one, create a Contact and appropriately add it to either inputs or outputs.
	 * The overall effect is to create wires for both sides of the Contact as necessary,
	 * reusing the same name when we don't know yet how it might be connected to the outside world.
	 */
	public void parseContactsLine(String line) {
		String[] names = line.split("->");
		String[] namesIn = names[0].trim().split(" ");
		String[] namesOut = names[1].trim().split(" ");
		
		for (String name : namesIn) {
            Wire wireIn = new Wire(name);
			innerWires.add(wireIn);
			Contact contactIn = new Contact(wireIn, wireIn, true);
			inputs.add(contactIn);
		}
		
		for (String name : namesOut) {
            Wire wireOut = new Wire(name);
			innerWires.add(wireOut);
			Contact contactOut = new Contact(wireOut, wireOut, false);
			outputs.add(contactOut);
		}
	}
	
	/**
	 * Given the string argument, find the one inner wire of this circuit that goes by that name.
	 */
	public Wire findWire(String name) {
		for (Wire wire : innerWires) {
			if (wire.getName().equals(name))
				return wire;
		}
		return null;
	}
	
	/**
	 * Given complete lists of Wire references, completely replace the outer wires of all your Contacts (found in inputs and outputs).
	 */
	public void hookUp(List<Wire> inWires, List<Wire> outWires) {
		if (inputs.size() != inWires.size())
			throw new ExceptionLogicParameters(true, inputs.size(), inWires.size());
		
		if (outputs.size() != outWires.size())
			throw new ExceptionLogicParameters(false, outputs.size(), outWires.size());
		
		for (int i = 0; i < inputs.size(); i++) {
			inputs.get(i).setIn(inWires.get(i));
		}

		for (int i = 0; i < outputs.size(); i++) {
			outputs.get(i).setOut(outWires.get(i));
		}
	}
	
	/**
	 * Given the entire line of either a Gate or sub-circuit, create the Gate object or Circuit object,
	 * ensuring you re-use any known wires (findWire to find them), hookUp sub-circuits to their specified wires,
	 * and add this new component to the components field.
	 */
	public void parseComponentLine(String line) throws IOException {
		// TODO
	}
	
	/**
	 * Feed signal values to the inputs contacts.
	 */
	@Override
	public void feed(List<Signal> inSignals) {
		if (inSignals.size() != inputs.size())
			throw new ExceptionLogicParameters(true, inputs.size(), inSignals.size());
		
		for (int i = 0; i < inputs.size(); i++) {
			inputs.get(i).getIn().setSignal(inSignals.get(i));
		}
	}

	/**
	 * Same notion, but obtain the Signal values out of this string via Signal.fromString.
	 */
	@Override
	public void feed(String inSignals) {
		feed(Signal.fromString(inSignals));
	}

	/**
	 * Fully update all outputs of all components in this circuit so that all signal values are stable (will not change until the circuit's inputs are modified).
	 * Returns a boolean indicating if any wires' Signal values changed.
	 */
	@Override
	public boolean propagate() {
		boolean ans = false;
		for (Logic component : components) {
			ans = component.propagate();
		}
		return ans;
	}

	/**
	 * Reads the signals on this circuit's outputs wires and returns them in a List.
	 */
	@Override
	public List<Signal> read() {
		List<Signal> signals = new ArrayList<>();
		for (Contact contact : outputs) {
			signals.add(contact.getOut().getSignal());
		}
		return signals;
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
		return outputs.toString();
	}

	/**
	 * Show the name, a colon, and the inputs, an arrow ->, and the outputs on the first line of the returned string.
	 * Then on subsequent lines, represent each sub-circuit or gate, indenting all components' string representations by two spaces.
	 */
	@Override public String toString() {
		StringBuilder sb = new StringBuilder(name + ":");
		for (Contact contact : inputs) {
			sb.append(contact.getIn().getName()).append(" ");
		}
		sb.append(" -> ");
		for (Contact contact : outputs) {
			sb.append(contact.getOut().getName());
		}
		return sb.toString();
	}
	
	/**
	 * Given a string that assumedly contains multiple lines, return a string that has added exactly two spaces at the front of each line that is in the string.
	 */
	public static String indent(String s) {
		StringBuilder sb = new StringBuilder("  ");
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			sb.append(ch);
			if (ch == '\n' && i < s.length() - 1) {
				sb.append("  ");
			}
		}
		return sb.toString();
	}
	
	// Getters/Setters

	public List<Logic> getComponents() {
		return components;
	}

	public void setComponents(List<Logic> components) {
		this.components = components;
	}

	public List<Contact> getInputs() {
		return inputs;
	}

	public void setInputs(List<Contact> inputs) {
		this.inputs = inputs;
	}

	public List<Contact> getOutputs() {
		return outputs;
	}

	public void setOutputs(List<Contact> outputs) {
		this.outputs = outputs;
	}

	public List<Wire> getInnerWires() {
		return innerWires;
	}

	public void setInnerWires(List<Wire> innerWires) {
		this.innerWires = innerWires;
	}

	public List<String> getImportables() {
		return importables;
	}

	public void setImportables(List<String> importables) {
		this.importables = importables;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
