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
    private List<BookPathTable> curBookList = null;
    BookOperate bookOperate = BookOperate.getInstance();
    UserView UserPanel = null;

    public UserControler() {
        UserPanel = new UserView();
        UserPanel.jb1.addMouseListener(new MouseAdapter() {
            //查找
            @Override
            public void mouseClicked(MouseEvent e) {
                UserPanel.findBookFrame.showFindBookField();
                findBook();
            }
        });
        UserPanel.findBookFrame.bookListTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    showBookItem( UserPanel.findBookFrame.bookListTable.getSelectedRow());
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
        UserPanel.findBookFrame.initErrAlert();
    }


    private void checkList(List<BookPathTable> extraBookList){
        if(curBookList != null) {
            curBookList.retainAll(extraBookList);
            if(curBookList != null) {
                UserPanel.findBookFrame.showBookList(curBookList);
                addConditionLabel( UserPanel.findBookFrame.searchBook.getText());
            }
            else
                UserPanel.findBookFrame.findErrAlert("没有找到符合条件图书！");
        }
        else{
            UserPanel.findBookFrame.showBookList(extraBookList);
            curBookList = extraBookList;
            addConditionLabel( UserPanel.findBookFrame.searchBook.getText());
        }
    }
    private void addConditionLabel(String con){
        UserPanel.findBookFrame.ConditionsLabel.setText( UserPanel.findBookFrame.ConditionsLabel.getText() +"  \"  "+ con + "  \"  ");
        UserPanel.findBookFrame.ConditionsLabel.invalidate();
    }
    private void clearConditionLabel(){
        UserPanel.findBookFrame.ConditionsLabel.setText("");
    }
    private void findBook(){
        //处理查找书的按钮点击事件
        UserPanel.findBookFrame.findBookByAuthor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> extraBookList = bookOperate.getBookbyWriter( UserPanel.findBookFrame.searchBook.getText());
                if (extraBookList != null )
                    checkList(extraBookList);
                else
                    UserPanel.findBookFrame.findErrAlert("【作者：" +  UserPanel.findBookFrame.searchBook.getText() + "】");
            }
        });
        UserPanel.findBookFrame.findBookByName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> extraBookList = bookOperate.getBookbyName( UserPanel.findBookFrame.searchBook.getText());
                if (extraBookList != null)
                    checkList(extraBookList);
                else
                    UserPanel.findBookFrame.findErrAlert("【书名：" +  UserPanel.findBookFrame.searchBook.getText() + "】");
            }
        });
        UserPanel.findBookFrame.findBookByKind.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> extraBookList = bookOperate.getBookbyKind( UserPanel.findBookFrame.searchBook.getText());
                if (extraBookList != null)
                    checkList(extraBookList);
                else
                    UserPanel.findBookFrame.findErrAlert("【种类：" +  UserPanel.findBookFrame.searchBook.getText() + "】");
            }
        });
        UserPanel.findBookFrame.findBookByPublisher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> extraBookList = bookOperate.getBookbyPublisher( UserPanel.findBookFrame.searchBook.getText());
                if (extraBookList != null)
                    checkList(extraBookList);
                else
                    UserPanel.findBookFrame.findErrAlert("【出版社：" +  UserPanel.findBookFrame.searchBook.getText() + "】");
            }
        });
        UserPanel.findBookFrame.findBookByIsbn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Book bookItem = bookOperate.getBookbyIsbn( UserPanel.findBookFrame.searchBook.getText());
                if (bookItem != null) {
                    curBookList = null;
//                    System.out.print(bookItem);
                    showBookItem(bookItem);
                }
                else
                    UserPanel.findBookFrame.findErrAlert("【ISBN ： " +  UserPanel.findBookFrame.searchBook.getText() + "】");
            }
        });
        UserPanel.findBookFrame.clearBookBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                curBookList = null;
                UserPanel.findBookFrame.showBookList(curBookList);
                clearConditionLabel();
            }
        });
    }

    private void showBookItem(Book bookItem){
    //    显示搜索到的单本书
        UserPanel.showBookInfoFrame(bookItem,bookOperate.getBookpathtable(bookItem.getIsbn()));
        System.out.print(bookItem.getIsbn());
    }

    private void showBookItem(int row){
        showBookItem(bookOperate.getBookbyIsbn(curBookList.get(row).getIsbn()));
    }

    public void jieyueRetrun(){}
    public void yudingReturn(){}


    public static void main(String[] args){

        UserControler test = new UserControler();
       // UserControler test2 = new UserControler();
    }


}
