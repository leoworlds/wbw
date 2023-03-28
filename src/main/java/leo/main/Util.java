package leo.main;

import java.util.Random;

public final class Util {
    private Util() {};

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private static final Random RANDOM = new Random();

    public static int randomInt(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public static String withCapitalLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
