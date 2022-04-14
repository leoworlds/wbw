package leo.main.setting.theme;

public class ThemeFactory {

    private static Theme theme;

    private static Class<? extends Theme> clazz = Dark.class;

    public static Theme theme() {
        if (theme == null) {
            try {
                theme = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Theme hasn't instantiated");
            }
        }
        return theme;
    }

    public static Class<? extends Theme> getClazz() {
        return clazz;
    }

    public static void setClazz(Class<? extends Theme> clazz) {
        ThemeFactory.clazz = clazz;
    }
}
