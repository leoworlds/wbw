package leo.main.dictionary;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WordEntity {
    private String word;
    private List<String> definitions;
}
