package controler;

import bean.Customer;
import service.CustomerService;
import view.AdminView;
import view.ErrAlert;
import view.UserView;

import javax.swing.*;

/**
 * Created by lecoan on 17-4-6.
 */
public class SignInAndUpController {

    private CustomerService service;
    private static SignInAndUpController instance;

    private SignInAndUpController(){
        service = CustomerService.getInstance();
    }

    public static SignInAndUpController getInstance(){
        if(instance == null) {
            instance = new SignInAndUpController();
        }
        return instance;
    }

    public void handleLogin(String id, String password, JFrame frame){
        if(id.equals("admin")){
            if(password.equals("123456")){
                frame.dispose();
                return;
            }
            ErrAlert.getInstance().findErrAlert(50,50,"用户名或密码错误");
            return;
        }

        Customer customer = service.getCustomerById(id);
        if(customer == null && customer.getPassword().equals(password)) {
            frame.dispose();
            //TODO
            return;
        }
        ErrAlert.getInstance().findErrAlert(50,50,"用户名或密码错误");

    }

    /**
     * 处理注册逻辑
     * @param customer 参数为注册用户对象，根据getType()判断是teacher还是student以便于向下强转
     */
    public void handleRegister(Customer customer, JFrame frame) {
        if(service.getCustomerById(customer.getId())!=null){
            ErrAlert.getInstance().findErrAlert(50,50,"用户已存在");
            return;
        }
        service.saveCustomer(customer);
        frame.dispose();
        //TODO
        //UserView view = new UserView();
    }
}
