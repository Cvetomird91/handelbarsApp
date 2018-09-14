package handlebarstest;

import com.fasterxml.jackson.core.JsonLocation;
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

class HandlebarsUtility {

    static String compile(String json, String hbs) {
        Handlebars handlebars = new Handlebars();
        handlebars.registerHelper("json", Jackson2Helper.INSTANCE);

        return compileTemplate(handlebars, json, hbs);
    }

    private static String compileTemplate(Handlebars handlebars, String json, String hbs) {
        try {
            Template template = handlebars.compileInline(hbs);

            return applyTemplate(template, json);
        } catch (HandlebarsException he) {
            HandlebarsError error = he.getError();
            return "Error | Trouble in compiling hbs file in line " + error.line + System.lineSeparator()
                    + "Reason: " + error.reason + " in  " + System.lineSeparator() + error.evidence;
        } catch (IOException e) {
            return "Error | Trouble in compiling hbs file. Message: " + e.getMessage();
        }
    }

    private static String applyTemplate(Template template, String json) {
        try {
            Context context;

            try {
                context = genContext(json);

            } catch (JsonParseException parseExc) {
                JsonLocation loc = parseExc.getLocation();
                return "Error | Trouble in parsing the json file\r\n Json line number: " + loc.getLineNr()
                        + "\r\n Json column number: " + loc.getColumnNr() + "\r\n Message:" + parseExc.getMessage();
            } catch (JsonMappingException mappingEcx) {
                return "Error | Trouble in mapping the json file. Message:" + mappingEcx.getMessage();
            } catch (IOException io) {
                return "Error | Input trouble with the json file. Message:" + io.getMessage();
            }

            return template.apply(context);
        } catch (IOException io) {
            return "Error | Trouble in applying json file to hbs file. Message: " + io.getMessage();
        }
    }

    private static Context genContext(String json) throws IOException {
        JsonNode jsonNode = new ObjectMapper().readValue(json, JsonNode.class);

        return Context.newBuilder(jsonNode)
                .resolver(JsonNodeValueResolver.INSTANCE,
                        JavaBeanValueResolver.INSTANCE,
                        FieldValueResolver.INSTANCE,
                        MapValueResolver.INSTANCE,
                        MethodValueResolver.INSTANCE
                ).build();
    }

}
