package controler;

import bean.Book;
import bean.BookPathTable;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import service.BookOperate;
import view.FindBookFrame;
import view.UserView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

/**
 * Created by ghoskno on 3/29/17.
 */
public class UserControler {
//    private List<BookPathTable> curBookList = null;
    BookOperate bookOperate = BookOperate.getInstance();
    FindBookFrame findBookFrame = FindBookFrame.getInstance();
    CommonControler commonControler = new CommonControler();
    UserView UserPanel = null;

    public UserControler() {
        UserPanel = new UserView();
        UserPanel.jb1.addMouseListener(new MouseAdapter() {
            //查找
            @Override
            public void mouseClicked(MouseEvent e) {
                findBookFrame.showFindBookField();
                commonControler.findBook();
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
                    findBookFrame.findErrAlert("【ISBN ： " + findBookFrame.searchBook.getText() + "】");
            }
        });
        findBookFrame.initErrAlert();
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
