package handlebarstest;

import handlebarsapp.FilesHandler;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilesHandlerTest {

    @Test
    void fullTest(){
        FilesHandler filesHandler = new FilesHandler();

        File dir = new File(".");
        File file = new File(dir,"temp.txt");
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

}