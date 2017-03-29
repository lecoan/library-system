package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/******************************************************************
 创建人: 杨翔
 日　期: 2017/3/25
 修改人:
 日　期:
 描　述: 登陆界面
 版　本: v1.00 Copyright(c).
 ******************************************************************/
public class LoginView {
    private final JButton button;
    private final JTextField username;
    private final JPasswordField password;

    public LoginView() {
        JFrame frame = new JFrame("login");
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setSize(500,500);
        JPanel panel = new JPanel(new FlowLayout());

        username = new JTextField(40);
        password = new JPasswordField(40);
        button = new JButton("sign in");
        panel.add(username);
        panel.add(password);
        panel.add(button);
        frame.setContentPane(panel);
        frame.setVisible(true);

        //TODO 添加提示
        JLabel name = new JLabel("username: ");
        JLabel pw = new JLabel("password: ");
    }


    public void setLoginListener(LoginInfo info) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                info.handleLoginInfo(username.getText(),new String(password.getPassword()));
            }
        });
    }

    public interface LoginInfo{
        public void handleLoginInfo(String username, String password);
    }
}