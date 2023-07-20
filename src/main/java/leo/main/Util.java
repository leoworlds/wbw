package leo.main;

import leo.main.setting.theme.FontConfig;

import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class Util {
    private Util() {}

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    public static final String SPACE = " ";
    private static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(new AffineTransform(), true, true);

    private static final Random RANDOM = new Random();

    public static int randomInt(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public static String withCapitalLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static List<TextWord> split(String text) {
        char[] chars = text.toCharArray();
        List<TextWord> words = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int first = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' ') {
                words.add(new TextWord(sb.toString(), first));
                sb.setLength(0);
                first = i;
            } else {
                sb.append(chars[i]);
            }
        }
        return words;
    }

    //todo remove
    public static List<String> split(String text, int width) {
        return split(Arrays.asList(text.split(SPACE)), width);
    }

    public static List<String> split(List<String> words, int width) {
        List<String> lines = new ArrayList<>();

        String line = "";
        String newLine;

        for (String word : words) {
            newLine = line + word + SPACE;

            double newLineWidth = getStringBounds(newLine).getWidth();

            if (newLineWidth > width) {
                lines.add(line);
                line = word + SPACE;
            } else {
                line = newLine;
            }
        }

        lines.add(line);

        return lines;
    }

    public static TextWord getWord(String string, int index) {
        String word = String.valueOf(string.charAt(index));
        char cFirst = ' ';
        char cLast = ' ';
        int iFirst = 1;
        int iLast = 1;
        do {
            if (index - iFirst >= 0) {
                cFirst = string.charAt(index - iFirst);
                if (wordChar(cFirst)) {
                    word = cFirst + word;
                    iFirst++;
                }
            } else {
                cFirst = ' ';
            }

            if (index + iLast < string.length()) {
                cLast = string.charAt(index + iLast);
                if (wordChar(cLast)) {
                    word = word + cLast;
                    iLast++;
                }
            } else {
                cLast = ' ';
            }
        } while (wordChar(cFirst) || wordChar(cLast));

        return new TextWord(word, index - iFirst + 1);
    }

    public static boolean wordChar(char c) {
        return String.valueOf(c).matches("[A-Za-z]");
    }

    public static Rectangle2D getStringBounds(String string) {
        return FontConfig.getFontConfig().getPlainTextFont().getStringBounds(string, FONT_RENDER_CONTEXT);
    }

}
