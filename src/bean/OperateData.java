package bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by lecoan on 17-4-14.
 */
public class OperateData implements Serializable {
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
