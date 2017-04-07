package controler;

import bean.Book;
import service.BookOperate;
import view.ErrAlert;
import view.FindBookFrame;
import view.UserView;

import java.awt.event.*;

/**
 * Created by ghoskno on 3/29/17.
 */
public class UserControler {
    //    private List<BookPathTable> curBookList = null;
    BookOperate bookOperate = BookOperate.getInstance();
    FindBookFrame findBookFrame = new FindBookFrame();
    ErrAlert errAlert = ErrAlert.getInstance();
    CommonControler commonControler = CommonControler.getInstance();
    UserView UserPanel = null;

    public UserControler() {
        UserPanel = new UserView();
        UserPanel.jb1.addMouseListener(new MouseAdapter() {
            //查找
            @Override
            public void mouseClicked(MouseEvent e) {
                findBookFrame.showFindBookField();
                commonControler.findBook(findBookFrame);
            }
        });
        findBookFrame.bookListTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    showBookItem(findBookFrame.bookListTable.getSelectedRow());
                }
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
        findBookFrame.findBookByIsbn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Book bookItem = bookOperate.getBookbyIsbn(findBookFrame.searchBook.getText());
                if (bookItem != null) {
                    commonControler.curBookList = null;
//                    System.out.print(bookItem);
                    showBookItem(bookItem);
                }
                else
                    errAlert.findErrAlert((int)(findBookFrame.Frame.getLocation().getX()+200),(int)(findBookFrame.Frame.getLocation().getY()+100),"找不到：【ISBN ： " + findBookFrame.searchBook.getText() + "】");
            }
        });
    }


    private void showBookItem(Book bookItem){
        //    显示搜索到的单本书
        UserPanel.showBookInfoFrame(bookItem,bookOperate.getBookpathtable(bookItem.getIsbn()));
        System.out.print(bookItem.getIsbn());
    }

    private void showBookItem(int row){
        showBookItem(bookOperate.getBookbyIsbn(commonControler.curBookList.get(row).getIsbn()));
    }

    public void jieyueRetrun(){}
    public void yudingReturn(){}


    public static void main(String[] args){
        UserControler test = new UserControler();
    }


}
