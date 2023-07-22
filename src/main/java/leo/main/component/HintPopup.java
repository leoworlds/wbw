package leo.main.component;

import leo.main.setting.theme.FontConfig;

import javax.swing.*;
import java.awt.*;

public class HintPopup extends JPopupMenu {

    private JLabel label = new JLabel();

    public HintPopup() {
        setFocusable(false);
        setBackground(Color.orange);

        label.setFont(FontConfig.getFontConfig().getPlainTextFont());
        add(label);
    }

    public void showText(Component invoker, String text, int x, int y) {
        label.setText(text);
        show(invoker, x, y);
    }

    public void hideText() {
       setVisible(false);
    }
}
