package controler;

import bean.*;
import constance.CustomerConstance;
import listener.GlobalActionDetector;
import service.BookOperate;
import service.CustomerService;
import view.ErrAlert;
import view.GetDate;
import view.UserView;
import bean.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * Created by ghoskno on 3/29/17.
 */
public class UserControler {

    private volatile static UserControler instance;

    public static UserControler getInstance() {
        synchronized (UserControler.class) {
            if (instance == null) {
                instance = new UserControler();
            }
            return instance;
        }
    }

    private UserControler() {
    }

    public UserView initUserView(Customer customer) {
        //this.customer = customer;
        UserView UserPanel = new UserView(customer);
        commonControler.findBook(UserPanel.findBookFrame);
        return UserPanel;
    }


}
