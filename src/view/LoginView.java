package view;

import oracle.jrockit.jfr.JFR;

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
public class LoginView extends JFrame{
    private final JButton button;
    private final JTextField id;
    private final JPasswordField password;

    public LoginView() {
        setTitle("login");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(500,500);

        JPanel panel = new JPanel();
        panel.setSize(500,500);
        Box mainBox = Box.createVerticalBox();
        Box idBox = Box.createHorizontalBox();
        Box pwBox = Box.createHorizontalBox();

        JLabel idLabel = new JLabel("id: ");
        JLabel pwLabel = new JLabel("password: ");
        id = new JTextField(30);
        password = new JPasswordField(30);
        button = new JButton("sign in");
        mainBox.add(idBox);
        mainBox.add(pwBox);
        panel.add(mainBox);
        idBox.add(idLabel);
        idBox.add(id);
        pwBox.add(pwLabel);
        pwBox.add(password);
        mainBox.add(button);

        setContentPane(panel);
        setVisible(true);

    }


    public void setLoginListener(LoginInfo info) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(info!=null)
                    info.handleLoginInfo(id.getText(),new String(password.getPassword()));
            }
        });
    }

    public interface LoginInfo{
        public void handleLoginInfo(String id, String password);
    }
}
