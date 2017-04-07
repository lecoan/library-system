package view;

import bean.Book;
import bean.BookPathTable;
import bean.BorrowMemory;
import service.BookOperate;
import service.CustomerService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by ghoskno on 3/25/17.
 */
public class AdminView {    //展示admin主面板
    AddPlaceHolder placeholderHandle = AddPlaceHolder.getInstance();

    public JFrame adminFrame = new JFrame("Admin Panel");

    public JButton addBookButton = new JButton("添加新书");
    public JButton findBookButton = new JButton("查找图书");

    public JFrame bookInfoFrame = new JFrame("图书信息");
    public JButton bookUpdateBtn = new JButton("更新图书");
        public JButton lookBorrowHistory = new JButton("借阅历史");
    public JButton unfreezeBtn = new JButton("解冻");

    public JButton bookDeleBtn = new JButton("删除图书");
    public JFrame modifyBookFrame = new JFrame("添加图书");

    public JButton modifyBookBtn = new JButton();   //添加/更新按钮
    //添加图书面板中输入框
    private JTextField bookNameInput = new JTextField(15);
    private JTextField bookPublisherInput = new JTextField(15);
    private JTextField bookAuthorInput = new JTextField(15);
    private JTextField bookNumInput = new JTextField(15);
    private JTextField bookKindInput= new JTextField(15);

    private JTextArea bookDesInput = new JTextArea(10,30);
    //查找用户界面
    public JPanel userPanel = new JPanel();
    public JTextField searchUserField = new JTextField();
    public JButton searchUserBtn = new JButton("搜索");
    public JLabel userName = new JLabel("");
    public JLabel userStuNum = new JLabel("");
    public JLabel userCollege = new JLabel("");
    public JLabel userStatus = new JLabel("");
    public JTextField userLimit= new JTextField(1);
    public JButton changeLimitBtn = new JButton("修改权限");
    public JButton lookBookListBtn= new JButton("查看借书情况");

    public FindBookFrame findBookFrame = new FindBookFrame();

    private JFrame bookBorrowFrame = new JFrame("借阅历史");
    JTable borrowTable= new JTable(0,0);

    public JLabel timeLabel = new JLabel();

    public AdminView(){
        //初始化界面
        adminFrame.setSize(400,600);
        adminFrame.setResizable(false);
        adminFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container mainCon = adminFrame.getContentPane();
        Box bodyBox = Box.createVerticalBox();
        mainCon.add(bodyBox,BorderLayout.CENTER);

        mainCon.add(initLabelPanel(),BorderLayout.NORTH);
        bodyBox.add(initBookPanel());
        bodyBox.add(initUserPanel());
//        Frame.initErrAlert();
        adminFrame.setLocation(300,200);
        adminFrame.setVisible(true);
        borrowTable.setEnabled(false);
        borrowTable.setPreferredScrollableViewportSize(new Dimension(580,200));
        bookBorrowFrame.getContentPane().add(new JScrollPane(borrowTable));
    }

    public void showModifyBookField(Book bookItem, BookPathTable bookPath){
        //展示添加//修改书本区域
        modifyBookFrame.setSize(500,450);
        modifyBookFrame.setResizable(false);
        modifyBookFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        JLabel bookNameLabel = new JLabel("书名");

        bookNameLabel.setBounds(100,20,60,20);
        bookNameInput.setBounds(200,20,140,20);

        JLabel bookPublisherLabel = new JLabel("出版社名");

        bookPublisherLabel.setBounds(100,60,60,20);
        bookPublisherInput.setBounds(200,60,140,20);

        JLabel bookAuthorLabel = new JLabel("作者");

        bookAuthorLabel.setBounds(100,100,60,20);
        bookAuthorInput.setBounds(200,100,140,20);

        JLabel bookNumLabel = new JLabel("数量");
        bookNumLabel.setBounds(100,140,60,20);
        bookNumInput.setBounds(200,140,140,20);

        JLabel bookKindLabel = new JLabel("种类");
        bookKindLabel.setBounds(100,180,60,20);
        bookKindInput.setBounds(200,180,140,20);

        JLabel bookDesLabel = new JLabel("简介");
        bookDesLabel.setBounds(100,220,60,20);
        bookDesInput.setBounds(100,250,300,100);
        bookDesInput.setLineWrap(true);

        modifyBookBtn.setBounds(220,360,80,20);

        JPanel container = new JPanel();
        container.setSize(600,400);

        if(bookItem != null){   //当前查看图书不为空，则为修改图书
            bookNameInput.setText(bookItem.getName());
            bookKindInput.setText(bookItem.getKind());
            bookAuthorInput.setText(bookItem.getWritername());
            bookDesInput.setText(bookItem.getIntroduction());
            bookNumInput.setText("" + bookPath.getTotalnum());
            bookPublisherInput.setText(bookItem.getPublishername());
            modifyBookBtn.setText("更新");
        }
        else{   //当前查看图书为空，则为添加图书
            placeholderHandle.addingPlaceholder(bookNameInput,"请输入书名");
            placeholderHandle.addingPlaceholder(bookPublisherInput,"请输入出版社名");
            placeholderHandle.addingPlaceholder(bookAuthorInput,"请输入作者");
            placeholderHandle.addingPlaceholder(bookNumInput,"请输入数量");
            placeholderHandle.addingPlaceholder(bookKindInput,"请输入种类");
            bookDesInput.setText("");
            modifyBookBtn.setText("添加");
        }

        if(bookPath.getRestnum()!=bookPath.getTotalnum()){
            bookNameInput.setEnabled(false);
            bookDesInput.setEnabled(false);
            bookPublisherInput.setEnabled(false);
            bookAuthorInput.setEnabled(false);
            bookKindInput.setEnabled(false);
        }

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
        container.add(modifyBookBtn);
        modifyBookFrame.setContentPane(container);
        modifyBookFrame.setLocation(300,100);
        modifyBookFrame.setVisible(true);

    }
    public void showBookInfoFrame(Book bookItem, BookPathTable bookItemPath){
        //显示图书详细信息界面
        bookInfoFrame.setSize(500,450);
        bookInfoFrame.setResizable(false);
        bookInfoFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        JLabel bookNameLabel = new JLabel("书名 ：  " + bookItem.getName());
        bookNameLabel.setBounds(100,20,300,20);

        JLabel bookPublisherLabel = new JLabel("出版社名 ：  " + bookItem.getPublishername());
        bookPublisherLabel.setBounds(100,50,300,20);


        JLabel bookAuthorLabel = new JLabel("作者 ：  " + bookItem.getWritername());
        bookAuthorLabel.setBounds(100,80,300,20);

        JLabel bookNumLabel = new JLabel("总数量 ：  " + bookItemPath.getTotalnum());
        bookNumLabel.setBounds(100,110,300,20);

        JLabel bookRestNumLabel = new JLabel("剩余数量 ：  " + bookItemPath.getRestnum());
        bookRestNumLabel.setBounds(100,140,300,20);


        JLabel bookKindLabel = new JLabel("种类 ：  " + bookItem.getKind());
        bookKindLabel.setBounds(100,180,300,20);;

        JLabel bookDesLabel = new JLabel("<html><body><p>" + "简介 ：  " + bookItem.getIntroduction()+ "</p></body></html>");
        System.out.print(bookItem.getIntroduction());
        bookDesLabel.setBounds(100,220,300,80);

        bookUpdateBtn.setBounds(50,320,100,30);
        lookBorrowHistory.setBounds(200,320,100,30);
        bookDeleBtn.setBounds(350,320,100,30);

        JPanel container = new JPanel();
        container.setSize(600,400);

        container.setLayout(null);
        container.add(bookNameLabel);
        container.add(bookPublisherLabel);
        container.add(bookNumLabel);
        container.add(bookRestNumLabel);
        container.add(bookAuthorLabel);
        container.add(bookKindLabel);
        container.add(bookDesLabel);
        container.add(bookUpdateBtn);
        container.add(lookBorrowHistory);
        container.add(bookDeleBtn);
        bookInfoFrame.setContentPane(container);
        bookInfoFrame.setLocation(300,100);
        bookInfoFrame.setVisible(true);

        bookItemPath.setRestnum(bookItemPath.getRestnum()-5);
        if(bookItemPath.getRestnum() != bookItemPath.getTotalnum()){
            bookDeleBtn.setEnabled(false);
        }
        else{
            bookDeleBtn.setEnabled(true);
        }
    }
    public void showBookBorrowFram(Book bookItem){
        bookBorrowFrame.setSize(500,450);
        bookBorrowFrame.setResizable(false);
        bookBorrowFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        String[] tableHeader = {"借出时间","归还时间","借阅人"};
        List<BorrowMemory> borrowList = bookItem.getBorrowmemory();
        Object[][] tableBody = new Object[5][3];
//        Object[][] tableBody = new Object[borrowList.size()][3];
//        for(int i=0;i<borrowList.size();i++){
        for(int i=0;i<5;i++){
//            Object[] rowData = {borrowList.get(i).getBorrowtime(),borrowList.get(i).getReturntime(),borrowList.get(i).getBorrowman()};
            Object[] rowData = {"" + bookItem.getName(),"borrowList.get(i).getReturntime()","borrowList.get(i).getBorrowman()"};
            tableBody[i] = rowData;
        }
        DefaultTableModel tableModel =  (DefaultTableModel)borrowTable.getModel();
        tableModel.setDataVector(tableBody,tableHeader);
        borrowTable.setVisible(true);
        bookBorrowFrame.setLocation((int)bookInfoFrame.getLocation().getX()+500,(int)bookInfoFrame.getLocation().getY() - 10);
        bookBorrowFrame.setVisible(true);
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

        labelBox.add(adminLabel);
        labelBox.add(Box.createHorizontalStrut(200));
        labelBox.add(timeLabel);
        labelBox.add(Box.createHorizontalStrut(30));
        labelBox.add(signOutBtn);
        panel.add(labelBox);
        panel.setBackground(new Color(60,200,255));
        return panel;
    }
    private JPanel initBookPanel(){
        int bookTotalNum = BookOperate.getInstance().GetTotalBooknum();
        int bookRestNum = BookOperate.getInstance().GetTotalRestbooknum();
        JPanel panel = new JPanel();
        Box containBox = Box.createVerticalBox();   //主容器盒子

        Box bookNumBox =  Box.createHorizontalBox();//书本总数盒子
        JLabel bookNumLabel = new JLabel("当前书本总数：");
        JLabel bookNum = new JLabel("" + bookTotalNum);
        bookNumBox.add(bookNumLabel);
        bookNumBox.add(Box.createGlue());
        bookNumBox.add(bookNum);

        Box borrowedBookNumBox =  Box.createHorizontalBox();//借出数量盒子
        JLabel borrowedBookNumLabel = new JLabel("未归还书本数：");
        JLabel borrowedBookNum = new JLabel("" + (bookTotalNum - bookRestNum));
        borrowedBookNumBox.add(borrowedBookNumLabel);
        borrowedBookNumBox.add(Box.createGlue());
        borrowedBookNumBox.add(borrowedBookNum);

        Box borrowRateBox =  Box.createHorizontalBox();//借出率盒子
        JLabel borrowRateLabel = new JLabel("图书借出率：");
        JLabel borrowRate = new JLabel(((bookTotalNum - bookRestNum)/bookTotalNum * 100) + "%");
        borrowRateBox.add(borrowRateLabel);
        borrowRateBox.add(Box.createGlue());
        borrowRateBox.add(borrowRate);

        addBookButton.setSize(50,20);
        addBookButton.setSize(50,20);
        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(addBookButton);
        buttonBox.add(Box.createHorizontalStrut(100));
        buttonBox.add(findBookButton);

        containBox.add(Box.createVerticalStrut(10));
        containBox.add(bookNumBox);
        containBox.add(Box.createVerticalStrut(20));
        containBox.add(borrowRateBox);
        containBox.add(Box.createVerticalStrut(20));
        containBox.add(borrowedBookNumBox);
        containBox.add(Box.createVerticalStrut(20));
        containBox.add(buttonBox);
        panel.add(containBox);
        return panel;
    }
    private JPanel initUserPanel(){
//        userPanel.setBackground(new Color(51,255,204));
        Box UserBox = Box.createVerticalBox();

        Box stuUserNumBox = Box.createHorizontalBox();
        JLabel stuUserNumLabel = new JLabel("当前学生用户总数：");
        JLabel stuUserNum = new JLabel("" + CustomerService.getInstance().getStudentNum());
        stuUserNumBox.add(stuUserNumLabel);
        stuUserNumBox.add(Box.createGlue());
        stuUserNumBox.add(stuUserNum);

        Box teacherUserNumBox = Box.createHorizontalBox();
        JLabel teacherUserNumLabel = new JLabel("当前教师用户总数：");
        JLabel teacherUserNum = new JLabel("" + CustomerService.getInstance().getTeacherNum());
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

        Box UserInfoBox = Box.createVerticalBox();
        Box userNameBox = Box.createHorizontalBox();
        Box userStuNumBox = Box.createHorizontalBox();
        Box userCollegeBox = Box.createHorizontalBox();
        Box userStatusBox = Box.createHorizontalBox();
        Box userLimitBox = Box.createHorizontalBox();

        JLabel userNameLabel = new JLabel("姓名：");
        JLabel userStuNumLabel = new JLabel("学号：");
        JLabel userCollegeLabel = new JLabel("学院：");
        JLabel userStatusLabel = new JLabel("状态：");
        JLabel userLimitLabel = new JLabel("权限：");

        userNameBox.add(userNameLabel);
        userNameBox.add(Box.createGlue());
        userNameBox.add(userName);

        userStuNumBox.add(userStuNumLabel);
        userStuNumBox.add(Box.createGlue());
        userStuNumBox.add(userStuNum);

        userCollegeBox.add(userCollegeLabel);
        userCollegeBox.add(Box.createGlue());
        userCollegeBox.add(userCollege);

        userStatusBox.add(userStatusLabel);
        userStatusBox.add(Box.createGlue());
        userStatusBox.add(userStatus);

        userLimitBox.add(userLimitLabel);
        userLimitBox.add(Box.createHorizontalStrut(150));
        userLimitBox.add(userLimit);

        UserInfoBox.add(userNameBox);
        UserInfoBox.add(userStuNumBox);
        UserInfoBox.add(userCollegeBox);
        UserInfoBox.add(userStatusBox);
        UserInfoBox.add(userLimitBox);

        Box UserBtnBox = Box.createHorizontalBox();
        UserBtnBox.add(changeLimitBtn);
        UserBtnBox.add(Box.createGlue());
        UserBtnBox.add(lookBookListBtn);
        UserBtnBox.add(Box.createGlue());
        UserBtnBox.add(unfreezeBtn);

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
        UserBox.add(Box.createVerticalStrut(20));
        UserBox.add(UserInfoBox);
        UserBox.add(Box.createVerticalStrut(20));
        UserBox.add(UserBtnBox);
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
