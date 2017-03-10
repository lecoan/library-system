package bean;

import service.CustomerService;

import java.util.List;

/**
 * Created by yangxiang on 17-3-8.
 */
public class Customer {

    private static final int MAX_NUM_FOR_STUDENT = 10;

    private static final int MAX_NUM_FOR_TEACHER = 20;

    private String username;

    private String password;

    private List<String> bookedList;

    private List<String> wantedList;

    private int delayedTimes;

    private boolean isFreezed;

    private CustomerService.CustomerType type;

    public int getMaxNumForRent() {
        if(type == CustomerService.CustomerType.students) {
            return MAX_NUM_FOR_STUDENT;
        } else {
            return MAX_NUM_FOR_TEACHER;
        }
    }

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

    public CustomerService.CustomerType getType() {
        return type;
    }

    public void setType(CustomerService.CustomerType type) {
        this.type = type;
    }
}
