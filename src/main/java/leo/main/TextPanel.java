package leo.main;

import leo.main.config.Config;
import leo.main.setting.theme.FontConfig;
import leo.main.setting.theme.Theme;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
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

        List<String> lines = Util.split(text, getWidth());

        int drawPosition = 0;

        for (int i = 0; i < lines.size(); i++) {
            g.setColor(Theme.getTheme().getTextColor());

            String string = lines.get(i);

            int h = (int)Util.getStringBounds(string).getHeight();

            int x = 10;
            int y = h + i*h;

            for (int j = 0; j < string.length(); j++) {
                char nextChar = string.charAt(j);

                String sChar = String.valueOf(nextChar);
                Rectangle2D rect = Util.getStringBounds(sChar);

                drawPosition++;

                if (drawPosition > position + 1) {
                    g.setColor(Theme.getTheme().getTextColor());
                } else if (drawPosition < position + 1){
                    g.setColor(Theme.getTheme().getTypeTextColor());
                } else {
                    g.setColor(Color.RED);
                    g.drawRect(x + j*(int)rect.getWidth(), y-h+h/4, (int)rect.getWidth(), (int)rect.getHeight());
                    g.setColor(Theme.getTheme().getTextColor());
                }

                g.drawString(sChar, x + j*(int)rect.getWidth(), y);
            }
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
