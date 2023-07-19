package leo.main.dictionary;

import leo.main.Util;
import leo.main.utils.FileUtils;

import java.util.*;

public class PropertyDictionary implements Dictionary {

    public Map<String, List<String>> map = new HashMap<>();
    List<Map.Entry<String, List<String>>> list;

    public PropertyDictionary() {
        for (String line : FileUtils.read("dictionary.txt")) {
            String [] keyValues = line.split("=");
            map.put(keyValues[0], Arrays.asList(keyValues[1].split(",")));
        }
        list = new ArrayList<>(map.entrySet());
    }

    @Override
    public WordEntity poll() {
        return peek();
    }

    @Override
    public WordEntity peek() {
        Map.Entry<String, List<String>> entry = list.get(Util.randomInt(0, list.size()));
        return new WordEntity(entry.getKey(), entry.getValue());
    }

    @Override
    public WordEntity get(String word) {
        return new WordEntity(word, map.get(word.toLowerCase()));
    }
}
