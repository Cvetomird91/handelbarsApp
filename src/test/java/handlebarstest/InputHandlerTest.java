package handlebarstest;

import com.fasterxml.jackson.databind.JsonNode;
import handlebarsapp.FilesHandler;
import handlebarsapp.InputHandler;
import handlebarsapp.JsonFiltering;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test void notEnoughArgs(){
        String[] args = new String[] {
                "-j", "path\\path2\\file.json"};

        InputHandler inputHandler = new InputHandler(args);
        inputHandler.processArgs();

        assertTrue(inputHandler.isInvalid());
    }

    @Test void requestingHelp(){
        InputHandler inputHandler = new InputHandler(new String[]{"/help"});
        inputHandler.processArgs();
        assertTrue(inputHandler.isRequestingHelp());
    }

    @Test void invalidArguments(){
        String[] args = new String[] {
                "-j", "path\\path2\\file.json",
                "-j", "path\\path2\\file.json",
        };

        InputHandler inputHandler = new InputHandler(args);
        inputHandler.processArgs();

        assertTrue(inputHandler.isInvalid());
    }

    @Test void newOption(){
        String[] args = new String[] {
                "-i", "path\\path2\\file.iso",
                "-d", "pasdadawdl",
        };

        InputHandler inputHandler = new InputHandler(args);
        inputHandler.processArgs();

        assertTrue(inputHandler.isInvalid());
        assertEquals(inputHandler.getErrorMessage(), "Invalid arguments, json or hbs file missing");
    }

    @Test void newArgumentI(){
        String[] args = new String[] {
                "-j", "path\\path2\\file.json",
                "-i", "path\\path2\\file.iso",
                "-h", "path\\path2\\file.hbs"

        };

        InputHandler inputHandler = new InputHandler(args);
        inputHandler.processArgs();

        assertTrue(inputHandler.isInvalid());
        assertEquals("No such argument \"-i\"", inputHandler.getErrorMessage());

    }

    @Test void noArgument(){
        InputHandler inputHandler = new InputHandler(new String[]{});
        inputHandler.processArgs();
        assertTrue(inputHandler.isInvalid());
    }

    @Test void outputOption(){
        String[] args = new String[] {
                "-j", "path\\path2\\file.json",
                "-h", "path\\path2\\file.hbs",
                "-o", "otherpath\\otherpath2\\output.txt"
        };

        InputHandler inputHandler = new InputHandler(args);
        inputHandler.processArgs();

        Map<String, String> parsedInputMap = inputHandler.getParsedInputMap();

        assertEquals("path\\path2\\file.json", parsedInputMap.get("json"));
        assertEquals("path\\path2\\file.hbs", parsedInputMap.get("hbs"));
        assertEquals("otherpath\\otherpath2\\output.txt", parsedInputMap.get("output"));
    }

    @Test void testRegEx(){
        assertTrue("/json".matches("([/-]?)json\\z"));
        assertTrue("-json".matches("([/-]?)json\\z"));
        assertTrue("json".matches("([/-]?)json\\z"));
        assertFalse("/jsonnn".matches("([/-]?)json\\z"));
        assertFalse("+json".matches("([/-]?)json\\z"));


    }

    @Test void fillMap(){
        String[] argsFull = new String[] {
                "/json", "path\\path2\\file.json",
                "/hbs", "path\\path2\\file.hbs",
                "/output", "otherpath\\otherpath2\\output.txt"
        };

        InputHandler inputHandlerFull = new InputHandler(argsFull);
        inputHandlerFull.processArgs();

        Map<String, String> parsedInputMapFull = inputHandlerFull.getParsedInputMap();

        assertFalse(inputHandlerFull.isInvalid());
        assertEquals("path\\path2\\file.json", parsedInputMapFull.get("json"));
        assertEquals("path\\path2\\file.hbs", parsedInputMapFull.get("hbs"));
        assertEquals("otherpath\\otherpath2\\output.txt", parsedInputMapFull.get("output"));

        String[] argsShort = new String[] {
                "-j", "path\\path2\\file.json",
                "path\\path2\\file.hbs" ,"-h",
        };

        InputHandler inputHandlerShort = new InputHandler(argsShort);
        inputHandlerShort.processArgs();

        Map<String, String> parsedInputMapShort = inputHandlerShort.getParsedInputMap();
        assertTrue(inputHandlerShort.isInvalid());
//
//        assertEquals("path\\path2\\file.json", parsedInputMapShort.get("json"));
//        assertEquals("path\\path2\\file.hbs", parsedInputMapShort.get("hbs"));
//        assertEquals("otherpath\\otherpath2\\output.txt", parsedInputMapShort.get("output"));
    }

//    @Test void jsonTest(){
//        JFileChooser chooser = new JFileChooser();
//        chooser.setCurrentDirectory(new File("."));
//        chooser.showOpenDialog(null);
//        File file = chooser.getSelectedFile();
//        FilesHandler filesHandler = new FilesHandler();
//        filesHandler.handleReadableFile("test", file.getPath());
//        JsonFiltering filtering = null;
//        try {
//            filtering = new JsonFiltering(filesHandler.getReadFile("test"));
//            List<JsonNode> list = filtering.applyFilter(node -> node.get("age").asInt() > 15);
//            JsonNode node = filtering.getNode();
//
//            System.out.println(filtering.getNode().toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}