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
 描　述: 启动界面
 版　本: v1.00 Copyright(c).
 ******************************************************************/
public class StartUpView {

    private StartUpInfo info;

    public StartUpView() {
        JFrame frame = new JFrame("register");
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new FlowLayout());
        JButton login = new JButton("login");
        JButton register = new JButton("register");

        login.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                info.handleLogin();
            }
        });

        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                info.handleRegister();
            }
        });

        frame.setVisible(true);
    }

    public void setStartUpInfo(StartUpInfo info) {
        this.info = info;
    }

    public interface StartUpInfo{
        public void handleLogin();
        public void handleRegister();
    }
}
