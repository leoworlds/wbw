package leo.main.dictionary.file;

import leo.main.Util;
import leo.main.dictionary.Dictionary;
import leo.main.dictionary.WordEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileDictionary implements Dictionary {

    private static final Pattern PATTERN_PARSE_DEFINITION = Pattern.compile("(?<=&quot;)(.*)(?=&quot;)");

    private static final String FILE_NAME = "/slovnyk_en-ru.xml";

    private int current = 0;
    private List<WordEntity> words;

    public FileDictionary() {
        try {
            words = parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    List<WordEntity> parse() throws Exception {

        List<WordEntity> words = new ArrayList<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(this.getClass().getResourceAsStream(FILE_NAME));
        doc.getDocumentElement().normalize();

        NodeList list = doc.getElementsByTagName("article");

        for (int temp = 0; temp < list.getLength(); temp++) {

            Node node = list.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node;

                String key = element.getElementsByTagName("key").item(0).getTextContent();
                String definition = element.getElementsByTagName("definition").item(0).getTextContent();

                words.add(new WordEntity(Util.withCapitalLetter(key), parseDefinition(definition)));
            }
        }

        return words;
    }

    @Override
    public WordEntity poll() {
        if (current == 0) {
            peek();
        }

        return words.remove(current);
    }

    @Override
    public WordEntity peek() {
        current = Util.randomInt(0, words.size());
        return words.get(current);
    }

    public static List<String> parseDefinition(String definition) {
        Matcher matcher = PATTERN_PARSE_DEFINITION.matcher(definition);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(Util.withCapitalLetter(matcher.group()));
        }
        return list;
    }
}
