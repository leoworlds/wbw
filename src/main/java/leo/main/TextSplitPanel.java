package leo.main;

import leo.main.config.Config;
import leo.main.setting.theme.Theme;

import javax.swing.*;
import java.awt.*;

public class TextSplitPanel extends TypePanel {

    TextPanel textPanel = new TextPanel();
    TranslateTextPanel translateTextPanel = new TranslateTextPanel();

    public TextSplitPanel() {
        setLayout(new BorderLayout());

        textPanel.setPreferredSize(new Dimension(0, 300));
        textPanel.addLevelListener(new LevelListener() {
            @Override
            public void event(LevelEvent e) {
                Config.config().getProps().setProperty("level", Integer.toString(e.getLevel() + 1));
                translateTextPanel.nextLevel();
            }
        });



        JSplitPane splitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, textPanel, translateTextPanel);
        splitPanel.setBackground(Theme.getTheme().getBackground());

        add(splitPanel);
    }

    @Override
    public boolean isPause() {
        return textPanel.isPause();
    }

    @Override
    public void setPause(boolean pause) {
        textPanel.setPause(pause);
    }

    @Override
    public void addCompletedListener(CompletedListener completedListener) {
        textPanel.addCompletedListener(completedListener);
    }
}
