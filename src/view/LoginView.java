package view;

import controler.SignInAndUpController;
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
public class LoginView extends JFrame {
    private final JButton button;
    private final JTextField id;
    private final JPasswordField password;

    private SignInAndUpController controller;

    public LoginView() {

        this.controller = SignInAndUpController.getInstance();

        setTitle("登录界面");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(500, 500);

        JPanel panel = new JPanel();
        panel.setSize(500, 500);
        Box mainBox = Box.createVerticalBox();
        Box idBox = Box.createHorizontalBox();
        Box pwBox = Box.createHorizontalBox();

        JLabel idLabel = new JLabel("学号或工号： ");
        JLabel pwLabel = new JLabel("密码： ");
        id = new JTextField(30);
        password = new JPasswordField(30);
        button = new JButton("登录");
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

        setLoginListener();

    }

    /**
     * 处理登陆逻辑
     * @see controler.SignInAndUpController#handleLogin(String, String, JFrame)
     */
    private void setLoginListener() {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String userid = id.getText();
                String pw = new String(password.getPassword());
                if (!"".equals(userid) && !"".equals(pw)) {
                    controller.handleLogin(userid, pw, LoginView.this);
                } else {
                    ErrAlert.getInstance().findErrAlert(50, 50, "请确保信息填写完整");
                }
            }
        });
    }
}
