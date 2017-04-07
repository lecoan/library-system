package view;

import bean.Book;
import bean.BookPathTable;
import javafx.scene.layout.Border;

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

    public JButton jb1 = new JButton();
    public JButton jb2 = new JButton();
    public JButton jb3 = new JButton("在借图书");
    public JButton jb4 = new JButton("预借图书");
    public JButton jb5 = new JButton("借阅历史");
    public JButton jb6 = new JButton("账户充值");
    public JButton jb7 = new JButton("确认");

    public UserView(){
        JFrame frame = new JFrame("USER");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(300,300,800,500);
        frame.setLayout(null);

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new ImagePanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        JPanel panel6 = new JPanel(){
            protected void paintComponent(Graphics g){
                ImageIcon icon = new ImageIcon("C:\\Users\\14632\\Desktop\\2.jpg");
                Image imgg = icon.getImage();
                g.drawImage(imgg,0,0,200,80,icon.getImageObserver());
            }
        };


        panel1.setBounds(0,0,800,100);
        panel1.setBackground(Color.PINK);
        panel1.setLayout(null);

        panel2.setBounds(0,100,200,300);
        panel2.setBackground(Color.WHITE);
        panel2.setLayout(null);

        panel3.setBounds(600,180,200,220);
        //panel3.setBackground(Color.LIGHT_GRAY);
        panel3.setLayout(null);

        panel4.setBounds(200,100,400,300);
        panel4.setBackground(Color.PINK);
        panel4.setLayout(null);

        panel5.setBounds(0,400,800,100);
        panel5.setBackground(Color.PINK);
        panel5.setLayout(null);

        panel6.setBounds(600,100,200,80);


        panel1.add(jb3);
        panel1.add(jb4);
        panel1.add(jb5);
        JLabel jl1 =new JLabel();
        JLabel jl2 =new JLabel();
        panel1.add(jl1);
        panel1.add(jl2);
        JLabel jl4 =new JLabel();
        jl1.setBounds(30,10,200,50);
        jl1.setText("基本信息");
        jl1.setForeground(Color.WHITE);
        jl1.setFont(new Font("宋体",Font.BOLD, 30));
        jl2.setBounds(337,10,200,50);
        jl2.setText("图书信息");
        jl2.setForeground(Color.WHITE);
        jl2.setFont(new Font("宋体",Font.BOLD, 30));

        jb3.setText("在借书籍");
        jb3.setBounds(200,70,120,30);
        javax.swing.border.Border b1 =BorderFactory.createLineBorder(Color.WHITE);
        javax.swing.border.Border b2 = BorderFactory.createEtchedBorder();
        jb3.setBorder(BorderFactory.createCompoundBorder(b1,b2));
        jb3.setBackground(Color.WHITE);
        jb3.setForeground(Color.PINK);
        jb3.setActionCommand("在借");
        jb3.addActionListener(this);

        jb4.setText("预借书籍");
        jb4.setBounds(340,70,120,30);
        javax.swing.border.Border b3 =BorderFactory.createLineBorder(Color.WHITE);
        javax.swing.border.Border b4 = BorderFactory.createEtchedBorder();
        jb4.setBorder(BorderFactory.createCompoundBorder(b3,b4));
        jb4.setBackground(Color.WHITE);
        jb4.setForeground(Color.PINK);
        jb4.setActionCommand("预借");
        jb4.addActionListener(this);

        jb5.setText("历史记录");
        jb5.setBounds(480,70,120,30);
        javax.swing.border.Border b5 =BorderFactory.createLineBorder(Color.WHITE);
        javax.swing.border.Border b6 = BorderFactory.createEtchedBorder();
        jb5.setBorder(BorderFactory.createCompoundBorder(b5,b6));
        jb5.setBackground(Color.WHITE);
        jb5.setForeground(Color.PINK);
        jb5.setActionCommand("历史");
        jb5.addActionListener(this);
//        jb2.setText("个人信息");
//        jb2.setBounds(20,40,100,25);
//        jb2.setBorder(BorderFactory.createRaisedBevelBorder());
//        jb2.setActionCommand("info");
//        jb2.addActionListener(this);

        panel2.add(jb6);
        JLabel mjl1 = new JLabel("学号");
        JLabel mjl2 = new JLabel("学院");
        JLabel mjl3 = new JLabel("在借本书");
        JLabel mjl4 = new JLabel("剩余本数");
        JLabel mjl5 = new JLabel("账户余额");
        JLabel mjl11 = new JLabel("学号");
        JLabel mjl22 = new JLabel("学院");
        JLabel mjl33 = new JLabel("在借本书");
        JLabel mjl44 = new JLabel("剩余本数");
        JLabel mjl55 = new JLabel("账户余额");
        panel2.add(mjl1);
        panel2.add(mjl2);
        panel2.add(mjl3);
        panel2.add(mjl4);
        panel2.add(mjl5);
        panel2.add(mjl11);
        panel2.add(mjl22);
        panel2.add(mjl33);
        panel2.add(mjl44);
        panel2.add(mjl55);
        mjl1.setBounds(10,20,100,30);
        mjl1.setFont(new Font("宋体",Font.BOLD, 20));
        mjl2.setBounds(10,70,100,30);
        mjl2.setFont(new Font("宋体",Font.BOLD, 20));
        mjl3.setBounds(10,120,100,30);
        mjl3.setFont(new Font("宋体",Font.BOLD, 20));
        mjl4.setBounds(10,170,100,30);
        mjl4.setFont(new Font("宋体",Font.BOLD, 20));
        mjl5.setBounds(10,220,100,30);
        mjl5.setFont(new Font("宋体",Font.BOLD, 20));

        jb6.setText("充值-。-");
        jb6.setBounds(50,260,100,30);
        javax.swing.border.Border b7 =BorderFactory.createLineBorder(Color.PINK);
        javax.swing.border.Border b8 = BorderFactory.createEtchedBorder();
        jb6.setBorder(BorderFactory.createCompoundBorder(b7,b8));
        jb6.setBackground(Color.PINK);
        jb6.setForeground(Color.WHITE);
        jb6.setActionCommand("充值界面");
        jb6.addActionListener(this);


        panel3.add(jb1);
        jb1.setBounds(0,0,200,220);
        jb1.setBorder(BorderFactory.createRaisedBevelBorder());
        jb1.setActionCommand("find");
        jb1.addActionListener(this);
        jb1.setBackground(Color.WHITE);



        frame.getContentPane().add(panel1);
        frame.getContentPane().add(panel2);
        frame.getContentPane().add(panel3);
        frame.getContentPane().add(panel4);
        frame.getContentPane().add(panel5);
        frame.getContentPane().add(panel6);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if("find".equals(e.getActionCommand())){

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

            g.drawImage(icon.getImage(), 0, 10, 200, 185, this);

        }

    }
//    public static void main(String args[]) {
//        UserView s = new UserView();
//    }
}
