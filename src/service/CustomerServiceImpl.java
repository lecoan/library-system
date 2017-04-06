package service;

import bean.Customer;
import bean.Student;
import constance.CustomerConstance;
import listener.GlobalActionDetector;
import util.StorageHelper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/******************************************************************
 创建人: 杨翔
 日　期: 2017/3/8
 修改人:
 日　期:
 描　述: 对user的数据操作的封装
 版　本: v1.00 Copyright(c).
 ******************************************************************/
public class CustomerServiceImpl implements CustomerService {

    private static final String USER_DATA_PATH = "./user";

    private static CustomerService instance;
    //TODO 存入文件
    private int studentNum;
    private int teacherNum;
    private Map<String,Customer> customerMap;
    private Set<Customer> customers;


    private CustomerServiceImpl() {
        customers = (Set<Customer>) StorageHelper.ReadObjectFromFile(USER_DATA_PATH);
        if(customers == null) customers = new HashSet<>();

        customerMap = new HashMap<>();
        for(Customer customer:customers) {
            customerMap.put(customer.getId(),customer);
        }

    }

    public static CustomerService getInstance() {
        if(instance != null) {
            return instance;
        }
        return instance = new CustomerServiceImpl();
    }

    @Override
    public Set<Customer> getAllCustomers() {
        return customers;
    }

    @Override
    public Customer getCustomerById(String id) {
        return customerMap.get(id);
    }

    @Override
    public void saveAllCustomers() {
        StorageHelper.WriteObjectToFile(customers,USER_DATA_PATH);
    }

    @Override
    public void saveCustomer(Customer customer) {
        if(customer instanceof Student) {
            studentNum++;
        } else {
            teacherNum++;
        }
        customers.add(customer);
        customerMap.put(customer.getId(),customer);
    }

    @Override
    public void freezeCustomer(Customer customer) {
        customer.setFreezed(true);
    }

    @Override
    public int rentBookByISBN(Customer customer, String isbn) {
        if(customer.isFreezed() || customer.getMaxNumForRent()<=customer.getBookedMap().size()) {
            return CustomerConstance.RENT_TO_MUCH;
        }
        customer.getBookedMap().put(isbn,GlobalActionDetector.getInstance().getDays());
        return CustomerConstance.RENT_SUCCESSFULL;
    }

    @Override
    public int returnBook(Customer customer, String isbn) {
        if(customer.getWantedSet().contains(isbn)) {
            customer.getWantedSet().remove(isbn);
        }
        return customer.getBookedMap().remove(isbn);
    }

    protected void finalize() throws Throwable {
        saveAllCustomers();
    }
}
