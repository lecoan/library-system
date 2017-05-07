package view;

import bean.Book;
import bean.BookPathTable;
import bean.*;
import service.BookOperate;
import service.CustomerService;
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
import listener.GlobalActionDetector;



/**
 * Created by TaBoo on 2017/3/25.
 */

public class UserView{
    Customer customer;
    BookOperate bookOperate = BookOperate.getInstance();
    public JFrame bookInfoFrame = new JFrame("图书信息");
    public FindBookFrame findBookFrame = new FindBookFrame();
    public BookJTable huanshulist = new BookJTable(0,0);
    public JFrame userframe = new JFrame("USER");
    JLabel jl1 =new JLabel();
    JLabel jl2 =new JLabel();
    JLabel jl3 =new JLabel();
    JLabel jl4 =new JLabel();

    public JButton jb1 = new JButton();
    public JButton zaijiejb = new JButton("在借图书");
    public JButton yujiejb = new JButton("预借图书");
    public JButton lishijb = new JButton("借阅历史");
    public JButton chongzhijb = new JButton("充值 ~。~");
    public JButton chongzhi10 = new JButton("10元");
    public JButton chongzhi50 = new JButton("50元");
    public JButton chongzhi100 = new JButton("100元");
    public JButton jieyuejb = new JButton("借阅");
    public JButton yudingjb = new JButton("预定");
    public JButton huanshujb = new JButton("幻术");
    public JButton inforchangejb = new JButton("消息");
    public JButton confirm = new JButton("确认修改");

    public JPanel panel1 = new JPanel();
    public JPanel panel2 = new JPanel();
    public JPanel panel3 = new ImagePanel();
    public JPanel panel4 = new JPanel();
    public JPanel panel5 = new JPanel();

    public JLabel mjl11 = new JLabel();
    public JLabel mjl33 = new JLabel();
    public JLabel mjl55 = new JLabel();

    public JFrame chongzhiframe = new JFrame("充值");
    public JFrame huanshuframe = new JFrame("还书");
    public JFrame inforchangeframe = new JFrame("修改个人消息");

//    public JTextField newname = new JTextField();
//    public JTextField newPassword = new JTextField();
//    public JTextField passwordConfirm = new JTextField();

    public UserView(Customer customer){
        this.customer = customer;
        userframe.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        userframe.setBounds(300,300,800,500);
        userframe.setLayout(null);

        JPanel panel6 = new JPanel(){
            protected void paintComponent(Graphics g){
                ImageIcon icon = new ImageIcon("res\\2.jpg");
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


        panel1.add(zaijiejb);
        panel1.add(yujiejb);
        panel1.add(lishijb);
        JLabel jl1 =new JLabel();
        JLabel jl2 =new JLabel();
        JLabel jl3 =new JLabel();
        panel1.add(jl1);
        panel1.add(jl2);
        panel1.add(jl3);
        jl1.setBounds(40,50,200,50);
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

        zaijiejb.setText("在借书籍");
        zaijiejb.setBounds(200,70,120,30);
        javax.swing.border.Border b10 =BorderFactory.createLineBorder(Color.PINK);
        javax.swing.border.Border b11 = BorderFactory.createEtchedBorder();
        zaijiejb.setBorder(BorderFactory.createCompoundBorder(b10,b11));
        zaijiejb.setBackground(Color.WHITE);
        zaijiejb.setForeground(new Color(192,57,43));

        yujiejb.setText("预借书籍");
        yujiejb.setBounds(340,70,120,30);
        javax.swing.border.Border b3 =BorderFactory.createLineBorder(Color.WHITE);
        javax.swing.border.Border b4 = BorderFactory.createEtchedBorder();
        yujiejb.setBorder(BorderFactory.createCompoundBorder(b3,b4));
        yujiejb.setBackground(Color.WHITE);
        yujiejb.setForeground(new Color(192,57,43));

        lishijb.setText("历史记录");
        lishijb.setBounds(480,70,120,30);
        javax.swing.border.Border b5 =BorderFactory.createLineBorder(Color.WHITE);
        javax.swing.border.Border b6 = BorderFactory.createEtchedBorder();
        lishijb.setBorder(BorderFactory.createCompoundBorder(b5,b6));
        lishijb.setBackground(Color.WHITE);
        lishijb.setForeground(new Color(192,57,43));

        panel2.add(chongzhijb);
        JLabel mjl1 = new JLabel("姓名");
        JLabel mjl2 = new JLabel("学号");
        JLabel mjl3 = new JLabel("在借本数");
        JLabel mjl4 = new JLabel("权限本数");
        JLabel mjl5 = new JLabel("账户余额");
        mjl11 = new JLabel(customer.getUsername());
        JLabel mjl22 = new JLabel(customer.getId());
        mjl33.setText(String.valueOf(customer.getBookedMap().size()));
        JLabel mjl44 = new JLabel(String.valueOf(customer.getMaxNumForRent()));
        mjl55.setText(String.valueOf(customer.getMoney()));

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

        chongzhijb.setBounds(50,260,100,30);
        javax.swing.border.Border b7 =BorderFactory.createLineBorder(Color.PINK);
        javax.swing.border.Border b8 = BorderFactory.createEtchedBorder();
        chongzhijb.setBorder(BorderFactory.createCompoundBorder(b7,b8));
        chongzhijb.setBackground(new Color(192,57,43));
        chongzhijb.setForeground(Color.WHITE);


        panel3.add(jb1);
        jb1.setBounds(0,0,200,220);
        jb1.setBorder(BorderFactory.createRaisedBevelBorder());
        jb1.setBackground(Color.WHITE);


        panel5.add(huanshujb);
        huanshujb.setBounds(650,15,100,30);
        javax.swing.border.Border b14 =BorderFactory.createLineBorder(Color.WHITE);
        javax.swing.border.Border b15 = BorderFactory.createEtchedBorder();
        huanshujb.setBorder(BorderFactory.createCompoundBorder(b14,b15));
        huanshujb.setBackground(Color.WHITE);
        huanshujb.setForeground(new Color(192,57,43));
        huanshujb.setText("还书");

        panel5.add(inforchangejb);
        inforchangejb.setBounds(50,15,100,30);
        javax.swing.border.Border b20 =BorderFactory.createLineBorder(Color.WHITE);
        javax.swing.border.Border b21 = BorderFactory.createEtchedBorder();
        inforchangejb.setBorder(BorderFactory.createCompoundBorder(b20,b21));
        inforchangejb.setBackground(Color.WHITE);
        inforchangejb.setForeground(new Color(192,57,43));
        inforchangejb.setText("修改个人信息");

        int flagzaijie = 0;
        int flagyujie = 0;

        Map<String, Integer> map = customer.getBookedMap();
        int[] zaijieJudge = new int[map.size()];
        Iterator<String> iterator = map.keySet().iterator();
        for(int i=0;i<map.size();i++){
            if (iterator.hasNext()){
                String key = iterator.next();
                zaijieJudge[i]=(GlobalActionDetector.getInstance().getDays() - map.get(key));
                if(zaijieJudge[i]>30){
                    flagzaijie = 1;
                }
            }
        }

        Set<String> yujieset = customer.getWantedSet();
        Iterator<String> it = yujieset.iterator();
        int[] yujieJudge = new int[10];
        for (int i = 0; i < 10; i++) {
            if (it.hasNext()) {
                String str = it.next();
                yujieJudge[i] = bookOperate.getBookpathtable(str).getRestnum();
                if(yujieJudge[i]>0){
                    flagyujie = 1;
                }
            }
        }

        JLabel zaijietishi = new JLabel("您有超出天数未还图书");
        if(flagzaijie == 1){
            panel5.add(zaijietishi);
            zaijietishi.setBounds(220,15,200,30);
            zaijietishi.setForeground(Color.WHITE);
            zaijietishi.setFont(new Font("幼圆",Font.BOLD, 15));
        }
        JLabel yujietishi = new JLabel("您有预定图书可以借阅");
        if(flagyujie == 1){
            panel5.add(yujietishi);
            yujietishi.setBounds(420,15,200,30);
            yujietishi.setForeground(Color.WHITE);
            yujietishi.setFont(new Font("幼圆",Font.BOLD, 15));
        }





        userframe.getContentPane().add(panel1);
        userframe.getContentPane().add(panel2);
        userframe.getContentPane().add(panel3);
        userframe.getContentPane().add(panel4);
        userframe.getContentPane().add(panel5);
        userframe.getContentPane().add(panel6);
        userframe.setVisible(true);
    }

    public void showBookInfoFrame(Book bookItem, BookPathTable bookItemPath){
        //图书信息
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
            ImageIcon icon = new ImageIcon("res\\1234.gif");
            g.drawImage(icon.getImage(), 0, 10, 200, 185, this);
        }

    }

    public void destroyUserView(){
        userframe.dispose();
        CustomerService.getInstance().updateCustomer(customer);
        findBookFrame.Frame.dispose();
        findBookFrame = null;
        bookInfoFrame.dispose();
        chongzhiframe.dispose();
        huanshuframe.dispose();
        inforchangeframe.dispose();
    }

}
