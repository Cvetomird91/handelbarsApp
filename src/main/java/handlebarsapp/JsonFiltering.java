package handlebarsapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class JsonFiltering {

    public JsonFiltering(String json) {
        super();
    }

    public List<JsonNode> applyFilter(Predicate<JsonNode> predicate) {
        return new ArrayList<JsonNode>();
    }

    public JsonNode getNode() throws IOException {
        return new TextNode("");
    }

    public Object getElements() {
        return new Object();
    }
}
