package service;

import bean.Customer;

import java.util.Set;

/******************************************************************
 创建人: 杨翔
 日　期: 2017/3/8
 修改人:
 日　期:
 描　述: 提供Customers的基本操作
 版　本: v1.00 Copyright(c).
 ******************************************************************/
public interface CustomerService {

    Set<Customer> getAllCustomers();

    Customer getCustomerById(String username);

    void saveAllCustomers();

    void saveCustomer(Customer customer);

    void freezeCustomer(Customer customer);

    /**
     * @return 0 if  operation executed successfully
     */
    int rentBookByISBN(Customer customer, String isbn);

    /**
     *
     * @param customer
     * @param isbn
     * @return 借阅时间
     */
    int returnBook(Customer customer, String isbn);

}
