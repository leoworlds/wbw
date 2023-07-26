package leo.main;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Queue;

public abstract class TypeComponent extends JComponent implements Pausable, Completable, Levelable {
    protected Queue<CompletedListener> completedListenerList = new LinkedList<>();
    protected Queue<LevelListener> levelListenerList = new LinkedList<>();

    @Override
    public void addCompletedListener(CompletedListener completedListener) {
        completedListenerList.add(completedListener);
    }

    @Override
    public void addLevelListener(LevelListener levelListener) {
        levelListenerList.add(levelListener);
    }
}
