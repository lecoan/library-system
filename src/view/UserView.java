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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import bean.*;


/**
 * Created by TaBoo on 2017/3/25.
 */

public class UserView implements ActionListener{
    Customer customer;
    public JFrame bookInfoFrame = new JFrame("图书信息");
    public FindBookFrame findBookFrame = new FindBookFrame();
    JFrame frame = new JFrame("USER");
    JLabel jl1 =new JLabel();
    JLabel jl2 =new JLabel();
    JLabel jl3 =new JLabel();
    JLabel jl4 =new JLabel();

    public JButton jb1 = new JButton();
    public JButton jb3 = new JButton("在借图书");
    public JButton jb4 = new JButton("预借图书");
    public JButton jb5 = new JButton("借阅历史");
    public JButton jb6 = new JButton("充值 ~。~");
    public JButton jb7 = new JButton("确认");
    public JButton jieyuejb = new JButton("借阅");
    public JButton yudingjb = new JButton("预定");

    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new ImagePanel();
    JPanel panel4 = new JPanel();
    JPanel panel5 = new JPanel();

    Object[][] zaijiestrings;
    Object[][] yujiestrings;
    Object[][] lishistrings;

    public UserView(Customer customer){
        this.customer = customer;
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(300,300,800,500);
        frame.setLayout(null);

        JPanel panel6 = new JPanel(){
            protected void paintComponent(Graphics g){
                ImageIcon icon = new ImageIcon("C:\\Users\\14632\\Desktop\\2.jpg");
                Image imgg = icon.getImage();
                g.drawImage(imgg,0,0,200,80,icon.getImageObserver());
            }
        };


        panel1.setBounds(0,0,800,100);
        panel1.setBackground(new Color(192,57,43));
        panel1.setLayout(null);

        panel2.setBounds(0,100,200,300);
        panel2.setBackground(Color.WHITE);
        panel2.setLayout(null);

        panel3.setBounds(600,180,200,220);
        //panel3.setBackground(Color.LIGHT_GRAY);
        panel3.setLayout(null);

        panel4.setBounds(200,100,400,300);
        javax.swing.border.Border x =BorderFactory.createLineBorder(new Color(192,57,43));
        javax.swing.border.Border y = BorderFactory.createEtchedBorder();
        panel4.setBorder(BorderFactory.createCompoundBorder(x,y));
        panel4.setBackground(Color.WHITE);
        panel4.setVisible(true);
        panel4.setLayout(new BorderLayout());

        panel5.setBounds(0,400,800,100);
        panel5.setBackground(new Color(192,57,43));
        panel5.setLayout(null);

        panel6.setBounds(600,100,200,80);


        panel1.add(jb3);
        panel1.add(jb4);
        panel1.add(jb5);
        JLabel jl1 =new JLabel();
        JLabel jl2 =new JLabel();
        JLabel jl3 =new JLabel();
        panel1.add(jl1);
        panel1.add(jl2);
        panel1.add(jl3);
        jl1.setBounds(35,50,200,50);
        jl1.setText("个人信息");
        jl1.setForeground(Color.WHITE);
        jl1.setFont(new Font("幼圆",Font.BOLD, 23));
        jl2.setBounds(337,10,200,50);
        jl2.setText("图书信息");
        jl2.setForeground(Color.WHITE);
        jl2.setFont(new Font("幼圆",Font.BOLD, 30));
        jl3.setBounds(620,50,200,50);
        jl3.setText("出品人：C511");
        jl3.setForeground(Color.WHITE);
        jl3.setFont(new Font("幼圆",Font.BOLD, 23));

        jb3.setText("在借书籍");
        jb3.setBounds(200,70,120,30);
        javax.swing.border.Border b10 =BorderFactory.createLineBorder(Color.PINK);
        javax.swing.border.Border b11 = BorderFactory.createEtchedBorder();
        jb3.setBorder(BorderFactory.createCompoundBorder(b10,b11));
        jb3.setBackground(Color.WHITE);
        jb3.setForeground(new Color(192,57,43));
        jb3.setActionCommand("在借");
        jb3.addActionListener(this);

        jb4.setText("预借书籍");
        jb4.setBounds(340,70,120,30);
        javax.swing.border.Border b3 =BorderFactory.createLineBorder(Color.WHITE);
        javax.swing.border.Border b4 = BorderFactory.createEtchedBorder();
        jb4.setBorder(BorderFactory.createCompoundBorder(b3,b4));
        jb4.setBackground(Color.WHITE);
        jb4.setForeground(new Color(192,57,43));
        jb4.setActionCommand("预借");
        jb4.addActionListener(this);

        jb5.setText("历史记录");
        jb5.setBounds(480,70,120,30);
        javax.swing.border.Border b5 =BorderFactory.createLineBorder(Color.WHITE);
        javax.swing.border.Border b6 = BorderFactory.createEtchedBorder();
        jb5.setBorder(BorderFactory.createCompoundBorder(b5,b6));
        jb5.setBackground(Color.WHITE);
        jb5.setForeground(new Color(192,57,43));
        jb5.setActionCommand("历史");
        jb5.addActionListener(this);

        panel2.add(jb6);
        JLabel mjl1 = new JLabel("姓名");
        JLabel mjl2 = new JLabel("学号");
        JLabel mjl3 = new JLabel("在借本数");
        JLabel mjl4 = new JLabel("权限本数");
        JLabel mjl5 = new JLabel("账户余额");
        JLabel mjl11 = new JLabel(customer.getUsername());
        JLabel mjl22 = new JLabel(customer.getId());
        JLabel mjl33 = new JLabel(String.valueOf(customer.getBookedMap().size()));
        JLabel mjl44 = new JLabel(String.valueOf(customer.getMaxNumForRent()));
        JLabel mjl55 = new JLabel(String.valueOf(customer.getMoney()));

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
        mjl1.setBounds(10,20,50,30);
        mjl1.setFont(new Font("幼圆",Font.BOLD, 20));
        mjl2.setBounds(10,70,50,30);
        mjl2.setFont(new Font("幼圆",Font.BOLD, 20));
        mjl3.setBounds(10,120,100,30);
        mjl3.setFont(new Font("幼圆",Font.BOLD, 20));
        mjl4.setBounds(10,170,100,30);
        mjl4.setFont(new Font("幼圆",Font.BOLD, 20));
        mjl5.setBounds(10,220,100,30);
        mjl5.setFont(new Font("幼圆",Font.BOLD, 20));

        mjl11.setBounds(80,20,120,30);
        mjl11.setFont(new Font("幼圆",Font.BOLD, 20));
        mjl22.setBounds(80,70,120,30);
        mjl22.setFont(new Font("幼圆",Font.BOLD, 18));
        mjl33.setBounds(120,120,80,30);
        mjl33.setFont(new Font("幼圆",Font.BOLD, 20));
        mjl44.setBounds(120,170,80,30);
        mjl44.setFont(new Font("幼圆",Font.BOLD, 20));
        mjl55.setBounds(120,220,80,30);
        mjl55.setFont(new Font("幼圆",Font.BOLD, 20));

        jb6.setBounds(50,260,100,30);
        javax.swing.border.Border b7 =BorderFactory.createLineBorder(Color.PINK);
        javax.swing.border.Border b8 = BorderFactory.createEtchedBorder();
        jb6.setBorder(BorderFactory.createCompoundBorder(b7,b8));
        jb6.setBackground(new Color(192,57,43));
        jb6.setForeground(Color.WHITE);
        jb6.setActionCommand("充值界面");
        jb6.addActionListener(this);


        panel3.add(jb1);
        jb1.setBounds(0,0,200,220);
        jb1.setBorder(BorderFactory.createRaisedBevelBorder());
        jb1.setActionCommand("find");
        jb1.addActionListener(this);
        jb1.setBackground(Color.WHITE);

        Map<String, Integer> map = customer.getBookedMap();
        zaijiestrings = new String[10][2];
        Iterator<String> iterator = map.keySet().iterator();
        for(int i=0;i<10;i++){
           if (iterator.hasNext()){
               String key = iterator.next();
                zaijiestrings[i][0]=key;
                zaijiestrings[i][1]=""+map.get(key);
            }
        }
        //在借列表
        String[] columnNames = {"书目", "借阅天数"};
        JTable table = new JTable(zaijiestrings,columnNames);
        table.setBackground(Color.lightGray);
        table.setEnabled(false);
        table.setRowHeight(27);
        panel4.add(new JScrollPane(table));
        table.setVisible(true);



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
            String[] columnNames = {"书目", "借阅天数"};
            JTable table = new JTable(zaijiestrings,columnNames);
            table.setBackground(Color.lightGray);
            table.setEnabled(false);
            table.setRowHeight(27);
            panel4.add(new JScrollPane(table));
            table.setVisible(true);
            panel4.validate();
        }
        else if("预借".equals(e.getActionCommand())) {
            yujiestrings = new String[10][1];
            Set<String> yujieset = customer.getWantedSet();
            Iterator<String> it = yujieset.iterator();

            for (int i = 0; i < 10; i++) {
                if (it.hasNext()) {
                    String str = it.next();
                    zaijiestrings[i][0] = str;
                }
            }
            //预借书单
            String[] columnNames = {"书目"};
            JTable table = new JTable(yujiestrings,columnNames);
            table.setBackground(Color.lightGray);
            table.setEnabled(false);
            table.setRowHeight(27);
            panel4.add(new JScrollPane(table));
            table.setVisible(true);
            panel4.validate();
        }
        else if("历史".equals(e.getActionCommand())){
//            JFrame frame = new JFrame("借阅历史");
//            frame.setBounds(700,300,600,400);
//            frame.setVisible(true);
//
//            String[] columnNames = {"A", "B", "C","D"};
//            Object[][] data = {{"12","23","38","234"},{"2","34","4","A"},{"AS","23","234","3"}};
//            JTable table = new JTable(data,columnNames);
//            table.setBackground(Color.white);
//
//            JScrollPane scrollPane = new JScrollPane(table);
//            frame.getContentPane().add(scrollPane,BorderLayout.CENTER);
//            frame.setTitle("在借");
//            frame.setVisible(true);
//            table.setFillsViewportHeight(true);
//            table.setVisible(true);
        }
        else if("充值界面".equals(e.getActionCommand())){
            JFrame frame1 = new JFrame("充值");
            frame1.setBounds(300,500,200,200);
            JPanel jpn = new JPanel(new FlowLayout());
            jpn.setBackground(new Color(192,57,43));
            jpn.setLayout(null);
            frame1.setContentPane(jpn);
            frame1.setVisible(true);

            JLabel jl = new JLabel("请输入充值金额");
            jl.setBounds(43,75,150,25);
            JTextField jtx = new JTextField();
            jtx.setBounds(20,45,150,25);

            jb7.setBounds(45,110,100,25);
            jb7.setBackground(Color.WHITE);
            jb7.setForeground(new Color(192,57,43));
            jb7.setActionCommand("充值");
            jb7.addActionListener(this);
            jpn.add(jtx);
            jpn.add(jb7);
            jpn.add(jl);
        }
        else if("充值".equals(e.getActionCommand())){
            JFrame frame = new JFrame("成功");
            frame.setBounds(320,550,150,100);
            frame.setVisible(true);

            JPanel jpn = new JPanel(new FlowLayout());
            jpn.setLayout(null);
            jpn.setBackground(Color.PINK);
            frame.setContentPane(jpn);

            JLabel jl = new JLabel("充值成功");
            jl.setBounds(40,20,80,25);
            jpn.add(jl);
        }
    }



    public void showBookInfoFrame(Book bookItem, BookPathTable bookItemPath){
        bookInfoFrame.setBounds(500,500,500,500);
        JPanel jpn = new JPanel(new FlowLayout());
        jpn.setLayout(null);
        bookInfoFrame.setContentPane(jpn);
        bookInfoFrame.setVisible(true);

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

        jieyuejb.setBounds(100,320,100,20);
        yudingjb.setBounds(275,320,100,20);

        jpn.add(bookNameLabel);
        jpn.add(bookPublisherLabel);
        jpn.add(bookAuthorLabel);
        jpn.add(bookNumLabel);
        jpn.add(bookRestNumLabel);
        jpn.add(bookKindLabel);
        jpn.add(bookDesLabel);
        jpn.add(jieyuejb);
        jpn.add(yudingjb);
    }

    class ImagePanel extends JPanel {

        public void paint(Graphics g) {
            super.paint(g);
            ImageIcon icon = new ImageIcon("C:\\Users\\14632\\Desktop\\1234.gif");
            g.drawImage(icon.getImage(), 0, 10, 200, 185, this);
        }

    }

}
