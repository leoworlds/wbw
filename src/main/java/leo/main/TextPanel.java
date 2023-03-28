package leo.main;

import leo.main.setting.theme.FontConfig;
import leo.main.setting.theme.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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


        Rectangle2D spaceRect = g.getFont().getStringBounds(SPACE, FONT_RENDER_CONTEXT);

        List<String> lines = new ArrayList<>();

        String line = "";
        int lineWidth = 0;

        for (Word word : splitWords(g, text)) {
            lineWidth += spaceRect.getWidth() + word.rect.getWidth();
            if (lineWidth > getWidth()) {
                lines.add(line);
                line = "";
                lineWidth = 0;
            }
            line += SPACE + word.text;
        }

        for (int i = 0; i < lines.size(); i++) {
            g.drawString(lines.get(i), 0, 40 + i*30);
        }
    }

    private static List<Word> splitWords(Graphics2D g, String text) {
        return Arrays.stream(text.split(SPACE))
                .map(word -> new Word(word, g.getFont().getStringBounds(word, FONT_RENDER_CONTEXT)))
                .collect(Collectors.toList());
    }

    private static class Word {

        Word(String text, Rectangle2D rect) {
            this.text = text;
            this.rect = rect;
        }

        String text;
        Rectangle2D rect;
    }
}
