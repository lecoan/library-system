package service;

import bean.Customer;

import java.util.List;

/**
 * Created by yangxiang on 17-3-8.
 */
public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer getCustomerByUsername(String username);

    void saveAllCustomers();

    void saveCustomer(Customer customer);

    void freezeCustomer(Customer customer);

    enum CustomerType {
        teacher,
        students
    }
}
