package controler;

import bean.Book;
import service.BookOperate;
import view.AdminView;
import view.ErrAlert;
import view.FindBookFrame;

import java.awt.event.*;

/**
 * Created by ghoskno on 3/29/17.
 */
public class AdminControler {
    BookOperate bookOperate = BookOperate.getInstance();
    FindBookFrame findBookFrame = FindBookFrame.getInstance();
    ErrAlert errAlert = ErrAlert.getInstance();
    CommonControler commonControler = new CommonControler();
    AdminView adminPanel = null;

    Book curBookItem = null;    //正在查看的图书

    public AdminControler() {   //初始化管理员界面
        adminPanel = new AdminView();
        commonControler.findBook();     //初始化查找图书界面
        //处理通过Isbn查找图书按钮点击事件
        findBookFrame.findBookByIsbn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Book bookItem = bookOperate.getBookbyIsbn(findBookFrame.searchBook.getText());
                if (bookItem != null) { //找到图书
                    commonControler.curBookList = null;
                    showBookItem(bookItem);
                }
                else
                    errAlert.findErrAlert((int)(findBookFrame.findBookFrame.getLocation().getX()+200),(int)(findBookFrame.findBookFrame.getLocation().getY()+100),"找不到：【ISBN ： " + findBookFrame.searchBook.getText() + "】");
            }
        });
        //添加图书界面关闭时保存添加图书信息
//        adminPanel.addBookFrame.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                bookOperate.SaveData();
//            }
//        });
        //点击查找图书按钮，显示查找图书界面
        adminPanel.findBookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //查找图书按钮点击
                findBookFrame.showFindBookField();
            }
        });
        adminPanel.adminFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                bookOperate.SaveData();
            }
        });
        //双击图书列表中某行时，显示图书详细信息
        findBookFrame.bookListTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    showBookItem(bookOperate.getBookbyIsbn(commonControler.curBookList.get(findBookFrame.bookListTable.getSelectedRow()).getIsbn()));
//                    showBookItem(findBookFrame.bookListTable.getSelectedRow());
                }
            }
        });
        //点击更新图书按钮，调用添加图书界面显示修改界面
        adminPanel.bookUpdateBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                adminPanel.showModifyBookField(curBookItem,bookOperate.getBookpathtable(curBookItem.getIsbn()));
            }
        });
        //点击删除图书界面,修改当前查看图书
        adminPanel.bookDeleBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                bookOperate.deleteBook(curBookItem.getIsbn());
                curBookItem = null;
            }
        });
        //点击添加图书按钮，显示添加图书界面
        adminPanel.addBookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //添加图书按钮点击
                adminPanel.showModifyBookField(null,null);
            }
        });
        //点击查找用户按钮
        adminPanel.searchUserBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //搜索用户按钮点击
                findUser();
            }
        });
        //点击修改/添加图书按钮
        adminPanel.modifyBookBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ModifyBook();
            }
        });
    }
    private void showBookItem(Book bookItem){   //显示图书信息
        //显示搜索到的单本书
        curBookItem = bookItem;
        adminPanel.showBookInfoFrame(bookItem,bookOperate.getBookpathtable(bookItem.getIsbn()));
    }
    private void ModifyBook(){  //修改图书
        //监听确认添加/修改图书按钮事件
        //校验信息后调用BookOperate方法添加书
        String[] bookInfo = adminPanel.submitBook();
//                if()判断合法性
        //生成新的Book对象
        Book newBook = new Book();
        newBook.setName(bookInfo[0]);
        newBook.setPublishername(bookInfo[1]);
        newBook.setWritername(bookInfo[2]);
        newBook.setIntroduction(bookInfo[5]);
        newBook.setKind(bookInfo[3]);
        newBook.setIsbn();
        /*当前查看图书对象不为空，则为更新操作，删除原有图书对象
        否则为添加新图书
         */
        if(curBookItem != null)
            bookOperate.deleteBook(curBookItem.getIsbn());
        bookOperate.addBook(newBook,new Integer(bookInfo[4]));
    }
    private void findUser(){
        System.out.print(adminPanel.searchUserField.getText());

    }
    public static void main(String[] args){
        AdminControler test = new AdminControler();
    }


}
