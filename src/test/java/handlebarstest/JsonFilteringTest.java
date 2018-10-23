package handlebarstest;

import com.fasterxml.jackson.databind.JsonNode;
import handlebarsapp.HandlebarsUtility;
import handlebarsapp.JsonFiltering;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    @Test
    void applyFilter() throws IOException {
        Charset charset = StandardCharsets.UTF_8;
        String json = HandlebarsUtility.readContentFromFile("/src/test/resources/data.json", charset);
        String jsonArray = HandlebarsUtility.readContentFromFile("/src/test/resources/array.json", charset);

        JsonFiltering filter1 = new JsonFiltering(json);
        filter1.applyFilter((JsonNode p) -> p.get("status").asText() == null || !p.get("status").asText().equals("Received"), "payments");

        for (JsonNode node : filter1.getFilteredRootNode()) {
            assertTrue(node.has("status"));
            assertTrue( node.get("status").isTextual() || node.get("status").isNull());
            assertFalse( node.get("status").asText().equals("Received"));
        }

        JsonFiltering filter2 = new JsonFiltering(jsonArray);
        filter2.applyFilter((JsonNode p) -> !p.get("value1").isNull(), null);
        System.out.println(filter2.getFilteredRootNode());

        for (JsonNode node : filter2.getFilteredRootNode()) {
            assertTrue(node.has("value1"));
            assertFalse(node.get("value1").isNull());
        }
    }

    private String getValue(JsonNode t, String k1) {
        return t.get(k1).textValue();
    }
}
