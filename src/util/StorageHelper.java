package util;


import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/******************************************************************
 创建人: 杨翔
 日　期: 2017/3/10
 修改人:
 日　期:
 描　述: 对磁盘的存取进行封装，方便使用
 版　本: v1.00 Copyright(c).
 ******************************************************************/
public class StorageHelper {
    private Map<String, Integer> config;
    private List<Event> eventList;

    private static StorageHelper instance;

    private StorageHelper() {
        config = (Map<String, Integer>) StorageHelper
                .ReadObjectFromFile("./config");
        if (config == null) {
            config = new TreeMap<>();
        }
        eventList = new LinkedList<>();
        start();
    }

    /**
     * @return singleton
     */
    public static StorageHelper getInstance() {
        if (instance == null) {
            instance = new StorageHelper();
        }
        return instance;
    }

    /**
     * 实现程序退出时的数据储存和其他的清扫工作
     */
    private void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            //执行其他地方传入的函数
            eventList.forEach(Event::handle);

            StorageHelper.WriteObjectToFile(config, "./config");
            System.out.println("saved in file");
        }));
    }

    /**
     * 为其他地方提供程序退出时的操作函数
     *
     * @param event 想要添加的程序退出操作实现
     */
    public void addQuitEvent(Event event) {
        eventList.add(event);
    }

    public void saveConfig(String key, int value) {
        config.put(key, value);
    }

    /**
     * @param key key
     * @return 如果不存在返回null
     */
    public Integer getConfig(String key) {
        return config.get(key);
    }

    /**
     * 读取一个序列化对象
     *
     * @param path 文件所在的位置
     * @return 如果文件为空返回null
     */
    public static Object ReadObjectFromFile(String path) {
        Object temp = null;
        File file = new File(path);
        FileInputStream in;
        if (!file.exists() || !file.isFile()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            in = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(in);
            temp = objIn.readObject();
            objIn.close();
            System.out.println("read object success!");
        } catch (IOException e) {
            System.out.println("read object failed" + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 序列化对象并写入磁盘
     * 只能写入一个对象，之后写入的会把之前写入的对象覆盖
     *
     * @param obj  序列化对象
     * @param path 文件存储路径
     */
    public static void WriteObjectToFile(Object obj, String path) {
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
            System.out.println("write object success!");
        } catch (IOException e) {
            System.out.println("write object failed");
            e.printStackTrace();
        }
    }

    /**
     * 程序关闭的事件处理接口
     * 想要添加关闭时的逻辑需要自行实现该接口并使用
     *
     * @see util.StorageHelper#addQuitEvent(Event)
     */
    public interface Event {
        void handle();
    }
}
