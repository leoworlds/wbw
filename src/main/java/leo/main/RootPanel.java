package leo.main;

import leo.main.dictionary.Dictionary;
import leo.main.dictionary.WordEntity;
import leo.main.setting.theme.ThemeFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RootPanel extends JComponent implements Runnable {

    private Queue<CompletedListener> completedListenerList = new LinkedList<>();

    private Dictionary dictionary;
    private Queue<RandomTextCell> cells = new ConcurrentLinkedQueue<>();
    private Queue<WordEntity> wordEntities = new ConcurrentLinkedQueue<>();
    private Queue<RandomTextCell> missed = new ConcurrentLinkedQueue<>();
    private Queue<RandomTextCell> completed = new ConcurrentLinkedQueue<>();
    private RandomTextCell typedTextCell;

    private int counter;
    private int nextCellCounter;
    private int mistakes;

    private volatile boolean isPause;

    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }

    public RootPanel(Dictionary dictionary) {

        this.dictionary = dictionary;

        newCell(dictionary.poll());


        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (Objects.equals(cells.peek().getWordEntity().getWord(), cells.peek().getTyped())) {
                        typedTextCell = cells.poll();
                        typedTextCell.done();
                        typedTextCell.cleanTyped();
                        completed.add(typedTextCell);
                        event();
                    } else {
                        mistake();
                    }
                } else if (cells.peek() != null && cells.peek().getWordEntity().getWord().startsWith(cells.peek().getTyped() + e.getKeyChar())) {
                    cells.peek().setTyped(cells.peek().getTyped() + e.getKeyChar());
                } else {
                    mistake();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        setFocusable(true);
        requestFocusInWindow();

        new Thread(this).start();
    }

    private void mistake() {
        cells.peek().cleanTyped();
        Toolkit.getDefaultToolkit().beep();
        mistakes++;
        event();
    }

    private void newCell(WordEntity wordEntity) {
        cells.add(new RandomTextCell(wordEntity));
        wordEntities.add(wordEntity);
    }

    private void event() {
        completedListenerList.forEach(completedListener -> completedListener.completed(new CompletedEvent() {
            @Override
            public int completed() {
                return completed.size();
            }

            @Override
            public int missed() {
                return missed.size();
            }

            @Override
            public int mistake() {
                return mistakes;
            }
        }));
    }

    @Override
    protected void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;

        g.setBackground(ThemeFactory.theme().getBackground());
        g.clearRect(0, 0, getWidth(), getHeight());
        g.setFont(new Font("Courier New", Font.BOLD, 24));
        g.setStroke(new BasicStroke(2));

        cells.forEach(c -> c.draw(g));

        if (typedTextCell != null) {
            typedTextCell.draw(g);
        }
    }

    @Override
    public void run() {

        while (true) {

            if (isPause) {
                continue;
            }

            nextCellCounter = Util.randomInt(100, 1000);

            if (counter >= nextCellCounter && dictionary.peek() != null) {
                newCell(dictionary.poll());
                counter = 0;
            }

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                return;
            }


            Iterator<RandomTextCell> iterator = cells.iterator();
            while (iterator.hasNext()) {
                RandomTextCell cell = iterator.next();
                if (cell.getY() > getHeight() - 40) {
                    missed.add(cell);
                    cell.cleanTyped();
                    iterator.remove();
                    System.exit(0);
                    event();
                    //                    Toolkit.getDefaultToolkit().beep();
                } else {
                    cell.setY(cell.getY() + 1);
                }
            }

            //            cells.forEach(c -> c.setY(c.getY() + 1));

            repaint();
            counter++;
        }
    }

    public void addCompletedListener(CompletedListener completedListener) {
        completedListenerList.add(completedListener);
    }
}
