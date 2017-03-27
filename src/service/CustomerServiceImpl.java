package service;

import bean.Book;
import bean.Customer;
import bean.Student;
import bean.Teacher;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import constance.CustomerConstance;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

/******************************************************************
 创建人: 杨翔
 日　期: 2017/3/8
 修改人:
 日　期:
 描　述: 对user的数据操作的封装
 版　本: v1.00 Copyright(c).
 ******************************************************************/
public class CustomerServiceImpl implements CustomerService {
    //TODO 更该代码
    private static final String USER_DATA_PATH = "./user.xml";

    private List<Customer> customerList = new LinkedList<>();

    private boolean hasInit = false;

    private static  final String [] indexes = {"username","password","booked-list","wanted-list","is-freezed","type","delayed-times"};

    @Override
    public List<Customer> getAllCustomers() {

        if(hasInit) {
            return customerList;
        }
        hasInit = true;
        File data = new File(USER_DATA_PATH);

        if(data.isFile() && data.exists()) {
            try {
                Document doc = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder()
                        .parse(data);
                customerList.clear();
                Element all = doc.getDocumentElement();
                NodeList cuslist = all.getElementsByTagName("customer");
                for(int i = 0; i<cuslist.getLength(); ++ i) {
                    Node customer = cuslist.item(i);
                    if(customer.getNodeType() == Node.ELEMENT_NODE) {
                        Element ele = (Element) customer;
                        Customer cus;
                        if(ele.getAttribute("type").equals("teacher"))
                           cus = new Teacher();
                        else
                            cus = new Student();
                        cus.setUsername(ele.getAttribute("username"));
                        cus.setPassword(ele.getAttribute("password"));
                        cus.setDelayedTimes(new Integer(ele.getAttribute("delayed-times")));
                        cus.setFreezed(new Boolean(ele.getAttribute("is-freezed")));

                        //获取书本列表
                        NodeList walist = ele.getElementsByTagName("wanted-list").item(0).getChildNodes();
                        List<String> wa = new LinkedList<>();
                        for(int j = 0;j<walist.getLength();++j) {
                            Node book = walist.item(j);
                            if(book.getNodeType() == Node.ELEMENT_NODE) {
                                String isbn= book.getChildNodes().item(0).getTextContent();
                                wa.add(isbn);
                            }
                        }
                        cus.setWantedList(wa);

                        NodeList bolist = ele.getElementsByTagName("booked-list").item(0).getChildNodes();
                        List<String> bo = new LinkedList<>();
                        for(int j = 0;j<bolist.getLength();++j) {
                            Node book = bolist.item(j);
                            if(book.getNodeType() == Node.ELEMENT_NODE) {
                                String isbn= book.getChildNodes().item(0).getTextContent();
                                bo.add(isbn);
                            }
                        }
                        cus.setBookedList(bo);
                        customerList.add(cus);
                    }
                }
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }

        }

        return customerList;
    }

    @Override
    public Customer getCustomerByUsername(String username) {
        if(customerList == null) {
            getAllCustomers();
        }
        for (Customer customer : customerList) {
            if(customer.getUsername().equals(username)) {
                return customer;
            }
        }
        return null;
    }

    @Override
    public void saveAllCustomers() {
        try {
            Document doc = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .newDocument();

            //root element
            Element all = doc.createElement("all-customer");

            for(Customer customer : customerList) {

                //创建customer
                Element cus = doc.createElement("customer");
                cus.setAttribute("username",customer.getUsername());
                cus.setAttribute("password",customer.getPassword());
                cus.setAttribute("type",customer.getType());
                cus.setAttribute("delayed-times", customer.getDelayedTimes()+"");
                cus.setAttribute("is-freezed",customer.isFreezed()+"");

                //booked-list
                Element bolist = doc.createElement("booked-list");

                for(String isbn : customer.getBookedList()) {
                    Element ISBN = doc.createElement("ISBN");
                    ISBN.appendChild(doc.createTextNode(isbn));
                    bolist.appendChild(ISBN);
                }

                //wanted-list
                Element walist = doc.createElement("wanted-list");
                for(String isbn : customer.getWantedList()) {
                    Element ISBN = doc.createElement("ISBN");
                    ISBN.appendChild(doc.createTextNode(isbn));
                    walist.appendChild(ISBN);
                }

                if(customer.getType().equals("student")) {
                    Student student = (Student) customer;
                    cus.setAttribute("colleage",student.getColleage());
                }
                cus.appendChild(bolist);
                cus.appendChild(walist);

                all.appendChild(cus);
            }

            doc.appendChild(all);

            Transformer transformer =TransformerFactory.newInstance()
                    .newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(USER_DATA_PATH));

            transformer.transform(source,result);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveCustomer(Customer customer) {
        customerList.add(customer);
    }

    @Override
    public void freezeCustomer(Customer customer) {
        customer.setFreezed(true);
    }

    @Override
    public int rentBookByName(Customer customer, String name) {
        List<Book> bookList = new BookOperate().getBookbyName(name);
        if(bookList.size()!=0){
            if(customer.getMaxNumForRent() > customer.getBookedList().size()) {
                Book book = bookList.get(0);
                customer.getBookedList().add(book.getIsbn());
                return CustomerConstance.RENT_SUCCESSFULL;
            }
            return CustomerConstance.RENT_TO_MUCH;
        }
        customer.getWantedList().add(name);
        return CustomerConstance.RENT_HAS_NO_BOOK;
    }

    @Override
    protected void finalize() throws Throwable {
        saveAllCustomers();
    }
}
