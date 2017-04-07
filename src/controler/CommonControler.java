package controler;

import bean.BookPathTable;
import listener.GlobalActionDetector;
import service.BookOperate;
import view.AddPlaceHolder;
import view.ErrAlert;
import view.FindBookFrame;
import view.GetDate;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Created by ghoskno on 4/6/17.
 */
public class CommonControler {  //通用控制器
    AddPlaceHolder placeholderHandle = AddPlaceHolder.getInstance(); //获取占位字符串工具单例
//    FindBookFrame findBookFrame = null;  //获取搜索图书面板单例
    BookOperate bookOperate = BookOperate.getInstance();        //获取图书操作器单例
    ErrAlert errAlert = ErrAlert.getInstance();
    GlobalActionDetector globalActionDetector = GlobalActionDetector.getInstance();
    private GetDate getDate = new GetDate();
    public List<BookPathTable> curBookList = null;              //设置当前查看图书列表为空
    public void setFindBookFrame(FindBookFrame findFrame){

    }
    private volatile static CommonControler instance;
    private  CommonControler(){}

    public static CommonControler getInstance(){
        synchronized (CommonControler.class) {
            if(instance == null) {
                instance = new CommonControler();
            }
            return instance;
        }
    }

    private void checkList(List<BookPathTable> extraBookList,FindBookFrame findBookFrame){
        //检查当前图书列表与新加条件查询所得图书列表集合关系
        if(curBookList != null) {   //当前列表不为空，显示列表集合交集
            curBookList.retainAll(extraBookList);
            if(curBookList != null) {
                findBookFrame.showBookList(curBookList,findBookFrame.Frame);
                addConditionLabel(findBookFrame.searchBook.getText(),findBookFrame);
            }
            else
                errAlert.findErrAlert((int)(findBookFrame.Frame.getLocation().getX()+200),(int)(findBookFrame.Frame.getLocation().getY()+100),"没有找到符合条件图书！");
        }
        else{       //当前列表为空，显示新查询列表
            findBookFrame.showBookList(extraBookList,findBookFrame.Frame);
            curBookList = extraBookList;
            addConditionLabel(findBookFrame.searchBook.getText(),findBookFrame);
        }
    }
    private void addConditionLabel(String con,FindBookFrame findBookFrame){     //修改查找条件显示
        findBookFrame.ConditionsLabel.setText(findBookFrame.ConditionsLabel.getText() +"  \"  "+ con + "  \"  ");
        findBookFrame.ConditionsLabel.invalidate();
    }
    private void clearConditionLabel(FindBookFrame findBookFrame){     //清空当前所有查找条件
        findBookFrame.ConditionsLabel.setText("");
    }
    public void findBook(FindBookFrame findBookFrame){
        //查找图书面板各项按钮点击事件监听
        //处理通过作者查找书的按钮点击事件
        findBookFrame.findBookByAuthor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> extraBookList = bookOperate.getBookbyWriter(findBookFrame.searchBook.getText());
                if (extraBookList != null )
                    checkList(extraBookList,findBookFrame);
                else
                    errAlert.findErrAlert((int)(findBookFrame.Frame.getLocation().getX()+200),(int)(findBookFrame.Frame.getLocation().getY()+100),"找不到：【作者：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        //处理通过书名查找书的按钮点击事件
        findBookFrame.findBookByName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> extraBookList = bookOperate.getBookbyName(findBookFrame.searchBook.getText());
                if (extraBookList != null)
                    checkList(extraBookList,findBookFrame);
                else
                    errAlert.findErrAlert((int)(findBookFrame.Frame.getLocation().getX()+200),(int)(findBookFrame.Frame.getLocation().getY()+100),"找不到：【书名：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        //处理通过种类查找书的按钮点击事件
        findBookFrame.findBookByKind.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> extraBookList = bookOperate.getBookbyKind(findBookFrame.searchBook.getText());
                if (extraBookList != null)
                    checkList(extraBookList,findBookFrame);
                else
                    errAlert.findErrAlert((int)(findBookFrame.Frame.getLocation().getX()+200),(int)(findBookFrame.Frame.getLocation().getY()+100),"找不到：【种类：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        //处理通过出版社查找书的按钮点击事件
        findBookFrame.findBookByPublisher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> extraBookList = bookOperate.getBookbyPublisher(findBookFrame.searchBook.getText());
                if (extraBookList != null)
                    checkList(extraBookList,findBookFrame);
                else
                    errAlert.findErrAlert((int)(findBookFrame.Frame.getLocation().getX()+200),(int)(findBookFrame.Frame.getLocation().getY()+100),"找不到：【出版社：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        //处理通过清楚当前查找的按钮点击事件
        findBookFrame.clearBookBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                curBookList = null;
                findBookFrame.showBookList(null,findBookFrame.Frame);
                findBookFrame.searchBook.setText("");
                placeholderHandle.addingPlaceholder(findBookFrame.searchBook,"请输入书名/书号/作者/出版社/类别进行搜索");
                clearConditionLabel(findBookFrame);

            }
        });
        //处理通过查找图书面板退出事件
        findBookFrame.Frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                curBookList = null;
                findBookFrame.showBookList(null,findBookFrame.Frame);
                clearConditionLabel(findBookFrame);
            }
        });
    }

    public void setDate(){
        System.out.print(getDate.getDate(globalActionDetector.getDays()));

    }
}
