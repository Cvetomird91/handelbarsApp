package test;

import handlebarstest.InputHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputHandlerTest {
    @Test
    void proceedSuccess() {
        InputHandler.proceed(new String[]{"-j", "path/path2/file.json", "-h", "path/path2/file.hbs"});

        assertNotNull(InputHandler.get("json"));
        assertNotNull(InputHandler.get("hbs"));
        assertNotNull(InputHandler.get("output"));


        assertEquals("path/path2/file.json", InputHandler.get("json"));
        assertEquals("path/path2/file.hbs", InputHandler.get("hbs"));
        assertEquals("path/path2/output.txt", InputHandler.get("output"));
    }

    @Test
    void defaultOutput() {
        InputHandler.proceed(new String[]{"-j", "path/path2/file.json", "-h", "path/path2/file.hbs"});
        assertNotNull(InputHandler.get("output"));
        assertEquals("path/path2/output.txt", InputHandler.get("output"));
    }

    @Test
    void notEnoughArguments() {
        InputHandler.proceed(new String[]{"1", "2", "3"});
        assertTrue(InputHandler.requestingHelp());
    }

    @Test
    void requestingHelp() {
        InputHandler.proceed(new String[]{"-help"});
        assertTrue(InputHandler.requestingHelp());
    }
}