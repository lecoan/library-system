

/**
 * Created by lecoan on 17-3-1.
 */
import bean.Book;
import service.BookOperate;

import java.io.*;

class BorrowMemory implements Serializable{
    private String borrowtime;
    private String borrowman;
    private String returntime;

    public void setBorrowtime(String borrowtime) {
        this.borrowtime = borrowtime;
    }

    public void setBorrowman(String borrowman) {
        this.borrowman = borrowman;
    }

    public void setReturntime(String returntime) {
        this.returntime = returntime;
    }

    public String getBorrowtime() {
        return borrowtime;
    }

    public String getBorrowman() {
        return borrowman;
    }

    public String getReturntime() {
        return returntime;
    }
}

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
