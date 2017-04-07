package controler;

import bean.Book;
import bean.BookPathTable;
import service.BookOperate;
import view.ErrAlert;
import view.FindBookFrame;
import view.UserView;

import java.awt.event.*;
import java.util.List;

/**
 * Created by ghoskno on 3/29/17.
 */
public class UserControler {

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

    }
    public void initUserView(){
        UserView UserPanel = new UserView();
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
        UserPanel.jb3.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //在借

            }
        });
        UserPanel.jb4.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //预借

            }
        });
        UserPanel.jb5.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //历史

            }
        });
        UserPanel.jb6.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //

            }
        });
        UserPanel.jb7.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //充值

            }
        });
    }

    private void showBookItem(Book bookItem,UserView UserPanel){
        //    显示搜索到的单本书
        UserPanel.findBookFrame.curBookItem = bookItem;
        UserPanel.showBookInfoFrame(bookItem,bookOperate.getBookpathtable(bookItem.getIsbn()));
        System.out.print(bookItem.getIsbn());
    }

    public void jieyueRetrun(){}
    public void yudingReturn(){}

    public static void main(String[] args){

        UserControler test = UserControler.getInstance();
        test.initUserView();
    }


}
