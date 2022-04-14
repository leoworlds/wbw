package leo.main.setting.theme;

import java.awt.*;

public class Light implements Theme {
    private Color borderColor = Color.gray;
    private Color textColor = Color.darkGray;

    private Color doneBorderColor = Color.green;
    private Color doneTextColor = Color.darkGray;

    @Override
    public Color getBackground() {
        return Color.white;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public Color getDoneBorderColor() {
        return doneBorderColor;
    }

    public Color getDoneTextColor() {
        return doneTextColor;
    }
}
