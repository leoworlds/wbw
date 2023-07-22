package leo.main.component;

import leo.main.dictionary.PropertyDictionary;
import leo.main.setting.theme.FontConfig;
import leo.main.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

public class EditPopup extends JPopupMenu {

    private JTextField textField = new JTextField();

    private String text;
    private Component invoker;

    public EditPopup(Component invoker, PropertyDictionary dictionary) {
        this.invoker = invoker;

        textField.setFont(FontConfig.getFontConfig().getPlainTextFont());
        add(textField);

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dictionary.map.put(text.toLowerCase(), Collections.singletonList(textField.getText()));
                setVisible(false);
                FileUtils.save(dictionary);
            }
        });
    }

    public void showText(String text, int x, int y) {
        this.text = text;
        textField.setText(text);
        show(invoker, x, y);
    }
}
