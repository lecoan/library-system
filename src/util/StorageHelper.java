package util;

import java.io.*;

/**
 * Created by lecoan on 2017/4/6.
 */
public class StorageHelper {

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
}
