package handlebarstest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        byte[] fileBytes = Files.readAllBytes(Paths.get("/home/scalefocus.com/tsvetomir.denchev/handelbarsApp/src/test/java/handlebarstest/test.json"));
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

    private String getValue(JsonNode t, String k1) {
        return t.get(k1).textValue();
    }
}
