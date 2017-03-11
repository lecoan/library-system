/**
 * Created by lecoan on 17-3-1.
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

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
    private List<BorrowMemory> borrowlist;//
    private int numbers;
    private int restnumber;

    public void add
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
    public List<Book> getAllBook() {
        if(this.booklist != null) {
            return this.booklist;
        }
        else {
            try {
                File file = new File("data/book.txt");
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                booklist = (List<Book>)ois.readObject();
                ois.close();
                fis.close();
            }
            catch(ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }




    public void saveAllBook() {
        if(this.booklist != null) {
            try{
                File file = new File("data/book.txt");
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(booklist);
                oos.flush();
                oos.close();
                fos.close();
            }
            catch(ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("not initialization");
        }
    }

    public List<Book> getBookbyWriter(String writername) {
        if(this.writersbooklist[writername] != null) {
            return this.writersbooklist[writername];
        }
        else {
            for(Book temp : booklist) {
                if(temp.getWritername() == writername) writersbooklist[writername].add(temp);
            }
            return writersbooklist[writername];
        }
    }
    public List<Book> getBookbyPublisher(String publishername) {
        if(this.publishersbooklist[publishername] != null) {
            return this.publishersbooklist[publishername];
        }
        else {
            for(Book temp : booklist) {
                if(temp.getPublishername() == publishername) publishersbooklist[publishername].add(temp);
            }
            return publishersbooklist[publishername];
        }
    }
    public Book getBookbyIsbn(String Isbn) {
        for(Book temp : booklist) {
            if(temp.getIsbn() == Isbn) return temp;
        }
        Book notfound;
        return notfound;
    }
    public List<Book> getBookbyName(String bookname) {
        if(this.samenamebooklist[bookname] != null) {
            return this.samenamebooklist[bookname];
        }
        else {
            for(Book temp : booklist) {
                if(temp.getName() == bookname) samenamebooklist[bookname].add(temp);
            }
            return samenamebooklist[bookname];
        }
    }

    public void addBook(Book newbook) {
        for(Book temp : booklist) {
            if(temp.getIsbn() == newbook.getIsbn()) {
                int totalnum = temp.getNumbers();
                int restnum = temp.getRestnumber();
                temp.setNumbers(totalnum + 1);
                temp.setRestnumber(restnum + 1);
                return;
            }
        }
        booklist.add(newbook);
    }
    public void deleteBook(String isbn) {
        for(i = 0; i < booklist.size(); ++i) {
            if(booklist.get(i).getIsbn() == isbn){
                int totalnum = temp.getNumbers();
                int restnum = temp.getRestnumber();
                if(restnum == 0) System.out.println("book not return!");
                else {
                    temp.setNumbers(totalnum - 1);
                    temp.setRestnumber(restnum - 1);
                }
                if(totalnum - 1 == 0) booklist.remove(i);
            }
        }
    }
}

public class Test {
    public static void main(String args[]) {

    }
}
