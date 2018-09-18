package handlebarsapp;

public class Application {

    public static void main(String[] args) {

        InputHandler inputHandler = new InputHandler(args);
        inputHandler.processArgs();

        if(inputHandler.isInvalid()){
            System.err.println(inputHandler.getErrorMessage());
        }

        if(inputHandler.isRequestingHelp()){
            System.out.println(InputHandler.helpMessage);
            System.exit(0);
        }

        FilesHandler filesHandler = new FilesHandler();
        filesHandler.handleFilesFromInput(inputHandler.getParsedInputMap());

        if(filesHandler.hasInvalidInput()){
            System.exit(-1);
        }

        String json = filesHandler.getReadFile("json");
        String hbs = filesHandler.getReadFile("hbs");

        String output = HandlebarsUtility.compile(json, hbs);

        if (output.startsWith("Error | ")) {
            System.err.println(output);
        } else {
            boolean successfulAppend = filesHandler.appendToWritableFile("output", output);
            if (!successfulAppend) System.err.println("Failed to append to output file located at: "
                    + filesHandler.getWritableFile("output"));
            System.out.println(output);
        }

        filesHandler.cleanUp();
    }

}
