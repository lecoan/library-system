package service;

import bean.Customer;
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

/**
 * Created by lecoan on 17-3-8.
 */
public class CustomerServiceImpl implements CustomerService {

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
                //获取所有的用户
                NodeList cuslist = all.getElementsByTagName("customer");
                for(int i = 0; i<cuslist.getLength(); ++ i) {
                    Node customer = cuslist.item(i);
                    if(customer.getNodeType() == Node.ELEMENT_NODE) {
                        Element ele = (Element) customer;
                        Customer cus = new Customer();
                        cus.setUsername(ele.getAttribute("username"));
                        cus.setPassword(ele.getAttribute("password"));
                        cus.setDelayedTimes(new Integer(ele.getAttribute("delayed-times")));
                        cus.setFreezed(new Boolean(ele.getAttribute("is-freezed")));
                        if("student".equals(ele.getAttribute("type"))) {
                            cus.setType(CustomerType.students);
                        } else {
                            cus.setType(CustomerType.teacher);
                        }

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
            if(customer.getUsername() == username) {
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
                if(customer.getType() == CustomerType.students) {
                    cus.setAttribute("type", "student");
                } else {
                    cus.setAttribute("type","teacher");
                }
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
    protected void finalize() throws Throwable {
        saveAllCustomers();
    }
}
