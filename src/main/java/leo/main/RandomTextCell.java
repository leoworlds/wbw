package leo.main;

import leo.main.dictionary.WordEntity;
import leo.main.setting.theme.Theme;

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

    private Color borderColor = Theme.getTheme().getBorderColor();
    private Color textColor = Theme.getTheme().getTextColor();

    private Color doneBorderColor = Theme.getTheme().getDoneBorderColor();
    private Color doneTextColor = Theme.getTheme().getDoneTextColor();

    private Color typeTextColor = Theme.getTheme().getTypeTextColor();

    private boolean done = false;

    private int x = 0;
    private int y = 0;

    private int randomDefinition;

    private String typed = EMPTY;

    public RandomTextCell(WordEntity wordEntity) {
        this.wordEntity = wordEntity;
        int size = wordEntity.getDefinitions().size();
        randomDefinition = Util.randomInt(0, size == 0 ? 1 : size);
    }

    void done() {
        done = true;
    }

    private String getText() {
        if (done) {
            if (wordEntity.getDefinitions() == null || wordEntity.getDefinitions().isEmpty()) {
                return "";
            } else {
                return wordEntity.getDefinitions().get(randomDefinition);
            }
        } else {
            return wordEntity.getWord();
        }
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
            g.setColor(typeTextColor);
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
