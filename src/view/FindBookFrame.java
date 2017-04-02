package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by ghoskno on 4/2/17.
 */
public class FindBookFrame {
    private volatile static FindBookFrame instance;
    private void FindBookFrame(){}
    AddPlaceHolder placeholderHandle = AddPlaceHolder.getInstance();
    public JFrame findBookFrame = new JFrame("查找图书");   //查找图书面板frame
    //查找图书面板组件
    public JButton findBookByIsbn = new JButton("按书号搜索");
    public JButton findBookByAuthor= new JButton("按作者搜索");
    public JButton findBookByPublisher = new JButton("按出版社搜索");
    public JButton findBookByName = new JButton("按书名搜索");
    public JButton findBookByKind = new JButton("按类别搜索");
    public JTextField searchBook = new JTextField();    //输入搜索框
    public BookJTable bookListTable = new BookJTable(0,0);
    public JFrame errFrame = new JFrame("Err!");
    public JLabel errMsg = new JLabel();

    public static FindBookFrame getInstance(){
        synchronized (FindBookFrame.class) {
            if(instance == null) {
                instance = new FindBookFrame();
            }
            return instance;
        }
    }
    public void showFindBookField(){
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

        placeholderHandle.addingPlaceholder(searchBook,placeholderText);
        findBookBoxT.add(searchBook);
        findBookBoxT.setSize(100,30);

        findBookBox.add(Box.createVerticalStrut(40));
        findBookBox.add(findBookBoxT);
        findBookBox.add(Box.createVerticalStrut(20));
        findBookBox.add(findBookBoxB);

        Box bookListBox = Box.createHorizontalBox();
        bookListTable.setPreferredScrollableViewportSize(new Dimension(580,200));
//        bookListTable.
        JScrollPane scrollPane = new JScrollPane(bookListTable);
        bookListBox.add(scrollPane);
        JPanel container = new JPanel();
        container.add(findBookBox,BorderLayout.NORTH);
        container.add(bookListBox);
        findBookFrame.setContentPane(container);
        findBookFrame.setLocation(300,100);
        findBookFrame.setVisible(true);
    }
    public void findErrAlert(String err){
        errMsg.setText("错误：找不到" + err);
        errFrame.setLocation((int)(findBookFrame.getLocation().getX()+200),(int)(findBookFrame.getLocation().getY()+100));
        errFrame.setVisible(true);
    }
}
