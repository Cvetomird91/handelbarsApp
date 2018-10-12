package handlebarstest;

import handlebarsapp.HandlebarsUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HandlebarsUtilityTest {

    @Test void compile(){
        assertEquals("Zdr John na 69", HandlebarsUtility.compile(
                "{\"potrebitel\" : {\"ime\":\"John\", \"godini\" : \"69\"} }",
                "Zdr {{potrebitel.ime}} na {{potrebitel.godini}}"
        ));
    }

}