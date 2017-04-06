package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by ghoskno on 3/30/17.
 */
public class AddPlaceHolder {
    private volatile static AddPlaceHolder instance;
    private  AddPlaceHolder(){};

    public static AddPlaceHolder getInstance(){
        synchronized (AddPlaceHolder.class) {
            if(instance == null) {
                instance = new AddPlaceHolder();
            }
            return instance;
        }
    }

    public void addingPlaceholder(JTextField textField,String text){
        textField.setText(text);
        textField.setForeground(Color.GRAY);
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            if (textField.getText().equals(text)) {
                textField.setText("");
                textField.setForeground(Color.BLACK);
            }
            }
        });
        textField.addFocusListener(new FocusAdapter() {
            //控制placeholder
            @Override
            public void focusLost(FocusEvent e) {
            if (textField.getText().isEmpty()) {
                textField.setForeground(Color.GRAY);
                textField.setText(text);
            }
            }
        });
    }
}
