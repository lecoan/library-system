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

    public static void main(String args[]) {

//
////        RegisterView test = new RegisterView();
//        Student testSt = new Student();
//        testSt.setColleage("asdfasdf");
//        testSt.setId("2015211432");
//        testSt.setMoney(23);
//        testSt.setUsername("ZYX是SB");
//
//        UserControler test = UserControler.getInstance();
//        test.initUserView(testSt);
        GlobalActionDetector.getInstance().startGlobalActionDetector();
        BookOperate oo = BookOperate.getInstance();
        try {
            FileReader fr = new FileReader("bookSpider/popular.txt");
            //可以换成工程目录下的其他文本文件
            BufferedReader br = new BufferedReader(fr);
            String ans = new String();
            String s = new String();
            s = br.readLine();
            while(s != null ){
                ans = ans + s;
                s = br.readLine();
            }//首先用“},{”将每一本书分开，然后得到每一本书的string，再用"##"将书的属性按照
            //书名，作者，出版社，简介，种类分开，然后为每一本书设置这几种属性以及setisbn,
            String[] book = ans.split("\\}\\,\\{");
            boolean flag = true;
            for(String ss:book) {//跳过第一本书，然后
                if(flag) flag = false;
                else{
                    String[] property = ss.split("##");
                    Book a = new Book();
                    a.setWritername(property[1]);
                    a.setPublishername(property[2]);
                    a.setKind(property[4]);
                    a.setName(property[0]);
                    a.setIntroduction(property[3]);
                    a.setIsbn();
                    oo.addBook(a,(int)(Math.random()*100 + 1));
                    System.out.println("add book success!!");
                }
            }
            oo.SaveData();
            System.out.println("ok!!!");
        }
        catch(IOException e) {
        }
//
//        GlobalActionDetector.getInstance().startGlobalActionDetector();
        for(int i = 100;i<1000;++i){
            Student student = new Student();
            student.setUsername("student"+i);
            student.setPassword("123456");
            student.setColleage("计算机学院");
            student.setId("2015211"+i);
            CustomerService.getInstance().saveCustomer(student);
        }
        for(int i = 100;i<500;++i){
            Teacher teacher = new Teacher();
            teacher.setUsername("teacher"+i);
            teacher.setPassword("123456");
            teacher.setId("2014211"+i);
            CustomerService.getInstance().saveCustomer(teacher);
        }
        new StartUpView();
    }
}
