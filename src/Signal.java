import java.util.ArrayList;
import java.util.List;

public enum Signal {
    HI, LO, X;

    /**
     * Returns the inversion of this signal. HI and LO return each other, and X returns itself.
     */
    public Signal invert() {
        switch (this) {
            case HI:
                return LO;
            case LO:
                return HI;
            default:
                return X;
        }
    }

    /**
     * Selects and returns a Signal representation
     */
    public static Signal fromString(char c) {
        switch (c) {
            case '1':
                return HI;
            case '0':
                return LO;
            case 'x':
            case 'X':
                return X;
            default:
                throw new ExceptionLogicMalformedSignal(c, "");
        }
    }

    /**
     * Returns the List of Signal values found in the input string
     */
    public static List<Signal> fromString(String inps) {
        List<Signal> resultList = new ArrayList<>();

        for (int i = 0; i < inps.length(); i++) {
            char ch = inps.charAt(i);

            // Check for whitespace and tab character
            if (ch == '\t' || ch == ' ')
                continue;

            resultList.add(Signal.fromString(ch));
        }

        return resultList;
    }

    /**
     * HI returns "1". LO returns "0". X returns "X".
     */
    @Override
    public String toString() {
        switch (this) {
            case HI:
                return "1";
            case LO:
                return "0";
            default:
                return "X";
        }
    }

    /**
     * Converts each signal in the List via toString, concatenates them into a single string, and returns it
     */
    public static String toString(List<Signal> sig) {
        StringBuilder sb = new StringBuilder();

        for (Signal signal : sig) {
            sb.append(signal.toString());
        }

        return sb.toString();
    }

}
