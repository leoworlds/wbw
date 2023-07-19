package leo.main.dictionary.wordnik;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import leo.main.dictionary.Dictionary;
import leo.main.dictionary.WordEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class WordnikDictionary implements Dictionary {
    private static final String URL = "http://api.wordnik.com:80/v4/words.json/randomWords?hasDictionaryDef=true&minCorpusCount=0&minLength=5&maxLength=15&limit=100&api_key=a2a73e7b926c924fad7001ca3111acd55af2ffabf50eb4ae5";

    private Queue<String> words = new LinkedList<>();

    public WordnikDictionary() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);

        Wordnik [] list = new Wordnik[0];
        try {
            list = new ObjectMapper().readValue(response.getBody(), Wordnik[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(list));

        for (Wordnik wordnik : list) {
            words.add(wordnik.getWord());
        }
    }

    @Override
    public WordEntity poll() {
        return new WordEntity(words.poll(), new ArrayList<>());
    }

    @Override
    public WordEntity peek() {
        return new WordEntity(words.peek(), new ArrayList<>());
    }

    @Override
    public WordEntity get(String word) {
        return null;
    }
}
