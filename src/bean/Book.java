package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Book implements Serializable {
    private String name;
    private String boughttime;
    private String writername;
    private String publishername;
    private String introduction;
    private String kind;//
    private String isbn;
    private List<BorrowMemory> borrowmemory;

    public void addBorrowMemory(BorrowMemory b) {
        if(borrowmemory == null) {
            borrowmemory = new ArrayList<>();
            borrowmemory.add(b);
        }
        else borrowmemory.add(b);
    }//如果借阅历史不为空，就不需要初始化list对象，否则要先初始化list再添加借阅历史。

    public List<BorrowMemory> getBorrowmemory() {
        return borrowmemory;
    }

    public Book() {}
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

    public String setIsbn() {
        this.isbn = this.publishername + "-" + this.writername + "-" + this.name + "-" + this.kind;
        return isbn;
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

    public String getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
    }

    public String getBoughttime() {
        return boughttime;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setBoughttime(String boughttime) {
        this.boughttime = boughttime;
    }

}