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
    FindBookFrame findBookFrame = FindBookFrame.getInstance();
    UserView UserPanel = null;

    public UserControler() {
        UserPanel = new UserView();
        UserPanel.jb1.addMouseListener(new MouseAdapter() {
            //查找
            @Override
            public void mouseClicked(MouseEvent e) {
                findBookFrame.showFindBookField();
                findBook();
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
        findBookFrame.initErrAlert();
    }


    private void checkList(List<BookPathTable> extraBookList){
        if(curBookList != null) {
            curBookList.retainAll(extraBookList);
            if(curBookList != null) {
                findBookFrame.showBookList(curBookList);
                addConditionLabel(findBookFrame.searchBook.getText());
            }
            else
                findBookFrame.findErrAlert("没有找到符合条件图书！");
        }
        else{
            findBookFrame.showBookList(extraBookList);
            curBookList = extraBookList;
            addConditionLabel(findBookFrame.searchBook.getText());
        }
    }
    private void addConditionLabel(String con){
        findBookFrame.ConditionsLabel.setText(findBookFrame.ConditionsLabel.getText() +"  \"  "+ con + "  \"  ");
        findBookFrame.ConditionsLabel.invalidate();
    }
    private void clearConditionLabel(){
        findBookFrame.ConditionsLabel.setText("");
    }
    private void findBook(){
        //处理查找书的按钮点击事件
        findBookFrame.findBookByAuthor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> extraBookList = bookOperate.getBookbyWriter(findBookFrame.searchBook.getText());
                if (extraBookList != null )
                    checkList(extraBookList);
                else
                    findBookFrame.findErrAlert("【作者：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        findBookFrame.findBookByName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> extraBookList = bookOperate.getBookbyName(findBookFrame.searchBook.getText());
                if (extraBookList != null)
                    checkList(extraBookList);
                else
                    findBookFrame.findErrAlert("【书名：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        findBookFrame.findBookByKind.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> extraBookList = bookOperate.getBookbyKind(findBookFrame.searchBook.getText());
                if (extraBookList != null)
                    checkList(extraBookList);
                else
                    findBookFrame.findErrAlert("【种类：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        findBookFrame.findBookByPublisher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> extraBookList = bookOperate.getBookbyPublisher(findBookFrame.searchBook.getText());
                if (extraBookList != null)
                    checkList(extraBookList);
                else
                    findBookFrame.findErrAlert("【出版社：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        findBookFrame.findBookByIsbn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Book bookItem = bookOperate.getBookbyIsbn(findBookFrame.searchBook.getText());
                if (bookItem != null) {
                    curBookList = null;
//                    System.out.print(bookItem);
                    showBookItem(bookItem);
                }
                else
                    findBookFrame.findErrAlert("【ISBN ： " + findBookFrame.searchBook.getText() + "】");
            }
        });
        findBookFrame.clearBookBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                curBookList = null;
                findBookFrame.showBookList(curBookList);
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
    }


}
