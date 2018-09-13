package handlebarstest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputHandler {

    // isn't this one big constant... why is necessary to overcomplicate this?
    public static final List<String> helpMessageList = Arrays.asList(
            "Usage:",
            "   -hbs && -json are required", // probably move this in another section, mandatory options or similar.
            "Merges and compiles an hbs and json files into a new one", // this should be the first message
            "Options:",
            "   -json(-j)      : Set the working directory", // why -json(-j) is used for directory. error in the description?
            "   -hbs(-h)       : set the name for both .hbs & .json files", // if it's used for both of the files then change the option's name. I guess this is an old description - it should be updated.
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


    // this method is doing too much work. in the ideal case a method should be doing only 1 thing.
    public static void proceed(String[] args) {


        // it might be better to make the number of arguments exact. Now I can specify as many arguments as I want > 4.


        // Check if valid or help requested
        if (args.length < 4 || args[0].matches("([-/]?)[hH]elp")) {
            input.put("help", getHelpMessage());
            return;
        }

        // why using regular expressions? maybe simple string comparisons with ignorance of the case sensitivity would be better.

        // Fill map
        for (int i = 0; i < args.length; i++) {
            if (args[i].matches("([/-]?)json\\z") || args[i].matches("([/-]?)j\\z")) {
                // why are you catching RuntimeExceptions? RuntimeExceptions should not be caught.
                // this could be rewritten with simple if statements based on the args length.
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
