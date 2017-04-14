package controler;

import bean.*;
import service.BookOperate;
import view.ErrAlert;
import view.FindBookFrame;
import view.UserView;
import bean.Customer;
import java.awt.event.*;
import java.util.List;

/**
 * Created by ghoskno on 3/29/17.
 */
public class UserControler {
    UserView userView[];
    Customer customer;
    BookOperate bookOperate = BookOperate.getInstance();
    ErrAlert errAlert = ErrAlert.getInstance();
    CommonControler commonControler = CommonControler.getInstance();

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
        UserView UserPanel = new UserView(customer);
        commonControler.findBook(UserPanel.findBookFrame);
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
        UserPanel.jieyuejb.addMouseListener(new MouseAdapter() {
            //查找
            @Override
            public void mouseClicked(MouseEvent e) {
                if(bookOperate.getBookpathtable(UserPanel.findBookFrame.curBookItem.getIsbn()).getRestnum()>0){
                    jieyueRetrun(UserPanel.findBookFrame.curBookItem.getIsbn());
                    UserPanel.bookInfoFrame.dispose();
                    UserPanel.findBookFrame.curBookItem = null;
                    commonControler.clearFindBookFrame(UserPanel.findBookFrame);
                    errAlert.findErrAlert((int)UserPanel.bookInfoFrame.getLocation().getX()+100,(int)UserPanel.bookInfoFrame.getLocation().getY()+100,"借阅成功");
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
        return UserPanel;
    }

    private void showBookItem(Book bookItem,UserView UserPanel){
        //    显示搜索到的单本书
        UserPanel.findBookFrame.curBookItem = bookItem;
        UserPanel.showBookInfoFrame(bookItem,bookOperate.getBookpathtable(bookItem.getIsbn()));
        System.out.print(bookItem.getIsbn());
    }

    public void jieyueRetrun(String isbn){
        bookOperate.UpdateBookrank(isbn);
    }

    public void yudingReturn(String isbn){
        bookOperate.addBorrowMemory(customer.getUsername(),isbn,"123");
    }

    public static void main(String[] args){

//        UserControler test = UserControler.getInstance();
//        test.initUserView();
    }


}
