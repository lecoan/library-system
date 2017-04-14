package util;

import org.omg.SendingContext.RunTime;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by lecoan on 2017/4/6.
 */
public class StorageHelper {
    //TODO 添加文件缓存
    private Map<String, Integer> config;
    private List<Event> eventList;

    private static StorageHelper instance;
    private StorageHelper(){
        config = (Map<String, Integer>) StorageHelper.ReadObjectFromFile("./config");
        if(config == null) {
            config = new TreeMap<>();
        }
        eventList = new LinkedList<>();
        start();
    }

    /**
     *
     * @return singleton
     */
    public static StorageHelper getInstance(){
        if(instance == null) {
            instance = new StorageHelper();
        }
        return instance;
    }

    private void start(){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            eventList.forEach(Event::handle);
            StorageHelper.WriteObjectToFile(config,"./config");
            System.out.println("saved in file");
        }));
    }

    public void addQuitEvent(Event event){
        eventList.add(event);
    }

    public void saveConfig(String key, int value){
        config.put(key,value);
    }

    /**
     *
     * @param key
     * @return 如果不存在返回null
     */
    public Integer getConfig(String key){
        return config.get(key);
    }

    /**
     *
     * @param path 文件所在的位置
     * @return 如果文件为空返回null
     */
    public static Object ReadObjectFromFile(String path) {
        Object temp = null;
        File file = new File(path);
        FileInputStream in;
        if(!file.exists() || !file.isFile()) {
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
            System.out.println("read object failed"+e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }//读取一个对象

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
    }//只能写入一个对象，之后写入的会把之前写入的对象覆盖

    public interface Event{
        void handle();
    }
}
