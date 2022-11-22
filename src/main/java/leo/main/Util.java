package leo.main;

import leo.main.config.Config;

import java.awt.*;
import java.util.Properties;
import java.util.Random;

public final class Util {
    private Util() {};

    private static final String THEME = "theme";
    private static final String DOT = ".";

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private static final Random RANDOM = new Random();

    public static int randomInt(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public static String withCapitalLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static Color getColor(String name) {
        Properties props  = Config.config().getProps();
        return Color.decode(props.getProperty(THEME + DOT + props.getProperty(THEME) + DOT + name));
    }
}
