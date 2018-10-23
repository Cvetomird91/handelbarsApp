package handlebarsapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.function.Predicate;

public class JsonFiltering implements IJsonFiltering {

    private JsonNode nodeTree;
    private ObjectMapper mapper = new ObjectMapper();
    private List<JsonNode> filteredElements;

    /*
     * parse valid JSON string into member variable
     */
    public JsonFiltering(String json) throws IOException {
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
    public void applyFilter(Predicate<JsonNode> predicate) throws IOException {
        JsonNode[] data = mapper.readValue(nodeTree.get("payments").toString(), JsonNode[].class);
        filteredElements = new ArrayList<JsonNode>(Arrays.asList(data));

        if (predicate != null)
            filteredElements.removeIf(predicate);
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

    public List<JsonNode> getFilteredElements(String parameter) {
        return this.filteredElements;
    }

    public JsonNode getFilteredRootNode() {
        JsonNode node = mapper.convertValue(filteredElements, JsonNode.class);
        return node;
    }
}
