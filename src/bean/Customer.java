package bean;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by yangxiang on 17-3-8.
 */
public abstract class Customer implements Serializable {

    private String username;
    private String password;
    private Map<String, Integer> bookedMap;
    private Set<String> wantedSet;
    private int delayedTimes;
    private boolean isFreezed;
    private String type;
    private String id;
    private int maxNumForRent;
    private float money;

    Customer() {
        bookedMap = new TreeMap<>();
        wantedSet = new TreeSet<>();
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

    public void setBookedMap(Map<String, Integer> bookedMap) {
        this.bookedMap = bookedMap;
    }

    public Set<String> getWantedSet() {
        return wantedSet;
    }

    public void setWantedSet(Set<String> wantedSet) {
        this.wantedSet = wantedSet;
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
}
