package leo.main;

import leo.main.setting.theme.FontConfig;
import lombok.SneakyThrows;

import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Util {
    private Util() {};

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private static final String SPACE = " ";
    private static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(new AffineTransform(), true, true);

    private static final Random RANDOM = new Random();

    public static int randomInt(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public static String withCapitalLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static List<String> split(String text, int width) {
        List<String> lines = new ArrayList<>();

        String line = "";
        String newLine;

        for (String word : text.split(SPACE)) {
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

    public static Rectangle2D getStringBounds(String string) {
        return FontConfig.getFontConfig().getPlainTextFont().getStringBounds(string, FONT_RENDER_CONTEXT);
    }

    @SneakyThrows
    public static String readFile() {
        return Files.readAllLines(Paths.get("src/main/resources/text.txt")).get(0);
    }
}
