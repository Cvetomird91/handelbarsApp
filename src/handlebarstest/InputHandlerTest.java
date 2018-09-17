package handlebarstest;

import handlebarsapp.InputHandler;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputHandlerTest {

    @Test
    void processArgs() {
        String[] args = new String[] {
                "-j", "path\\path2\\file.json",
                "-h", "path\\path2\\file.hbs",
        };

        InputHandler inputHandler = new InputHandler(args);
        inputHandler.processArgs();

        Map<String, String> parsedInputMap = inputHandler.getParsedInputMap();

        assertEquals("path\\path2\\file.json", parsedInputMap.get("json"));
        assertEquals("path\\path2\\file.hbs", parsedInputMap.get("hbs"));
        assertEquals("path\\path2\\output.txt", parsedInputMap.get("output"));

    }
}