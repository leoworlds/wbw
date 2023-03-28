package leo.main.setting.theme;

import leo.main.config.Config;
import leo.main.config.Props;

import java.awt.*;

public class FontConfig {

    private static Font font;
    private static FontConfig fontConfig;

    private final Props props = Config.config().getProps();

    private FontConfig() {
        font = new Font(
                props.getProperty("theme.font.plain.name", "Courier New"),
                props.getProperty("theme.font.plain.style", 1),
                props.getProperty("theme.font.plain.size", 24));
    }

    public static FontConfig getFontConfig() {
        if (fontConfig == null) {
            fontConfig = new FontConfig();
        }

        return fontConfig;
    }

    public Font getPlainTextFont() {
        return font;
    }
}
