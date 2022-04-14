package leo.main.dictionary.wordnik;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WordnikList {
    private List<Wordnik> wordnikList;
    public  WordnikList() {
        wordnikList = new ArrayList<>();
    }
}
