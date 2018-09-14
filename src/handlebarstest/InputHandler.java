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
    private final FilesHandler filesHandler = new FilesHandler();
    private final Map<String, String> input = new HashMap<>();

    InputHandler(String[] args) {
        // it might be better to make the number of arguments exact. Now I can specify as many arguments as I want > 4.
        // 2 arguments are mandatory and 1 is optional so the number can vary between 4 and 6, but I have limited it to only those two options


        // Check if valid or help requested
        if (args.length < 4 || args[0].matches("([-/]?)[hH]elp") || args.length > 6 || args.length % 2 == 1) {
            System.out.println(helpMessage);
            System.exit(0);
        }

        fillInputMapFromArgs(args);

        nullifyIfInvalid();

        generateOutputFileIfNotPresent();

        handleFiles();
    }

    private void fillInputMapFromArgs(String args[]) {
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

    private void generateOutputFileIfNotPresent() {
        if (!input.containsKey("output")) {
            String filePath = input.get("hbs");
            int lastIndex = filePath.lastIndexOf("/");
            input.put("output", filePath.substring(0, lastIndex + 1) + "output.txt");
        }
    }

    private void handleFiles() {
        for (String id : new String[]{"json", "hbs"}) //Readable files
            if (!filesHandler.handleReadableFile(id, input.get(id))) {
                System.err.println("Invalid " + id + " file: " + input.get(id));
                Runtime.getRuntime().exit(-1);
            }

        //Writaable files
        if (!filesHandler.handleWritableFile("output", input.get("output"))) {
            System.err.println("Invalid json file: " + input.get("output"));
            Runtime.getRuntime().exit(-1);
        }
    }

    FilesHandler getFilesHandler() {
        return filesHandler;
    }

}
