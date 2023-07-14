package leo.main;

import leo.main.config.Config;
import leo.main.setting.theme.FontConfig;
import leo.main.setting.theme.Theme;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

import static leo.main.Util.readFile;

public class TextPanel extends TypePanel {

    private List<String> texts;
    private String text;

    String typed = "";
    String typedWord = "";

    int typedWordCounter = 0;

    private int mistakeCounter;
    private int position;
    private int mistakePosition;

    private long startTime;

    private int level;

    public TextPanel(String fileName) {

        level = Config.config().getProps().getProperty("level", 1) - 1;

        texts = readFile(fileName);
        text = texts.get(level);

        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (position < 1) {
                    startTime = System.currentTimeMillis() - 400;
                }

                char newChar = e.getKeyChar() == KeyEvent.VK_ENTER ? ' ' : e.getKeyChar();

                String newTyped = typed + newChar;
                if (text.startsWith(newTyped)) {
                    typed = newTyped;
                    position++;

                    if (newChar == ' ') {
                        typedWord = "";
                        typedWordCounter++;
                        event();
                    } else {
                        typedWord += newChar;
                    }
                } else {
                    Toolkit.getDefaultToolkit().beep();
                    if (mistakePosition != position) {
                        mistakeCounter++;
                        mistakePosition = position;
                    }
                    event();
                }

                if (text.length() <= position && texts.size() > level + 1) {
                    if (mistakeCounter == 0) {
                        text = texts.get(++level);
                    }
                    mistakePosition = mistakeCounter = position = typedWordCounter = 0;
                    typedWord = typed = "";
                }

                repaint();
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

        if (position >= text.length()) {
            return;
        }
        char nextChar = text.charAt(position);
        nextChar = Objects.equals(nextChar, ' ') ? '\u0fd5' : nextChar;

        g.setColor(Color.red);
        List<String> lines1 = Util.split(typed + nextChar, getWidth());
        for (int i = 0; i < lines1.size(); i++) {
            g.drawString(lines1.get(i), 10, 40 + i*30);
        }

        g.setColor(Theme.getTheme().getTypeTextColor());
        List<String> typedLines = Util.split(typed, getWidth());
        for (int i = 0; i < typedLines.size(); i++) {
            g.drawString(typedLines.get(i), 10, 40 + i*30);
        }
    }

    @Override
    public boolean isPause() {
        return false;
    }

    @Override
    public void setPause(boolean pause) {
        //todo
    }

    private void event() {
        completedListenerList.forEach(completedListener -> completedListener.completed(new CompletedEvent() {
            @Override
            public int completed() {
                return typedWordCounter;
            }

            @Override
            public int missed() {
                return 0;
            }

            @Override
            public int mistake() {
                return mistakeCounter;
            }

            @Override
            public int speed() {
                return (int)(1000*60*position / (System.currentTimeMillis() - startTime));
            }
        }));
    }
}
