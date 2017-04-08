import bean.BookPathTable;
import bean.Student;
import bean.Teacher;
import listener.GlobalActionDetector;
import service.BookOperate;
import service.CustomerService;
import view.StartUpView;

import java.util.List;

/**
 * Created by lecoan on 17-3-1.
 */

public class App {
    public static void main(String args[]) {
        GlobalActionDetector.getInstance().startGlobalActionDetector();
//        for(int i = 100;i<1000;++i){
//            Student student = new Student();
//            student.setUsername("student"+i);
//            student.setPassword("123456");
//            student.setColleage("计算机学院");
//            student.setId("2015211"+i);
//            CustomerService.getInstance().saveCustomer(student);
//        }
//        for(int i = 100;i<500;++i){
//            Teacher teacher = new Teacher();
//            teacher.setUsername("teacher"+i);
//            teacher.setPassword("123456");
//            teacher.setId("2014211"+i);
//            CustomerService.getInstance().saveCustomer(teacher);
//        }
        new StartUpView();

    }
}
