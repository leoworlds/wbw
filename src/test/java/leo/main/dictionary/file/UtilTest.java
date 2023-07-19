package leo.main.dictionary.file;

import leo.main.Util;
import org.junit.jupiter.api.Test;

import static leo.main.Util.getWord;
import static leo.main.Util.withCapitalLetter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void wordChar_upper_test() {
        assertTrue(Util.wordChar('A'));
    }

    @Test
    void wordChar_lower_test() {
        assertTrue(Util.wordChar('a'));
    }

    @Test
    void test() {
        System.out.println("\'Value\',".replaceAll("[^A-Za-z?]", ""));
    }
}
