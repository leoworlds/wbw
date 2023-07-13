package leo.main;

import leo.main.dictionary.WordEntity;
import leo.main.dictionary.file.FileDictionary;
import leo.main.setting.theme.FontConfig;
import leo.main.setting.theme.Theme;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Objects;

import static leo.main.Util.readFile;

public class TextPanel extends TypePanel {

    final String text;

    String typed = "";
    String typedWord = "";

    int typedWordCounter = 0;

    private int mistakes = 0;
    private int position = 0;

    public TextPanel(String fileName) {

        text = readFile(fileName);

        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
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
                    mistakes++;
                    event();
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
                return mistakes;
            }
        }));
    }
}
