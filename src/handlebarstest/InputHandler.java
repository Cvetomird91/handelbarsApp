package handlebarstest;

import java.util.HashMap;
import java.util.Map;

class InputHandler {

    private static final String helpMessage = "Usage:\r\n" +
            "Merges and compiles an hbs and json files into a new one\r\n" +
            "Mandatory options:\r\n" +
            "   -json(-j)      : Set the json file location\r\n" +
            "   -hbs(-h)       : set the hbs file location\r\n" +
            "Non-mandatory options: \r\n" +
            "   -output(-o)    : sets the output file(default is output.txt in the same folder as the hbs file)\r\n";
    private final Map<String, String> input = new HashMap<>();

    private final String[] args;

    InputHandler(String[] args) {
        this.args = args;
    }

    void processArgs() {
        checkArgsLength();

        checkRequestingHelp();

        fillInputMapFromArgs();

        nullifyIfInvalid();

        generateOutputFilePathIfNotPresent();
    }

    private void checkArgsLength() {
        int length = args.length;
        if (length < 4 || length > 6 || length % 2 == 1) {
            System.out.println("Invalid number of arguments: " + length);
            System.out.println(helpMessage);
            System.exit(0);
        }
    }

    private void checkRequestingHelp() {
        if (args[0].matches("([-/]?)[hH]elp")) {
            System.out.println(helpMessage);
            System.exit(0);
        }
    }

    private void fillInputMapFromArgs() {
        // why using regular expressions? maybe simple string comparisons with ignorance of the case sensitivity would be better.
        // The regular expressions don't apply to case sensitivity, I want arguments to accept both "/" and "-" calls
        for (int i = 0; i < args.length; i++) {
            if (args[i].matches("([/-]?)json\\z") || args[i].matches("([/-]?)j\\z")) {
                input.put("json", args[++i]);
            } else if (args[i].matches("([/-]?)hbs\\z") || args[i].matches("([/-]?)h\\z")) {
                input.put("hbs", args[++i]);
            } else if (args[i].matches("([/-]?)output\\z") || args[i].matches("([/-]?)o\\z")) {
                input.put("output", args[++i]);
            }
        }
    }

    private void nullifyIfInvalid() {
        boolean valid = input.containsKey("json") && input.containsKey("hbs");
        if (!valid) {
            input.clear();
            System.out.println(helpMessage);
            System.exit(0);
        }
    }

    private void generateOutputFilePathIfNotPresent() {
        if (!input.containsKey("output")) {
            String filePath = input.get("hbs");
            int lastIndex = filePath.lastIndexOf("/");
            input.put("output", filePath.substring(0, lastIndex + 1) + "output.txt");
        }
    }

    Map<String, String> getParsedInputMap() {
        return input;
    }

}
