package leo.main.dictionary.my;

import leo.main.Util;
import leo.main.dictionary.Dictionary;
import leo.main.dictionary.WordEntity;

import java.util.*;

public class CharDictionary implements Dictionary {

    private static final String WORDS = "`1234567890-=[]\\;',./~!@#$%^&*()_+{}|:\"<>?";

    private char []  chars = WORDS.toCharArray();
    private String current;

    @Override
    public WordEntity poll() {

        current = Character.toString(chars[(Util.randomInt(0, chars.length))]);

        return new WordEntity(current, Collections.singletonList(current));
    }

    @Override
    public WordEntity peek() {
        return new WordEntity(current, Collections.singletonList(current));
    }
}
