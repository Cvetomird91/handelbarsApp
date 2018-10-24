package handlebarstest;

import handlebarsapp.FilesHandler;
import handlebarsapp.InputHandler;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilesHandlerTest {

    @Test
    void fullReadAndWriteTest() throws IOException {
        FilesHandler filesHandler = new FilesHandler();

        File file = new File("temp.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            return;
        }
        file.deleteOnExit();
        String path = file.getPath();

        String toAppend = "proba123";

        filesHandler.handleWritableFile("testWrite", path);
        filesHandler.appendToWritableFile("testWrite",toAppend);

        filesHandler.handleReadableFile("testRead", path);
        String read = filesHandler.getReadFile("testRead");

        assertEquals(toAppend, read.trim());

        filesHandler.cleanUp();
    }

    @Test void readInvalidFilesFromInputHandler() throws IOException {
        String[] args = new String[] {
                "-j", "path\\path2\\file.json",
                "-h", "path\\path2\\file.hbs",
        };

        InputHandler inputHandler = new InputHandler(args);
        inputHandler.processArgs();

        FilesHandler filesHandler = new FilesHandler();
        filesHandler.handleFilesFromInput(inputHandler.getParsedInputMap());

        assertTrue(filesHandler.hasInvalidInput());
    }

    @Test void handleEmptyFile(){
        File file = new File(new File("."), "empty.txt");
        try {
            file.createNewFile();
            file.deleteOnExit();

            FilesHandler filesHandler = new FilesHandler();
            filesHandler.handleReadableFile("empty", file.getPath());
        } catch (IOException e) {

        }

    }

    @Test void appendWithBadCharset(){
        File file = new File(new File("."), "empty.txt");
        try {
            file.createNewFile();
            file.deleteOnExit();

            FilesHandler filesHandler = new FilesHandler();
            filesHandler.handleReadableFile("empty", file.getPath());
            System.out.println("path: " + filesHandler.getWritableFile("empty"));
        } catch (IOException e) {

        }
    }

}