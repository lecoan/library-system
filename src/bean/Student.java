package bean;

import constance.CustomerConstance;
import sun.print.CUPSPrinter;

/**
 * Created by lecoan on 17-3-25.
 */
public class Student extends Customer {

    private String colleage;
    private String classNumber;

    public Student() {
        setType("student");
    }

    public String getColleage() {
        return colleage;
    }

    public void setColleage(String colleage) {
        this.colleage = colleage;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }




    @Override
    public int getMaxNumForRent() {
        return CustomerConstance.MAX_NUM_FOR_STUDENT;
    }
}
