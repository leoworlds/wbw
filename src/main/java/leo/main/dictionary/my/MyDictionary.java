package leo.main.dictionary.my;

import leo.main.dictionary.Dictionary;
import leo.main.dictionary.WordEntity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MyDictionary implements Dictionary {

    private Queue<String> words = new LinkedList<>();

    public MyDictionary() {
        words.add("aaa");
        words.add("Curiously");
        words.add("Funny");
        words.add("Leonardo");
        words.add("aaa");
    }

    @Override
    public WordEntity poll() {
        return new WordEntity(words.poll(), new ArrayList<>());
    }

    @Override
    public WordEntity peek() {
        return new WordEntity(words.peek(), new ArrayList<>());
    }
}
