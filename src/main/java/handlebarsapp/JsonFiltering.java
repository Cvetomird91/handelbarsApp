package handlebarsapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class JsonFiltering implements IJsonFiltering {

    private JsonNode nodeTree;

    /*
     * parse valid JSON string into member variable
     */
    public JsonFiltering(String json) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            nodeTree = mapper.readTree(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * the default implementation should not mutate the input and it
     * should store it in a member variable w/out changes
     */
    public void applyFilter(Predicate<JsonNode> predicate) {
        JsonNode nodeTree;
    }

    public Object getElements() {
        return this.nodeTree;
    }

    public JsonNode getRootNode() {
        return this.nodeTree;
    }

    public String toString() {
        return nodeTree.toString();
    }

    public List<JsonNode> getFilteredElements() {
        return new ArrayList<JsonNode>();
    }
}
