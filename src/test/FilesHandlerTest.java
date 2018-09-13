package test;

import handlebarstest.FilesHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilesHandlerTest {

    @Test
    void handleJsonFile() {
        assertTrue(
                FilesHandler.handleJsonFile(
                        "C:\\Users\\kalin.krastev\\Desktop\\files\\junit\\junit.json"
                ));
    }

    @Test
    void handleHbsFile() {
        assertTrue(
                FilesHandler.handleHbsFile(
                        "C:\\Users\\kalin.krastev\\Desktop\\files\\junit\\junit.hbs"
                ));
    }

    @Test
    void appendToOutputFile() throws IOException {
        assertTrue(
                FilesHandler.handleOutputFile(
                        "C:\\Users\\kalin.krastev\\Desktop\\files\\junitOutputest.txt"
                ));
        FilesHandler.appendToOutputFile("jUnit Test");
        assertEquals(
                "jUnit Test",
                Files.readAllLines(Paths.get("C:\\Users\\kalin.krastev\\Desktop\\files\\junitOutputest.txt")).get(0)
        );
    }
}