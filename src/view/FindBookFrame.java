package view;

import bean.Book;
import bean.BookPathTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by ghoskno on 4/2/17.
 */
public class FindBookFrame {

    private AddPlaceHolder placeholderHandle = AddPlaceHolder.getInstance();
    public JFrame Frame = new JFrame("查找图书");   //查找图书面板frame
    //查找图书面板组件
    public JLabel ConditionsLabel = new JLabel();
    public JLabel CountLabel = new JLabel();
    public JButton findBookByIsbn = new JButton("按书号搜索");
    public JButton findBookByAuthor= new JButton("按作者搜索");
    public JButton findBookByPublisher = new JButton("按出版社搜索");
    public JButton findBookByName = new JButton("按书名搜索");
    public JButton findBookByKind = new JButton("按类别搜索");
    public JButton clearBookBtn = new JButton("清除搜索");
    public JTextField searchBook = new JTextField();    //输入搜索框
    public BookJTable bookListTable = new BookJTable(0,0);

    public java.util.List<BookPathTable> curBookList = null;              //设置当前查看图书列表为空
    public Book curBookItem = null;    //正在查看的图书


    public void FindBookFrame(){

    }
    public void showFindBookField(){    //初始查找图书面板
        String placeholderText = "请输入书名/书号/作者/出版社/类别进行搜索";

        Frame.setSize(700,400);
        Frame.setResizable(false);
        Frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                searchBook.setText(placeholderText);
            }
        });

        Box findBookBox = Box.createVerticalBox();
        Box findBookBoxB = Box.createHorizontalBox();
        Box findBookBoxT = Box.createHorizontalBox();


        findBookBoxB.add(findBookByName);
        findBookBoxB.add(Box.createHorizontalStrut(5));
        findBookBoxB.add(findBookByIsbn);
        findBookBoxB.add(Box.createHorizontalStrut(5));
        findBookBoxB.add(findBookByAuthor);
        findBookBoxB.add(Box.createHorizontalStrut(5));
        findBookBoxB.add(findBookByPublisher);
        findBookBoxB.add(Box.createHorizontalStrut(5));
        findBookBoxB.add(findBookByKind);
        findBookBoxB.add(Box.createHorizontalStrut(5));
        findBookBoxB.add(clearBookBtn);

        placeholderHandle.addingPlaceholder(searchBook,placeholderText);
        findBookBoxT.add(searchBook);
        findBookBoxT.setSize(100,30);

        Box ConditionsLabelBox = Box.createHorizontalBox();
        ConditionsLabelBox.add(ConditionsLabel);
        ConditionsLabelBox.add(CountLabel);
        ConditionsLabelBox.add(Box.createGlue());

        findBookBox.add(Box.createVerticalStrut(20));
        findBookBox.add(ConditionsLabelBox);
        findBookBox.add(Box.createVerticalStrut(20));
        findBookBox.add(findBookBoxT);
        findBookBox.add(Box.createVerticalStrut(20));
        findBookBox.add(findBookBoxB);

        Box bookListBox = Box.createHorizontalBox();
        bookListTable.setPreferredScrollableViewportSize(new Dimension(580,200));
        JScrollPane scrollPane = new JScrollPane(bookListTable);
        bookListBox.add(scrollPane);
        JPanel container = new JPanel();

        container.add(findBookBox,BorderLayout.CENTER);
        container.add(bookListBox,BorderLayout.SOUTH);
        Frame.setContentPane(container);
        Frame.setLocation(300,100);
        showBookList(null, Frame);
        Frame.setVisible(true);
    }
    public java.util.List<BookPathTable> showBookList(java.util.List<BookPathTable> bookList,JFrame findBookFrame){
        //显示书的列表
        String[] bookTableHead = {"书名","出版社","作者","种类","剩余数量"};
        if(bookList == null) {  //传入图书列表为null，清空当前列表
            DefaultTableModel tableModel =  (DefaultTableModel)bookListTable.getModel();
            tableModel.setDataVector((Object[][]) null,bookTableHead);
            bookListTable.setVisible(false);
            findBookFrame.getContentPane().validate();
            return null;
        }
        Object[][] books = new Object[bookList.size()][5];
        for (int i = 0; i < bookList.size(); i++) {
            String[] bookIsbn = bookList.get(i).getIsbn().split("\\&\\&");
            Object[] bookInfo = {bookIsbn[2], bookIsbn[0], bookIsbn[1], bookIsbn[3], bookList.get(i).getRestnum()};
            books[i] = bookInfo;
        }
        DefaultTableModel tableModel =  (DefaultTableModel)bookListTable.getModel();
        tableModel.setDataVector(books,bookTableHead);
        bookListTable.setCellEditable(bookList.size(),5);
        bookListTable.setVisible(true);
        findBookFrame.getContentPane().validate();
        return bookList;
    }
}
