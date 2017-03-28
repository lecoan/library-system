package bean;

import java.io.Serializable;
public class BookPathTable implements Serializable{
    private String ibsn;
    private String bookpath;

    public void setIbsn(String _ibsn) {ibsn = _ibsn;}
    public void setBookpath(String _bookpath) {bookpath = _bookpath;}
    public String getIbsn() {return ibsn;}
    public String getBookpath() {return bookpath;}
}