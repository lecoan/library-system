package service;

import bean.Customer;
import bean.Student;
import constance.CustomerConstance;
import listener.GlobalActionDetector;
import util.Cache;
import util.LRUCache;
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

    private static final String USER_DATA_PATH = "./data/user";
    private static final int CACHE_SIZE = 1000;
    private static final long DEFAULT_CACHE_SAVE_TIME = 10 * 1000 * 10;

    private static CustomerService instance;

    /**
     * 当前各类用户总数
     */
    private int studentNum;
    private int teacherNum;
    private int rentedNum;

    private GlobalActionDetector detector;
    private StorageHelper helper;
    private Map<String, Integer> lastAccessTimeMap;
    /**
     * 用户缓存，减少磁盘读写次数
     */
    private Cache<String, Customer> cache;

    private Set<Customer> loginedCustomers;

    public int getStudentNum() {
        return studentNum;
    }

    public int getTeacherNum() {
        return teacherNum;
    }

    private CustomerService() {
        //获取之前的系统信息
        helper = StorageHelper.getInstance();
        Integer temp = helper.getConfig("studentNum");
        studentNum = temp == null ? 0 : temp;
        temp = helper.getConfig("teacherNum");
        teacherNum = temp == null ? 0 : temp;
        temp = helper.getConfig("rentedNum");
        rentedNum = temp == null ? 0 : temp;

        detector = GlobalActionDetector.getInstance();
        cache = new LRUCache<>(CACHE_SIZE, DEFAULT_CACHE_SAVE_TIME);

        Map<String, Integer> tt = (Map<String, Integer>)
                StorageHelper.ReadObjectFromFile(USER_DATA_PATH+"_lastAccessTime");
        lastAccessTimeMap = tt != null? tt : new HashMap<>();
        //程序退出时保存相应的数据
        helper.addQuitEvent(() -> {
            helper.saveConfig("teacherNum", teacherNum);
            helper.saveConfig("studentNum", studentNum);
            helper.saveConfig("rentedNum", rentedNum);
            System.out.println("saved");
        });

        loginedCustomers = new HashSet<>();
    }

    /**
     * @return singleton
     */
    public static CustomerService getInstance() {
        if (instance != null) {
            return instance;
        }
        return instance = new CustomerService();
    }

    /**
     * 通过用户的学号来获取用户实例
     *
     * @param id 用户的学号
     * @return 用户实例
     */
    public Customer getCustomerById(String id) {
        Customer customer = cache.get(id);
        if (customer == null) {
            Map<String, Customer> customerMap = (Map<String, Customer>) StorageHelper
                    .ReadObjectFromFile(USER_DATA_PATH + "_" + hash(id));

            if (customerMap == null) {
                return null;
            } else {
                customer = customerMap.get(id);
                cache.put(id, customer);
            }

        }
        return customer;
    }

    /**
     * 更新用户信息
     *
     * @param customer 需要更新的用户
     */
    public void updateCustomer(Customer customer) {
        Map<String, Customer> customerMap = (Map<String, Customer>) StorageHelper
                .ReadObjectFromFile(USER_DATA_PATH + "_" + hash(customer.getId()));
        if (customerMap == null) {
            customerMap = new HashMap<>();
        }
        customerMap.put(customer.getId(), customer);
        cache.put(customer.getId(), customer);
        StorageHelper.WriteObjectToFile(customerMap, USER_DATA_PATH + "_" + hash(customer.getId()));
    }

    /**
     * 保存用户
     *
     * @param customer 需要保存的用户
     */
    public void saveCustomer(Customer customer) {
        if (customer instanceof Student) {
            studentNum++;
        } else {
            teacherNum++;
        }
        Map<String, Customer> customerMap = (Map<String, Customer>) StorageHelper
                .ReadObjectFromFile(USER_DATA_PATH + "_" + hash(customer.getId()));
        if (customerMap == null) {
            customerMap = new HashMap<>();
        }
        customerMap.put(customer.getId(), customer);
        cache.put(customer.getId(), customer);
        StorageHelper.WriteObjectToFile(customerMap, USER_DATA_PATH + "_" + hash(customer.getId()));
    }

    /**
     * @param customer customer
     * @param isbn     isbn
     * @return 无意义
     */
    public int rentBookByISBN(Customer customer, String isbn) {
        //如果该书在用户心愿单里
        if (customer.getWantedSet().contains(isbn)) {
            customer.getWantedSet().remove(isbn);
        }
        if (customer.getBookedMap().isEmpty())
            rentedNum++;
        customer.getBookedMap().put(isbn, GlobalActionDetector.getInstance().getDays());
        return CustomerConstance.RENT_SUCCESSFULL;
    }

    /**
     * @param customer customer
     * @param isbn     isbn
     * @return 借阅时间
     */
    public int returnBook(Customer customer, String isbn) {
        int rentTime = customer.getBookedMap().remove(isbn);
        if (customer.getBookedMap().isEmpty()) {
            rentedNum--;
        }
        //检测是否超时
        if (detector.getDays() - rentTime > CustomerConstance.MAX_RENT_TIME) {

            customer.setDelayedTimes(customer.getDelayedTimes() + 1);
        }
        List<String> list = customer.getHistoryList();
        //最多保存30条历史
        if (list.size() >= 30) {
            list.remove(list.size() - 1);
        }
        list.add(0, isbn + "##" + rentTime + "##" + GlobalActionDetector.getInstance().getDays());
        return rentTime;
    }

    /**
     * @param customer customer
     * @return 当用户被冻结时返回true
     */
    public boolean caculateMoney(Customer customer) {
        customer.getBookedMap().forEach((s, integer) -> {
            int rentTime = customer.getBookedMap().get(s);
            if (detector.getDays() - rentTime > CustomerConstance.MAX_RENT_TIME) {
                customer.setMoney((float) (customer.getMoney() - 0.2));
                lastAccessTimeMap.put(customer.getId(),detector.getDays());
            }
        });
        if (customer.getMoney() < CustomerConstance.MAX_DEBT) {
            customer.setFreezed(true);
            return true;
        }
        return false;
    }

    /**
     * @return 当前已借书用户量
     */
    public int getRentedNum() {
        return rentedNum;
    }

    /**
     * @param id 学号或工号
     * @return hash值
     */
    private int hash(String id) {
        return new Integer(id) % 10;
    }

    public void updateMoney(Customer customer) {
        customer.getBookedMap().forEach((s, integer) -> {
            int rentTime = customer.getBookedMap().get(s);
            if (detector.getDays() - rentTime > CustomerConstance.MAX_RENT_TIME) {
                Integer temp = lastAccessTimeMap.get(customer.getId());
                int last = temp == null ? detector.getDays():temp;
                customer.setMoney((float) (customer.getMoney() - (detector.getDays() - last) * 0.2));
                lastAccessTimeMap.put(customer.getId(),detector.getDays());
            }
        });
        if(customer.getMoney()<CustomerConstance.MAX_DEBT){
            customer.setFreezed(true);
        }
    }

    public void logout(Customer customer) {
        loginedCustomers.remove(customer);
    }

    public boolean isLogin(Customer customer) {
        return loginedCustomers.contains(customer);
    }

    public void login(Customer customer) {
        loginedCustomers.add(customer);
    }

    /**
     * @param customer
     * @return 用户当前的
     */
    public int getDelayedTimes(Customer customer) {
        final int[] count = {0};
        customer.getBookedMap().forEach((s, integer) -> {
            if (detector.getDays() - 30 > integer) {
                count[0]++;
            }
        });
        return count[0] + customer.getDelayedTimes();
    }
}
