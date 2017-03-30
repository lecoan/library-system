package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

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

    public void addingPlaceholder(JTextField textFiekd,String text){
        textFiekd.setForeground(Color.GRAY);
        textFiekd.addFocusListener(new FocusAdapter() {
            //控制placeholder
            @Override
            public void focusGained(FocusEvent e) {
                if (textFiekd.getText().equals(text)) {
                    textFiekd.setText("");
                    textFiekd.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (textFiekd.getText().isEmpty()) {
                    textFiekd.setForeground(Color.GRAY);
                    textFiekd.setText(text);
                }
            }
        });
    }
}
