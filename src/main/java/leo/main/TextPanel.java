package leo.main;

import leo.main.config.Config;
import leo.main.dictionary.PropertyDictionary;
import leo.main.dictionary.WordEntity;
import leo.main.setting.theme.FontConfig;
import leo.main.setting.theme.Theme;
import leo.main.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static leo.main.Util.*;

public class TextPanel extends TypePanel {

    private List<String> texts;
    private String text;

    private String typed = "";
    private String typedWord = "";

    private int typedWordCounter = 0;

    private int mistakeCounter;
    private int position;
    private int mistakePosition;

    private long startTime;

    private int level;

    private List<String> lines;
    private List<String> dirtyWords;

    private TextWord selectedWord;
    private int selectedLine;

    private int mouseX;
    private int mouseY;
    private boolean mousePressed;

    private int xPosition;
    private int yPosition;

    HintPopup hintPopup = new HintPopup();

    private PropertyDictionary dictionary = new PropertyDictionary();

    public TextPanel(String fileName) {

        level = Config.config().getProps().getProperty("level", 1) - 1;

        texts = FileUtils.read(fileName);
        text = texts.get(level);
        dirtyWords = Arrays.asList(text.split(SPACE));

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


                        WordEntity wordEntity = dictionary.get(typedWord.replaceAll("[^A-Za-z?]", ""));
                        if (wordEntity.getDefinitions() != null) {
                            hintPopup.setText(wordEntity.getDefinitions().get(0));
                            int width = (int)Util.getStringBounds(wordEntity.getWord()).getWidth();
                            hintPopup.show(TextPanel.this, xPosition - width/2, yPosition + 26);
                        } else {
                            hintPopup.setVisible(false);
                        }




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

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                mousePressed = true;

                int h = (int)Util.getStringBounds(lines.get(0)).getHeight();
                int w = (int)Util.getStringBounds(String.valueOf(lines.get(0).charAt(0))).getWidth();

                int charX = (mouseX - 10)/w;
                int charY = mouseY/h;

                char c = lines.get(charY).charAt(charX);

                selectedWord = Util.getWord(lines.get(charY), charX);
                selectedLine = charY;

                System.out.println(charX + ":" + charY + "=" + c + ":" + selectedWord);

                WordEntity wordEntity = dictionary.get(selectedWord.getWord());
                if (wordEntity.getDefinitions() != null) {
                    hintPopup.setText(wordEntity.getDefinitions().get(0));
                    hintPopup.show(TextPanel.this, mouseX, yPosition + 30);
                } else {
                    JPopupMenu popupMenu = new JPopupMenu();
                    JTextField textField = new JTextField(selectedWord.getWord());
                    textField.setFont(FontConfig.getFontConfig().getPlainTextFont());
                    popupMenu.add(textField);

                    textField.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dictionary.map.put(selectedWord.getWord().toLowerCase(), Collections.singletonList(textField.getText()));
                            popupMenu.setVisible(false);


                            List<String> lines = dictionary.map.entrySet().stream()
                                    .map(entry -> entry.getKey() + "=" + String.join(",", entry.getValue()))
                                    .sorted(String::compareTo)
                                    .collect(Collectors.toList());


                            FileUtils.save("dictionary.txt", lines);
                        }
                    });

                    popupMenu.show(TextPanel.this, mouseX, mouseY);
                }

                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mousePressed = false;
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                lines = Util.split(dirtyWords, getWidth());
            }
        });
    }

    @Override
    public void paint(Graphics gg) {
        Graphics2D g = (Graphics2D) gg;
        g.setFont(FontConfig.getFontConfig().getPlainTextFont());

        //todo
        if (lines == null) {
            lines = Util.split(dirtyWords, getWidth());
        }

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
                    xPosition = x + j*(int)rect.getWidth();
                    yPosition = y-h+h/4;
                    g.drawRect(xPosition, yPosition, (int)rect.getWidth(), (int)rect.getHeight());
                    g.setColor(Theme.getTheme().getTextColor());
                }

                if (selectedWord != null && selectedLine == i && j >= selectedWord.getPosition() && j < selectedWord.getPosition() + selectedWord.getLength()) {
                    g.setColor(Color.orange);
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
