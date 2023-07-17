package leo.main.dictionary.file;

import org.junit.jupiter.api.Test;

import static leo.main.Util.getWord;
import static leo.main.Util.withCapitalLetter;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilTest {

    @Test
    void withCapitalLetter_test() {
        assertEquals("Test", withCapitalLetter("teSt"));
    }

    @Test void getWord_test_middle() {
        assertEquals("asdfg", getWord("qwert asdfg zxcvb", 7));
    }

    @Test void getWord_test_first() {
        assertEquals("qwert", getWord("qwert asdfg zxcvb", 2));
    }

    @Test void getWord_test_last() {
        assertEquals("zxcvb", getWord("qwert asdfg zxcvb", 15));
    }
}
