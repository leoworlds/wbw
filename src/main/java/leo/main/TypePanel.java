package leo.main;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Queue;

public abstract class TypePanel extends JComponent implements Pausable, Completable {
    protected Queue<CompletedListener> completedListenerList = new LinkedList<>();

    @Override
    public void addCompletedListener(CompletedListener completedListener) {
        completedListenerList.add(completedListener);
    }
}
