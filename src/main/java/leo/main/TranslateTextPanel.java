package leo.main;

import leo.main.config.Config;
import leo.main.setting.theme.FontConfig;
import leo.main.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;

import static leo.main.Util.SPACE;

public class TranslateTextPanel extends JComponent {

    private List<String> texts;
    private String text;
    private int level;

    private List<String> lines;
    private List<String> dirtyWords;

    public TranslateTextPanel() {
        level = Config.config().getProps().getProperty("level", 1) - 1;

        texts = FileUtils.read(Config.config().getProps().getProperty("file_ua"));
        text = texts.get(level);
        dirtyWords = Arrays.asList(text.split(SPACE));
    }

    @Override
    public void paint(Graphics gg) {
        Graphics2D g = (Graphics2D) gg;
        g.setFont(FontConfig.getFontConfig().getPlainTextFont());
        g.setColor(Color.gray);

        //todo
        if (lines == null) {
            lines = Util.split(dirtyWords, getWidth());
        }

        for (int i = 0; i < lines.size(); i++) {
            String string = lines.get(i);

            int h = (int)Util.getStringBounds(string).getHeight();

            int x = 10;
            int y = h + i*h;

            for (int j = 0; j < string.length(); j++) {
                char nextChar = string.charAt(j);

                String sChar = String.valueOf(nextChar);
                Rectangle2D rect = Util.getStringBounds(sChar);

                g.drawString(sChar, x + j*(int)rect.getWidth(), y);
            }
        }
    }

    public void nextLevel() {
        text = texts.get(++level);
        dirtyWords = Arrays.asList(text.split(SPACE));
        lines = Util.split(dirtyWords, getWidth());
        repaint();
    }
}
