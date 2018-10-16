package handlebarstest;

import handlebarsapp.HandlebarsUtility;
import org.junit.jupiter.api.Test;

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

}