package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by lecoan on 17-3-25.
 */
public class RegisterView {

    public RegisterView() {
        JFrame frame = new JFrame("register");
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        JPanel panel = new JPanel(new FlowLayout());
        JTextField username = new JTextField(40);
        JPasswordField password = new JPasswordField(40);


        frame.setVisible(true);
    }
}
