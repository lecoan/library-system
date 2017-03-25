package bean;

import constance.CustomerConstance;
import service.CustomerService;

import java.util.List;

/**
 * Created by yangxiang on 17-3-8.
 */
public abstract class Customer {

    private String username;

    private String password;

    private List<String> bookedList;

    private List<String> wantedList;

    private int delayedTimes;

    private boolean isFreezed;

    private String type;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public abstract int getMaxNumForRent();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getBookedList() {
        return bookedList;
    }

    public void setBookedList(List<String> bookedList) {
        this.bookedList = bookedList;
    }

    public List<String> getWantedList() {
        return wantedList;
    }

    public void setWantedList(List<String> wantedList) {
        this.wantedList = wantedList;
    }

    public int getDelayedTimes() {
        return delayedTimes;
    }

    public void setDelayedTimes(int delayedTimes) {
        this.delayedTimes = delayedTimes;
    }

    public boolean isFreezed() {
        return isFreezed;
    }

    public void setFreezed(boolean freezed) {
        isFreezed = freezed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
