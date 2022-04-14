package leo.main.dictionary.file;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileDictionaryTest {

    @Test
    public void parseDefinition_test() {
        String s = "<k>wood</k>\n" +
                "WOOD\n" +
                "  &quot;One&quot;\n" +
                "<k>wood</k>\n" +
                "WOOD\n" +
                "  &quot;Two&quot;\n" +
                "<k>wood</k>\n" +
                "WOOD\n" +
                "  &quot;Three&quot;";

        List<String> list = FileDictionary.parseDefinition(s);
        assertEquals("One", list.get(0));
        assertEquals("Two", list.get(1));
        assertEquals("Three", list.get(2));
    }
}
