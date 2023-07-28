package leo.main;

import leo.main.config.Config;
import leo.main.dictionary.Dictionary;
import leo.main.dictionary.PropertyDictionary;
import leo.main.dictionary.mix.MixDictionary;
import leo.main.dictionary.my.CharDictionary;
import leo.main.setting.theme.Theme;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class Main {

    private static final String TITLE = "Word by Word";
    private static final boolean isSymbol = Boolean.parseBoolean(Config.config().getProps().getProperty("symbol", "true"));

    public static void main(String ... strings) {
//        Dictionary dictionary = isSymbol
//                ? new MixDictionary(new CharDictionary(), new FileDictionary())
//                : new FileDictionary();

        Dictionary dictionary = isSymbol
                ? new MixDictionary(new CharDictionary(), new PropertyDictionary())
                : new PropertyDictionary();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(TITLE);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(Util.WIDTH, Util.HEIGHT);
            frame.getContentPane().setBackground(Theme.getTheme().getBackground());

            frame.setVisible(true);
            frame.setLocationRelativeTo(null);

//            TypePanel typePanel = Config.config().getProps().getProperty("mode", 0) == 0
//                    ? new FallWordPanel(dictionary)
//                    : new TextPanel(Config.config().getProps().getProperty("file"));

            TypePanel typePanel = Config.config().getProps().getProperty("mode", 0) == 0
                    ? new FallWordPanel(dictionary)
                    : new TextSplitPanel();

            typePanel.addCompletedListener(e -> {
                frame.setTitle((e.completed() <= 0 && e.mistake() == 0) ? TITLE : TITLE + e.getInfo());
            });

            frame.addWindowFocusListener(new WindowFocusListener() {
                @Override
                public void windowGainedFocus(WindowEvent e) {
                    typePanel.setPause(false);
                }

                @Override
                public void windowLostFocus(WindowEvent e) {
                    typePanel.setPause(true);
                }
            });

            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    Config.config().store();
                }
            });

            frame.add(typePanel);
        });
    }
}
