package leo.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import leo.main.dictionary.file.FileDictionary;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class Main {

    private static final String TITLE = "Word by Word";

    public static void main(String ... strings) throws JsonProcessingException {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(TITLE);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(Util.WIDTH, Util.HEIGHT);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);

            RootPanel rootPanel = new RootPanel(new FileDictionary());

            rootPanel.addCompletedListener(e -> frame.setTitle((e.completed() <= 0) ? TITLE :
                    TITLE + "  +" + e.completed() + " -" +
                            (e.missed() + e.mistake()) + " " +
                            100*e.completed() / (e.missed() + e.mistake() + e.completed()) + "%"));

            frame.add(rootPanel);
//                frame.add(new RootPanel(new WordnikDictionary()));
//                frame.add(new RootPanel(new MyDictionary()));
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
