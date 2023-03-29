package leo.main;

import leo.main.setting.theme.FontConfig;
import leo.main.setting.theme.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import static leo.main.Util.readFile;

public class TextPanel extends JComponent {

    String text1 = "0000000000 1111111111 2222222222 333 4444444444 5555 666666666666666666666666666666666 777 88888888888888888888888 99999";
    String text = readFile();

    String typed = "";

    public TextPanel() {
        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char newChar = e.getKeyChar() == KeyEvent.VK_ENTER ? ' ' : e.getKeyChar();

                String newTyped = typed + newChar;
                if (text.startsWith(newTyped)) {
                    typed = newTyped;
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }

                repaint();

                System.out.println(typed);
            }
        });
    }

    @Override
    public void paint(Graphics gg) {
        Graphics2D g = (Graphics2D) gg;
        g.setFont(FontConfig.getFontConfig().getPlainTextFont());

        g.setColor(Theme.getTheme().getTextColor());
        List<String> lines = Util.split(text, getWidth());
        for (int i = 0; i < lines.size(); i++) {
            g.drawString(lines.get(i), 10, 40 + i*30);
        }

        g.setColor(Theme.getTheme().getTypeTextColor());
        List<String> typedLines = Util.split(typed, getWidth());
        for (int i = 0; i < typedLines.size(); i++) {
            g.drawString(typedLines.get(i), 10, 40 + i*30);
        }
    }
}
