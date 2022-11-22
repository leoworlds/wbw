package leo.main.setting.theme;

import leo.main.config.Config;
import lombok.Data;

import java.awt.*;
import java.util.Properties;

@Data
public class Theme {

    private static Theme theme;

    private static final String THEME = "theme";
    private static final String DOT = ".";

    private Color background = getColor("background");
    private Color borderColor = getColor("border");
    private Color textColor = getColor("text");
    private Color doneBorderColor = getColor("done.border");
    private Color doneTextColor = getColor("done.text");
    private Color typeTextColor = getColor("type.text");

    private static Color getColor(String name) {
        Properties props  = Config.config().getProps();
        return Color.decode(props.getProperty(THEME + DOT + props.getProperty(THEME) + DOT + name));
    }

    public static Theme getTheme() {
        if (theme == null) {
            theme = new Theme();
        }

        return theme;
    }
}
