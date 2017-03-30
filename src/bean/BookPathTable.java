package bean;

import java.io.Serializable;
public class BookPathTable implements Serializable{
    private String isbn;
    private String bookpath;
    private int borrownum;//借阅次数，每次发生借阅行为，相应图书的借阅次数加一

    public int getBorrownum() { return borrownum; }
    public void setBorrownum(int _borrownum) { borrownum = _borrownum;}
    public void setIsbn(String _isbn) {isbn = _isbn;}
    public void setBookpath(String _bookpath) { bookpath = _bookpath;}
    public String getIsbn() { return isbn;}
    public String getBookpath() { return bookpath;}
}