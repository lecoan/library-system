

/**
 * Created by lecoan on 17-3-1.
 */
import bean.Book;
import service.BookOperate;

import java.io.*;

public class App {
    public static void main(String args[]) {
        Book test = new Book();
        Book test1 = new Book();
        test.setRestnumber(1);
        test.setNumbers(1);
        test.setBoughttime("11 11");
        test.setIntroduction("try");
        test.setName("hhhh");
        test.setKind("lishi");
        test.setPublishername("bupt");
        test.setWritername("yy");
        test.setIsbn();
        BookOperate bo = new BookOperate();
        bo.addBook(test);
        bo.saveAllBook();
        bo.getAllBook();
        test1 = bo.getBookbyIsbn("bupt-yy-hhhh");
        System.out.println(test1.getKind());
    }
}
