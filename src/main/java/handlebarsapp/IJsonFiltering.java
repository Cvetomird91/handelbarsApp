package handlebarsapp;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.function.Predicate;

public interface IJsonFiltering {
    void applyFilter(Predicate<JsonNode> predicate);

    JsonNode getRootNode();

    List<JsonNode> getFilteredElements();
}
