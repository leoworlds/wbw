package leo.main;

import leo.main.dictionary.WordEntity;
import leo.main.setting.theme.ThemeFactory;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class RandomTextCell {

    private static final String EMPTY = "";

    private static final int BORDER = 10;
    private static final int ROUND = 14;

    private WordEntity wordEntity;

    private Color borderColor = ThemeFactory.theme().getBorderColor();
    private Color textColor = ThemeFactory.theme().getTextColor();

    private Color doneBorderColor = ThemeFactory.theme().getDoneBorderColor();
    private Color doneTextColor = ThemeFactory.theme().getDoneTextColor();

    private boolean done = false;

    private int x = 0;
    private int y = 0;

    private int randomDefinition;

    private String typed = EMPTY;

    public RandomTextCell(WordEntity wordEntity) {
        this.wordEntity = wordEntity;
        randomDefinition = Util.randomInt(0, wordEntity.getDefinitions().size());
    }

    void done() {
        done = true;
    }

    private String getText() {
        return done ? wordEntity.getDefinitions().get(randomDefinition) : wordEntity.getWord();
    }

    public void draw(Graphics2D g) {

        FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
        Rectangle2D fRect = g.getFont().getStringBounds(getText(), frc);

        int width = (int)g.getClipBounds().getWidth();

        if (x == 0) {
            x = Util.randomInt(0, width - (int) fRect.getWidth() - BORDER * 2);
            y = 0;
        }

        RoundRectangle2D sRect = new RoundRectangle2D.Float();
        sRect.setRoundRect(x - BORDER, y - BORDER, fRect.getWidth() + BORDER * 2, fRect.getHeight() + BORDER * 2, ROUND, ROUND);

        g.setBackground(Color.WHITE);
        g.setColor(done ? doneBorderColor : borderColor);
        g.draw(sRect);

        g.setColor(done ? doneTextColor : textColor);
        g.drawString(getText(), x, y + (int)fRect.getHeight() - BORDER/2);

        if (!done && typed != null && typed.length() > 0) {
            g.setColor(Color.red);
            g.drawString(typed, x, y + (int)fRect.getHeight() - BORDER/2);
        }
    }

    public WordEntity getWordEntity() {
        return wordEntity;
    }

    public void setWordEntity(WordEntity wordEntity) {
        this.wordEntity = wordEntity;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "TextCell{" +
                "text='" + getText() + '\'' +
                '}';
    }

    public void setTyped(String typed) {
        this.typed = typed;
    }

    public String getTyped() {
        return typed;
    }

    public void cleanTyped() {
        typed = EMPTY;
    }
}
