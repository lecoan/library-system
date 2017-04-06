package view;

import bean.Book;
import bean.BookPathTable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import controler.UserControler;

/**
 * Created by TaBoo on 2017/3/25.
 */

public class UserView implements ActionListener{
    JLabel jl1 =new JLabel();
    JLabel jl2 =new JLabel();
    JLabel jl3 =new JLabel();
    JLabel jl4 =new JLabel();

    public JButton jb1 = new JButton();
    public JButton jb2 = new JButton();
    public JButton jb3 = new JButton("在借图书");
    public JButton jb4 = new JButton("预借图书");
    public JButton jb5 = new JButton("借阅历史");
    public JButton jb6 = new JButton("账户充值");
    public JButton jb7 = new JButton("充值");

    public UserView(){
        JFrame frame = new JFrame("USER");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(300,300,350,120);
        JPanel panel1 = new JPanel(new FlowLayout());
        JPanel panel2 = new JPanel(new FlowLayout());
        panel1.setLayout(null);
        panel2.setLayout(null);


        jl1.setBounds(20,10,60,25);
        jl1.setText("用户名");
        jl2.setBounds(80,10,60,25);
        jl2.setText("asdf");
        jl3.setBounds(20,35,60,25);
        jl3.setText("账户余额");
        jl4.setBounds(80,35,60,25);
        jl4.setText("20.00");
        panel1.add(jl1);
        panel1.add(jl2);
        panel1.add(jl3);
        panel1.add(jl4);

        jb1.setText("搜索");
        jb1.setBounds(200,10,100,25);
        jb1.setActionCommand("find");
        jb1.addActionListener(this);
        jb1.setVisible(true);
        jb2.setText("个人信息");
        jb2.setBounds(200,40,100,25);
        jb2.setActionCommand("info");
        jb2.addActionListener(this);
        panel1.add(jb1);
        panel1.add(jb2);

        frame.setContentPane(panel1);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if("find".equals(e.getActionCommand())){

        }
        else if("info".equals(e.getActionCommand())){
            JFrame frame = new JFrame("信息");
            frame.setBounds(500,300,250,220);
            JPanel jpn = new JPanel(new FlowLayout());
            jpn.setLayout(null);

            jb3.setBounds(45,10,160,25);
            jb3.setActionCommand("在借");
            jb3.addActionListener(this);
            jb4.setBounds(45,55,160,25);
            jb4.setActionCommand("预借");
            jb4.addActionListener(this);
            jb5.setBounds(45,100,160,25);
            jb5.setActionCommand("历史");
            jb5.addActionListener(this);
            jb6.setBounds(45,145,160,25);
            jb6.setActionCommand("充值界面");
            jb6.addActionListener(this);
            jpn.add(jb3);
            jpn.add(jb4);
            jpn.add(jb5);
            jpn.add(jb6);

            frame.setContentPane(jpn);
            frame.setVisible(true);
        }
        else if("在借".equals(e.getActionCommand())) {
            JFrame frame = new JFrame("在借图书");
            frame.setBounds(700,300,600,400);

            String[] columnNames = {"A", "B", "C","D"};
            Object[][] data = {{"12","23","38","234"},{"2","34","4","A"},{"AS","23","234","3"}};
            JTable table = new JTable(data,columnNames);
            table.setBackground(Color.white);

            JScrollPane scrollPane = new JScrollPane(table);
            frame.getContentPane().add(scrollPane,BorderLayout.CENTER);
            frame.setTitle("在借");
            frame.setVisible(true);
            table.setFillsViewportHeight(true);
            /*TableColumn column = null;
            for (int i=0;i<3;i++){
                column = table.getColumnModel().getColumn(i);
                if(i==2){
                    column.setPreferredWidth(100);
                }else{
                    column.setPreferredWidth(30);
                }
            }*/
            table.setVisible(true);
        }
        else if("预借".equals(e.getActionCommand())){
            JFrame frame = new JFrame("预借图书");
            frame.setBounds(700,300,600,400);
            frame.setVisible(true);

            String[] columnNames = {"A", "B", "C","D"};
            Object[][] data = {{"12","23","38","234"},{"2","34","4","A"},{"AS","23","234","3"}};
            JTable table = new JTable(data,columnNames);
            table.setBackground(Color.white);

            JScrollPane scrollPane = new JScrollPane(table);
            frame.getContentPane().add(scrollPane,BorderLayout.CENTER);
            frame.setTitle("在借");
            frame.setVisible(true);
            table.setFillsViewportHeight(true);
            /*TableColumn column = null;
            for (int i=0;i<3;i++){
                column = table.getColumnModel().getColumn(i);
                if(i==2){
                    column.setPreferredWidth(100);
                }else{
                    column.setPreferredWidth(30);
                }
            }*/
            table.setVisible(true);
        }
        else if("历史".equals(e.getActionCommand())){
            JFrame frame = new JFrame("借阅历史");
            frame.setBounds(700,300,600,400);
            frame.setVisible(true);

            String[] columnNames = {"A", "B", "C","D"};
            Object[][] data = {{"12","23","38","234"},{"2","34","4","A"},{"AS","23","234","3"}};
            JTable table = new JTable(data,columnNames);
            table.setBackground(Color.white);

            JScrollPane scrollPane = new JScrollPane(table);
            frame.getContentPane().add(scrollPane,BorderLayout.CENTER);
            frame.setTitle("在借");
            frame.setVisible(true);
            table.setFillsViewportHeight(true);
            table.setVisible(true);
        }
        else if("充值界面".equals(e.getActionCommand())){
            JFrame frame = new JFrame("充值");
            frame.setBounds(700,300,200,220);
            JPanel jpn = new JPanel(new FlowLayout());
            jpn.setLayout(null);
            frame.setContentPane(jpn);
            frame.setVisible(true);

            JLabel jl = new JLabel("请输入充值金额");
            jl.setBounds(43,75,150,25);
            JTextField jtx = new JTextField();
            jtx.setBounds(20,45,150,25);

            jb7.setBounds(45,110,100,25);
            jb7.setActionCommand("充值");
            jb7.addActionListener(this);
            jpn.add(jtx);
            jpn.add(jb7);
            jpn.add(jl);
        }
        else if("充值".equals(e.getActionCommand())){
            JFrame frame = new JFrame("成功");
            frame.setBounds(730,360,150,100);
            frame.setVisible(true);

            JPanel jpn = new JPanel(new FlowLayout());
            jpn.setLayout(null);
            frame.setContentPane(jpn);

            JLabel jl = new JLabel("充值成功");
            jl.setBounds(40,20,80,25);
            jpn.add(jl);
        }
    }

    public void showBookInfoFrame(Book bookItem, BookPathTable bookItemPath){
        JFrame frame = new JFrame();
        frame.setBounds(500,500,500,450);
        JPanel jpn = new JPanel(new FlowLayout());
        jpn.setLayout(null);
        frame.setContentPane(jpn);
        frame.setVisible(true);

        JLabel bookNameLabel = new JLabel("书名 ：  " + bookItem.getName());
        bookNameLabel.setBounds(100,20,300,20);

        JLabel bookPublisherLabel = new JLabel("出版社名 ：  " + bookItem.getPublishername());
        bookPublisherLabel.setBounds(100,50,300,20);


        JLabel bookAuthorLabel = new JLabel("作者 ：  " + bookItem.getWritername());
        bookAuthorLabel.setBounds(100,80,300,20);

        JLabel bookNumLabel = new JLabel("总数量 ：  " + bookItemPath.getTotalnum());
        bookNumLabel.setBounds(100,110,300,20);

        JLabel bookRestNumLabel = new JLabel("剩余数量 ：  " + bookItemPath.getRestnum());
        bookRestNumLabel.setBounds(100,140,300,20);


        JLabel bookKindLabel = new JLabel("种类 ：  " + bookItem.getKind());
        bookKindLabel.setBounds(100,180,300,20);;

        JLabel bookDesLabel = new JLabel("<html><body><p>" + "简介 ：  " + bookItem.getIntroduction()+ "</p></body></html>");
        System.out.print(bookItem.getIntroduction());
        bookDesLabel.setBounds(100,220,300,80);

        jpn.add(bookNameLabel);
        jpn.add(bookPublisherLabel);
        jpn.add(bookAuthorLabel);
        jpn.add(bookNumLabel);
        jpn.add(bookRestNumLabel);
        jpn.add(bookKindLabel);
        jpn.add(bookDesLabel);
    }
//    public static void main(String args[]) {
//        UserView s = new UserView();
//    }
}
