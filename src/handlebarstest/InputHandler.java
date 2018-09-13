package handlebarstest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputHandler {

    public static final List<String> helpMessageList = Arrays.asList(
            "Usage:",
            "   -hbs && -json are required",
            "Merges and compiles an hbs and json files into a new one",
            "Options:",
            "   -json(-j)      : Set the working directory",
            "   -hbs(-h)       : set the name for both .hbs & .json files",
            "   -output(-o)    : sets the output file(default is output.txt)"
    );
    public static final String helpMessage;
    private static Map<String, String> input = new HashMap<>();

    static {
        helpMessage = String.join(System.lineSeparator(), helpMessageList);
    }

    public static String get(String key) {
        return input.get(key);
    }

    public static Map<String, String> proceed(String args[]) {

        // Check if valid or help requested
        if (args.length < 4 || args[0].matches("([-/]?)[hH]elp")) {
            input.put("help", getHelpMessage());
            return input;
        }

        // Fill map
        for (int i = 0; i < args.length; i++) {
            if (args[i].matches("([/-]?)json\\z") || args[i].matches("([/-]?)j\\z")) {
                try {
                    input.put("json", args[++i]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("No argument follows json option");
                }
            } else if (args[i].matches("([/-]?)hbs\\z") || args[i].matches("([/-]?)h\\z")) {
                try {
                    input.put("hbs", args[++i]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("No argument follows hbs option");
                }
            } else if (args[i].matches("([/-]?)output\\z") || args[i].matches("([/-]?)o\\z")) {
                try {
                    input.put("output", args[++i]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("No argument follows output option");
                }
            }
        }

        // Check for requested fields
        boolean valid = input.containsKey("json") && input.containsKey("hbs");

        // Nullify if invalid
        if (!valid) {
            input.clear();
            input.put("help", getHelpMessage());
        }

        // Generate output file if not present
        if (!input.containsKey("output")) {
            String filePath = input.get("hbs");
            int lastIndex = filePath.lastIndexOf("/");
            input.put("output", filePath.substring(0, lastIndex + 1) + "output.txt");
        }

        return input;
    }

    public static Map<String, String> getInput() {
        return input;
    }

    public static boolean requestingHelp() {
        return input.containsKey("help");
    }

    public static String getHelpMessage() {
        return helpMessage;
    }

}
