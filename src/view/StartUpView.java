package view;

import controler.SignInAndUpController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/******************************************************************
 创建人: 杨翔
 日　期: 2017/3/25
 修改人:
 日　期:
 描　述: 启动界面
 版　本: v1.00 Copyright(c).
 ******************************************************************/
public class StartUpView extends JFrame{

    private SignInAndUpController controller;

    public StartUpView() {
        controller = SignInAndUpController.getInstance();

        setTitle("main");
        setSize(500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new FlowLayout());
        JButton login = new JButton("login");
        JButton register = new JButton("register");
        panel.add(login);
        panel.add(register);
        setContentPane(panel);

        login.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginView loginView = new LoginView();
            }
        });

        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterView view = new RegisterView();
            }
        });

        setVisible(true);
    }
}
