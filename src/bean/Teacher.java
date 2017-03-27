package bean;

import constance.CustomerConstance;

/**
 * Created by lecoan on 17-3-25.
 */
public class Teacher extends Customer {

    public Teacher() {
        setType("teacher");
    }

    @Override
    public int getMaxNumForRent() {
        return CustomerConstance.MAX_NUM_FOR_TEACHER;
    }
}
