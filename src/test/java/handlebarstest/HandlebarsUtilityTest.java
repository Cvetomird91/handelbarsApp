package handlebarstest;

import com.fasterxml.jackson.databind.JsonNode;
import handlebarsapp.HandlebarsUtility;
import handlebarsapp.JsonFiltering;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HandlebarsUtilityTest {

    @Test void compile(){
        String result = HandlebarsUtility.compile(
                "{\"potrebitel\" : {\"ime\":\"John\", \"godini\" : \"69\"} }",
                "Zdr {{potrebitel.ime}} na {{potrebitel.godini}}"
        );
        String expected = "Zdr John na 69";
        assertEquals(expected, result);

        System.out.println(result);
    }

    @Test
    void compileFromFile() throws IOException {
        Charset charset = StandardCharsets.UTF_8;

        String json = HandlebarsUtility.readContentFromFile("/src/test/resources/data.json", charset);
        String tpl = HandlebarsUtility.readContentFromFile("/src/test/resources/list.hbs", charset);

        String[] patterns = {"Payment ID", "Created", "Status", "Value1", "transactionID", "paymentProfileID"};

        String result = HandlebarsUtility.compile(
                json, tpl
        );

        JsonFiltering filtering = new JsonFiltering(json);

        JsonNode root = filtering.getRootNode().get("payments");

        for (String s : patterns) {

            Pattern pattern = Pattern.compile(s, Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(result);

            int count = Stream.iterate(0, i -> i + 1)
                    .filter(i -> !matcher.find())
                    .findFirst()
                    .get();

            assertEquals(root.size(), count);
        }
    }

}