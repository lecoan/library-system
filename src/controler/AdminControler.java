package controler;

import bean.Book;
import bean.BookPathTable;
import service.BookOperate;
import sun.swing.BakedArrayList;
import view.AdminView;
import service.BookOperate.*;
import view.FindBookFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by ghoskno on 3/29/17.
 */
public class AdminControler {
    private List<BookPathTable> bookList = null;
    BookOperate bookOperate = BookOperate.getInstance();
    FindBookFrame findBookFrame = FindBookFrame.getInstance();
    AdminView adminPanel = null;

    public AdminControler() {
        adminPanel = new AdminView();
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
    public List<BookPathTable> showBookList(List<BookPathTable> bookList){
        //显示书的列表
        String[] bookTableHead = {"书名","出版社","作者","种类","剩余数量"};
        Object[][] books = new Object[bookList.size()][5];
        for(int i=0;i<bookList.size();i++){
            String[] bookIsbn = bookList.get(i).getIsbn().split("-");
            Object[] bookInfo = {bookIsbn[2],bookIsbn[0],bookIsbn[1],bookIsbn[3],bookList.get(i).getRestnum()};
            books[i] = bookInfo;
        }
        DefaultTableModel tableModel =  (DefaultTableModel)findBookFrame.bookListTable.getModel();
        tableModel.setDataVector(books,bookTableHead);
        findBookFrame.bookListTable.setCellEditable(bookList.size(),5);
        findBookFrame.bookListTable.setVisible(true);
        findBookFrame.findBookFrame.getContentPane().validate();
        return bookList;
    }
    private void showBookItem(Book bookItem){
        //显示搜索到的单本书
        adminPanel.showBookInfoFrame(bookItem,bookOperate.getBookpathtable(bookItem.getIsbn()));
    }
    private void showBookItem(int row){
        showBookItem(bookOperate.getBookbyIsbn(bookList.get(row).getIsbn()));
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
    private void findBook(){
        //处理查找书的按钮点击事件
        findBookFrame.findBookByAuthor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                bookList = bookOperate.getBookbyWriter(findBookFrame.searchBook.getText());
                if (bookList != null)
                    showBookList(bookList);
                else
                    findBookFrame.findErrAlert("【作者：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        findBookFrame.findBookByName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                bookList = bookOperate.getBookbyName(findBookFrame.searchBook.getText());
                if (bookList != null)
                    showBookList(bookList);
                else
                    findBookFrame.findErrAlert("【书名：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        findBookFrame.findBookByKind.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                bookList = bookOperate.getBookbyKind(findBookFrame.searchBook.getText());
                if (bookList != null)
                    showBookList(bookList);
                else
                    findBookFrame.findErrAlert("【种类：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        findBookFrame.findBookByPublisher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                bookList = bookOperate.getBookbyPublisher(findBookFrame.searchBook.getText());
                if (bookList != null)
                    showBookList(bookList);
                else
                    findBookFrame.findErrAlert("【出版社：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        findBookFrame.findBookByIsbn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Book bookItem = bookOperate.getBookbyIsbn(findBookFrame.searchBook.getText());
                if (bookItem != null) {
//                    System.out.print(bookItem);
                    showBookItem(bookItem);
                }
                else
                    findBookFrame.findErrAlert("【ISBN ： " + findBookFrame.searchBook.getText() + "】");
            }
        });
    }
    private void findUser(){

    }
    public static void main(String[] args){
        AdminControler test = new AdminControler();
    }


}
