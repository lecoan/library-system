package bean;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookPathTable implements Serializable {

    private String isbn;
    private String bookpath;
    private int borrownum;//借阅次数，每次发生借阅行为，相应图书的借阅次数加一
    private int restnum;//当前图书的剩余数量
    private int totalnum;//当前图书的总量
    private List<Pair<String, String>> borrowlist;

    public void deleteBorrowMan(String name) {
        for(int i = 0; i < borrowlist.size(); ++i) {
            if(borrowlist.get(i).getKey().equals(name)) { borrowlist.remove(i);break;}
        }
    }
    public void addBorrowMan(String name, String time) {
        if(borrowlist == null) {
            borrowlist = new ArrayList<>();
        }
        borrowlist.add(new Pair(name,time));
    }

    public List<Pair<String, String>> getBorrowList() {
        return borrowlist;
    }

    public int getBorrownum() {
        return borrownum;
    }

    public void setBorrownum(int _borrownum) {
        borrownum = _borrownum;
    }

    public void setIsbn(String _isbn) {
        isbn = _isbn;
    }

    public void setBookpath(String _bookpath) {
        bookpath = _bookpath;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getBookpath() {
        return bookpath;
    }

    public int getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(int totalnum) {
        this.totalnum = totalnum;
    }

    public int getRestnum() {
        return restnum;
    }

    public void setRestnum(int restnum) {
        this.restnum = restnum;
    }
}