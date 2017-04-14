package controler;

import bean.*;
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

/**
 * Created by ghoskno on 3/29/17.
 */
public class UserControler {
    UserView userView[];
    Customer customer;
    BookOperate bookOperate = BookOperate.getInstance();
    CustomerService customerService = CustomerService.getInstance();
    ErrAlert errAlert = ErrAlert.getInstance();
    CommonControler commonControler = CommonControler.getInstance();

    Object[][] zaijiestrings;
    Object[][] yujiestrings;
    Object[][] lishistrings;
    List<String> A;

    private volatile static UserControler instance;

    public static UserControler getInstance(){
        synchronized (UserControler.class) {
            if(instance == null) {
                instance = new UserControler();
            }
            return instance;
        }
    }
    private UserControler() {
        //initUserView(customer);
    }
    public UserView initUserView(Customer customer){
        this.customer = customer;
        UserView UserPanel = new UserView(customer);
        commonControler.findBook(UserPanel.findBookFrame);
        UserPanel.userframe.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
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
                if(e.getClickCount() == 2){
                    showBookItem(bookOperate.getBookbyIsbn(UserPanel.findBookFrame.curBookList.get(UserPanel.findBookFrame.bookListTable.getSelectedRow()).getIsbn()),UserPanel);
                }
            }
        });
        UserPanel.findBookFrame.findBookByIsbn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Book bookItem = bookOperate.getBookbyIsbn(UserPanel.findBookFrame.searchBook.getText());
                if (bookItem != null) { //找到图书
                    UserPanel.findBookFrame.curBookList = null;
                    showBookItem(bookItem,UserPanel);
                }
                else
                    errAlert.findErrAlert((int)(UserPanel.findBookFrame.Frame.getLocation().getX()+200),(int)(UserPanel.findBookFrame.Frame.getLocation().getY()+100),"找不到：【ISBN ： " + UserPanel.findBookFrame.searchBook.getText() + "】");
            }
        });
        UserPanel.zaijiejb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                zaijieTable(UserPanel);
            }
        });
        UserPanel.yujiejb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                yujietable(UserPanel);
            }
        });
        UserPanel.lishijb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lishitable(UserPanel);
            }
        });
        UserPanel.chongzhijb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                chongzhi(UserPanel);
            }
        });
        UserPanel.chongzhiwanchengJB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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

                chongzhiReturn(customer,UserPanel);
                UserPanel.mjl55.setText(String.valueOf(customer.getBookedMap().size()));
                UserPanel.panel2.validate();
            }
        });
        UserPanel.jieyuejb.addMouseListener(new MouseAdapter() {
            //查找
            @Override
            public void mouseClicked(MouseEvent e) {
                if(bookOperate.getBookpathtable(UserPanel.findBookFrame.curBookItem.getIsbn()).getRestnum()>0&&customer.isFreezed()==false){
                    jieyueRetrun(UserPanel.findBookFrame.curBookItem.getIsbn());
                    UserPanel.bookInfoFrame.dispose();
                    UserPanel.findBookFrame.curBookItem = null;
                    commonControler.clearFindBookFrame(UserPanel.findBookFrame);
                    errAlert.findErrAlert((int)UserPanel.bookInfoFrame.getLocation().getX()+100,(int)UserPanel.bookInfoFrame.getLocation().getY()+100,"借阅成功");
                    UserPanel.mjl33.setText(String.valueOf(customer.getBookedMap().size()));
                    UserPanel.panel2.validate();
                }
                else if(customer.isFreezed()==true){
                    errAlert.findErrAlert((int)UserPanel.bookInfoFrame.getLocation().getX()+100,(int)UserPanel.bookInfoFrame.getLocation().getY()+100,"你已被冻结");
                }
                else {
                    errAlert.findErrAlert((int)UserPanel.bookInfoFrame.getLocation().getX()+100,(int)UserPanel.bookInfoFrame.getLocation().getY()+100,"剩余数量不足，请预定");
                }
            }
        });
        UserPanel.yudingjb.addMouseListener(new MouseAdapter() {
            //查找
            @Override
            public void mouseClicked(MouseEvent e) {
                yudingReturn(UserPanel.findBookFrame.curBookItem.getIsbn());
            }
        });
        UserPanel.huanshujb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                huanshujiemian(UserPanel);
            }
        });
        UserPanel.huanshulist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    int a = UserPanel.huanshulist.getSelectedRow();
                    Map<String, Integer> map = customer.getBookedMap();
                    String[] zaijiestringsX = new String[10];
                    Iterator<String> iterator = map.keySet().iterator();
                    for(int i=0;i<10;i++){
                        if (iterator.hasNext()){
                            String key = iterator.next();
                            zaijiestringsX[i]=key;
                        }
                    }
                    errAlert.findErrAlert(500,500,"还书成功");
                    String ISBN = zaijiestringsX[a];
                    huanshuReturn(ISBN);
                    UserPanel.mjl33.setText(String.valueOf(customer.getBookedMap().size()));
                    UserPanel.panel2.validate();
                }
            }
        });

        return UserPanel;
    }

    private void showBookItem(Book bookItem,UserView UserPanel){
        //    显示搜索到的单本书
        UserPanel.findBookFrame.curBookItem = bookItem;
        UserPanel.showBookInfoFrame(bookItem,bookOperate.getBookpathtable(bookItem.getIsbn()));
        System.out.print(bookItem.getIsbn());
    }

    public void zaijieTable(UserView userPanel){
        Map<String, Integer> map = customer.getBookedMap();
        zaijiestrings = new String[10][2];
        Iterator<String> iterator = map.keySet().iterator();
        for(int i=0;i<10;i++){
            if (iterator.hasNext()){
                String key = iterator.next();
                zaijiestrings[i][0]=key.split("&&")[2];
                zaijiestrings[i][1]=""+ (GlobalActionDetector.getInstance().getDays() - map.get(key));
            }
        }
        //在借列表
        String[] columnNames = {"书目", "借阅天数"};
        JTable table = new JTable(zaijiestrings,columnNames);
        table.setBackground(Color.lightGray);
        table.setEnabled(false);
        table.setRowHeight(27);
        userPanel.panel4.add(new JScrollPane(table));
        table.setVisible(true);
        userPanel.panel4.validate();
    }

    public void yujietable(UserView userPanel){
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
        userPanel.panel4.add(new JScrollPane(table));
        table.setVisible(true);
        userPanel.panel4.validate();
    }

    public void lishitable(UserView userPanel){
        lishistrings = new String[30][1];
        A = customer.getHistoryList();
        for (int i = 0; i < A.size(); i++) {
            lishistrings[i][0] = A.get(i).split("&&")[2];
        }
        String[] columnNames = {"书目"};
        JTable table = new JTable(lishistrings,columnNames);
        table.setBackground(Color.lightGray);
        table.setEnabled(false);
        table.setRowHeight(27);
        userPanel.panel4.add(new JScrollPane(table));
        table.setVisible(true);
        userPanel.panel4.validate();
    }

    public void chongzhi(UserView userPanel){
        userPanel.chongzhiframe.setBounds(300,500,200,200);
        JPanel jpn = new JPanel(new FlowLayout());
        jpn.setBackground(new Color(192,57,43));
        jpn.setLayout(null);
        userPanel.chongzhiframe.setContentPane(jpn);
        userPanel.chongzhiframe.setVisible(true);

        JLabel jl = new JLabel("请输入充值金额");
        jl.setBounds(43,75,150,25);
        JTextField jtx = new JTextField();
        jtx.setBounds(20,45,150,25);

        userPanel.chongzhiwanchengJB.setBounds(45,110,100,25);
        userPanel.chongzhiwanchengJB.setBackground(Color.WHITE);
        userPanel.chongzhiwanchengJB.setForeground(new Color(192,57,43));
        jpn.add(jtx);
        jpn.add(userPanel.chongzhiwanchengJB);
        jpn.add(jl);
    }

    public void jieyueRetrun(String isbn){
        bookOperate.UpdateBookrank(isbn);
        customerService.rentBookByISBN(customer,isbn);
    }

    public void yudingReturn(String isbn){

    }

    public void chongzhiReturn(Customer customer,UserView userPanel){

    }

    public void huanshujiemian(UserView userPanel){
        userPanel.huanshuframe.setBounds(700,500,300,440);
        JPanel jpn = new JPanel(new FlowLayout());
        jpn.setLayout(new BorderLayout());
        userPanel.huanshuframe.setContentPane(jpn);
        userPanel.huanshuframe.setVisible(true);

        Map<String, Integer> map = customer.getBookedMap();
        zaijiestrings = new String[10][2];
        Iterator<String> iterator = map.keySet().iterator();
        for(int i=0;i<10;i++){
            if (iterator.hasNext()){
                String key = iterator.next();
                zaijiestrings[i][0]=key.split("&&")[2];
                zaijiestrings[i][1]=""+ (GlobalActionDetector.getInstance().getDays() - map.get(key));
            }
        }

        userPanel.huanshulist.setPreferredScrollableViewportSize(new Dimension(300,200));
        JScrollPane scrollPane = new JScrollPane(userPanel.huanshulist);
        jpn.add(scrollPane);

        String[] bookTableHead = {"书目"};
        DefaultTableModel tableModel =  (DefaultTableModel)userPanel.huanshulist.getModel();
        tableModel.setDataVector(zaijiestrings,bookTableHead);
        userPanel.huanshulist.setRowHeight(40);
        userPanel.huanshulist.setCellEditable(10,5);
        userPanel.huanshulist.setVisible(true);

        jpn.add(userPanel.huanshulist);
        userPanel.huanshuframe.validate();
    }

    public void huanshuReturn(String isbn){
        GetDate ggetDate = new GetDate();
        int a=customerService.returnBook(customer,isbn);
        bookOperate.addBorrowMemory(customer.getUsername(),isbn,ggetDate.getDate(a));
    }

    public static void main(String[] args){

//        UserControler test = UserControler.getInstance();
//        test.initUserView();
    }


}
