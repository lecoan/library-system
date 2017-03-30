import bean.Book;
import service.BookOperate;
import view.LoginView;

/**
 * Created by lecoan on 17-3-1.
 */

public class App {
    public static void main(String args[]) {
        BookOperate oo = new BookOperate();
        Book a = new Book();
        /*a.setWritername("11");
        a.setNumbers(1);
        a.setRestnumber(1);
        a.setPublishername("22");
        a.setKind("+");
        a.setName("33");
        a.setIsbn();
        oo.addBook(a);*/
        a = oo.getBookbyIsbn("22-11-33");
        System.out.println(oo.GetTotalBooknum());
        System.out.println(a.getName());
        oo.SaveData();
    }
}
