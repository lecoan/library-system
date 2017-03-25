package view;

import javax.swing.*;

/**
 * Created by ghoskno on 3/25/17.
 */
public class AdminView {
    public AdminView(){
        JFrame frame = new JFrame("Admin Panel");
        frame.setSize(1000,600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.add(panel);
        frame.setVisible(true);
    }
    public static void main (String[] args){
        AdminView test = new AdminView();
    }
}
