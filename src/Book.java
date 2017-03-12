import java.io.Serializable;

/**
 * Created by zyx on 2017/3/12.
 */
public class Book implements Serializable {
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

    public void setName(String _name) {
        this.name = _name;
    }

    public void setBoughttime(String _boughttime) {
        this.boughttime = _boughttime;
    }

    public void setNumbers(int _numbers) {
        this.numbers = _numbers;
    }
}
