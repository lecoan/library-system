
package service;
/*创建bookoperate操作对象时，在结束使用后要调用一次savedata*/

import bean.Book;
import bean.BookPathTable;
import bean.BorrowMemory;
import listener.GlobalActionDetector;
import view.GetDate;

import java.io.*;
import java.util.*;
//只有booklist中是实时的图书数量信息。
//将booklist按照hashmap存储
//其实在不同图书分类索引表中，只需要存储图书的编号的list
class OperateData implements Serializable {
    public Map<String, BookPathTable> booklist;
    public Map<String, List<BookPathTable>> writersbooklist;
    public Map<String, List<BookPathTable>> publishersbooklist;
    public Map<String, List<BookPathTable>> samenamebooklist;
    public Map<String, List<BookPathTable>> samekindbooklist;
    public List<BookPathTable> ranklist;//借阅次数最高的二十本书的排行榜
    public int pathnum;
    public int totalbooknum;//增加一本书就加一，删除一本书就减一
    public int restbooknum;//借阅一本书就加一，归还一本书就减一
}
public class BookOperate {
    private Map<String, BookPathTable> booklist;
    private List<BookPathTable> ranklist;
    private Map<String, List<BookPathTable>> writersbooklist;
    private Map<String, List<BookPathTable>> publishersbooklist;
    private Map<String, List<BookPathTable>> samenamebooklist;
    private Map<String, List<BookPathTable>> samekindbooklist;
    private int pathnum;//代表当前正在保存的图书文件
    private int totalbooknum;//增加一本书就加一，删除一本书就减一
    private int restbooknum;//借阅一本书就减一，归还一本书就加一
    private final int MaxNum = 2000;//每个文件保存图书的最大数量
    private final String[] bookpath = {"book1.xml", "book2.xml", "book3.xml", "book4.xml", "book5.xml"};
    //图书可以保存的所有文件，每个文件最多保存2000本图书
    private volatile static BookOperate instance;
    public BookPathTable getBookpathtable(String isbn) {
        return booklist.get(isbn);
    }
    public static BookOperate getInstance() {
        if (instance == null) {
            instance = new BookOperate();
        }
        return instance;
    }

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
            System.out.println("write object success!");
        } catch (IOException e) {
            System.out.println("write object failed");
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
            System.out.println("read object success!");
        } catch (IOException e) {
            System.out.println("read object failed");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }//读取一个对象

    private void AddNewIndextoTable(Map<String, List<BookPathTable>> list, String key, BookPathTable index) {
        if (list.get(key) == null) {
            List<BookPathTable> ll = new ArrayList<>();
            ll.add(index);
            list.put(key, ll);
        } else {
            list.get(key).add(index);
        }
    }

    private String SaveBook(Book newbook) {
        List<Book> templist = new ArrayList<>();
        File file = new File(bookpath[pathnum]);
        if (!file.exists()) {
            templist.add(newbook);
            WriteObjectToFile(templist, bookpath[pathnum]);
            return bookpath[pathnum];
        }//如果存储图书的文件还未建立，就可以直接将图书存入
        else {
            templist = (List<Book>) ReadObjectFromFile(bookpath[pathnum]);
            if (templist.size() < MaxNum) {
                templist.add(newbook);
                WriteObjectToFile(templist, bookpath[pathnum]);
                return bookpath[pathnum];
            } //当前文件存储容量未满时，就可以将图书存入
            else {
                WriteObjectToFile(templist, bookpath[pathnum]);//先将原来的数据写回文件
                if (pathnum < 4) {
                    pathnum++;
                    List<Book> newlist = new ArrayList<>();
                    newlist.add(newbook);
                    WriteObjectToFile(newlist, bookpath[pathnum]);
                    return bookpath[pathnum];
                } else {
                    System.out.println("all full!");
                    return null;
                }
            }//如果文件存储容量已满，就要到下一个目标文件存储信息，如果都满了就无法存储。
        }

    }//将一本书保存到文件中，首先需要检查当前文件是否容量已满，如果已满

    //就需要将当前文件加一，如果超出最大容量限制，就提示无法存储，将图书保存后返回保存的路径
    //调用这个函数就会直接将图书存入文件，判断是否存在这本书是在booklist中判断
    private void UpdateBook(Book newbook) {
        String isbn = new String();
        isbn = newbook.getIsbn();
        BookPathTable temp = getBookpathtable(isbn);
        if(temp != null) {
            List<Book> templist = new ArrayList<>();
            templist = (List<Book>) ReadObjectFromFile(temp.getBookpath());
            for (int j = 0; j < templist.size(); ++j) {
                if (templist.get(j).getIsbn().equals(isbn)) {
                    templist.set(j, newbook);
                    break;
                }
            }
            WriteObjectToFile(templist, temp.getBookpath());
            System.out.println("update success!");
        }
        else System.out.println("update fail!");
    }//更新一本图书的内容，实际更新的是借阅历史

    private void DeleteFromTable(Map<String, List<BookPathTable>> list, String key, String isbn) {
        if (list.get(key).size() == 0) {
            System.out.println("no elements!");
        } else {
            for (int i = 0; i < list.get(key).size(); ++i) {
                if (list.get(key).get(i).getIsbn().equals(isbn)) {
                    list.get(key).remove(i);
                    break;
                }
            }
        }
    }//从四个索引表中删除图书

    private BookOperate() {
        File file = new File("book.xml");
        if (!file.exists()) {
            ranklist = new ArrayList<>();
            writersbooklist = new HashMap<>();
            publishersbooklist = new HashMap<>();
            samenamebooklist = new HashMap<>();
            samekindbooklist = new HashMap<>();
            booklist = new HashMap<>();
            pathnum = 0;
            totalbooknum = 0;
            restbooknum = 0;
            System.out.println("first time run!");
        }//文件不存在说明未经过初始化，所以要将变量进行初始化
        else {
            OperateData data = new OperateData();
            data = (OperateData) ReadObjectFromFile("book.xml");
            booklist = data.booklist;
            ranklist = data.ranklist;
            publishersbooklist = data.publishersbooklist;
            samenamebooklist = data.samenamebooklist;
            writersbooklist = data.writersbooklist;
            samekindbooklist = data.samekindbooklist;
            pathnum = data.pathnum;
            totalbooknum = data.totalbooknum;
            restbooknum = data.restbooknum;
            System.out.println("next run!");
        }//直接从文件中读取出数据赋给相关数据
    }//单例模式

    public boolean SaveData() {
        OperateData data = new OperateData();
        data.booklist = booklist;
        data.ranklist = ranklist;
        data.publishersbooklist = publishersbooklist;
        data.samenamebooklist = samenamebooklist;
        data.writersbooklist = writersbooklist;
        data.samekindbooklist = samekindbooklist;
        data.pathnum = pathnum;
        data.totalbooknum = totalbooknum;
        data.restbooknum = restbooknum;
        WriteObjectToFile(data, "book.xml");
        return true;
    }

    // 将图书操作对象保存到文件中
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

    public List<BookPathTable> getRanklist() {
        return ranklist;
    }

    public Book getBookbyIsbn(String Isbn) {
        boolean flag = false;//判断这本书是否存在
        String path = new String();
        BookPathTable temp = getBookpathtable(Isbn);
        if(temp != null) {
            path = temp.getBookpath();
            System.out.println("find the book:" + temp.getIsbn());
            flag = true;
        }
        //先从索引表中寻找图书所在文件
        if (flag) {
            List<Book> templist = new ArrayList<>();
            templist = (List<Book>) ReadObjectFromFile(path);
            //System.out.println(templist.size());
            for (int j = 0; j < templist.size(); ++j) {
                if (templist.get(j).getIsbn().equals(Isbn)) {
                    System.out.println("get the book by isbn!");
                    return templist.get(j);
                }
            }
        }//如果找到就从相应文件中读取图书并返回
        else {
            System.out.println("not found the book!");
        }
        return null;
    }

    public boolean addBook(Book newbook) {
        String isbn = new String();
        isbn = newbook.getIsbn();
        BookPathTable index = getBookpathtable(isbn);
        if(index != null) {
            index.setTotalnum(index.getTotalnum() + 1);
            index.setRestnum(index.getRestnum() + 1);
            booklist.remove(isbn);
            booklist.put(isbn, index);

        }
        //首先会在booklist里面找这本书
        // 如果这本书已经在文件中保存过，只需要将这本书的总数量,剩余数量加一即可
        else {
            String path = new String();
            path = SaveBook(newbook);
            BookPathTable index1 = new BookPathTable();
            index1.setBookpath(path);
            index1.setIsbn(isbn);
            index1.setBorrownum(0);
            index1.setTotalnum(1);
            index1.setRestnum(1);
            booklist.put(isbn, index1);//将新的图书索引加入booklist
            AddNewIndextoTable(writersbooklist, newbook.getWritername(), index1);
            AddNewIndextoTable(publishersbooklist, newbook.getPublishername(), index1);
            AddNewIndextoTable(samenamebooklist, newbook.getName(), index1);
            AddNewIndextoTable(samekindbooklist, newbook.getKind(), index1);
        }//否则就要重新保存这本书
        totalbooknum++;
        restbooknum++;
        return true;//代表添加成功
    }//添加一本新书，保存到相应文件，并将这本书对应的索引保存到图书索引中，

    //并且按照种类，作者，出版社，书名将这本书保存到相应的索引中
    public boolean deleteBook(String isbn) {
        boolean flag = false;
        BookPathTable index = getBookpathtable(isbn);
        if(index != null) {
            Book temp = getBookbyIsbn(isbn);
            if (index.getRestnum() >= 1) {//
                index.setRestnum(index.getRestnum() - 1);
                if (index.getTotalnum() >= 2) {
                    index.setTotalnum(index.getTotalnum() - 1);
                    System.out.println("only delete from num!");
                    booklist.remove(isbn);
                    booklist.put(isbn, index);
                    Book book = getBookbyIsbn(isbn);
                    UpdateTable(book);
                }
                else {
                    List<Book> templist = new ArrayList<>();
                    templist = (List<Book>) ReadObjectFromFile(index.getBookpath());
                    for (int j = 0; j < templist.size(); ++j) {
                        if (templist.get(j).getIsbn().equals(isbn)) {
                            templist.remove(j);
                            break;
                        }
                    }//从文件删除
                    WriteObjectToFile(templist, index.getBookpath());
                    booklist.remove(isbn);
                    //还需要从排行榜删除
                    DeleteFromTable(writersbooklist, temp.getWritername(), isbn);
                    DeleteFromTable(publishersbooklist, temp.getPublishername(), isbn);
                    DeleteFromTable(samekindbooklist, temp.getKind(), isbn);
                    DeleteFromTable(samenamebooklist, temp.getName(), isbn);
                }
                totalbooknum--;
                restbooknum--;
            } else {
                System.out.println("cannot delete, not return!");
                return false;
            }
        }
        return false;//图书不存在无法删除。
    }//首先找到这本书，然后检查图书的剩余数量与总数量，从而判断这本书是否可以删除

    public int GetTotalBooknum() {
        return totalbooknum;
    }

    public int GetTotalRestbooknum() {
        return restbooknum;
    }

    public List<BookPathTable> GetBorrowRanklist() {
        return ranklist;
    }

    private void Sort(List<BookPathTable> list) {
        for(int i = 0; i < list.size(); ++i) {
            for(int j = list.size() - 1; j > i; --j) {
                if(list.get(j).getBorrownum() > list.get(j - 1).getBorrownum()) {
                    BookPathTable temp = list.get(j - 1);
                    list.set(j - 1, list.get(j));
                    list.set(j, temp);
                }
            }
        }
    }
    public boolean UpdateBookrank(String isbn) {
        booklist.get(isbn).setBorrownum(booklist.get(isbn).getBorrownum() + 1);
        //更新借阅次数
        BookPathTable temp = getBookpathtable(isbn);
        if(ranklist.size() == 0) {
            ranklist.add(temp);
        }
        else {
           ranklist.add(temp);
           Sort(ranklist);
           ranklist.remove(ranklist.size() - 1);
        }
        restbooknum--;
        return true;
    }//此时剩余图书数量减1,总数量不变

    public void addBorrowMemory(String borrowman, String isbn, String borrowtime) {
        Book book = new Book();
        book = getBookbyIsbn(isbn);
        BorrowMemory bm = new BorrowMemory();
        bm.setBorrowman(borrowman);
        bm.setBorrowtime(borrowtime);
        GlobalActionDetector gg = GlobalActionDetector.getInstance();
        int pastday = gg.getDays();
        GetDate date = new GetDate();
        bm.setReturntime(date.getDate(pastday));
        book.addBorrowMemory(bm);
        UpdateBook(book);
        restbooknum++;
    }//为一本书添加借阅历史，并更新图书此时剩余图书数量加一*/
    private void UpdateTable(Book book)//修改各个不同索引表中的图书数量
    {
        _UpdateTable(writersbooklist, book.getWritername(),book.getIsbn());
        _UpdateTable(publishersbooklist, book.getPublishername(), book.getIsbn());
        _UpdateTable(samekindbooklist, book.getKind(), book.getIsbn());
        _UpdateTable(samenamebooklist, book.getName(),  book.getIsbn());
    }

    private void _UpdateTable(Map<String, List<BookPathTable>> table, String key, String isbn) {
        for(int i = 0 ; i < table.get(key).size(); ++i) {
            if(table.get(key).get(i).getIsbn().equals(isbn)) {
                table.get(key).get(i).setRestnum(booklist.get(isbn).getRestnum());
                table.get(key).get(i).setTotalnum(booklist.get(isbn).getTotalnum());
            }
        }
    }

    public boolean SetBooknum(String isbn, int num) {
        if(booklist.get(isbn).getIsbn().equals(isbn)) {
            Book book = getBookbyIsbn(isbn);
            booklist.get(isbn).setTotalnum(num);
            booklist.get(isbn).setRestnum(num);
            UpdateTable(book);
            return true;
        }
        return false;
    }
}
//程序结束时要调用savedata将bookoperate数据保存，通过图书编号找到特定图书后，显示剩余数量的问题。
//两个list求交集，并集，多重条件查找。
