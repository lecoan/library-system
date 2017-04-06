import bean.Book;
import bean.BookPathTable;
import bean.Customer;
import bean.Student;
import listener.GlobalActionDetector;
import service.BookOperate;
import service.CustomerService;
import service.CustomerServiceImpl;
import view.GetDate;
import view.LoginView;
import view.StartUpView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by lecoan on 17-3-1.
 */

public class App {
    public static void main(String args[]) {
        CustomerService customerService = CustomerServiceImpl.getInstance();
        Customer customer = new Student();
        customerService.saveCustomer(customer);
        customerService.saveAllCustomers();
    }
}
