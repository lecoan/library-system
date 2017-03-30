package bean;

import java.io.Serializable;

/**
 * Created by zyx on 2017/3/12.
 */
public class BorrowMemory implements Serializable {
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
}//当图书归还的时候生成这本图书的借阅历史，并存入文件
