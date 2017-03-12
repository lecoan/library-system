import java.io.*;
import java.util.*;

/**
 * Created by zyx on 2017/3/12.
 */
public class BookOperate {
    private List<Book> booklist;
    private Map<String, List<Book>> writersbooklist;
    private Map<String, List<Book>> publishersbooklist;
    private Map<String, List<Book>> samenamebooklist;
    public BookOperate() {
        writersbooklist = new HashMap<>();
        publishersbooklist = new HashMap<>();
        samenamebooklist = new HashMap<>();
        booklist = new ArrayList<>();
    }
    public List<Book> getAllBook() {
        if (this.booklist.size() != 0) {
            return this.booklist;
        }
        else {
            try {
                File file = new File("book.txt");
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                booklist = (List<Book>) ois.readObject();
                ois.close();
                fis.close();
                return this.booklist;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }//若图书列表为空，则从文件中读入，否则会直接返回图书列表。

    public void saveAllBook() {
        if(this.booklist.size() != 0) {
            try{
                File file = new File("book.txt");
                if(!file.exists())
                    file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(booklist);
                oos.flush();
                oos.close();
                fos.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("not initialization");
        }
    }//图书列表不为空就直接保存到文件。

    public List<Book> getBookbyWriter(String writername) {
        if(writersbooklist.get(writername) != null) {
            return writersbooklist.get(writername);
        }
        else {
            List<Book> wsl = new ArrayList();
            for(Book temp : booklist) {
                if(temp.getWritername().equals(writername)) wsl.add(temp);
            }
            writersbooklist.put(writername, wsl);
            return writersbooklist.get(writername);
        }
    }//如果已经建立过相应的索引，就可以直接返回。
    public List<Book> getBookbyPublisher(String publishername) {
        if(publishersbooklist.get(publishername) != null) {
            return publishersbooklist.get(publishername);
        }
        else {
            List<Book> psn = new ArrayList();
            for(Book temp : booklist) {
                if(temp.getPublishername().equals(publishername)) psn.add(temp);
            }
            publishersbooklist.put(publishername, psn);
            return publishersbooklist.get(publishername);
        }
    }
    public Book getBookbyIsbn(String Isbn) {
        for(Book temp : booklist) {
            if(temp.getIsbn().equals(Isbn)) return temp;
        }
        Book notfound = new Book();
        return notfound;
    }//不存在就返回一本空的书。
    public List<Book> getBookbyName(String bookname) {
        if(samenamebooklist.get(bookname) != null) {
            return samenamebooklist.get(bookname);
        }
        else {
            List<Book> bl = new ArrayList();
            for(Book temp : booklist) {
                if(temp.getName().equals(bookname)) bl.add(temp);
            }
            samenamebooklist.put(bookname, bl);
            return samenamebooklist.get(bookname);
        }
    }

    public void addBook(Book newbook) {
        if(booklist.size() == 0) {
            booklist.add(newbook);
        }
        else {
            for(Book temp : booklist) {
                if(temp.getIsbn().equals(newbook.getIsbn()) ) {
                    int totalnum = temp.getNumbers();
                    int restnum = temp.getRestnumber();
                    temp.setNumbers(totalnum + 1);
                    temp.setRestnumber(restnum + 1);
                    return;
                }
            }
            booklist.add(newbook);
            if(writersbooklist.get(newbook.getWritername()) != null) {
                writersbooklist.get(newbook.getWritername()).add(newbook);
            }
            if(publishersbooklist.get(newbook.getPublishername()) != null) {
                publishersbooklist.get(newbook.getPublishername()).add(newbook);
            }
            if(samenamebooklist.get(newbook.getName()) != null) {
                samenamebooklist.get(newbook.getName()).add(newbook);
            }
        }
    }//增加一本书，如果在booklist 中已经存在书本，就需要检查增加的书是否存在于列表中，若存在完全相同的书（即isbn
     //相同，则只需要增加这本书的剩余数与总数，否则增加一本新书。如果已经初始化三个索引，需要加到三个不同索引中。
    public void deleteBook(String isbn) {
        for(int i = 0; i < booklist.size(); ++i) {
            if(booklist.get(i).getIsbn().equals(isbn)){
                int totalnum = booklist.get(i).getNumbers();
                int restnum = booklist.get(i).getRestnumber();
                if(restnum == 0)
                    System.out.println("book not return!");
                else {
                    booklist.get(i).setNumbers(totalnum - 1);
                    booklist.get(i).setRestnumber(restnum - 1);
                }
                if(totalnum - 1 == 0) {
                    booklist.remove(i);
                    if(writersbooklist.get(booklist.get(i).getWritername()) != null) {
                        List<Book> wsl = writersbooklist.get(booklist.get(i).getWritername());
                        int len = wsl.size();
                        for(int j = 0; j < len; ++j) {
                            if(wsl.get(i).getIsbn() == isbn ) wsl.remove(i);
                        }
                    }
                    if(publishersbooklist.get(booklist.get(i).getPublishername()) != null) {
                       List<Book> psl = publishersbooklist.get(booklist.get(i).getPublishername());
                       int len = psl.size();
                        for(int j = 0; j < len; ++j) {
                            if(psl.get(i).getIsbn() == isbn ) psl.remove(i);
                        }
                    }
                    if(samenamebooklist.get(booklist.get(i).getName()) != null) {
                       List<Book> sbl = samenamebooklist.get(booklist.get(i).getName());
                       int len = sbl.size();
                        for(int j = 0; j < len; ++j) {
                            if(sbl.get(i).getIsbn() == isbn ) sbl.remove(i);
                        }
                    }// wait for test the object if change.
                 }
                }
            }
        }
    }//删除一本书，需要先检查在图书列表中是否存在这本书的isbn，若存在，则检查剩余数是否大于等于一，
     //若剩余数大于等于1，则可以直接删除，如果总数变为0，则把这本书整体从图书列表中删除。当图书删除
     //时，如果索引已经初始化，需要将索引中的图书也删除。

