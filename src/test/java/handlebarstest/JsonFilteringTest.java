package handlebarstest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import handlebarsapp.InputHandler;
import handlebarsapp.FilesHandler;
import handlebarsapp.JsonFiltering;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFilteringTest {

    @Test
    void parseJsonTest() throws IOException {
        String json = "{\"k1\":\"v1\",\"k2\":\"v2\"}";

        JsonFiltering filter = new JsonFiltering(json);
        JsonNode root = filter.getRootNode();
        assertTrue(root.has("k2"));

        assertEquals("v1", getValue(root, "k1"));
        assertEquals("v2", getValue(root, "k2"));
    }

    @Test
    void parseFromTextTest() throws IOException {

        byte[] fileBytes = Files.readAllBytes(Paths.get(System.getProperty("user.dir") +"/src/test/java/handlebarstest/test.json"));
        Charset charset = StandardCharsets.UTF_8;
        String json = new String(fileBytes, charset);

        JsonFiltering filter = new JsonFiltering(json);
        JsonNode data = filter.getRootNode().get("data");

        for (JsonNode node : data) {
            assertTrue(node.has("age"));
            assertTrue( node.get("age").isInt() );
            assertTrue( node.get("age").intValue() > 0);
        }
    }

//    @Test
//    void removeElementsTest() throws IOException {
//        //TODO: refactor in a separate method to avoid repeating
//        byte[] fileBytes = Files.readAllBytes(Paths.get(System.getProperty("user.dir") +"/src/main/resources/data.json"));
//
//        Charset charset = StandardCharsets.UTF_8;
//        String json = new String(fileBytes, charset);
//
//        JsonFiltering filter = new JsonFiltering(json);
//        JsonNode rootNode = filter.getRootNode().get("data");
//        ArrayNode lst = (ArrayNode)rootNode;
//
//        for (int i = 0; i < lst.size(); i++) {
//            try {
//                if (lst.get(i) != null && lst.get(i).has("status") && !lst.get(i).get("status").textValue().equals("Received")) {
//                    System.out.println(lst.get(i));
//                }
//            } catch (NullPointerException e) {
//
//            }
//        }
//    }

    private String getValue(JsonNode t, String k1) {
        return t.get(k1).textValue();
    }
}
