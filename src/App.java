import bean.Book;
import bean.Student;
import bean.Teacher;
import listener.GlobalActionDetector;
import service.BookOperate;
import service.CustomerService;
import view.StartUpView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by lecoan on 17-3-1.
 */

public class App {
    public static void main(String args[]){
        GlobalActionDetector.getInstance().startGlobalActionDetector();
        new StartUpView();
    }
}
