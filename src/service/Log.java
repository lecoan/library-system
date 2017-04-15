package service;


import listener.GlobalActionDetector;
import view.GetDate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyx on 2017/4/2.
 */
public class Log {
    private final String[] operatetype = {"冻结用户","解冻用户","添加图书",
                                 "删除图书","借阅图书","归还图书","修改图书","修改权限"};
    //public enum type {Freeze,Refreeze,AddBook,DelBook,BorBook,RetBook,ModBook};
    private List<String[]> log;
    private volatile static Log instance;
    private static void WriteObjectToFile(Object obj, String path) {
        File file = new File(path);
        FileOutputStream out;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            out = new FileOutputStream(file);
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(obj);
            objOut.flush();
            objOut.close();
            System.out.println("write object success!--log");
        } catch (IOException e) {
            System.out.println("write object failed--log");
            e.printStackTrace();
        }
    }//只能写入一个对象，之后写入的会把之前写入的对象覆盖

    private static Object ReadObjectFromFile(String path) {
        Object temp = null;
        File file = new File(path);
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(in);
            temp = objIn.readObject();
            objIn.close();
            System.out.println("read object success!--log");
        } catch (IOException e) {
            System.out.println("read object failed--log");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }//读取一个对象

    public static Log getInstance() {
        if(instance == null) {
            instance = new Log();
        }
        return instance;
    }
    private Log() {
        File file = new File("log.xml");
        if(!file.exists()) {
            log = new ArrayList<>();
        }
        else{
            log = (List<String[]>)ReadObjectFromFile("log.xml");
        }
    }
    public void CreateLog(String operator, int _type, String info) {
        GetDate date = new GetDate();
        String day = date.getDate(GlobalActionDetector.getInstance().getDays());
        String[] ll = {day, operator, operatetype[_type], info};
        log.add(ll);
        try {
            File file=new File("log.txt");
            if(!file.exists())
                file.createNewFile();
                FileOutputStream out=new FileOutputStream(file,true);
                StringBuffer sb=new StringBuffer();
                sb.append(day + ":  " + operator + "  " + operatetype[_type] + "  " + info);
                out.write(sb.toString().getBytes("utf-8"));
            out.close();
        }
        catch (IOException e) {
            System.out.println("write log");
        }
    }
    public List<String[]> GetLog() {
        return log;
    }
    public void Save() {
        WriteObjectToFile(log, "log.xml");
    }
}
