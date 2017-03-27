package view;

import bean.Customer;
import bean.Student;
import bean.Teacher;

import javax.swing.*;
import java.awt.*;
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
public class RegisterView {

    private JButton submit;
    private JRadioButton teacher;
    private JRadioButton student;
    private JTextField username;
    private JPasswordField password;

    private JTextField userID;
    private JComboBox<String> clleages;
    private RegisterInfo info;

    private boolean isTeacher;
    private boolean isOK;

    public RegisterView() {

        initView();

        addListener();

    }

    private void addListener() {
        teacher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideCompent();
                isTeacher = true;
            }
        });

        teacher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCompent();
                isTeacher = false;
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

                info.handleRegisterInfo(customer);
            }
        });
    }

    private boolean judge() {
        return false;
    }

    private void initView() {

        JFrame frame = new JFrame("register");
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        JPanel panel = new JPanel(new FlowLayout());
        username = new JTextField(40);
        password = new JPasswordField(40);
        userID = new JTextField(40);

        teacher = new JRadioButton("teacher");
        student = new JRadioButton("student");

        clleages = new JComboBox<>();
        clleages.addItem("计算机学院");
        clleages.addItem("信息与通讯学院");
        clleages.addItem("电子学院");
        clleages.addItem("经济管理学院");
        clleages.addItem("艺术与传媒学院");

        ButtonGroup group = new ButtonGroup();
        group.add(teacher);
        group.add(student);
        submit = new JButton("register");

        panel.add(username);
        panel.add(password);
        panel.add(userID);
        panel.add(teacher);
        panel.add(student);
        panel.add(clleages);
        panel.add(submit);
        frame.setVisible(true);
    }

    public void setRegisterInfoHandler(RegisterInfo infoHandler) {

        this.info = infoHandler;
    }

    public interface RegisterInfo {

        /**
         * 处理注册逻辑
         * @param customer 参数为注册用户对象，根据getType()判断是teacher还是student以便于向下强转
         */
        public void handleRegisterInfo(Customer customer);

    }

    private void showCompent() {
        clleages.setVisible(true);
    }

    private void hideCompent() {
        clleages.setVisible(false);
    }
}
