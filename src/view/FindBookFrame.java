package view;

import bean.BookPathTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.List;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

/**
 * Created by ghoskno on 4/2/17.
 */
public class FindBookFrame {
    private volatile static FindBookFrame instance;

    private AddPlaceHolder placeholderHandle = AddPlaceHolder.getInstance();
    public JFrame findBookFrame = new JFrame("查找图书");   //查找图书面板frame
    //查找图书面板组件
    public JLabel ConditionsLabel = new JLabel();
    public JButton findBookByIsbn = new JButton("按书号搜索");
    public JButton findBookByAuthor= new JButton("按作者搜索");
    public JButton findBookByPublisher = new JButton("按出版社搜索");
    public JButton findBookByName = new JButton("按书名搜索");
    public JButton findBookByKind = new JButton("按类别搜索");
    public JButton clearBookBtn = new JButton("清除搜索");
    public JTextField searchBook = new JTextField();    //输入搜索框
    public BookJTable bookListTable = new BookJTable(0,0);


    private void FindBookFrame(){
    }
    public static FindBookFrame getInstance(){
        synchronized (FindBookFrame.class) {
            if(instance == null) {
                instance = new FindBookFrame();
            }
            return instance;
        }
    }
    public void showFindBookField(){    //初始查找图书面板
        String placeholderText = "请输入书名/书号/作者/出版社/类别进行搜索";

        findBookFrame.setSize(700,400);
        findBookFrame.setResizable(false);
        findBookFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        findBookFrame.addWindowListener(new WindowAdapter() {
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
        findBookFrame.setContentPane(container);
        findBookFrame.setLocation(300,100);
        showBookList(null);
        findBookFrame.setVisible(true);
    }
    public java.util.List<BookPathTable> showBookList(java.util.List<BookPathTable> bookList){
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
            String[] bookIsbn = bookList.get(i).getIsbn().split("-");
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
