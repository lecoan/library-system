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
public class RegisterView extends JFrame {

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

    /**
     * 添加点击处理逻辑
     */
    private void addListener() {
        teacher.addActionListener(e -> {
            isTeacher = true;
            changeCompent();
            idBox.setVisible(true);
        });

        student.addActionListener(e -> {
            isTeacher = false;
            changeCompent();
            idBox.setVisible(true);
        });

        submit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!judge()) {
                    return;
                }
                Customer customer = null;
                if (isTeacher) {
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

                controller.handleRegister(customer, RegisterView.this);
            }
        });
    }

    /**
     * 根据用户的身份更新文字
     */
    private void changeCompent() {
        if(isTeacher){
            idLabel.setText("工号：");
            colleageBox.setVisible(false);
        } else {
            idLabel.setText("学号：");
            colleageBox.setVisible(true);
        }
    }

    /**
     * 判断输入是否合法
     *
     * @return 输入是否合法
     */
    private boolean judge() {
        boolean isOk = true;
        if (username.getText().isEmpty() || !username.getText().matches("^[A-Za-z0-9]{4,40}$")) {
            isOk = false;
        }
        String pw = new String(password.getPassword());
        if (pw.isEmpty() || !pw.matches("^[A-Za-z0-9]{4,40}$")) {
            isOk = false;
        }
        if (!userID.getText().matches("^\\d{10}$")) {
            isOk = false;
        }
        return isOk;
    }

    private void initView() {

        setTitle("注册界面");
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
        nameBox.add(new JLabel("用户名："));
        nameBox.add(Box.createHorizontalStrut(20));
        nameBox.add(username);
        password = new JPasswordField(30);
        passwordBox.add(new JLabel("密码："));
        nameBox.add(Box.createHorizontalStrut(20));
        passwordBox.add(password);
        userID = new JTextField(30);
        idBox.add(idLabel);
        idBox.add(userID);
        teacher = new JRadioButton("教师");
        student = new JRadioButton("学生");
        ButtonGroup group = new ButtonGroup();
        group.add(teacher);
        group.add(student);
        typeBox.add(new JLabel("用户类型"));
        typeBox.add(teacher);
        typeBox.add(student);
        clleages = new JComboBox<>();
        clleages.addItem("计算机学院");
        clleages.addItem("信息与通讯学院");
        clleages.addItem("电子学院");
        clleages.addItem("经济管理学院");
        clleages.addItem("艺术与传媒学院");
        colleageBox.add(new JLabel("学院"));
        colleageBox.add(clleages);

        submit = new JButton("注册");
        mainBox.add(submit);
        idBox.setVisible(false);
        colleageBox.setVisible(false);
        panel.add(mainBox);
        setContentPane(panel);
        setVisible(true);
    }
}
