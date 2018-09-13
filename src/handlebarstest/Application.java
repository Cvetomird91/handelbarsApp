package handlebarstest;

import static handlebarstest.FilesHandler.*;
import static handlebarstest.HandlebarsUtility.compile;
import static handlebarstest.HandlebarsUtility.getOutput;
import static handlebarstest.InputHandler.*;


public class Application {

    // revisit the idea of static methods/classes.

    public static void main(String[] args) {

        // maybe change the name of this method. "proceed" looks too general.
        proceed(args);

        // this should be in the input handler...
        if (requestingHelp()) {
            System.out.println(getHelpMessage());
            System.exit(0);
        }

        // the handle* methods could be reworked in one generic method.

        // this should be in the input handler...
        if (!handleJsonFile(get("json"))) {
            System.err.println("Invalid Json File: " + get("json"));
            Runtime.getRuntime().exit(-1);
        }

        // this should be in the input handler...
        if (!handleHbsFile(get("hbs"))) {
            System.err.println("Invalid Hbs File: " + get("hbs"));
            Runtime.getRuntime().exit(-1);
        }

        // this should be in the input handler...
        if (!handleOutputFile(get("output"))) {
            System.err.println("Invalid Output File: " + get("output"));
            Runtime.getRuntime().exit(-1);
        }

        if (compile(getJson(), getHbs())) {
            String output = getOutput();
            if (appendToOutputFile(output)) {
                System.err.println("Failed to append to output file");
            }
            System.out.println(output);
        } else {
            System.err.println(getOutput());
        }
    }

}
