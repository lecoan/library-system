/**
 * Created by lecoan on 17-3-1.
 */
class Book {
    private String name;
    private String boughttime;
    private String writername;
    private String publishername;
    private String introduction;
    private String kind;
    private String isbn;
    private int numbers;
    private int restnumber;

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


    public List<Book> getAllBook() {
        if(this.booklist != null) {
            return this.booklist;
        }
        else {
            try {
                File file = new File("data/book.txt");
          if(!file.exists() || file.isDirectory())
             throw new FileNotFoundException();
          BufferedReader br = new BufferedReader(new FileReader(file));
          String current = null;
          while(current = br.readLine() != null) {
                        String[] temp = current.split(" ");
                        Book b;
                        b.setName(temp[0]);
                        b.setBoughttime(temp[1]);
                        b.setWritername(temp[2]);
                        b.setPublishername(temp[3]);
                        b.setIntroduction(temp[4]);
                        b.setKind(temp[5]);
                        b.setIsbn();
                        b.setNumbers((int)temp[6]);
                        b.setRestnumber((int)temp[7]);
                        booklist.add(b);
                    }
            }
            catch(FileNotFoundException()) {
                System.Out.println("FileNotFound");
            }
        }
    }
    public void saveAllBook() {

    }

    public List<Book> getBookbyWriter(String writername) {
        if(this.writersbooklist[writername] != null) {
            return this.writersbooklist[writername];
        }
        else {

        }
    }
    public List<Book> getBookbyPublisher(String publishername) {

    }
    public Book getBookbyIsbn(String Isbn) {

    }
    public List<Book> getBookbyName(String bookname) {

    }

    public void addBook(Book newbook) {
        booklist.add(newbook);
    }
    public void deleteBook(String isbn) {

    }
}
public class Test {
    public static void main(String args[]) {

    }
}
