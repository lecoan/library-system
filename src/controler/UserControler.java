package controler;

import bean.*;
import constance.CustomerConstance;
import listener.GlobalActionDetector;
import service.BookOperate;
import service.CustomerService;
import view.ErrAlert;
import view.GetDate;
import view.UserView;
import bean.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * Created by ghoskno on 3/29/17.
 */
public class UserControler {
    UserView userView[];
    BookOperate bookOperate = BookOperate.getInstance();
    CustomerService customerService = CustomerService.getInstance();
    ErrAlert errAlert = ErrAlert.getInstance();
    CommonControler commonControler = CommonControler.getInstance();

    Object[][] zaijiestrings;
    Object[][] yujiestrings;
    Object[][] lishistrings;
    List<String> A;
    private String mm;
    public String name, pass1, pass2;

    public JTextField newName = new JTextField();
    public JTextField newPassward = new JTextField();
    public JTextField passConfirm = new JTextField();

    private volatile static UserControler instance;

    public static UserControler getInstance() {
        synchronized (UserControler.class) {
            if (instance == null) {
                instance = new UserControler();
            }
            return instance;
        }
    }

    private UserControler() {
    }

    public UserView initUserView(Customer customer) {
        UserView UserPanel = new UserView(customer);
        commonControler.findBook(UserPanel.findBookFrame);

        UserPanel.userframe.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //查书按钮
                bookOperate.SaveData();
                UserPanel.destroyUserView();
            }
        });

        UserPanel.jb1.addMouseListener(new MouseAdapter() {
            //查找
            @Override
            public void mouseClicked(MouseEvent e) {
                UserPanel.findBookFrame.showFindBookField();
            }
        });

        UserPanel.findBookFrame.bookListTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showBookItem(bookOperate.getBookbyIsbn(UserPanel.findBookFrame.curBookList.get(UserPanel.findBookFrame.bookListTable.getSelectedRow()).getIsbn()), UserPanel);
                }
            }
        });

        UserPanel.findBookFrame.findBookByIsbn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Book bookItem = bookOperate.getBookbyIsbn(UserPanel.findBookFrame.searchBook.getText());
                if (bookItem != null) {
                    //找到图书
                    UserPanel.findBookFrame.curBookList = null;
                    showBookItem(bookItem, UserPanel);
                } else
                    errAlert.findErrAlert((int) (UserPanel.findBookFrame.Frame.getLocation().getX() + 200), (int) (UserPanel.findBookFrame.Frame.getLocation().getY() + 100), "找不到：【ISBN ： " + UserPanel.findBookFrame.searchBook.getText() + "】");
            }
        });

        UserPanel.zaijiejb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //在借列表
                zaijieTable(UserPanel);
            }
        });

        UserPanel.yujiejb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //预借列表
                yujietable(UserPanel);
            }
        });

        UserPanel.lishijb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //历史列表
                lishitable(UserPanel);
            }
        });

        UserPanel.chongzhijb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //充值按钮
                chongzhi(UserPanel);
            }
        });

        UserPanel.chongzhi10.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                chongzhi10Return(customer, UserPanel);
                UserPanel.chongzhiframe.dispose();
                UserPanel.mjl55.setText(String.format("%.2f", customer.getMoney()));
                UserPanel.panel2.validate();
            }
        });

        UserPanel.chongzhi50.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                chongzhi50Return(customer, UserPanel);
                UserPanel.chongzhiframe.dispose();
                UserPanel.mjl55.setText(String.format("%.2f", customer.getMoney()));
                UserPanel.panel2.validate();
            }
        });

        UserPanel.chongzhi100.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                chongzhi100Return(customer, UserPanel);
                UserPanel.chongzhiframe.dispose();
                UserPanel.mjl55.setText(String.format("%.2f", customer.getMoney()));
                UserPanel.panel2.validate();
            }
        });

        UserPanel.jieyuejb.addMouseListener(new MouseAdapter() {
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
                        if (UserPanel.findBookFrame.curBookItem.getIsbn() == zaijieJudge[i][0]) {
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
                        if (UserPanel.findBookFrame.curBookItem.getIsbn() == yujieJudge[i][0]) {
                            flagYujie = 1;
                        }
                    }
                }


                if (flagZaijie == 1) {
                    errAlert.findErrAlert((int) UserPanel.bookInfoFrame.getLocation().getX() + 100, (int) UserPanel.bookInfoFrame.getLocation().getY() + 100, "当前书本已经借阅，请于借书单中查看");
                } else if (bookOperate.getBookpathtable(UserPanel.findBookFrame.curBookItem.getIsbn()).getRestnum() > 0 && (customer.isFreezed() == false) && customer.getBookedMap().size() < customer.getMaxNumForRent()) {
                    jieyueRetrun(UserPanel.findBookFrame.curBookItem.getIsbn(), customer);
                    if (flagYujie == 1) {

                    }
                    UserPanel.bookInfoFrame.dispose();
                    UserPanel.findBookFrame.curBookItem = null;
                    commonControler.clearFindBookFrame(UserPanel.findBookFrame);
                    errAlert.findErrAlert((int) UserPanel.bookInfoFrame.getLocation().getX() + 100, (int) UserPanel.bookInfoFrame.getLocation().getY() + 100, "借阅成功");
                    UserPanel.mjl33.setText(String.valueOf(customer.getBookedMap().size()));
                    UserPanel.panel2.validate();
                } else if (customer.getBookedMap().size() >= customer.getMaxNumForRent()) {
                    errAlert.findErrAlert((int) UserPanel.bookInfoFrame.getLocation().getX() + 100, (int) UserPanel.bookInfoFrame.getLocation().getY() + 100, "已达到最大借书数量");
                } else if (customer.isFreezed()) {
                    errAlert.findErrAlert((int) UserPanel.bookInfoFrame.getLocation().getX() + 100, (int) UserPanel.bookInfoFrame.getLocation().getY() + 100, "你已被冻结");
                } else {
                    errAlert.findErrAlert((int) UserPanel.bookInfoFrame.getLocation().getX() + 100, (int) UserPanel.bookInfoFrame.getLocation().getY() + 100, "剩余数量不足，请预定");
                }
            }
        });

        UserPanel.yudingjb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //预定按钮
                if(UserPanel.yudingjb.isEnabled() == false){
                    return;
                }
                if (bookOperate.getBookpathtable(UserPanel.findBookFrame.curBookItem.getIsbn()).getRestnum() == 0 && (customer.isFreezed() == false)) {
                    yudingReturn(UserPanel.findBookFrame.curBookItem.getIsbn(), customer);
                    UserPanel.bookInfoFrame.dispose();
                    UserPanel.findBookFrame.curBookItem = null;
                    commonControler.clearFindBookFrame(UserPanel.findBookFrame);
                    errAlert.findErrAlert((int) UserPanel.bookInfoFrame.getLocation().getX() + 100, (int) UserPanel.bookInfoFrame.getLocation().getY() + 100, "预定成功");
                } else if (bookOperate.getBookpathtable(UserPanel.findBookFrame.curBookItem.getIsbn()).getRestnum() > 0) {
                    errAlert.findErrAlert((int) UserPanel.bookInfoFrame.getLocation().getX() + 100, (int) UserPanel.bookInfoFrame.getLocation().getY() + 100, "当前图书可以借阅无需预定");
                } else if (customer.isFreezed()) {
                    errAlert.findErrAlert((int) UserPanel.bookInfoFrame.getLocation().getX() + 100, (int) UserPanel.bookInfoFrame.getLocation().getY() + 100, "你已被冻结");
                }
            }
        });

        UserPanel.huanshujb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //还书按钮
                JScrollPane scrollPane = new JScrollPane(UserPanel.huanshulist);
                huanshujiemian(UserPanel);

            }
        });

        UserPanel.huanshulist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int a = UserPanel.huanshulist.getSelectedRow();
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
                    UserPanel.huanshuframe.dispose();
                    String ISBN = zaijiestringsX[a];
                    huanshuReturn(ISBN, customer);
                    UserPanel.mjl33.setText(String.valueOf(customer.getBookedMap().size()));
                    UserPanel.panel2.validate();
                    final boolean[] shouldShow = {false};
                    map.forEach((s, integer) -> {
                        if (GlobalActionDetector.getInstance().getDays() - integer > 30) {
                            shouldShow[0] = true;
                        }
                    });
                    if (shouldShow[0]) {
                        UserPanel.zaijietishi.setText("您有超出天数未还图书");
                    } else {
                        UserPanel.zaijietishi.setText("");
                    }
                }
            }
        });

        UserPanel.inforchangejb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                inforchange(customer, UserPanel);
            }
        });

        UserPanel.confirm.addMouseListener(new MouseAdapter() {
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
                    customerService.updateCustomer(customer);
                    UserPanel.mjl11.setText(name);
                    UserPanel.panel2.validate();
                    UserPanel.inforchangeframe.dispose();
                }
            }
        });

        return UserPanel;
    }

    private void showBookItem(Book bookItem, UserView UserPanel) {
        //    显示搜索到的单本书
        UserPanel.findBookFrame.curBookItem = bookItem;
        UserPanel.showBookInfoFrame(bookItem, bookOperate.getBookpathtable(bookItem.getIsbn()));
        System.out.print(bookItem.getIsbn());
    }

    public void zaijieTable(UserView userPanel) {
        Map<String, Integer> map = userPanel.customer.getBookedMap();
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
        Set<String> yujieset = userPanel.customer.getWantedSet();
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
        userPanel.yujietishi.setText("");
    }

    public void lishitable(UserView userPanel) {
        A = userPanel.customer.getHistoryList();
//        lishistrings = new String[A.size()][1];

        String[][] borrowHistoryList = new String[A.size()][3];
        for (int i = 0; i < A.size(); i++) {
            borrowHistoryList[i][0] = A.get(i).split("&&")[2];
            borrowHistoryList[i][1] = GetDate.getDate(new Integer(A.get(i).split("##")[1]));
            borrowHistoryList[i][2] = GetDate.getDate(new Integer(A.get(i).split("##")[2]));
        }


//        for (int i = 0; i < A.size(); i++) {
//            lishistrings[i][0] = A.get(i).split("&&")[2];
//        }
        String[] columnNames = {"书目","借书时间","还书时间"};
        JTable table = new JTable(borrowHistoryList, columnNames);
        table.setBackground(Color.lightGray);
        table.setEnabled(false);
        table.setRowHeight(27);
        userPanel.panel4.add(new JScrollPane(table));
        table.setVisible(true);
        userPanel.panel4.validate();
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
        userPanel.chongzhi100.setBounds(230, 35, 90, 30);
        userPanel.chongzhi100.setBackground(Color.WHITE);
        userPanel.chongzhi100.setForeground(new Color(192, 57, 43));

        jpn.add(userPanel.chongzhi10);
        jpn.add(userPanel.chongzhi50);
        jpn.add(userPanel.chongzhi100);
        jpn.add(JL);
    }

    public void jieyueRetrun(String isbn, Customer customer) {
        //借阅后对整体数据改动
        bookOperate.UpdateBookrank(isbn, customer.getUsername());
        customerService.rentBookByISBN(customer,isbn);
    }

    public void yudingReturn(String isbn, Customer customer) {
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
        customerService.updateCustomer(customer);

    }

    public void chongzhi50Return(Customer customer, UserView userPanel) {
        //充值后对整体数据改动
        mm = "50.00";
        float chongzhimoney = Float.parseFloat(mm);
        float num = customer.getMoney();
        customer.setMoney(num + chongzhimoney);
        if (customer.getMoney() > CustomerConstance.MAX_DEBT && customer.isFreezed()) {
            customer.setFreezed(false);
        }
        customerService.updateCustomer(customer);

    }

    public void chongzhi100Return(Customer customer, UserView userPanel) {
        //充值后对整体数据改动
        mm = "100.00";
        float chongzhimoney = Float.parseFloat(mm);
        float num = customer.getMoney();
        customer.setMoney(num + chongzhimoney);
        if (customer.getMoney() > CustomerConstance.MAX_DEBT && customer.isFreezed()) {
            customer.setFreezed(false);
        }
        customerService.updateCustomer(customer);

    }

    public void huanshujiemian(UserView userPanel) {
        //还书界面
        userPanel.huanshuframe.setBounds(700, 500, 300, 440);
        userPanel.huanshuframe.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel jpn = new JPanel(new FlowLayout());
        jpn.setLayout(new BorderLayout());
        userPanel.huanshuframe.setContentPane(jpn);
        userPanel.huanshuframe.setVisible(true);

        Map<String, Integer> map = userPanel.customer.getBookedMap();
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

    public void huanshuReturn(String isbn, Customer customer) {
        //还书后对整体数据改动
        int a = customerService.returnBook(customer, isbn);
        bookOperate.addBorrowMemory(customer.getUsername(), isbn, GetDate.getDate(a));
    }

}
