package handlebarsapp;

import java.util.HashMap;
import java.util.Map;

public class InputHandler {

    public static final String helpMessage = "Usage:\r\n" +
            "Merges and compiles an hbs and json files into a new one\r\n" +
            "Mandatory options:\r\n" +
            "   -json(-j)      : Set the json file location\r\n" +
            "   -hbs(-h)       : set the hbs file location\r\n" +
            "Non-mandatory options: \r\n" +
            "   -output(-o)    : sets the output file(default is output.txt in the same folder as the hbs file)\r\n";
    private final Map<String, String> input = new HashMap<>();

    private final String[] args;

    private boolean requestingHelp = false, invalid = false;

    private String errorMessage;

    public InputHandler(String[] args) {
        this.args = args;
    }

    public void processArgs() {
        checkArgsLength();

        checkRequestingHelp();

        if(requestingHelp || invalid) return;

        fillInputMapFromArgs();

        nullifyIfInvalid();

        if(invalid) return;

        generateOutputFilePathIfNotPresent();
    }

    private void checkArgsLength() {
        int length = args.length;
        if (length < 4 || length > 6 || length % 2 == 1) {
            errorMessage = "Invalid number of arguments: " + length;
            requestingHelp = true;
            invalid = true;
        }
    }

    private void checkRequestingHelp() {
        if(args.length == 0) return;
        if (matchesAny(args[0],"help")) {
            requestingHelp = true;
        }
    }

    public void fillInputMapFromArgs() {
        // why using regular expressions? maybe simple string comparisons with ignorance of the case sensitivity would be better.
        // The regular expressions don't apply to case sensitivity, I want arguments to accept both "/" and "-" calls
        for (int i = 0; i < args.length; i++) {
            if(i >= args.length-1) break;

            if (matchesAny(args[i],"json", "j")) {
                input.put("json", args[++i]);
            } else if (matchesAny(args[i],"hbs", "h")) {
                input.put("hbs", args[++i]);
            } else if (matchesAny(args[i], "output", "o")) {
                input.put("output", args[++i]);
            } else if(args[i].matches("[/-].*")){
                errorMessage = "No such argument \"" + args[i] + '"';
            }
        }
    }

    private boolean matchesAny(String toCheck, String... conditions){
        for(String str : conditions){
            if(toCheck.matches("([/-]?)" + str + "\\z")) return true;
        }
        return false;
    }

    private void nullifyIfInvalid() {
        boolean valid = input.containsKey("json") && input.containsKey("hbs");
        if (!valid) {
            input.clear();
            errorMessage = "Invalid arguments, json or hbs file missing";
            requestingHelp = true;
            invalid = true;
        }
    }

    private void generateOutputFilePathIfNotPresent() {
        if (!input.containsKey("output")) {
            String filePath = input.get("hbs");
            int lastIndex = filePath.lastIndexOf("\\");
            input.put("output", filePath.substring(0, lastIndex + 1) + "output.txt");
        }
    }

    public Map<String, String> getParsedInputMap() {
        return input;
    }

    public boolean isRequestingHelp(){
        return requestingHelp;
    }

    public boolean isInvalid(){
        return invalid || errorMessage != null;
    }

    private void handleError(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

}
