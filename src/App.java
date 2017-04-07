import bean.Book;
import bean.BookPathTable;
import listener.GlobalActionDetector;
import service.BookOperate;
import view.GetDate;
import view.LoginView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by lecoan on 17-3-1.
 */

public class App {
    public static void main(String args[]) {
        //GlobalActionDetector.getInstance().startGlobalActionDetector();
        BookOperate oo = BookOperate.getInstance();
        
        try {
            FileReader fr = new FileReader("popular.txt");
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
//        Book a = new Book();
//        a = oo.getBookbyIsbn("百花洲文艺出版社-笛子Ocarina-今天也想表白你：小绿和小蓝-");
//        System.out.println(a.getIntroduction());
//        System.out.println(a.getKind());
//        System.out.println(oo.GetTotalBooknum());
//        System.out.println(oo.GetTotalRestbooknum());
//        oo.UpdateBookrank(a.getIsbn());
//        //oo.addBorrowMemory("11","百花洲文艺出版社-笛子Ocarina-今天也想表白你：小绿和小蓝",
//        // "1112165");
//        oo.SaveData();
    }
}
