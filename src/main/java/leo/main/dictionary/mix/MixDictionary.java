package leo.main.dictionary.mix;

import leo.main.Util;
import leo.main.dictionary.Dictionary;
import leo.main.dictionary.WordEntity;

public class MixDictionary implements Dictionary {

    private Dictionary [] dictionaries;

    int index;

    public MixDictionary(Dictionary ... dictionaries) {
        this.dictionaries = dictionaries;
    }

    @Override
    public WordEntity poll() {
        index = Util.randomInt(0, dictionaries.length);
        return dictionaries[index].poll();
    }

    @Override
    public WordEntity peek() {
        return dictionaries[index].peek();
    }

    @Override
    public WordEntity get(String word) {
        return null;
    }
}
