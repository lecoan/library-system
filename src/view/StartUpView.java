package view;

import bean.BookPathTable;
import controler.SignInAndUpController;
import listener.GlobalActionDetector;
import service.BookOperate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

/******************************************************************
 创建人: 杨翔
 日　期: 2017/3/25
 修改人:
 日　期:
 描　述: 启动界面
 版　本: v1.00 Copyright(c).
 ******************************************************************/
public class StartUpView extends JFrame {

    private GlobalActionDetector detector;

    private JTable table;
    public StartUpView() {
        //controller = SignInAndUpController.getInstance();
        detector = GlobalActionDetector.getInstance();


        initView();

    }

    /**
     * 初始化界面
     */
    private void initView() {
        JLabel label = new JLabel("当前时间: "+GetDate.getDate(detector.getDays()));
        detector.addEvent(days -> label.setText("当前时间: "+GetDate.getDate(detector.getDays())));

        setTitle("主界面");
        setSize(500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new FlowLayout());
        JButton login = new JButton("登录");
        JButton register = new JButton("注册");
        panel.add(login);
        panel.add(register);
        panel.add(label);
        setContentPane(panel);

        //添加处理逻辑
        login.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new LoginView();
            }
        });

        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new RegisterView();
            }
        });

        table = new JTable(0, 0);

        updateTable();

        detector.addEvent(days -> {
            updateTable();
        });
        table.setEnabled(false);
        table.setVisible(true);
        panel.add(new JScrollPane(table));
        setVisible(true);

    }

    private void updateTable() {
        java.util.List<BookPathTable> list = BookOperate.getInstance().getRanklist();
        String[] tableHeader = {"排名", "书名", "借阅次数"};
        Object[][] tableBody = new Object[list.size()][3];
        for (int i = 0; i < list.size(); i++) {
            Object[] rowData = {"" + (i+1), "" + list.get(i).getIsbn().split("&&")[2], "" + list.get(i).getBorrownum()};
            tableBody[i] = rowData;
        }
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setDataVector(tableBody, tableHeader);
    }
}
