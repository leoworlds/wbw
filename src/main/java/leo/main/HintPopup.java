package leo.main;

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

    public void setText(String text) {
        label.setText(text);
    }
}
