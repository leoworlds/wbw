package leo.main;

import leo.main.config.Config;
import leo.main.dictionary.file.FileDictionary;
import leo.main.dictionary.mix.MixDictionary;
import leo.main.dictionary.my.CharDictionary;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class Main {

    private static final String TITLE = "Word by Word";

    public static void main(String ... strings) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(TITLE);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(Util.WIDTH, Util.HEIGHT);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);

            RootPanel rootPanel = new RootPanel(new MixDictionary(new CharDictionary(), new FileDictionary()));

            rootPanel.addCompletedListener(e -> {
                frame.setTitle((e.completed() <= 0 && e.mistake() == 0) ? TITLE : TITLE + "  +" + e.getStatistic());

                if (e.completed() >= Config.getComplete()) {
                    System.exit(0);
                }
            });

            frame.add(rootPanel);
            frame.addWindowFocusListener(new WindowFocusListener() {
                @Override
                public void windowGainedFocus(WindowEvent e) {
                    rootPanel.setPause(false);
                }

                @Override
                public void windowLostFocus(WindowEvent e) {
                    rootPanel.setPause(true);
                }
            });
        });
    }
}
