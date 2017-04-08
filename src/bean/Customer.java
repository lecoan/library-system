package bean;

import java.io.Serializable;
import java.util.*;

/**
 * Created by yangxiang on 17-3-8.
 */
public abstract class Customer implements Serializable {

    protected String username;
    protected String password;
    protected Map<String, Integer> bookedMap;
    protected Set<String> wantedSet;
    protected int delayedTimes;
    protected boolean isFreezed;
    protected String type;
    protected String id;
    protected int maxNumForRent;
    protected float money;
    protected List<String> historyList;

    Customer() {
        bookedMap = new TreeMap<>();
        wantedSet = new TreeSet<>();
        historyList = new LinkedList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Map<String, Integer> getBookedMap() {
        return bookedMap;
    }


    public Set<String> getWantedSet() {
        return wantedSet;
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

    public int getMaxNumForRent() {
        return maxNumForRent;
    }

    public void setMaxNumForRent(int maxNumForRent) {
        this.maxNumForRent = maxNumForRent;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public List<String> getHistoryList() {
        return historyList;
    }

}
