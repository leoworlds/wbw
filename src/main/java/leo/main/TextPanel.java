package leo.main;

import leo.main.setting.theme.FontConfig;
import leo.main.setting.theme.Theme;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TextPanel extends JComponent {

    String text1 = "I have a public repository on GitHub. I want to replicate/copy it and work on a new project" +
            " based on this repository, but I don't want to affect how it is now. " +
            "I tried forking it using the GitHub UI but it didn't do anything.";

    String text = "0000000000 1111111111 2222222222 333 4444444444 5555 666666666666666666666666666666666 777 88888888888888888888888 99999";

    @Override
    public void paint(Graphics gg) {
        Graphics2D g = (Graphics2D) gg;
        g.setFont(FontConfig.getFontConfig().getPlainTextFont());
        g.setColor(Theme.getTheme().getTextColor());

        List<String> lines = Util.split(text, getWidth());

        for (int i = 0; i < lines.size(); i++) {
            g.drawString(lines.get(i), 0, 40 + i*30);
        }
    }
}
