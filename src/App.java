import bean.Book;
import bean.Customer;
import bean.Student;
import controler.UserControler;
import service.BookOperate;
import service.CustomerService;
import view.RegisterView;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by lecoan on 17-3-1.
 */

public class App {
    public static void main(String args[]) {

//        RegisterView test = new RegisterView();
        Student testSt = new Student();
        testSt.setColleage("asdfasdf");
        testSt.setId("2015211432");
        testSt.setMoney(23);
        testSt.setUsername("ZYX是SB");

        UserControler test = UserControler.getInstance();
        test.initUserView(testSt);
    }
}
