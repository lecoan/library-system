package controler;

import bean.Book;
import service.BookOperate;
import view.AdminView;
import view.FindBookFrame;

import java.awt.event.*;

/**
 * Created by ghoskno on 3/29/17.
 */
public class AdminControler {
//    private List<BookPathTable> curBookList = null;
    BookOperate bookOperate = BookOperate.getInstance();
//    CustomerService customerService = CustomerService;
    FindBookFrame findBookFrame = FindBookFrame.getInstance();
    CommonControler commonControler = new CommonControler();
    AdminView adminPanel = null;

    Book curBookItem = null;    //正在查看的图书

    public AdminControler() {
        adminPanel = new AdminView();
        commonControler.findBook();
        findBookFrame.findBookByIsbn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Book bookItem = bookOperate.getBookbyIsbn(findBookFrame.searchBook.getText());
                if (bookItem != null) {
                    commonControler.curBookList = null;
                    showBookItem(bookItem);
                }
                else
                    findBookFrame.findErrAlert("【ISBN ： " + findBookFrame.searchBook.getText() + "】");
            }
        });
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
        adminPanel.bookUpdateBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                adminPanel.showAddBookField(curBookItem,bookOperate.getBookpathtable(curBookItem.getIsbn()));
            }
        });
        adminPanel.addBookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //添加图书按钮点击
                adminPanel.showAddBookField(null,null);
            }
        });
        adminPanel.searchUserBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //添加图书按钮点击
                findUser();
            }
        });
        adminPanel.modifyBookBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ModifyBook();
            }
        });
    }
    private void showBookItem(Book bookItem){
        //显示搜索到的单本书
        curBookItem = bookItem;
        adminPanel.showBookInfoFrame(bookItem,bookOperate.getBookpathtable(bookItem.getIsbn()));
    }
    private void showBookItem(int row){
        showBookItem(bookOperate.getBookbyIsbn(commonControler.curBookList.get(row).getIsbn()));
    }
    private void ModifyBook(){
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
        if(curBookItem != null)
            bookOperate.deleteBook(curBookItem.getIsbn());
        bookOperate.addBook(newBook);
        bookOperate.SetBooknum(newBook.getIsbn(),new Integer(bookInfo[4]));
//        bookOperate.
        curBookItem = null;
    }
    private void findUser(){
        System.out.print(adminPanel.searchUserField.getText());

    }
    public static void main(String[] args){
        AdminControler test = new AdminControler();
    }


}
