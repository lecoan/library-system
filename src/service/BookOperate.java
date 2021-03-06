
package service;
/*创建bookoperate操作对象时，在结束使用后要调用一次savedata*/
import bean.Book;
import bean.BookPathTable;
import bean.BorrowMemory;
import bean.OperateData;
import listener.GlobalActionDetector;
import util.LRUCache;
import util.StorageHelper;
import view.GetDate;

import java.io.*;
import java.util.*;

import static util.StorageHelper.ReadObjectFromFile;
/*BookOperate
作者：张悦祥
功能：实现对图书数据的所有操作
主要函数：
    增加图书
    删除图书
    更新图书借阅次数排行榜
    获取特定的图书数据
    为图书增加借阅历
    获取预约图书中已经归还的图书
主要设计思路：
    将图书分文件存储，并为图书创建索引表
    索引表储存图书保存的文件，以及一些占用内存小，需要经常改变的变量
    图书的比较大的数据（如借阅历史等）保存到文件中，然后从文件中读取时建立缓冲区，以提高效率
*/
public class BookOperate {
    private Map<String, BookPathTable> booklist;
    private List<BookPathTable> ranklist;
    private LRUCache<String, Book> bufferlist;//缓存哈希表
    private Map<String, List<BookPathTable>> writersbooklist;
    private Map<String, List<BookPathTable>> publishersbooklist;
    private Map<String, List<BookPathTable>> samenamebooklist;
    private Map<String, List<BookPathTable>> samekindbooklist;
    private int pathnum;//代表当前正在保存的图书文件
    private int totalbooknum;//增加一本书就加一，删除一本书就减一
    private int restbooknum;//借阅一本书就减一，归还一本书就加一
    private final int MaxNum = 2000;//每个文件保存图书的最大数量
    private final String[] bookpath = {"./data/book1.xml", "./data/book2.xml", "./data/book3.xml", "./data/book4.xml",
            "./data/book5.xml"};//图书可以保存的所有文件，每个文件最多保存2000本图书

    private volatile static BookOperate instance;

    public BookPathTable getBookpathtable(String isbn) {
        return booklist.get(isbn);
    }

    public static BookOperate getInstance() {
        if (instance == null) {
            synchronized (BookOperate.class) {
                if (instance == null) {
                    instance = new BookOperate();
                }
            }
        }
        return instance;
    }

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
            StorageHelper.WriteObjectToFile(templist, bookpath[pathnum]);
            return bookpath[pathnum];
        }//如果存储图书的文件还未建立，就可以直接将图书存入
        else {
            templist = (List<Book>) ReadObjectFromFile(bookpath[pathnum]);
            if (templist.size() < MaxNum) {
                templist.add(newbook);
                StorageHelper.WriteObjectToFile(templist, bookpath[pathnum]);
                return bookpath[pathnum];
            } //当前文件存储容量未满时，就可以将图书存入
            else {
                StorageHelper.WriteObjectToFile(templist, bookpath[pathnum]);//先将原来的数据写回文件
                if (pathnum < 4) {
                    pathnum++;
                    List<Book> newlist = new ArrayList<>();
                    newlist.add(newbook);
                    StorageHelper.WriteObjectToFile(newlist, bookpath[pathnum]);
                    return bookpath[pathnum];
                } else {
                    System.out.println("all full!  -- bookoperate");
                    return null;
                }
            }//如果文件存储容量已满，就要到下一个目标文件存储信息，如果都满了就无法存储。
        }

    }//将一本书保存到文件中，首先需要检查当前文件是否容量已满，如果已满
    // 就需要将当前文件加一，如果超出最大容量限制，就提示无法存储，将图书保存后返回保存的路径
    // 调用这个函数就会直接将图书存入文件，判断是否存在这本书是在booklist中判断
    private List<BookPathTable> GetallMatchlist(Map<String, List<BookPathTable>> map, String key) {
        List<BookPathTable> ans = new ArrayList<>();
        Set<String> set = map.keySet();
        for(String s : set) {
            if(s.contains(key)) {
                if(map.get(s) != null)
                    ans.addAll(map.get(s));
            }
        }
        return ans;
    }
    private void UpdateBook(Book newbook) {
        String isbn = new String();
        isbn = newbook.getIsbn();
        if (bufferlist.get(isbn) != null) bufferlist.remove(isbn);
        bufferlist.put(isbn, newbook);
        BookPathTable temp = getBookpathtable(isbn);
        if (temp != null) {
            List<Book> templist;
            templist = (List<Book>) ReadObjectFromFile(temp.getBookpath());
            for (int j = 0; j < templist.size(); ++j) {
                if (templist.get(j).getIsbn().equals(isbn)) {
                    templist.set(j, newbook);
                    break;
                }
            }
            StorageHelper.WriteObjectToFile(templist, temp.getBookpath());
            System.out.println("update success! -- bookoperate");
        } else System.out.println("update fail!  -- bookoperate");
    }//更新一本图书的内容，实际更新的是借阅历史

    private void DeleteFromTable(Map<String, List<BookPathTable>> list, String key, String isbn) {
        if (list.get(key).size() == 0) {
            System.out.println("no elements!  -- bookoperate");
        } else {
            for (int i = 0; i < list.get(key).size(); ++i) {
                if (list.get(key).get(i).getIsbn().equals(isbn)) {
                    list.get(key).remove(i);
                    break;
                }
            }
        }
    }
    //从四个索引表中删除图书

    private BookOperate() {
        File file = new File("./data/book.xml");
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
            System.out.println("first time run!  -- bookoperate");
        }//文件不存在说明未经过初始化，所以要将变量进行初始化
        else {
            OperateData data = (OperateData) ReadObjectFromFile("./data/book.xml");
            booklist = data.booklist;
            ranklist = data.ranklist;
            publishersbooklist = data.publishersbooklist;
            samenamebooklist = data.samenamebooklist;
            writersbooklist = data.writersbooklist;
            samekindbooklist = data.samekindbooklist;
            pathnum = data.pathnum;
            totalbooknum = data.totalbooknum;
            restbooknum = data.restbooknum;
            System.out.println("next run!  -- bookoperate");
        }//直接从文件中读取出数据赋给相关数据
        bufferlist = new LRUCache<>(1000, 10 * 1000 * 10);
    }//单例模式

    private void Sort(List<BookPathTable> list) {
        for (int i = 0; i < list.size(); ++i) {
            for (int j = list.size() - 1; j > i; --j) {
                if (list.get(j).getBorrownum() > list.get(j - 1).getBorrownum()) {
                    list.add(j - 1, list.get(j));
                    list.remove(j + 1);
                }
            }
        }
    }

    private void UpdateTable(Book book) {//修改各个不同索引表中的图书数量
        _UpdateTable(writersbooklist, book.getWritername(), book.getIsbn());
        _UpdateTable(publishersbooklist, book.getPublishername(), book.getIsbn());
        _UpdateTable(samekindbooklist, book.getKind(), book.getIsbn());
        _UpdateTable(samenamebooklist, book.getName(), book.getIsbn());
    }

    private void _UpdateTable(Map<String, List<BookPathTable>> table, String key, String isbn) {
        for (int i = 0; i < table.get(key).size(); ++i) {
            if (table.get(key).get(i).getIsbn().equals(isbn)) {
                table.get(key).get(i).setRestnum(booklist.get(isbn).getRestnum());
                table.get(key).get(i).setTotalnum(booklist.get(isbn).getTotalnum());
            }
        }
    }


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
        StorageHelper.WriteObjectToFile(data, "./data/book.xml");
        return true;
    }// 将图书操作对象保存到文件中

    public List<BookPathTable> getBookbyKind(String _kind) {
        List<BookPathTable> copylist = new ArrayList<>();
        copylist.addAll(GetallMatchlist(samekindbooklist, _kind));
        if(copylist.size() > 0)
            return copylist;
        else
            return null;
    }

    public List<String> ArrivedBook(Set<String> set) {
        List<String> ans = new ArrayList<>();
        for (String isbn : set) {
            BookPathTable index = getBookpathtable(isbn);
            if (index != null) if (index.getRestnum() > 0) ans.add(isbn);
        }
        if (ans.size() == 0) return null;
        return ans;
    }

    public List<BookPathTable> getBookbyWriter(String writername) {
        List<BookPathTable> copylist = new ArrayList<>();
        copylist.addAll(GetallMatchlist(writersbooklist, writername));
        if(copylist.size() > 0)
            return copylist;
        else
            return null;
    }

    public List<BookPathTable> getBookbyPublisher(String publishername) {
        List<BookPathTable> copylist = new ArrayList<>();
        copylist.addAll(GetallMatchlist(publishersbooklist, publishername));
        if(copylist.size() > 0)
            return copylist;
        else
            return null;
    }

    public List<BookPathTable> getBookbyName(String bookname) {
            List<BookPathTable> copylist = new ArrayList<>();
            copylist.addAll(GetallMatchlist(samenamebooklist, bookname));
            if(copylist.size() > 0)
                return copylist;
            else
                return null;
    }

    public List<BookPathTable> getRanklist() {
        if (ranklist != null) {
            List<BookPathTable> copylist = new ArrayList<>();
            copylist.addAll(ranklist);
            return copylist;
        }
        return null;
    }//返回的都是拷贝

    public Book getBookbyIsbn(String Isbn) {
        boolean flag = false;//判断这本书是否存在
        String path = new String();
        BookPathTable temp = getBookpathtable(Isbn);
        if (temp != null) {
            path = temp.getBookpath();
            System.out.println("find the book:" + temp.getIsbn() + "-- bookoperate");
            flag = true;
        }
        //先从索引表中寻找图书所在文件
        if (flag) {
            if (bufferlist.get(Isbn) != null) {
                System.out.println("get the book by isbn! --througn buffer  -- bookoperate");
                return bufferlist.get(Isbn);
            } else {
                List<Book> templist = new ArrayList<>();
                templist = (List<Book>) ReadObjectFromFile(path);
                //System.out.println(templist.size());
                for (int j = 0; j < templist.size(); ++j) {
                    if (templist.get(j).getIsbn().equals(Isbn)) {
                        System.out.println("get the book by isbn!  -- bookoperate");
                        bufferlist.put(Isbn, templist.get(j));
                        return templist.get(j);
                    }
                }
            }

        }//如果找到就从相应文件中读取图书并返回,但是修改这本图书并不会影响文件中的图书
        else {
            System.out.println("not found the book!  -- bookoperate");
        }
        return null;
    }

    public boolean addBook(Book newbook, int num) {
        String isbn = new String();
        isbn = newbook.getIsbn();
        BookPathTable index = getBookpathtable(isbn);
        if (index != null) {
            SetBooknum(isbn, index.getTotalnum() + num);
            //booklist.remove(isbn);
            //booklist.put(isbn, index);
        }
        //首先会在booklist里面找这本书,因为传递的是引用，所以修改会影响原位置的数据
        // 如果这本书已经在文件中保存过，只需要将这本书的总数量,剩余数量加一即可
        else {
            String path;
            path = SaveBook(newbook);
            BookPathTable index1 = new BookPathTable();
            index1.setBookpath(path);
            index1.setIsbn(isbn);
            index1.setBorrownum(0);
            index1.setTotalnum(num);
            index1.setRestnum(num);
            booklist.put(isbn, index1);//将新的图书索引加入booklist
            AddNewIndextoTable(writersbooklist, newbook.getWritername(), index1);
            AddNewIndextoTable(publishersbooklist, newbook.getPublishername(), index1);
            AddNewIndextoTable(samenamebooklist, newbook.getName(), index1);
            AddNewIndextoTable(samekindbooklist, newbook.getKind(), index1);
        }//否则就要重新保存这本书
        totalbooknum = totalbooknum + num;
        restbooknum = restbooknum + num;
        return true;//代表添加成功
    }
    //添加一本新书，保存到相应文件，并将这本书对应的索引保存到图书索引中，
    // 并且按照种类，作者，出版社，书名将这本书保存到相应的索引中

    public boolean deleteBook(String isbn) {
        if (bufferlist.get(isbn) != null) bufferlist.remove(isbn);
        BookPathTable index = getBookpathtable(isbn);
        if (index != null) {
            Book temp = getBookbyIsbn(isbn);
            List<Book> templist = new ArrayList<>();
            templist = (List<Book>) ReadObjectFromFile(index.getBookpath());
            for (int j = 0; j < templist.size(); ++j) {
                if (templist.get(j).getIsbn().equals(isbn)) {
                    templist.remove(j);
                    break;
                }
            }//从文件删除
            StorageHelper.WriteObjectToFile(templist, index.getBookpath());
            totalbooknum = totalbooknum - index.getTotalnum();
            restbooknum = restbooknum - index.getTotalnum();
            booklist.remove(isbn);
            for(int i = 0; i < ranklist.size(); ++i) {
                if(ranklist.get(i).getIsbn().equals(isbn)) {
                    ranklist.remove(i);
                }
            }//从排行榜删除
            DeleteFromTable(writersbooklist, temp.getWritername(), isbn);
            DeleteFromTable(publishersbooklist, temp.getPublishername(), isbn);
            DeleteFromTable(samekindbooklist, temp.getKind(), isbn);
            DeleteFromTable(samenamebooklist, temp.getName(), isbn);
        } else {
            System.out.println("cannot delete, not exist!  -- bookoperate");
            return false;
        }
        return false;//图书不存在无法删除。
    }
    //首先找到这本书，然后检查图书的剩余数量与总数量，从而判断这本书是否可以删除

    public int GetTotalBooknum() {
        return totalbooknum;
    }

    public int GetTotalRestbooknum() {
        return restbooknum;
    }

    public boolean UpdateBookrank(String isbn,String name) {
        if (booklist.get(isbn).getRestnum() == 0) return false;

        GlobalActionDetector gg = GlobalActionDetector.getInstance();
        booklist.get(isbn).addBorrowMan(name,  GetDate.getDate(gg.getDays()));

        booklist.get(isbn).setBorrownum(booklist.get(isbn).getBorrownum() + 1);
        booklist.get(isbn).setRestnum(booklist.get(isbn).getRestnum() - 1);
        //更新借阅次数,剩余数量
        BookPathTable temp = getBookpathtable(isbn);
        if (ranklist.size() == 0) {
            ranklist.add(temp);//增加引用
        } else {
            boolean flag = false;
            for(int i = 0; i < ranklist.size(); ++i ) {
                if(ranklist.get(i).getIsbn().equals(isbn)) {
                    flag = true;
                }
            }
            if(!flag) {
                ranklist.add(temp);
                if (ranklist.size() > 20) {
                    Sort(ranklist);
                    ranklist.remove(ranklist.size() - 1);
                }
            }
            Sort(ranklist);
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
        bm.setReturntime(GetDate.getDate(gg.getDays()));
        book.addBorrowMemory(bm);
        booklist.get(isbn).setRestnum(booklist.get(isbn).getRestnum() + 1);//
        booklist.get(isbn).deleteBorrowMan(borrowman);//
        UpdateBook(book);
        restbooknum++;
    }
    //为一本书添加借阅历史，并更新图书此时剩余图书数量加一*/

    private boolean SetBooknum(String isbn, int num) {
        Book book = getBookbyIsbn(isbn);
        booklist.get(isbn).setTotalnum(num);
        booklist.get(isbn).setRestnum(num);
        UpdateTable(book);
        return true;
    }

}
//程序结束时要调用savedata将bookoperate数据保存，通过图书编号找到特定图书后，显示剩余数量的问题。
