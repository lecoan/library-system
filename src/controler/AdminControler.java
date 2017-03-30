package controler;

import bean.Book;
import bean.BookPathTable;
import service.BookOperate;
import view.AdminView;
import service.BookOperate.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Created by ghoskno on 3/29/17.
 */
public class AdminControler {
    BookOperate bookOperate = new BookOperate();
    AdminView adminPanel = null;

    public AdminControler() {
        adminPanel = new AdminView();
        adminPanel.findBookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //查找图书按钮点击
                adminPanel.showFindBookField();
                findBook();
            }
        });
        adminPanel.addBookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //添加图书按钮点击
                adminPanel.showAddBookField();
                addBook();
            }
        });
        adminPanel.searchUserBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //添加图书按钮点击
                findUser();
            }
        });
    }
    private void showBookList(List<BookPathTable> bookList){
        //显示书的列表
    }
    private void showBookItem(Book bookItem){
        //显示搜索到的单本书
    }
    private void addBook(){
        //监听确认添加按钮事件
        //校验信息后调用BookOperate方法添加书
    }
    private void findBook(){
        //处理查找书的按钮点击事件
        adminPanel.findBookByAuthor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> bookList = bookOperate.getBookbyWriter(adminPanel.searchBook.getText());
                System.out.print(bookList);
                showBookList(bookList);
            }
        });
        adminPanel.findBookByName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> bookList = bookOperate.getBookbyName(adminPanel.searchBook.getText());
                System.out.print(bookList);
                showBookList(bookList);
            }
        });
        adminPanel.findBookByKind.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> bookList = bookOperate.getBookbyKind(adminPanel.searchBook.getText());
                System.out.print(bookList);
                showBookList(bookList);
            }
        });
        adminPanel.findBookByPublisher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> bookList = bookOperate.getBookbyPublisher(adminPanel.searchBook.getText());
                System.out.print(bookList);
                showBookList(bookList);
            }
        });
        adminPanel.findBookByIsbn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Book bookItem = bookOperate.getBookbyIsbn(adminPanel.searchBook.getText());
                System.out.print(bookItem);
                showBookItem(bookItem);
            }
        });
    }
    private void findUser(){

    }
    public static void main(String[] args){
        AdminControler test = new AdminControler();
    }


}
