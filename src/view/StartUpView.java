package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by lecoan on 17-3-25.
 */
public class StartUpView {
    public StartUpView() {
        JFrame frame = new JFrame("register");
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new FlowLayout());
        JButton login = new JButton("login");
        JButton register = new JButton("register");
//        JButton button = new JButton("");
        frame.setVisible(true);
    }
}
