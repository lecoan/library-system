package bean;

import constance.CustomerConstance;

/**
 * Created by lecoan on 17-3-25.
 */
public class Student extends Customer {

    private String colleage;

    public Student() {
        setType("student");
        setMaxNumForRent(CustomerConstance.MAX_NUM_FOR_STUDENT);
    }

    public String getColleage() {
        return colleage;
    }

    public void setColleage(String colleage) {
        this.colleage = colleage;
    }
}
