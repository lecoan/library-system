package view;

import bean.Book;
import bean.BookPathTable;
import bean.*;
import constance.CustomerConstance;
import controler.CommonControler;
import service.BookOperate;
import service.CustomerService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import java.awt.Graphics;
import java.util.*;

import bean.*;
import listener.GlobalActionDetector;


/**
 * Created by TaBoo on 2017/3/25.
 */

public class UserView {
    public Customer customer;
    BookOperate bookOperate = BookOperate.getInstance();
    public JFrame bookInfoFrame = new JFrame("图书信息");
    public FindBookFrame findBookFrame = new FindBookFrame();
    public BookJTable huanshulist = new BookJTable(0, 0);
    public JFrame userframe = new JFrame("USER");
    JLabel jl1 = new JLabel();
    JLabel jl2 = new JLabel();
    JLabel jl3 = new JLabel();
    JLabel jl4 = new JLabel();

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
//    public JButton renovate = new JButton(("刷新"));

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
    BookOperate bookOperate = BookOperate.getInstance();
    CustomerService customerService = CustomerService.getInstance();
    ErrAlert errAlert = ErrAlert.getInstance();
    CommonControler commonControler = CommonControler.getInstance();

    Object[][] zaijiestrings;
    Object[][] yujiestrings;
    Object[][] lishistrings;
    java.util.List<String> A;
    private String mm;
    public String name, pass1, pass2;

    public JTextField newName = new JTextField();
    public JTextField newPassward = new JTextField();
    public JTextField passConfirm = new JTextField();

//    public JTextField newname = new JTextField();
//    public JTextField newPassword = new JTextField();
//    public JTextField passwordConfirm = new JTextField();

    public UserView(Customer customer) {
        this.customer = customer;
        userframe.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        userframe.setBounds(300, 300, 800, 500);
        userframe.setLayout(null);

        JPanel panel6 = new JPanel() {
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon("res\\2.jpg");
                Image imgg = icon.getImage();
                g.drawImage(imgg, 0, 0, 200, 80, icon.getImageObserver());
            }
        };


        panel1.setBounds(0, 0, 800, 100);
        panel1.setBackground(new Color(192, 57, 43));
        panel1.setLayout(null);

        panel2.setBounds(0, 100, 200, 300);
        panel2.setBackground(Color.WHITE);
        panel2.setLayout(null);

        panel3.setBounds(600, 180, 200, 220);
        //panel3.setBackground(Color.LIGHT_GRAY);
        panel3.setLayout(null);

        panel4.setBounds(200, 100, 400, 300);
        javax.swing.border.Border x = BorderFactory.createLineBorder(new Color(192, 57, 43));
        javax.swing.border.Border y = BorderFactory.createEtchedBorder();
        panel4.setBorder(BorderFactory.createCompoundBorder(x, y));
        panel4.setBackground(Color.WHITE);
        panel4.setVisible(true);
        panel4.setLayout(new BorderLayout());

        panel5.setBounds(0, 400, 800, 100);
        panel5.setBackground(new Color(192, 57, 43));
        panel5.setLayout(null);

        panel6.setBounds(600, 100, 200, 80);


        panel1.add(zaijiejb);
        panel1.add(yujiejb);
        panel1.add(lishijb);
        JLabel jl1 = new JLabel();
        JLabel jl2 = new JLabel();
        JLabel jl3 = new JLabel();
        panel1.add(jl1);
        panel1.add(jl2);
        panel1.add(jl3);
        jl1.setBounds(40, 50, 200, 50);
        jl1.setText("个人信息");
        jl1.setForeground(Color.WHITE);
        jl1.setFont(new Font("幼圆", Font.BOLD, 23));
        jl2.setBounds(337, 10, 200, 50);
        jl2.setText("图书信息");
        jl2.setForeground(Color.WHITE);
        jl2.setFont(new Font("幼圆", Font.BOLD, 30));
        jl3.setBounds(620, 50, 200, 50);
        jl3.setText("出品人：C511");
        jl3.setForeground(Color.WHITE);
        jl3.setFont(new Font("幼圆", Font.BOLD, 23));

        zaijiejb.setText("在借书籍");
        zaijiejb.setBounds(200, 70, 120, 30);
        javax.swing.border.Border b10 = BorderFactory.createLineBorder(Color.PINK);
        javax.swing.border.Border b11 = BorderFactory.createEtchedBorder();
        zaijiejb.setBorder(BorderFactory.createCompoundBorder(b10, b11));
        zaijiejb.setBackground(Color.WHITE);
        zaijiejb.setForeground(new Color(192, 57, 43));

        yujiejb.setText("预借书籍");
        yujiejb.setBounds(340, 70, 120, 30);
        javax.swing.border.Border b3 = BorderFactory.createLineBorder(Color.WHITE);
        javax.swing.border.Border b4 = BorderFactory.createEtchedBorder();
        yujiejb.setBorder(BorderFactory.createCompoundBorder(b3, b4));
        yujiejb.setBackground(Color.WHITE);
        yujiejb.setForeground(new Color(192, 57, 43));

        lishijb.setText("历史记录");
        lishijb.setBounds(480, 70, 120, 30);
        javax.swing.border.Border b5 = BorderFactory.createLineBorder(Color.WHITE);
        javax.swing.border.Border b6 = BorderFactory.createEtchedBorder();
        lishijb.setBorder(BorderFactory.createCompoundBorder(b5, b6));
        lishijb.setBackground(Color.WHITE);
        lishijb.setForeground(new Color(192, 57, 43));

        panel2.add(chongzhijb);
        //panel2.add(renovate);
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
        mjl1.setBounds(10, 20, 50, 30);
        mjl1.setFont(new Font("幼圆", Font.BOLD, 20));
        mjl2.setBounds(10, 70, 50, 30);
        mjl2.setFont(new Font("幼圆", Font.BOLD, 20));
        mjl3.setBounds(10, 120, 100, 30);
        mjl3.setFont(new Font("幼圆", Font.BOLD, 20));
        mjl4.setBounds(10, 170, 100, 30);
        mjl4.setFont(new Font("幼圆", Font.BOLD, 20));
        mjl5.setBounds(10, 220, 100, 30);
        mjl5.setFont(new Font("幼圆", Font.BOLD, 20));

        mjl11.setBounds(80, 20, 120, 30);
        mjl11.setFont(new Font("幼圆", Font.BOLD, 20));
        mjl22.setBounds(80, 70, 120, 30);
        mjl22.setFont(new Font("幼圆", Font.BOLD, 18));
        mjl33.setBounds(120, 120, 80, 30);
        mjl33.setFont(new Font("幼圆", Font.BOLD, 20));
        mjl44.setBounds(120, 170, 80, 30);
        mjl44.setFont(new Font("幼圆", Font.BOLD, 20));
        mjl55.setBounds(100, 220, 70, 30);
        mjl55.setFont(new Font("幼圆", Font.BOLD, 18));

        chongzhijb.setBounds(50, 260, 100, 30);
        javax.swing.border.Border b7 = BorderFactory.createLineBorder(Color.PINK);
        javax.swing.border.Border b8 = BorderFactory.createEtchedBorder();
        chongzhijb.setBorder(BorderFactory.createCompoundBorder(b7, b8));
        chongzhijb.setBackground(new Color(192, 57, 43));
        chongzhijb.setForeground(Color.WHITE);

//        renovate.setBounds(150,220,50,30);
//        javax.swing.border.Border b16 =BorderFactory.createLineBorder(Color.PINK);
//        javax.swing.border.Border b17 = BorderFactory.createEtchedBorder();
//        renovate.setBorder(BorderFactory.createCompoundBorder(b7,b8));
//        renovate.setBackground(new Color(192,57,43));
//        renovate.setForeground(Color.WHITE);


        panel3.add(jb1);
        jb1.setBounds(0, 0, 200, 220);
        jb1.setBorder(BorderFactory.createRaisedBevelBorder());
        jb1.setBackground(Color.WHITE);


        panel5.add(huanshujb);
        huanshujb.setBounds(650, 15, 100, 30);
        javax.swing.border.Border b14 = BorderFactory.createLineBorder(Color.WHITE);
        javax.swing.border.Border b15 = BorderFactory.createEtchedBorder();
        huanshujb.setBorder(BorderFactory.createCompoundBorder(b14, b15));
        huanshujb.setBackground(Color.WHITE);
        huanshujb.setForeground(new Color(192, 57, 43));
        huanshujb.setText("还书");

        panel5.add(inforchangejb);
        inforchangejb.setBounds(50, 15, 100, 30);
        javax.swing.border.Border b20 = BorderFactory.createLineBorder(Color.WHITE);
        javax.swing.border.Border b21 = BorderFactory.createEtchedBorder();
        inforchangejb.setBorder(BorderFactory.createCompoundBorder(b20, b21));
        inforchangejb.setBackground(Color.WHITE);
        inforchangejb.setForeground(new Color(192, 57, 43));
        inforchangejb.setText("修改个人信息");

        zaijietishi = new JLabel("您有超出天数未还图书");
        yujietishi = new JLabel("您有预定图书可以借阅");
        zaijietishi.setBounds(220, 15, 200, 30);
        zaijietishi.setForeground(Color.WHITE);
        zaijietishi.setFont(new Font("幼圆", Font.BOLD, 15));
        yujietishi.setBounds(420, 15, 200, 30);
        yujietishi.setForeground(Color.WHITE);
        yujietishi.setFont(new Font("幼圆", Font.BOLD, 15));
        panel5.add(zaijietishi);
        panel5.add(yujietishi);

        getNofication(customer);

        userframe.getContentPane().add(panel1);
        userframe.getContentPane().add(panel2);
        userframe.getContentPane().add(panel3);
        userframe.getContentPane().add(panel4);
        userframe.getContentPane().add(panel5);
        userframe.getContentPane().add(panel6);
        userframe.setVisible(true);

        GlobalActionDetector.getInstance().addEvent(days -> {
            CustomerService.getInstance().caculateMoney(customer);
            mjl55.setText(customer.getMoney() + "");
            getNofication(customer);
        });
    }

    private void getNofication(Customer customer) {
        int flagzaijie = 0;
        int flagyujie = 0;

        Map<String, Integer> map = customer.getBookedMap();
        int[] zaijieJudge = new int[map.size()];
        Iterator<String> iterator = map.keySet().iterator();
        for (int i = 0; i < map.size(); i++) {
            if (iterator.hasNext()) {
                String key = iterator.next();
                zaijieJudge[i] = (GlobalActionDetector.getInstance().getDays() - map.get(key));
                if (zaijieJudge[i] > 30) {
                    flagzaijie = 1;
                }
            }
        }

        Set<String> yujieset = customer.getWantedSet();
        Iterator<String> it = yujieset.iterator();
        int[] yujieJudge = new int[yujieset.size()];
        for (int i = 0; i < yujieset.size(); i++) {
            if (it.hasNext()) {
                String str = it.next();
                yujieJudge[i] = bookOperate.getBookpathtable(str).getRestnum();
                if (yujieJudge[i] > 0) {
                    flagyujie = 1;
                }
            }
        }
        if (flagyujie == 1) {
            yujietishi.setText("您有预定图书可以借阅");
        } else {
            yujietishi.setText("");
        }
        if (flagzaijie == 1) {
            zaijietishi.setText("您有超出天数未还图书");
        } else {
            zaijietishi.setText("");
        }
    }

    public JLabel yujietishi;
    public JLabel zaijietishi;

    public void showBookInfoFrame(Book bookItem, BookPathTable bookItemPath) {
        //图书信息
        bookInfoFrame.setBounds(500, 500, 500, 500);
        JPanel jpn = new JPanel(new FlowLayout());
        jpn.setLayout(null);
        bookInfoFrame.setContentPane(jpn);
        bookInfoFrame.setVisible(true);

        JLabel bookNameLabel = new JLabel("书名 ：  " + bookItem.getName());
        bookNameLabel.setBounds(100, 20, 300, 20);

        JLabel bookPublisherLabel = new JLabel("出版社名 ：  " + bookItem.getPublishername());
        bookPublisherLabel.setBounds(100, 50, 300, 20);


        JLabel bookAuthorLabel = new JLabel("作者 ：  " + bookItem.getWritername());
        bookAuthorLabel.setBounds(100, 80, 300, 20);

        JLabel bookNumLabel = new JLabel("总数量 ：  " + bookItemPath.getTotalnum());
        bookNumLabel.setBounds(100, 110, 300, 20);

        JLabel bookRestNumLabel = new JLabel("剩余数量 ：  " + bookItemPath.getRestnum());
        bookRestNumLabel.setBounds(100, 140, 300, 20);


        JLabel bookKindLabel = new JLabel("种类 ：  " + bookItem.getKind());
        bookKindLabel.setBounds(100, 180, 300, 20);
        ;

        JLabel bookDesLabel = new JLabel("<html><body><p>" + "简介 ：  " + bookItem.getIntroduction() + "</p></body></html>");
        System.out.print(bookItem.getIntroduction());
        bookDesLabel.setBounds(100, 220, 300, 80);

        jieyuejb.setBounds(100, 320, 100, 20);
        yudingjb.setBounds(275, 320, 100, 20);

        jpn.add(bookNameLabel);
        jpn.add(bookPublisherLabel);
        jpn.add(bookAuthorLabel);
        jpn.add(bookNumLabel);
        jpn.add(bookRestNumLabel);
        jpn.add(bookKindLabel);
        jpn.add(bookDesLabel);
        jpn.add(jieyuejb);
        jpn.add(yudingjb);

        addListener();
    }

    private void addListener() {
         userframe.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //查书按钮
                bookOperate.SaveData();
                 destroyUserView();
            }
        });

         jb1.addMouseListener(new MouseAdapter() {
            //查找
            @Override
            public void mouseClicked(MouseEvent e) {
                 findBookFrame.showFindBookField();
            }
        });

         findBookFrame.bookListTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showBookItem(bookOperate.getBookbyIsbn( findBookFrame.curBookList.get( findBookFrame.bookListTable.getSelectedRow()).getIsbn()), this);
                }
            }
        });

         findBookFrame.findBookByIsbn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Book bookItem = bookOperate.getBookbyIsbn( findBookFrame.searchBook.getText());
                if (bookItem != null) {
                    //找到图书
                     findBookFrame.curBookList = null;
                    showBookItem(bookItem, this);
                } else
                    errAlert.findErrAlert((int) ( findBookFrame.Frame.getLocation().getX() + 200), (int) ( findBookFrame.Frame.getLocation().getY() + 100), "找不到：【ISBN ： " +  findBookFrame.searchBook.getText() + "】");
            }
        });

         zaijiejb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //在借列表
                zaijieTable(this);
            }
        });

         yujiejb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //预借列表
                yujietable(this);
            }
        });

         lishijb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //历史列表
                lishitable(this);
            }
        });

         chongzhijb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //充值按钮
                chongzhi(this);
            }
        });

         chongzhi10.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                chongzhi10Return(customer, this);
                 chongzhiframe.dispose();
                 mjl55.setText(String.valueOf(customer.getMoney()));
                 panel2.validate();
            }
        });

         chongzhi50.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                chongzhi50Return(customer, this);
                 chongzhiframe.dispose();
                 mjl55.setText(String.valueOf(customer.getMoney()));
                 panel2.validate();
            }
        });

         chongzhi100.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                chongzhi100Return(customer, this);
                 chongzhiframe.dispose();
                 mjl55.setText(String.valueOf(customer.getMoney()));
                 panel2.validate();
            }
        });
         jieyuejb.addMouseListener(new MouseAdapter() {
            //查找
            @Override
            public void mouseClicked(MouseEvent e) {
                //借阅按钮
                int flagZaijie = 0;
                int flagYujie = 0;

                Map<String, Integer> map = customer.getBookedMap();
                Object[][] zaijieJudge = new String[map.size()][1];
                Iterator<String> iterator = map.keySet().iterator();
                for (int i = 0; i < map.size(); i++) {
                    if (iterator.hasNext()) {
                        String key = iterator.next();
                        zaijieJudge[i][0] = key;
                        if ( findBookFrame.curBookItem.getIsbn() == zaijieJudge[i][0]) {
                            flagZaijie = 1;
                        }
                    }
                }

                Set<String> yujieset = customer.getWantedSet();
                Object[][] yujieJudge = new String[yujieset.size()][2];
                Iterator<String> it = yujieset.iterator();

                for (int i = 0; i < yujieset.size(); i++) {
                    if (it.hasNext()) {
                        String str = it.next();
                        yujieJudge[i][0] = str;
                        if ( findBookFrame.curBookItem.getIsbn() == yujieJudge[i][0]) {
                            flagYujie = 1;
                        }
                    }
                }


                if (flagZaijie == 1) {
                    errAlert.findErrAlert((int)  bookInfoFrame.getLocation().getX() + 100, (int)  bookInfoFrame.getLocation().getY() + 100, "当前书本已经借阅，请于借书单中查看");
                } else if (bookOperate.getBookpathtable( findBookFrame.curBookItem.getIsbn()).getRestnum() > 0 && (!customer.isFreezed()) && customer.getBookedMap().size() < customer.getMaxNumForRent()) {
                    jieyueRetrun( findBookFrame.curBookItem.getIsbn());
                    if (flagYujie == 1) {

                    }
                     bookInfoFrame.dispose();
                     findBookFrame.curBookItem = null;
                    commonControler.clearFindBookFrame( findBookFrame);
                    errAlert.findErrAlert((int)  bookInfoFrame.getLocation().getX() + 100, (int)  bookInfoFrame.getLocation().getY() + 100, "借阅成功");
                     mjl33.setText(String.valueOf(customer.getBookedMap().size()));
                     panel2.validate();
                } else if (customer.getBookedMap().size() > customer.getMaxNumForRent()) {
                    errAlert.findErrAlert((int)  bookInfoFrame.getLocation().getX() + 100, (int)  bookInfoFrame.getLocation().getY() + 100, "账户权限不足");
                } else if (customer.isFreezed() == true) {
                    errAlert.findErrAlert((int)  bookInfoFrame.getLocation().getX() + 100, (int)  bookInfoFrame.getLocation().getY() + 100, "你已被冻结");
                } else {
                    errAlert.findErrAlert((int)  bookInfoFrame.getLocation().getX() + 100, (int)  bookInfoFrame.getLocation().getY() + 100, "剩余数量不足，请预定");
                }
            }
        });

         yudingjb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //预定按钮
                if (bookOperate.getBookpathtable( findBookFrame.curBookItem.getIsbn()).getRestnum() == 0 && (customer.isFreezed() == false)) {
                    yudingReturn( findBookFrame.curBookItem.getIsbn());
                     bookInfoFrame.dispose();
                     findBookFrame.curBookItem = null;
                    commonControler.clearFindBookFrame( findBookFrame);
                    errAlert.findErrAlert((int)  bookInfoFrame.getLocation().getX() + 100, (int)  bookInfoFrame.getLocation().getY() + 100, "预定成功");
                } else if (bookOperate.getBookpathtable( findBookFrame.curBookItem.getIsbn()).getRestnum() > 0) {
                    errAlert.findErrAlert((int)  bookInfoFrame.getLocation().getX() + 100, (int)  bookInfoFrame.getLocation().getY() + 100, "当前图书可以借阅无需预定");
                } else if (customer.isFreezed()) {
                    errAlert.findErrAlert((int)  bookInfoFrame.getLocation().getX() + 100, (int)  bookInfoFrame.getLocation().getY() + 100, "你已被冻结");
                }
            }
        });

         huanshujb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //还书按钮
                JScrollPane scrollPane = new JScrollPane( huanshulist);
                huanshujiemian(this);

            }
        });

         huanshulist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int a =  huanshulist.getSelectedRow();
                    Map<String, Integer> map = customer.getBookedMap();
                    String[] zaijiestringsX = new String[map.size()];
                    Iterator<String> iterator = map.keySet().iterator();
                    for (int i = 0; i < map.size(); i++) {
                        if (iterator.hasNext()) {
                            String key = iterator.next();
                            zaijiestringsX[i] = key;
                        }
                    }
                    errAlert.findErrAlert(500, 500, "还书成功");
                     huanshuframe.dispose();
                    String ISBN = zaijiestringsX[a];
                    huanshuReturn(ISBN);
                     mjl33.setText(String.valueOf(customer.getBookedMap().size()));
                     panel2.validate();
                    final boolean[] shouldShow = {false};
                    map.forEach((s, integer) -> {
                        if (GlobalActionDetector.getInstance().getDays() - integer > 30) {
                            shouldShow[0] = true;
                        }
                    });
                    if (shouldShow[0]) {
                         zaijietishi.setText("您有超出天数未还图书");
                    } else {
                         zaijietishi.setText("");
                    }
                }
            }
        });

         inforchangejb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                inforchange(customer, this);
            }
        });

         confirm.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                name = newName.getText().trim();
                pass1 = newPassward.getText().trim();
                pass2 = passConfirm.getText().trim();
                if (!pass1.equals(pass2)) {
                    errAlert.findErrAlert(500, 500, "两次输入密码不一样");
                } else if (pass1.isEmpty() || !pass1.matches("^[A-Za-z0-9]{4,40}$")) {
                    errAlert.findErrAlert(500, 500, "密码应为4到40位的字母和注释组成");
                } else if (name.isEmpty() || !name.matches("^[A-Za-z0-9]{4,40}$")) {
                    errAlert.findErrAlert(500, 500, "用户名应为4到40位的字母和注释组成");
                } else {
                    customer.setPassword(pass1);
                    customer.setUsername(name);
                     mjl11.setText(name);
                     panel2.validate();
                     inforchangeframe.dispose();
                }
            }
        });
    }

    class ImagePanel extends JPanel {

        public void paint(Graphics g) {
            super.paint(g);
            ImageIcon icon = new ImageIcon("res\\1234.gif");
            g.drawImage(icon.getImage(), 0, 10, 200, 185, this);
        }

    }

    public void destroyUserView() {
        userframe.dispose();
        CustomerService.getInstance().updateCustomer(customer);
        findBookFrame.Frame.dispose();
        findBookFrame = null;
        bookInfoFrame.dispose();
        chongzhiframe.dispose();
        huanshuframe.dispose();
        inforchangeframe.dispose();
    }
    public void jieyueRetrun(String isbn) {
        //借阅后对整体数据改动
        bookOperate.UpdateBookrank(isbn);
        customerService.rentBookByISBN(customer, isbn);
    }

    public void yudingReturn(String isbn) {
        //预定后对整体数据改动
        customer.getWantedSet().add(isbn);
    }

    public void chongzhi10Return(Customer customer, UserView userPanel) {
        //充值后对整体数据改动
        mm = "10.00";
        float chongzhimoney = Float.parseFloat(mm);
        float num = customer.getMoney();
        customer.setMoney(num + chongzhimoney);
        if (customer.getMoney() > CustomerConstance.MAX_DEBT && customer.isFreezed()) {
            customer.setFreezed(false);
        }

    }

    public void chongzhi50Return(Customer customer, UserView userPanel) {
        //充值后对整体数据改动
        mm = "50.00";
        float chongzhimoney = Float.parseFloat(mm);
        float num = customer.getMoney();
        customer.setMoney(num + chongzhimoney);

    }

    public void chongzhi100Return(Customer customer, UserView userPanel) {
        //充值后对整体数据改动
        mm = "100.00";
        float chongzhimoney = Float.parseFloat(mm);
        float num = customer.getMoney();
        customer.setMoney(num + chongzhimoney);

    }

    public void huanshujiemian(UserView userPanel) {
        //还书界面
        userPanel.huanshuframe.setBounds(700, 500, 300, 440);
        userPanel.huanshuframe.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel jpn = new JPanel(new FlowLayout());
        jpn.setLayout(new BorderLayout());
        userPanel.huanshuframe.setContentPane(jpn);
        userPanel.huanshuframe.setVisible(true);

        Map<String, Integer> map = customer.getBookedMap();
        Iterator<String> iterator = map.keySet().iterator();
        zaijiestrings = new String[map.size()][2];
        for (int i = 0; i < map.size(); i++) {
            if (iterator.hasNext()) {
                String key = iterator.next();
                zaijiestrings[i][0] = key.split("&&")[2];
                zaijiestrings[i][1] = "" + (GlobalActionDetector.getInstance().getDays() - map.get(key));
            }
        }

        userPanel.huanshulist.setPreferredScrollableViewportSize(new Dimension(300, 200));
        JScrollPane scrollPane = new JScrollPane(userPanel.huanshulist);
        jpn.add(scrollPane);

        String[] bookTableHead = {"书目"};
        DefaultTableModel tableModel = (DefaultTableModel) userPanel.huanshulist.getModel();
        tableModel.setDataVector(zaijiestrings, bookTableHead);
        userPanel.huanshulist.setRowHeight(30);
        userPanel.huanshulist.setCellEditable(0, 5);
        userPanel.huanshulist.setVisible(true);

        //userPanel.huanshuframe.dispose();
    }

    public void huanshuReturn(String isbn) {
        //还书后对整体数据改动
        int a = customerService.returnBook(customer, isbn);
        bookOperate.addBorrowMemory(customer.getUsername(), isbn, GetDate.getDate(a));
    }
    public void chongzhi(UserView userPanel) {
        userPanel.chongzhiframe.setBounds(600, 500, 340, 120);
        JPanel jpn = new JPanel(new FlowLayout());
        jpn.setBackground(new Color(192, 57, 43));
        jpn.setLayout(null);
        userPanel.chongzhiframe.setContentPane(jpn);
        userPanel.chongzhiframe.setVisible(true);

        JLabel JL = new JLabel("选择充值金额");
        JL.setBounds(125, 10, 100, 20);
        JL.setFont(new Font("幼圆", Font.BOLD, 13));
        JL.setForeground(Color.WHITE);

        userPanel.chongzhi10.setBounds(30, 35, 70, 30);
        userPanel.chongzhi10.setBackground(Color.WHITE);
        userPanel.chongzhi10.setForeground(new Color(192, 57, 43));
        userPanel.chongzhi50.setBounds(130, 35, 70, 30);
        userPanel.chongzhi50.setBackground(Color.WHITE);
        userPanel.chongzhi50.setForeground(new Color(192, 57, 43));
        userPanel.chongzhi100.setBounds(230, 35, 70, 30);
        userPanel.chongzhi100.setBackground(Color.WHITE);
        userPanel.chongzhi100.setForeground(new Color(192, 57, 43));

        jpn.add(userPanel.chongzhi10);
        jpn.add(userPanel.chongzhi50);
        jpn.add(userPanel.chongzhi100);
        jpn.add(JL);
    }
    public void inforchange(Customer customer, UserView userPanel) {
        //个人信息修改界面
        userPanel.inforchangeframe = new JFrame();
        userPanel.inforchangeframe.setBounds(500, 500, 500, 320);
        JPanel JP = new JPanel();
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        userPanel.inforchangeframe.setVisible(true);
        userPanel.inforchangeframe.setLayout(null);

        jp1.setBounds(0, 0, 500, 50);
        jp1.setBackground(new Color(192, 57, 43));
        jp1.setLayout(null);

        JLabel title = new JLabel("个人信息修改");
        title.setFont(new Font("幼圆", Font.BOLD, 20));
        jp1.add(title);
        title.setBounds(175, 15, 150, 20);

        JP.setBounds(0, 50, 500, 200);
        JP.setLayout(null);

        jp2.setBounds(0, 250, 500, 50);
        jp2.setBackground(new Color(192, 57, 43));
        jp2.setLayout(null);

        JLabel oldname = new JLabel("旧用户名:");
        JLabel oleName = new JLabel(customer.getUsername());
        JLabel newname = new JLabel("新用户名:");
        //JTextField newName = new JTextField();

        JLabel newpassward = new JLabel("新密码:");
        JLabel passconfirm = new JLabel("新密码确认:");
//        JTextField newPassward = new JTextField();
//        JTextField passConfirm = new JTextField();

        oldname.setFont(new Font("幼圆", Font.BOLD, 15));
        oleName.setFont(new Font("幼圆", Font.BOLD, 15));
        newname.setFont(new Font("幼圆", Font.BOLD, 15));
        newpassward.setFont(new Font("幼圆", Font.BOLD, 15));
        passconfirm.setFont(new Font("幼圆", Font.BOLD, 15));

        javax.swing.border.Border b7 = BorderFactory.createLineBorder(Color.WHITE);
        javax.swing.border.Border b8 = BorderFactory.createEtchedBorder();
        userPanel.confirm.setBorder(BorderFactory.createCompoundBorder(b7, b8));
        userPanel.confirm.setBackground(new Color(192, 57, 43));
        userPanel.confirm.setForeground(Color.WHITE);

        JP.add(oleName);
        JP.add(oldname);
        JP.add(newName);
        JP.add(newname);
        JP.add(newPassward);
        JP.add(newpassward);
        JP.add(passconfirm);
        JP.add(passConfirm);
        JP.add(userPanel.confirm);

        oldname.setBounds(50, 20, 100, 20);
        oleName.setBounds(150, 20, 100, 20);
        newname.setBounds(250, 20, 100, 20);
        newName.setBounds(350, 20, 100, 20);

        newpassward.setBounds(50, 100, 100, 20);
        newPassward.setBounds(130, 100, 100, 20);
        passconfirm.setBounds(250, 100, 100, 20);
        passConfirm.setBounds(350, 100, 100, 20);

        userPanel.confirm.setBounds(200, 150, 100, 25);

//        name = newName.getText().trim();
//        pass1 = newPassward.getText().trim();
//        pass2 = passConfirm.getText().trim();


        userPanel.inforchangeframe.getContentPane().add(JP);
        userPanel.inforchangeframe.getContentPane().add(jp1);
        userPanel.inforchangeframe.getContentPane().add(jp2);
        userPanel.inforchangeframe.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
    private void showBookItem(Book bookItem, UserView UserPanel) {
        //    显示搜索到的单本书
        UserPanel.findBookFrame.curBookItem = bookItem;
        UserPanel.showBookInfoFrame(bookItem, bookOperate.getBookpathtable(bookItem.getIsbn()));
        System.out.print(bookItem.getIsbn());
    }

    public void zaijieTable(UserView userPanel) {
        Map<String, Integer> map = customer.getBookedMap();
        zaijiestrings = new String[map.size()][2];
        Iterator<String> iterator = map.keySet().iterator();
        for (int i = 0; i < map.size(); i++) {
            if (iterator.hasNext()) {
                String key = iterator.next();
                zaijiestrings[i][0] = key.split("&&")[2];
                zaijiestrings[i][1] = "" + (GlobalActionDetector.getInstance().getDays() - map.get(key));
            }
        }
        //在借列表
        String[] columnNames = {"书目", "借阅天数"};
        JTable table = new JTable(zaijiestrings, columnNames);
        table.setBackground(Color.lightGray);
        table.setEnabled(false);
        table.setRowHeight(27);
        userPanel.panel4.add(new JScrollPane(table));
        table.setVisible(true);
        userPanel.panel4.validate();
    }

    public void yujietable(UserView userPanel) {
        Set<String> yujieset = customer.getWantedSet();
        yujiestrings = new String[yujieset.size()][2];
        Iterator<String> it = yujieset.iterator();

        for (int i = 0; i < yujieset.size(); i++) {
            if (it.hasNext()) {
                String str = it.next();
                yujiestrings[i][0] = str.split("&&")[2];
                yujiestrings[i][1] = String.valueOf(bookOperate.getBookpathtable(str).getRestnum());
            }
        }
        //预借书单
        String[] columnNames = {"书目", "当前剩余数量"};
        JTable table = new JTable(yujiestrings, columnNames);
        table.setBackground(Color.lightGray);
        table.setEnabled(false);
        table.setRowHeight(27);
        userPanel.panel4.add(new JScrollPane(table));
        table.setVisible(true);
        userPanel.panel4.validate();
    }

    public void lishitable(UserView userPanel) {
        A = customer.getHistoryList();
        lishistrings = new String[A.size()][1];
        for (int i = 0; i < A.size(); i++) {
            lishistrings[i][0] = A.get(i).split("&&")[2];
        }
        String[] columnNames = {"书目"};
        JTable table = new JTable(lishistrings, columnNames);
        table.setBackground(Color.lightGray);
        table.setEnabled(false);
        table.setRowHeight(27);
        userPanel.panel4.add(new JScrollPane(table));
        table.setVisible(true);
        userPanel.panel4.validate();
    }
}
