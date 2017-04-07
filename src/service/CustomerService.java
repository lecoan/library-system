package service;

import bean.Customer;
import bean.Student;
import constance.CustomerConstance;
import listener.GlobalActionDetector;
import util.StorageHelper;

import java.util.*;
import java.util.function.BiConsumer;

/******************************************************************
 创建人: 杨翔
 日　期: 2017/3/8
 修改人:
 日　期:
 描　述: 对user的数据操作的封装
 版　本: v1.00 Copyright(c).
 ******************************************************************/
public class CustomerService {

    private static final String USER_DATA_PATH = "./user";

    private static CustomerService instance;
    private int studentNum;
    private int teacherNum;
    private Map<String, Customer> customerMap;
    private Set<Customer> customers;
    private GlobalActionDetector detector;
    private StorageHelper helper;

    public int getStudentNum(){
        return studentNum;
    }
    public int getTeacherNum(){
        return teacherNum;
    }
    private CustomerService() {
        helper = StorageHelper.getInstance();
        Integer temp = helper.getConfig("studentNum");
        studentNum = temp == null ? 0 : temp;
        temp = helper.getConfig("teacherNum");
        teacherNum = temp == null ? 0 : temp;
        customers = (Set<Customer>) StorageHelper.ReadObjectFromFile(USER_DATA_PATH);
        if (customers == null) customers = new HashSet<>();
        customerMap = new HashMap<>();
        for (Customer customer : customers) {
            customerMap.put(customer.getId(), customer);
        }

        detector = GlobalActionDetector.getInstance();

        helper.addQuitEvent(() -> {
            saveAllCustomers();
            helper.saveConfig("teacherNum", teacherNum);
            helper.saveConfig("studentNum", studentNum);
            System.out.println("saved");
        });
    }

    public static CustomerService getInstance() {
        if (instance != null) {
            return instance;
        }
        return instance = new CustomerService();
    }

    public Set<Customer> getAllCustomers() {
        return customers;
    }

    public Customer getCustomerById(String id) {
        return customerMap.get(id);
    }

    private void saveAllCustomers() {
        StorageHelper.WriteObjectToFile(customers, USER_DATA_PATH);
    }

    public void saveCustomer(Customer customer) {
        if (customer instanceof Student) {
            studentNum++;
        } else {
            teacherNum++;
        }
        customers.add(customer);
        customerMap.put(customer.getId(), customer);
        System.out.println("ok");
    }

    public void freezeCustomer(Customer customer) {
        customer.setFreezed(true);
    }

    /**
     * @param customer
     * @param isbn
     * @return CustomerConstance.RENT_TO_MUCH  CustomerConstance.RENT_SUCCESSFULL
     */
    public int rentBookByISBN(Customer customer, String isbn) {
        if (customer.isFreezed() || customer.getMaxNumForRent() <= customer.getBookedMap().size()) {
            return CustomerConstance.RENT_TO_MUCH;
        }
        customer.getBookedMap().put(isbn, GlobalActionDetector.getInstance().getDays());
        return CustomerConstance.RENT_SUCCESSFULL;
    }

    /**
     * @param customer
     * @param isbn
     * @return 借阅时间
     */
    public int returnBook(Customer customer, String isbn) {
        if (customer.getWantedSet().contains(isbn)) {
            customer.getWantedSet().remove(isbn);
        }
        int rentTime = customer.getBookedMap().remove(isbn);
        if (detector.getDays() - rentTime > 30) {

            customer.setDelayedTimes(customer.getDelayedTimes() + 1);
        }
        return rentTime;
    }

    /**
     * @param customer
     * @return 当用户被冻结时返回true
     */
    public boolean caculateMoney(Customer customer) {
        customer.getBookedMap().forEach((s, integer) -> {
            int rentTime = customer.getBookedMap().get(s);
            customer.setMoney(customer.getMoney() - (detector.getDays() - rentTime));
        });
        if (customer.getMoney() < CustomerConstance.MAX_DEBT) {
            customer.setFreezed(true);
            return true;
        }
        return false;
    }
}
