package handlebarsapp;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public interface IJsonFiltering {
    void applyFilter(Predicate<JsonNode> predicate, String rootNodeKey) throws IOException;

    JsonNode getRootNode();

    List<JsonNode> getFilteredElements(String parameter);

    public JsonNode getFilteredRootNode();
}
