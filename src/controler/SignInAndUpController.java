package controler;

import bean.Customer;
import listener.GlobalActionDetector;
import service.CustomerService;
import view.AdminView;
import view.ErrAlert;
import view.UserView;
import controler.UserControler;

import javax.swing.*;

/******************************************************************
 创建人: 杨翔
 日　期: 2017/3/10
 修改人:
 日　期:
 描　述: 控制用户的登陆和注册
 版　本: v1.00 Copyright(c).
 ******************************************************************/
public class SignInAndUpController {

    private CustomerService service;
    private static SignInAndUpController instance;

    private SignInAndUpController() {
        service = CustomerService.getInstance();
    }

    /**
     * @return singleton
     */
    public static SignInAndUpController getInstance() {
        if (instance == null) {
            instance = new SignInAndUpController();
        }
        return instance;
    }

    /**
     * @param id       用户的学号或者工号
     * @param password 密码
     * @param frame    view层窗口实例
     */
    public void handleLogin(String id, String password, JFrame frame) {
        if (id.equals("admin")) {
            if (password.equals("123456")) {
                AdminControler controler = AdminControler.getInstance();
                controler.initAdminView();
                frame.dispose();
                AdminControler.getInstance();
                return;
            }
            ErrAlert.getInstance().findErrAlert(50, 50, "用户名或密码错误");
            return;
        }

        Customer customer = service.getCustomerById(id);
        if (customer != null && customer.getPassword().equals(password)) {
            if (service.isLogin(customer)) {
                ErrAlert.getInstance().findErrAlert(50, 50, "请不要重复登陆");
            } else {
                service.updateMoney(customer);
                frame.dispose();
                UserControler userControler = UserControler.getInstance();
                userControler.initUserView(customer);
                service.login(customer);
            }

            return;
        }
        ErrAlert.getInstance().findErrAlert(50, 50, "用户名或密码错误");

    }

    /**
     * 处理注册逻辑
     *
     * @param customer 参数为注册用户对象，根据getType()判断是teacher还是student以便于向下强转
     */
    public void handleRegister(Customer customer, JFrame frame) {
        if (service.getCustomerById(customer.getId()) != null) {
            ErrAlert.getInstance().findErrAlert(50, 50, "用户已存在");
            return;
        }
        service.saveCustomer(customer);
        frame.dispose();

    }
}
