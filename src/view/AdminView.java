package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by ghoskno on 3/25/17.
 */
public class AdminView {    //展示admin主面板
    AddPlaceHolder placeholderHandle = AddPlaceHolder.getInstance();

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

    public JPanel userPanel = new JPanel();
    public JTextField searchUserField = new JTextField();
    public JButton searchUserBtn = new JButton("搜索");

    public AdminView(){
        JFrame frame = new JFrame("Admin Panel");
        frame.setSize(1000,600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        BorderLayout layout = new BorderLayout();

        Container mainCon = frame.getContentPane();
        Box bodyBox = Box.createHorizontalBox();
        mainCon.add(bodyBox,BorderLayout.CENTER);

        mainCon.add(initLabelPanel(),BorderLayout.NORTH);
        bodyBox.add(initBookPanel());
        bodyBox.add(initUserPanel());
        bodyBox.add(initLogPanel());

        frame.setVisible(true);

    }
    public void showFindBookField(){
        findBookFrame.setSize(700,400);
        findBookFrame.setResizable(false);
        findBookFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        findBookFrame.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                findBookFrame.dispose();
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
        String placeholderText = "请输入书名/书号/作者/出版社/类别进行搜索";
        placeholderHandle.addingPlaceholder(searchBook,placeholderText);
        findBookBoxT.add(searchBook);
        findBookBoxT.setSize(100,30);

        findBookBox.add(Box.createVerticalStrut(40));
        findBookBox.add(findBookBoxT);
        findBookBox.add(Box.createVerticalStrut(20));
        findBookBox.add(findBookBoxB);

        JPanel container = new JPanel();
        container.add(findBookBox);
        findBookFrame.setContentPane(container);
        findBookFrame.setLocation(300,100);
        findBookFrame.setVisible(true);
    }
    public void showAddBookField(){
        //展示添加书本区域
    }
    private JPanel initLabelPanel(){
        JPanel panel = new JPanel();
        JLabel adminLabel = new JLabel("Admin");
        panel.add(adminLabel);
        JButton button = new JButton("Label");
        panel.add(button);
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

        containBox.add(Box.createVerticalStrut(50));
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

        UserBox.add(Box.createVerticalStrut(50));
        UserBox.add(stuUserNumBox);
        UserBox.add(Box.createVerticalStrut(20));
        UserBox.add(teacherUserNumBox);
        UserBox.add(Box.createVerticalStrut(20));
        UserBox.add(borrowUserNumBox);
        UserBox.add(Box.createVerticalStrut(20));
        UserBox.add(findUserBox);
//        UserBox.setMinimumSize(new Dimension(300,400));
        UserBox.setBackground(new Color(30,30,30));
        userPanel.add(UserBox);

//        System.out.print(UserBox.getComponents().length);
//        for (Component item:UserBox.getComponents()) {
//            System.out.print(item);
//        }

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
