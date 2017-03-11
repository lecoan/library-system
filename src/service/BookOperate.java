package service;

import bean.Book;

import java.io.*;
import java.util.*;

/**
 * Created by lecoan on 17-3-11.
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
                booklist = new LinkedList<>();
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
    }

    public void saveAllBook() {
        if(this.booklist != null) {
            try{
                File file = new File("book.txt");
                if(!file.exists()) {
                    file.createNewFile();
                }
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
    }

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
    }
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
    }
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
        }
    }

    public void deleteBook(String isbn) {
        for(int i = 0; i < booklist.size(); ++i) {
            if(booklist.get(i).getIsbn().equals(isbn)){
                int totalnum = booklist.get(i).getNumbers();
                int restnum = booklist.get(i).getRestnumber();
                if(restnum == 0) System.out.println("book not return!");
                else {
                    booklist.get(i).setNumbers(totalnum - 1);
                    booklist.get(i).setRestnumber(restnum - 1);
                }
                if(totalnum - 1 == 0) booklist.remove(i);
            }
        }
    }
}

