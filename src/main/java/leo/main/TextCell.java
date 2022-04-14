package leo.main;

import leo.main.dictionary.WordEntity;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class TextCell {

    private static final int BORDER = 10;
    private static final int ROUND = 14;

    private WordEntity wordEntity;
    private int x;
    private int y;

    private Color borderColor = Color.gray;
    private Color textColor = Color.darkGray;

    public TextCell(WordEntity wordEntity, int x, int y) {
        this.wordEntity = wordEntity;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g) {


        FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
        Rectangle2D fRect = g.getFont().getStringBounds(wordEntity.getWord(), frc);

        RoundRectangle2D sRect = new RoundRectangle2D.Float();
        sRect.setRoundRect(x - BORDER, y - BORDER, fRect.getWidth() + BORDER * 2, fRect.getHeight() + BORDER * 2, ROUND, ROUND);

        g.setColor(borderColor);
        g.draw(sRect);

        g.setColor(textColor);
        g.drawString(wordEntity.getWord(), x, y + (int)fRect.getHeight() - BORDER/2);
    }

    @Override
    public String toString() {
        return wordEntity.toString();
    }
}
