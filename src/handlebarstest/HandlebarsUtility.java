package handlebarstest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.*;
import com.github.jknack.handlebars.context.FieldValueResolver;
import com.github.jknack.handlebars.context.JavaBeanValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.context.MethodValueResolver;

import java.io.IOException;

import static handlebarstest.FilesHandler.getHbs;
import static handlebarstest.FilesHandler.getJson;

public class HandlebarsUtility {

    private static String output = "";
    private static boolean trouble = false;

    public static boolean compile(String json, String hbs) {

        try {
            JsonNode jsonNode = new ObjectMapper().readValue(getJson(), JsonNode.class);
            Handlebars handlebars = new Handlebars();
            handlebars.registerHelper("json", Jackson2Helper.INSTANCE);

            Context context = Context
                    .newBuilder(jsonNode)
                    .resolver(JsonNodeValueResolver.INSTANCE,
                            JavaBeanValueResolver.INSTANCE,
                            FieldValueResolver.INSTANCE,
                            MapValueResolver.INSTANCE,
                            MethodValueResolver.INSTANCE
                    )
                    .build();

            try {
                Template template = handlebars.compileInline(getHbs());

                try {
                    output = template.apply(context);
                    return true;
                } catch (IOException io) {
                    output = "Trouble in applying json file to hbs file. Message: " + io.getMessage();
                }

            } catch (HandlebarsException he) {
                HandlebarsError error = he.getError();
                output = "Trouble in compiling hbs file in line " + error.line + System.lineSeparator()
                        + "Reason: " + error.reason + " in  " + System.lineSeparator() + error.evidence;
            } catch (IOException e) {
                output = "Trouble in compiling hbs file. Message: " + e.getMessage();
            }

        } catch (JsonParseException parseExc) {
            output = "Trouble in parsing the json file. Message:" + parseExc.getMessage();
        } catch (JsonMappingException mappingEcx) {
            output = "Trouble in mapping the json file. Message:" + mappingEcx.getMessage();
        } catch (IOException io) {
            output = "Input trouble with the json file. Message:" + io.getMessage();
        }

        trouble = true;

        return false;
    }

    public static String getOutput() {
        return output;
    }

}
