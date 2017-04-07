package controler;

import bean.Book;
import bean.BookPathTable;
import service.BookOperate;
import view.AdminView;
import view.FindBookFrame;

import java.awt.event.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by ghoskno on 3/29/17.
 */
public class AdminControler {
    private List<BookPathTable> curBookList = null;
    BookOperate bookOperate = BookOperate.getInstance();
    FindBookFrame findBookFrame = FindBookFrame.getInstance();
    AdminView adminPanel = null;

    public AdminControler() {
        adminPanel = new AdminView();
        findBook();
        adminPanel.addBookFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                bookOperate.SaveData();
            }
        });
        adminPanel.findBookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //查找图书按钮点击
                findBookFrame.showFindBookField();
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
        adminPanel.addBookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //添加图书按钮点击
                adminPanel.showAddBookField();
            }
        });
        adminPanel.searchUserBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //添加图书按钮点击
                findUser();
            }
        });
        adminPanel.submitAddBook.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addBook();
            }
        });
    }
    private void showBookItem(Book bookItem){
        //显示搜索到的单本书
        adminPanel.showBookInfoFrame(bookItem,bookOperate.getBookpathtable(bookItem.getIsbn()));
    }
    private void showBookItem(int row){
        showBookItem(bookOperate.getBookbyIsbn(curBookList.get(row).getIsbn()));
    }
    private void addBook(){
        //监听确认添加按钮事件
        //校验信息后调用BookOperate方法添加书
        String[] bookInfo = adminPanel.submitBook();
//                if()判断合法性
        Book newBook = new Book();
        newBook.setName(bookInfo[0]);
        newBook.setPublishername(bookInfo[1]);
        newBook.setWritername(bookInfo[2]);
        newBook.setIntroduction(bookInfo[5]);
        newBook.setKind(bookInfo[3]);
        newBook.setIsbn();
        bookOperate.addBook(newBook);
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
    private void findUser(){

    }
    public static void main(String[] args){
        AdminControler test = new AdminControler();
    }


}
