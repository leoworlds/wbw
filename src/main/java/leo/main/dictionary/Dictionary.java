package leo.main.dictionary;

public interface Dictionary {
    WordEntity poll();
    WordEntity peek();
    WordEntity get(String word);
}
