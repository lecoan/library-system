package service;

import bean.Book;
import bean.BookPathTable;

import java.io.*;
import java.util.*;

class OperateData implements Serializable{
    public List<BookPathTable> booklist;
    public Map<String, List<BookPathTable>> writersbooklist;
    public Map<String, List<BookPathTable>> publishersbooklist;
    public Map<String, List<BookPathTable>> samenamebooklist;
    public Map<String, List<BookPathTable>> samekindbooklist;
    public int pathnum;
}
public class BookOperate {
    private List<BookPathTable> booklist;
    private Map<String, List<BookPathTable>> writersbooklist;
    private Map<String, List<BookPathTable>> publishersbooklist;
    private Map<String, List<BookPathTable>> samenamebooklist;
    private Map<String, List<BookPathTable>> samekindbooklist;
    private int pathnum;//代表当前正在保存的图书文件
    private final int MaxNum = 2000;//每个文件保存图书的最大数量
    private final String[] bookpath = {"book1.xml", "book2.xml", "book3.xml", "book4.xml", "book5.xml"};
    //图书可以保存的所有文件，每个文件最多保存2000本图书
    private static void WriteObjectToFile(Object obj, String path) {
        File file =new File(path);
        FileOutputStream out;
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            out = new FileOutputStream(file);
            ObjectOutputStream objOut=new ObjectOutputStream(out);
            objOut.writeObject(obj);
            objOut.flush();
            objOut.close();
            System.out.println("write object success!");
        } catch (IOException e) {
            System.out.println("write object failed");
            e.printStackTrace();
        }
    }//只能写入一个对象，之后写入的会把之前写入的对象覆盖
    private static Object ReadObjectFromFile(String path) {
        Object temp=null;
        File file =new File(path);
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            ObjectInputStream objIn=new ObjectInputStream(in);
            temp=objIn.readObject();
            objIn.close();
            System.out.println("read object success!");
        } catch (IOException e) {
            System.out.println("read object failed");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }//读取一个对象
    private void AddNewIndextoTable(Map<String, List<BookPathTable>> list, Book newbook, String key, String path) {
        BookPathTable index = new BookPathTable();
        index.setIbsn(newbook.getIsbn());
        index.setBookpath(path);
        if(list.get(key) == null) {
        List<BookPathTable> ll = new ArrayList<>();
        ll.add(index);
        list.put(key,ll);
    }
        else {
        list.get(key).add(index);
    }
}
    private String SaveBook(Book newbook) {
        List<Book> templist = new ArrayList<>();
        File file = new File(bookpath[pathnum]);
        if(!file.exists()) {

            WriteObjectToFile(templist,bookpath[pathnum]);
            return bookpath[pathnum];
        }
        else {
            templist = (List<Book>)ReadObjectFromFile(bookpath[pathnum]);
            if(templist.size() < MaxNum) {
                templist.add(newbook);
                WriteObjectToFile(templist,bookpath[pathnum]);
                return bookpath[pathnum];
            }
            else {
                WriteObjectToFile(templist,bookpath[pathnum]);//先将原来的数据写回文件
                if(pathnum < 4) {
                    pathnum++;
                    List<Book> newlist = new ArrayList<>();
                    newlist.add(newbook);
                    WriteObjectToFile(newlist,bookpath[pathnum]);
                    return bookpath[pathnum];
                }
                else {
                    System.out.println("full!");
                    return null;
                }
            }
        }

    }//将一本书保存到文件中，首先需要检查当前文件是否容量已满，如果已满
                                                 //就需要将当前文件加一，如果超出最大容量限制，就提示无法存储，将图书保存后返回保存的路径
    private void UpdateBook(Book newbook){
          String isbn = new String();
          isbn = newbook.getIsbn();
          for(int i = 0; i < booklist.size(); ++i) {
              if(isbn == booklist.get(i).getIbsn()) {
                  List<Book> templist = new ArrayList<>();
                  templist = (List<Book>)ReadObjectFromFile(booklist.get(i).getBookpath());
                  for(int j = 0; j < templist.size(); ++j) {
                      if(isbn == templist.get(j).getIsbn()) {
                          templist.set(j, newbook);
                          break;
                      }
                  }
                  WriteObjectToFile(templist, booklist.get(i).getBookpath());
                  System.out.println("update success!");
                  break;
              }
          }

    }//更新一本图书的内容，实际更新的是剩余数量，与借阅历史
    private void DeleteFromTable(Map<String, List<BookPathTable>> list, String key, String isbn) {
        if(list.get(key).size() == 0) {
            System.out.println("no elements!");
        }
        else {
            for(int i = 0; i < list.get(key).size(); ++i) {
                if(isbn == list.get(key).get(i).getIbsn()) {
                    list.get(key).remove(i);
                    break;
                }
            }
        }
    }
    public BookOperate() {
        File file = new File("book.xml");
        if(!file.exists()) {
            writersbooklist = new HashMap<>();
            publishersbooklist = new HashMap<>();
            samenamebooklist = new HashMap<>();
            samekindbooklist = new HashMap<>();
            booklist = new ArrayList<>();
            pathnum = 0;
        }//文件不存在说明未经过初始化，所以要将变量进行初始化
        else {
            OperateData data = new OperateData();
            data = (OperateData)ReadObjectFromFile("book.xml");
            booklist = data.booklist;
            pathnum = data.pathnum;
            publishersbooklist = data.publishersbooklist;
            samenamebooklist = data.samenamebooklist;
            writersbooklist = data.writersbooklist;
            samekindbooklist = data.samekindbooklist;
        }
    }
    public void SaveData() {
        OperateData data = new OperateData();
        data.booklist = booklist;
        data.pathnum = pathnum;
        data.publishersbooklist = publishersbooklist;
        data.samenamebooklist = samenamebooklist;
        data.writersbooklist = writersbooklist;
        data.samekindbooklist = samekindbooklist;
        WriteObjectToFile(data, "book.xml");
    }
    // 将图书操作对象保存到文件中,为了防止写入对象的头部覆盖对象而引起
    // 错误，每次写入时先将整体全部读出，然后再一起写入
    public List<BookPathTable> getBookbyKind(String _kind) {
        return samekindbooklist.get(_kind);
    }
    public List<BookPathTable> getBookbyWriter(String writername) {
        return writersbooklist.get(writername);
    }
    public List<BookPathTable> getBookbyPublisher(String publishername) {
        return publishersbooklist.get(publishername);
    }
    public List<BookPathTable> getBookbyName(String bookname) {
        return samenamebooklist.get(bookname);
    }
    public Book getBookbyIsbn(String Isbn) {
             int i;
             String path = new String();
             for( i = 0; i < booklist.size(); ++i) {
                 System.out.println(booklist.get(i).getIbsn() + "*");
                 System.out.println(Isbn + "#");
                 if(booklist.get(i).getIbsn() == Isbn) {
                     path = booklist.get(i).getBookpath();
                     System.out.println("333333");
                     break;
                 }//此处两个字符串相等却无法判断。
             } //先从索引表中寻找图书所在文件
            if(i < booklist.size()){
                     List<Book> templist = new ArrayList<>();
                     templist = (List<Book>)ReadObjectFromFile(path);
                     for(int j = 0; j < templist.size(); ++j) {
                         if(templist.get(j).getIsbn() == Isbn) {
                             return templist.get(j);
                         }
                     }
             }//如果找到就从相应文件中读取图书并返回
            else {
                 System.out.println("not found");
            }
            return null;
    }

    public void addBook(Book newbook) {
        String temp = new String();
        temp = newbook.getIsbn();
        Book exist = new Book();
        exist = getBookbyIsbn(temp);
        if(exist != null) {
            int num = exist.getNumbers();
            exist.setNumbers(num + 1);
            UpdateBook(exist);
        }//如果这本书已经在文件中保存过，只需要将这本书的总数量加一即可
        else {
            String path = new String();
            path = SaveBook(newbook);
            BookPathTable index = new BookPathTable();
            index.setBookpath(path);
            index.setIbsn(temp);
            booklist.add(index);
            AddNewIndextoTable(writersbooklist, newbook, newbook.getWritername(), path);
            AddNewIndextoTable(publishersbooklist, newbook, newbook.getPublishername(), path);
            AddNewIndextoTable(samenamebooklist, newbook, newbook.getName(), path);
            AddNewIndextoTable(samekindbooklist, newbook, newbook.getKind(), path);
        }//否则就要重新保存这本书
    }//添加一本新书，保存到相应文件，并将这本书对应的索引保存到图书索引中，
    //并且按照种类，作者，出版社，书名将这本书保存到相应的索引中
    public void deleteBook(String isbn) {
           Book temp = new Book();
           temp = getBookbyIsbn(isbn);
           if(temp.getRestnumber() >= 1) {
               temp.setRestnumber(temp.getRestnumber() - 1);
               if(temp.getNumbers() >= 2 ) {
                   temp.setNumbers(temp.getNumbers() - 1);
                   UpdateBook(temp);
               }
               else {
                   for(int i = 0; i < booklist.size(); ++i) {
                       if(booklist.get(i).getIbsn() == temp.getIsbn()) {
                           List<Book> templist = new ArrayList<>();
                           templist = (List<Book>)ReadObjectFromFile(booklist.get(i).getBookpath());
                           for(int j = 0; j < templist.size(); ++j) {
                               if(templist.get(j).getIsbn() == temp.getIsbn()) {
                                   templist.remove(j);
                                   break;
                               }
                           }//从文件删除
                           WriteObjectToFile(templist, booklist.get(i).getBookpath());
                           booklist.remove(i);
                           break;//再从图书索引表中删除
                       }
                   }
                   DeleteFromTable(writersbooklist, temp.getWritername(), isbn);
                   DeleteFromTable(publishersbooklist, temp.getPublishername(), isbn);
                   DeleteFromTable(samekindbooklist, temp.getKind(), isbn);
                   DeleteFromTable(samenamebooklist, temp.getName(), isbn);
               }//需要将图书从五个索引表以及文件中分别删除
           }
           else {
               System.out.println("cannot delete, not return!");
           }
    }//首先找到这本书，然后检查图书的剩余数量与总数量，从而判断这本书是否可以删除



    /*public void addBorrowMemory(BorrowList memory, String ibsn) {
    }//为一本书添加借阅历史，并更新图书*/
}
//程序结束时要调用savedata将bookoperate数据保存
