package view;

import bean.Customer;
import bean.Student;
import bean.Teacher;
import controler.SignInAndUpController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/******************************************************************
 创建人: 杨翔
 日　期: 2017/3/25
 修改人:
 日　期:
 描　述: 注册界面
 版　本: v1.00 Copyright(c).
 ******************************************************************/
public class RegisterView extends JFrame{

    private SignInAndUpController controller;

    private JButton submit;
    private JRadioButton teacher;
    private JRadioButton student;
    private JTextField username;
    private JPasswordField password;

    private JTextField userID;
    private JComboBox<String> clleages;

    private boolean isTeacher;
    private JLabel idLabel;
    private Box idBox;
    private Box colleageBox;

    public RegisterView() {
        this.controller = SignInAndUpController.getInstance();
        initView();
        addListener();
    }

    private void addListener() {
        teacher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isTeacher = true;
                changeCompent();
                idBox.setVisible(true);
            }
        });

        student.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isTeacher = false;
                changeCompent();
                idBox.setVisible(true);
            }
        });

        submit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!judge()) {
                    return;
                }
                Customer customer = null;
                if(isTeacher) {
                    Teacher teacher = new Teacher();
                    customer = teacher;
                } else {
                    Student student = new Student();
                    student.setColleage(clleages.getItemAt(clleages.getSelectedIndex()));
                    customer = student;
                }
                customer.setUsername(username.getText());
                customer.setPassword(new String(password.getPassword()));
                customer.setId(userID.getText());

                controller.handleRegister(customer,RegisterView.this);
            }
        });
    }

    private void changeCompent() {
        if(isTeacher){
            idLabel.setText("gong hao");
            colleageBox.setVisible(false);
        } else {
            idLabel.setText("xue hao");
            colleageBox.setVisible(true);
        }
    }

    private boolean judge() {
        boolean isOk = true;
        if(username.getText().isEmpty() || !username.getText().matches("^[A-Za-z0-9]{4,40}$")) {
            isOk = false;
        }
        String pw = new String(password.getPassword());
        if(pw.isEmpty() || !pw.matches("^[A-Za-z0-9]{4,40}$")) {
            isOk = false;
        }
        if(!userID.getText().matches("^\\d{10}$")){
            isOk = false;
        }
        return isOk;
    }

    private void initView() {

        setTitle("register");
        setSize(500,500);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();

        Box mainBox = Box.createVerticalBox();
        Box nameBox = Box.createHorizontalBox();
        Box passwordBox = Box.createHorizontalBox();
        Box typeBox = Box.createHorizontalBox();
        idBox = Box.createHorizontalBox();
        colleageBox = Box.createHorizontalBox();
        mainBox.add(nameBox);
        mainBox.add(passwordBox);
        mainBox.add(typeBox);
        mainBox.add(idBox);
        mainBox.add(colleageBox);

        idLabel = new JLabel();

        username = new JTextField(30);
        nameBox.add(new JLabel("username:"));
        nameBox.add(Box.createHorizontalStrut(20));
        nameBox.add(username);
        password = new JPasswordField(30);
        passwordBox.add(new JLabel("password:"));
        nameBox.add(Box.createHorizontalStrut(20));
        passwordBox.add(password);
        userID = new JTextField(30);
        idBox.add(idLabel);
        idBox.add(userID);
        teacher = new JRadioButton("teacher");
        student = new JRadioButton("student");
        ButtonGroup group = new ButtonGroup();
        group.add(teacher);
        group.add(student);
        typeBox.add(new JLabel("type"));
        typeBox.add(teacher);
        typeBox.add(student);
        clleages = new JComboBox<>();
        clleages.addItem("计算机学院");
        clleages.addItem("信息与通讯学院");
        clleages.addItem("电子学院");
        clleages.addItem("经济管理学院");
        clleages.addItem("艺术与传媒学院");
        colleageBox.add(new JLabel("college"));
        colleageBox.add(clleages);

        submit = new JButton("register");
        mainBox.add(submit);
        idBox.setVisible(false);
        colleageBox.setVisible(false);
        panel.add(mainBox);
        setContentPane(panel);
        setVisible(true);
    }
}
