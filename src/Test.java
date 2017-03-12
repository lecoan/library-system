/**
 * Created by lecoan on 17-3-1.
 */
import java.io.*;
import java.util.*;
public class Test {
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
            List<Book> nn = bo.getBookbyName("hhh");
            System.out.println(test1.getKind());
            if(nn.size() != 0) System.out.println(nn.get(0).getPublishername());
    }
}
