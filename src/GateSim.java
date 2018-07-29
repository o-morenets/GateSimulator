import java.io.IOException;

public class GateSim {

	public static void main(String[] args) {
		if (args.length == 2) {
			Circuit circuit = null;
			
			try {
				circuit = new Circuit(args[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String out = circuit.inspect(args[1]);
			System.out.println(out);
		} else {
			System.out.println("Not enough arguments. Use circuit name as args[0] and a string of inputs as args[1]");
		}
	}

}
