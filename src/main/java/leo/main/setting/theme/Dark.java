package leo.main.setting.theme;

import java.awt.*;

public class Dark implements Theme {
    @Override
    public Color getBackground() {
        return Color.black;
    }

    @Override
    public Color getBorderColor() {
        return Color.blue;
    }

    @Override
    public Color getTextColor() {
        return Color.blue;
    }

    @Override
    public Color getDoneBorderColor() {
        return Color.MAGENTA;
    }

    @Override
    public Color getDoneTextColor() {
        return Color.green;
    }
}
