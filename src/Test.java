/**
 * Created by lecoan on 17-3-1.
 */
import java.io.*;
import java.util.*;

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
class Book implements Serializable{
    private String name;
    private String boughttime;
    private String writername;
    private String publishername;
    private String introduction;
    private String kind;//
    private String isbn;
    //private List<BorrowMemory> borrowlist;//
    private int numbers;
    private int restnumber;

    //public void add
    public void Book() {}
    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setIsbn() {
        this.isbn = this.publishername + "-" + this.writername + "-" + this.name;
    }
    public void setWritername(String writername) {
        this.writername = writername;
    }
    public void setPublishername(String publishername) {
        this.publishername = publishername;
    }

    public String getWritername() {
        return writername;
    }

    public String getPublishername() {
        return publishername;
    }

    public void setRestnumber(int restnumber) {
        this.restnumber = restnumber;
    }

    public int getRestnumber() {
        return restnumber;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
    }

    public String getBoughttime() {
        return boughttime;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBoughttime(String boughttime) {
        this.boughttime = boughttime;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }
}
class BookOperate {
    private List<Book> booklist;
    private Map<String, List<Book>> writersbooklist;
    private Map<String, List<Book>> publishersbooklist;
    private Map<String, List<Book>> samenamebooklist;
    public BookOperate() {
        writersbooklist = new HashMap<>();
        publishersbooklist = new HashMap<>();
        samenamebooklist = new HashMap<>();
        booklist = new ArrayList<>();
    }
    public List<Book> getAllBook() {
        if (this.booklist.size() != 0) {
            return this.booklist;
        }
        else {
            try {
                booklist = new LinkedList<>();
                File file = new File("book.txt");
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                booklist = (List<Book>) ois.readObject();
                ois.close();
                fis.close();
                return this.booklist;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void saveAllBook() {
        if(this.booklist != null) {
            try{
                File file = new File("book.txt");
                if(!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(booklist);
                oos.flush();
                oos.close();
                fos.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("not initialization");
        }
    }

    public List<Book> getBookbyWriter(String writername) {
        if(writersbooklist.get(writername) != null) {
            return writersbooklist.get(writername);
        }
        else {
            List<Book> wsl = new ArrayList();
            for(Book temp : booklist) {
                if(temp.getWritername().equals(writername)) wsl.add(temp);
            }
            writersbooklist.put(writername, wsl);
            return writersbooklist.get(writername);
        }
    }
    public List<Book> getBookbyPublisher(String publishername) {
        if(publishersbooklist.get(publishername) != null) {
            return publishersbooklist.get(publishername);
        }
        else {
            List<Book> psn = new ArrayList();
            for(Book temp : booklist) {
                if(temp.getPublishername().equals(publishername)) psn.add(temp);
            }
            publishersbooklist.put(publishername, psn);
            return publishersbooklist.get(publishername);
        }
    }
    public Book getBookbyIsbn(String Isbn) {
        for(Book temp : booklist) {
            if(temp.getIsbn().equals(Isbn)) return temp;
        }
        Book notfound = new Book();
        return notfound;
    }
    public List<Book> getBookbyName(String bookname) {
        if(samenamebooklist.get(bookname) != null) {
            return samenamebooklist.get(bookname);
        }
        else {
            List<Book> bl = new ArrayList();
            for(Book temp : booklist) {
                if(temp.getName().equals(bookname)) bl.add(temp);
            }
            samenamebooklist.put(bookname, bl);
            return samenamebooklist.get(bookname);
        }
    }

    public void addBook(Book newbook) {
        if(booklist.size() == 0) {
            booklist.add(newbook);
        }
        else {
            for(Book temp : booklist) {
                if(temp.getIsbn().equals(newbook.getIsbn()) ) {
                    int totalnum = temp.getNumbers();
                    int restnum = temp.getRestnumber();
                    temp.setNumbers(totalnum + 1);
                    temp.setRestnumber(restnum + 1);
                    return;
                }
            }
            booklist.add(newbook);
        }
    }
    public void deleteBook(String isbn) {
        for(int i = 0; i < booklist.size(); ++i) {
            if(booklist.get(i).getIsbn().equals(isbn)){
                int totalnum = booklist.get(i).getNumbers();
                int restnum = booklist.get(i).getRestnumber();
                if(restnum == 0) System.out.println("book not return!");
                else {
                    booklist.get(i).setNumbers(totalnum - 1);
                    booklist.get(i).setRestnumber(restnum - 1);
                }
                if(totalnum - 1 == 0) booklist.remove(i);
            }
        }
    }
}

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
            System.out.println(test1.getKind());
    }
}
