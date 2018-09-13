package test;

import handlebarstest.FilesHandler;
import handlebarstest.HandlebarsUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HandlebarsUtilityTest {

    @Test
    void compile() {
        FilesHandler.handleJsonFile("C:\\Users\\kalin.krastev\\Desktop\\files\\junit\\junit.json");
        FilesHandler.handleHbsFile("C:\\Users\\kalin.krastev\\Desktop\\files\\junit\\junit.hbs");

        HandlebarsUtility.compile(
                FilesHandler.getJson(), FilesHandler.getHbs()
        );

        assertEquals("Parent name Ivan, age 40\r\n" +
                "Child name Georgi, age 10", HandlebarsUtility.getOutput());
    }
}