package leo.main.dictionary.file;

import leo.main.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilTest {

    @Test
    public void withCapitalLetter_test() {
        Assertions.assertEquals("Test", Util.withCapitalLetter("teSt"));
    }

}
