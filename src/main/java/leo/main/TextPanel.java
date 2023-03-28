package leo.main;

import leo.main.setting.theme.FontConfig;
import leo.main.setting.theme.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class TextPanel extends JComponent {

//    String text = "I have a public repository on GitHub. I want to replicate/copy it and work on a new project" +
//            " based on this repository, but I don't want to affect how it is now. " +
//            "I tried forking it using the GitHub UI but it didn't do anything.";

    String text = "0000000000 1111111111 2222222222 333 4444444444 5555 666666666666666666666666666666666 777 88888888888888888888888 99999";

    private static final String SPACE = " ";
    private static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(new AffineTransform(), true, true);

    @Override
    public void paint(Graphics gg) {
        Graphics2D g = (Graphics2D) gg;
        g.setFont(FontConfig.getFontConfig().getPlainTextFont());
        g.setColor(Theme.getTheme().getTextColor());

        List<String> lines = new ArrayList<>();

        String line = "";
        String newLine;

        for (String word : text.split(SPACE)) {
            newLine = line + word + SPACE;

            double newLineWidth = g.getFont().getStringBounds(newLine, FONT_RENDER_CONTEXT).getWidth();

            if (newLineWidth > getWidth()) {
                lines.add(line);
                line = word + SPACE;
            } else {
                line = newLine;
            }
        }

        lines.add(line);

        for (int i = 0; i < lines.size(); i++) {
            g.drawString(lines.get(i), 0, 40 + i*30);
        }
    }
}
