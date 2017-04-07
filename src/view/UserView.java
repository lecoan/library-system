package view;

import bean.Book;
import bean.BookPathTable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Graphics;


/**
 * Created by TaBoo on 2017/3/25.
 */

public class UserView implements ActionListener{
    public FindBookFrame findBookFrame = FindBookFrame.getInstance();
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
        frame.setBounds(300,300,850,500);
        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        panel1.setBounds(0,0,700,80);
        JPanel panel2 = new JPanel();
        JLabel JL = new JLabel("ASDFASFASDF");
        /*String[] rankedList = {"lal","asdfasf"};
        JList borrowRateRank = new JList(rankedList);
        borrowRateRank.setBorder(BorderFactory.createTitledBorder("借阅次数最高的二十本书"));
        borrowRateRank.setVisibleRowCount(8);
        panel2.add(borrowRateRank);*/
        panel2.setBounds(0,100,300,300);
        JL.setBounds(0,0,100,100);
        JPanel panel3 = new ImagePanel();
        panel3.setBounds(480,175,200,185);
        panel.setLayout(null);
        panel1.setLayout(null);
        panel2.setLayout(null);


        jl1.setBounds(20,10,100,25);
        jl1.setText("用户名");
        jl1.setFont(new Font("宋体",Font.BOLD, 16));
        jl2.setBounds(120,10,100,25);
        jl2.setText("asdf");
        jl2.setFont(new Font("宋体",Font.BOLD, 16));
        jl3.setBounds(20,35,100,25);
        jl3.setText("账户余额");
        jl3.setFont(new Font("宋体",Font.BOLD, 16));
        jl4.setBounds(120,35,100,25);
        jl4.setText("20.00");
        panel1.add(jl1);
        panel1.add(jl2);
        panel1.add(jl3);
        panel1.add(jl4);

        jb1.setText("搜索");
        jb1.setBounds(200,10,100,25);
        jb1.setBorder(BorderFactory.createRaisedBevelBorder());
        jb1.setActionCommand("find");
        jb1.addActionListener(this);
        jb1.setVisible(true);
        jb2.setText("个人信息");
        jb2.setBounds(200,40,100,25);
        jb2.setBorder(BorderFactory.createRaisedBevelBorder());
        jb2.setActionCommand("info");
        jb2.addActionListener(this);
        panel1.add(jb1);
        panel1.add(jb2);

        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);

        frame.setContentPane(panel);
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
            jb3.setBorder(BorderFactory.createRaisedBevelBorder());
            jb3.setActionCommand("在借");
            jb3.addActionListener(this);
            jb4.setBounds(45,55,160,25);
            jb4.setBorder(BorderFactory.createRaisedBevelBorder());
            jb4.setActionCommand("预借");
            jb4.addActionListener(this);
            jb5.setBounds(45,100,160,25);
            jb5.setBorder(BorderFactory.createRaisedBevelBorder());
            jb5.setActionCommand("历史");
            jb5.addActionListener(this);
            jb6.setBounds(45,145,160,25);
            jb6.setBorder(BorderFactory.createRaisedBevelBorder());
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

            String[] columnNames = {"A", "B", "C","D","e"};
            Object[][] data = {{"12","23","38","234","1"},{"2","2","34","4","A"},{"AS","23","2","234","3"}};
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

    class ImagePanel extends JPanel {

        public void paint(Graphics g) {

            super.paint(g);

// ImageIcon icon = new ImageIcon("D:\\1.jpg");

            ImageIcon icon = new ImageIcon("C:\\Users\\14632\\Desktop\\1234.gif");

            g.drawImage(icon.getImage(), 0, 0, 200, 185, this);

        }

    }
//    public static void main(String args[]) {
//        UserView s = new UserView();
//    }
}
