package leo.main;

public class TextWord {
    private String word;
    private int position;

    public TextWord(String word, int position) {
        this.word = word;
        this.position = position;
    }

    public String getWord() {
        return word;
    }

    public int getPosition() {
        return position;
    }

    public int getLength() {
        return word.length();
    }

    @Override
    public String toString() {
        return "[" + position + ':' + word + "]";
    }
}
