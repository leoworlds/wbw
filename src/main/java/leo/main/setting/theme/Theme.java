package leo.main.setting.theme;

import lombok.Data;

import java.awt.*;

import static leo.main.Util.getColor;

@Data
public class Theme {
    private static Theme theme;

    private Color background = getColor("background");
    private Color borderColor = getColor("border");
    private Color textColor = getColor("text");
    private Color doneBorderColor = getColor("done.border");
    private Color doneTextColor = getColor("done.text");
    private Color typeTextColor = getColor("type.text");

    public static Theme getTheme() {
        if (theme == null) {
            theme = new Theme();
        }

        return theme;
    }
}
