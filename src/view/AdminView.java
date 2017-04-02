package view;

import bean.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by ghoskno on 3/25/17.
 */
public class AdminView {    //展示admin主面板
    AddPlaceHolder placeholderHandle = AddPlaceHolder.getInstance();
    public JFrame adminFrame = new JFrame("Admin Panel");

    public JButton addBookButton = new JButton("添加新书");
    public JButton findBookButton = new JButton("查找图书");

    public JFrame findBookFrame = new JFrame("查找图书");   //查找图书面板frame
    //查找图书面板组件
    public JButton findBookByIsbn = new JButton("按书号搜索");
    public JButton findBookByAuthor= new JButton("按作者搜索");
    public JButton findBookByPublisher = new JButton("按出版社搜索");
    public JButton findBookByName = new JButton("按书名搜索");
    public JButton findBookByKind = new JButton("按类别搜索");
    public JTextField searchBook = new JTextField();    //输入搜索框
    public BookJTable bookListTable = new BookJTable(0,0);

    public JFrame bookInfoFrame = new JFrame("图书信息");

    public JFrame addBookFrame = new JFrame("添加图书");
    public JButton submitAddBook = new JButton("添加");
    private JTextField bookNameInput = new JTextField(15);
    private JTextField bookPublisherInput = new JTextField(15);
    private JTextField bookAuthorInput = new JTextField(15);
    private JTextField bookNumInput = new JTextField(15);
    private JTextField bookKindInput= new JTextField(15);
    private JTextArea bookDesInput = new JTextArea(10,30);

    public JPanel userPanel = new JPanel();
    public JTextField searchUserField = new JTextField();
    public JButton searchUserBtn = new JButton("搜索");

    private JFrame errFrame = new JFrame("Err!");
    private JLabel errMsg = new JLabel();


    public AdminView(){
        adminFrame.setSize(600,400);
        adminFrame.setResizable(false);
        adminFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        BorderLayout layout = new BorderLayout();

        Container mainCon = adminFrame.getContentPane();
        Box bodyBox = Box.createHorizontalBox();
        mainCon.add(bodyBox,BorderLayout.CENTER);

        mainCon.add(initLabelPanel(),BorderLayout.NORTH);
        bodyBox.add(initBookPanel());
        bodyBox.add(initUserPanel());
        initErrAlert();
//        bodyBox.add(initLogPanel());
        adminFrame.setLocation(300,200);
        adminFrame.setVisible(true);

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
    public void showAddBookField(){
        //展示添加书本区域
        addBookFrame.setSize(500,450);
        addBookFrame.setResizable(false);
        addBookFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        JLabel bookNameLabel = new JLabel("书名");

        bookNameLabel.setBounds(100,20,60,20);
        bookNameInput.setBounds(200,20,140,20);
        placeholderHandle.addingPlaceholder(bookNameInput,"请输入书名");

        JLabel bookPublisherLabel = new JLabel("出版社名");

        bookPublisherLabel.setBounds(100,60,60,20);
        bookPublisherInput.setBounds(200,60,140,20);
        placeholderHandle.addingPlaceholder(bookPublisherInput,"请输入出版社名");

        JLabel bookAuthorLabel = new JLabel("作者");

        bookAuthorLabel.setBounds(100,100,60,20);
        bookAuthorInput.setBounds(200,100,140,20);
        placeholderHandle.addingPlaceholder(bookAuthorInput,"请输入作者");

        JLabel bookNumLabel = new JLabel("数量");
        bookNumLabel.setBounds(100,140,60,20);
        bookNumInput.setBounds(200,140,140,20);
        placeholderHandle.addingPlaceholder(bookNumInput,"请输入数量");

        JLabel bookKindLabel = new JLabel("种类");
        bookKindLabel.setBounds(100,180,60,20);
        bookKindInput.setBounds(200,180,140,20);
        placeholderHandle.addingPlaceholder(bookKindInput,"请输入种类");

        JLabel bookDesLabel = new JLabel("简介");
        bookDesLabel.setBounds(100,220,60,20);
        bookDesInput.setBounds(100,250,300,100);

        submitAddBook.setBounds(220,360,80,20);

        JPanel container = new JPanel();
        container.setSize(600,400);

        container.setLayout(null);
        container.add(bookNameInput);
        container.add(bookNameLabel);
        container.add(bookPublisherInput);
        container.add(bookPublisherLabel);
        container.add(bookNumInput);
        container.add(bookNumLabel);
        container.add(bookAuthorInput);
        container.add(bookAuthorLabel);
        container.add(bookKindLabel);
        container.add(bookKindInput);
        container.add(bookDesInput);
        container.add(bookDesLabel);
        container.add(submitAddBook);
        addBookFrame.setContentPane(container);
        addBookFrame.setLocation(300,100);
        addBookFrame.setVisible(true);

    }
    public void showBookInfoFrame(Book bookItem){
        bookInfoFrame.setSize(500,450);
        bookInfoFrame.setResizable(false);
        bookInfoFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        JLabel bookNameLabel = new JLabel("书名 ：  " + bookItem.getName());
        bookNameLabel.setBounds(100,20,300,20);

        JLabel bookPublisherLabel = new JLabel("出版社名 ：  " + bookItem.getPublishername());
        bookPublisherLabel.setBounds(100,60,300,20);


        JLabel bookAuthorLabel = new JLabel("作者 ：  " + bookItem.getWritername());
        bookAuthorLabel.setBounds(100,100,300,20);

        JLabel bookNumLabel = new JLabel("数量 ：  " + bookItem.getWritername());
        bookNumLabel.setBounds(100,140,300,20);


        JLabel bookKindLabel = new JLabel("种类 ：  " + bookItem.getKind());
        bookKindLabel.setBounds(100,180,300,20);;

        JLabel bookDesLabel = new JLabel("<html><body><p>" + "简介 ：  " + bookItem.getIntroduction()+ "</p></body></html>");
        System.out.print(bookItem.getIntroduction());
        bookDesLabel.setBounds(100,220,300,80);

        JPanel container = new JPanel();
        container.setSize(600,400);

        container.setLayout(null);
        container.add(bookNameLabel);
        container.add(bookPublisherLabel);
        container.add(bookNumLabel);
        container.add(bookAuthorLabel);
        container.add(bookKindLabel);
        container.add(bookDesLabel);
        bookInfoFrame.setContentPane(container);
        bookInfoFrame.setLocation(300,100);
        bookInfoFrame.setVisible(true);
    }
    private void initErrAlert(){
        errFrame.setSize(300,80);
        errFrame.setResizable(false);
        errFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        errMsg.setSize(200,80);
        //修改字体颜色
        Box errBox = Box.createHorizontalBox();
        errBox.add(Box.createHorizontalGlue());
        errBox.add(errMsg);
        errBox.add(Box.createHorizontalGlue());
        errFrame.getContentPane().add(errBox);
        errFrame.setVisible(false);

    }
    public void findErrAlert(String err){
        errMsg.setText("错误：找不到" + err);
        errFrame.setLocation((int)(findBookFrame.getLocation().getX()+200),(int)(findBookFrame.getLocation().getY()+100));
        errFrame.setVisible(true);
    }
    public String[] submitBook(){
        String[] bookInfo = {bookNameInput.getText(),bookPublisherInput.getText(),bookAuthorInput.getText(),bookKindInput.getText(),bookNumInput.getText(),bookDesInput.getText()};
        return bookInfo;
    }
    private JPanel initLabelPanel(){
        JPanel panel = new JPanel();
        Box labelBox = Box.createHorizontalBox();
        JLabel adminLabel = new JLabel("Admin");
        JButton signOutBtn = new JButton("退出");
        JLabel timeLabel = new JLabel("2017年4月1日");
        labelBox.add(adminLabel);
        labelBox.add(Box.createHorizontalStrut(300));
        labelBox.add(timeLabel);
        labelBox.add(Box.createHorizontalStrut(30));
        labelBox.add(signOutBtn);
        panel.add(labelBox);
        panel.setBackground(new Color(60,200,255));
        return panel;
    }
    private JPanel initBookPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(new Color(51,255,255));
        Box containBox = Box.createVerticalBox();   //主容器盒子

        Box bookNumBox =  Box.createHorizontalBox();//书本总数盒子
        JLabel bookNumLabel = new JLabel("当前书本总数：");
        JLabel bookNum = new JLabel("360020");
        bookNumBox.add(bookNumLabel);
        bookNumBox.add(Box.createGlue());
        bookNumBox.add(bookNum);

        Box borrowedBookNumBox =  Box.createHorizontalBox();//借出数量盒子
        JLabel borrowedBookNumLabel = new JLabel("未归还书本数：");
        JLabel borrowedBookNum = new JLabel("3200");
        borrowedBookNumBox.add(borrowedBookNumLabel);
        borrowedBookNumBox.add(Box.createGlue());
        borrowedBookNumBox.add(borrowedBookNum);

        Box borrowRateBox =  Box.createHorizontalBox();//借出率盒子
        JLabel borrowRateLabel = new JLabel("图书借出率：");
        JLabel borrowRate = new JLabel("30%");
        borrowRateBox.add(borrowRateLabel);
        borrowRateBox.add(Box.createGlue());
        borrowRateBox.add(borrowRate);

        String[] rankedList = {"lal","asdfasf"};
        JList borrowRateRank = new JList(rankedList);
        borrowRateRank.setBorder(BorderFactory.createTitledBorder("借阅次数最高的二十本书"));
        borrowRateRank.setVisibleRowCount(8);



        addBookButton.setSize(50,20);
        addBookButton.setSize(50,20);
        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(addBookButton);
        buttonBox.add(Box.createHorizontalStrut(40));
        buttonBox.add(findBookButton);

        containBox.add(Box.createVerticalStrut(10));
        containBox.add(bookNumBox);
        containBox.add(Box.createVerticalStrut(20));
        containBox.add(borrowRateBox);
        containBox.add(Box.createVerticalStrut(20));
        containBox.add(borrowedBookNumBox);
        containBox.add(Box.createVerticalStrut(20));
        containBox.add(new JScrollPane(borrowRateRank));
        containBox.add(Box.createVerticalStrut(20));
        containBox.add(buttonBox);
        panel.add(containBox);
        return panel;
    }
    private JPanel initUserPanel(){
        userPanel.setBackground(new Color(51,255,204));
        Box UserBox = Box.createVerticalBox();

        Box stuUserNumBox = Box.createHorizontalBox();
        JLabel stuUserNumLabel = new JLabel("当前学生用户总数：");
        JLabel stuUserNum = new JLabel("3500");
        stuUserNumBox.add(stuUserNumLabel);
        stuUserNumBox.add(Box.createGlue());
        stuUserNumBox.add(stuUserNum);

        Box teacherUserNumBox = Box.createHorizontalBox();
        JLabel teacherUserNumLabel = new JLabel("当前教师用户总数：");
        JLabel teacherUserNum = new JLabel("3500");
        teacherUserNumBox.add(teacherUserNumLabel);
        teacherUserNumBox.add(Box.createGlue());
        teacherUserNumBox.add(teacherUserNum);

        Box borrowUserNumBox = Box.createHorizontalBox();
        JLabel borrowUserNumLabel = new JLabel("已借书用户总数：");
        JLabel borrowUserNum = new JLabel("3500");
        borrowUserNumBox.add(borrowUserNumLabel);
        borrowUserNumBox.add(Box.createGlue());
        borrowUserNumBox.add(borrowUserNum);

        Box findUserBox = Box.createHorizontalBox();
        String placeholderText = "请输入用户名                        ";
        searchUserField.setText(placeholderText);
        placeholderHandle.addingPlaceholder(searchUserField,placeholderText);
        searchUserField.setSize(100,20);

        findUserBox.add(searchUserField);
        findUserBox.add(Box.createHorizontalStrut(30));
        findUserBox.add(searchUserBtn);

        UserBox.add(Box.createVerticalStrut(10));
        UserBox.add(stuUserNumBox);
        UserBox.add(Box.createVerticalStrut(20));
        UserBox.add(teacherUserNumBox);
        UserBox.add(Box.createVerticalStrut(20));
        UserBox.add(borrowUserNumBox);
        UserBox.add(Box.createVerticalStrut(20));
        UserBox.add(findUserBox);
        UserBox.setBackground(new Color(30,30,30));
        userPanel.add(UserBox);


        return userPanel;
    }
    private JPanel initLogPanel(){
        JPanel panel = new JPanel();
        JButton button = new JButton("Log");
        panel.add(button);
        panel.setBackground(new Color(255,255,255));
        return panel;
    }

}
