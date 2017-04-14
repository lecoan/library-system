package controler;

import bean.BookPathTable;
import listener.GlobalActionDetector;
import service.BookOperate;
import service.Log;
import util.StorageHelper;
import view.AddPlaceHolder;
import view.ErrAlert;
import view.FindBookFrame;
import view.GetDate;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
//    public List<BookPathTable> curBookList = null;              //设置当前查看图书列表为空
    public void setFindBookFrame(FindBookFrame findFrame){

    }
    private volatile static CommonControler instance;
    private  CommonControler(){
        StorageHelper.getInstance().addQuitEvent(new StorageHelper.Event() {
            @Override
            public void handle() {
                bookOperate.SaveData();
                Log.getInstance().Save();
            }
        });
    }

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
        if(findBookFrame.curBookList != null && findBookFrame.curBookList.size() != 0) {   //当前列表不为空，显示列表集合交集
            List<BookPathTable> tempList = new ArrayList<>();
            tempList.addAll(findBookFrame.curBookList);
            tempList.retainAll(extraBookList);
            if(tempList != null && tempList.size() != 0) {
                findBookFrame.curBookList.retainAll(extraBookList);
                findBookFrame.showBookList(findBookFrame.curBookList,findBookFrame.Frame);
                if(!Pattern.matches(".*[" + findBookFrame.searchBook.getText() + "]+.*",findBookFrame.ConditionsLabel.getText()))
                    addConditionLabel(findBookFrame.searchBook.getText(), findBookFrame);
            }
            else
                errAlert.findErrAlert((int)(findBookFrame.Frame.getLocation().getX()+200),(int)(findBookFrame.Frame.getLocation().getY()+100),"没有找到符合条件图书！");
        }
        else{       //当前列表为空，显示新查询列表
            findBookFrame.showBookList(extraBookList,findBookFrame.Frame);
            findBookFrame.curBookList = extraBookList;
            addConditionLabel(findBookFrame.searchBook.getText(),findBookFrame);
        }
    }
    private void addConditionLabel(String con,FindBookFrame findBookFrame){     //修改查找条件显示
        findBookFrame.ConditionsLabel.setText(findBookFrame.ConditionsLabel.getText() +"  \""+ con + "\"  ");
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
                if(findBookFrame.searchBook.getText().equals("请输入书名/书号/作者/出版社/类别进行搜索")){
                    errAlert.findErrAlert((int)(findBookFrame.Frame.getLocation().getX()+200),(int)(findBookFrame.Frame.getLocation().getY()+100),"请输入查询内容");
                    return;
                }
                List<BookPathTable> extraBookList = bookOperate.getBookbyWriter(findBookFrame.searchBook.getText());
                if (extraBookList != null && extraBookList.size() != 0)
                    checkList(extraBookList,findBookFrame);
                else
                    errAlert.findErrAlert((int)(findBookFrame.Frame.getLocation().getX()+200),(int)(findBookFrame.Frame.getLocation().getY()+100),"找不到：【作者：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        //处理通过书名查找书的按钮点击事件
        findBookFrame.findBookByName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(findBookFrame.searchBook.getText().equals("请输入书名/书号/作者/出版社/类别进行搜索")){
                    errAlert.findErrAlert((int)(findBookFrame.Frame.getLocation().getX()+200),(int)(findBookFrame.Frame.getLocation().getY()+100),"请输入查询内容");
                    return;
                }
                List<BookPathTable> extraBookList = bookOperate.getBookbyName(findBookFrame.searchBook.getText());
                if (extraBookList != null && extraBookList.size() != 0)
                    checkList(extraBookList,findBookFrame);
                else
                    errAlert.findErrAlert((int)(findBookFrame.Frame.getLocation().getX()+200),(int)(findBookFrame.Frame.getLocation().getY()+100),"找不到：【书名：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        //处理通过种类查找书的按钮点击事件
        findBookFrame.findBookByKind.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(findBookFrame.searchBook.getText().equals("请输入书名/书号/作者/出版社/类别进行搜索")){
                    errAlert.findErrAlert((int)(findBookFrame.Frame.getLocation().getX()+200),(int)(findBookFrame.Frame.getLocation().getY()+100),"请输入查询内容");
                    return;
                }
                List<BookPathTable> extraBookList = bookOperate.getBookbyKind(findBookFrame.searchBook.getText());
                if (extraBookList != null && extraBookList.size() != 0)
                    checkList(extraBookList,findBookFrame);
                else
                    errAlert.findErrAlert((int)(findBookFrame.Frame.getLocation().getX()+200),(int)(findBookFrame.Frame.getLocation().getY()+100),"找不到：【种类：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        //处理通过出版社查找书的按钮点击事件
        findBookFrame.findBookByPublisher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(findBookFrame.searchBook.getText().equals("请输入书名/书号/作者/出版社/类别进行搜索")){
                    errAlert.findErrAlert((int)(findBookFrame.Frame.getLocation().getX()+200),(int)(findBookFrame.Frame.getLocation().getY()+100),"请输入查询内容");
                    return;
                }
                List<BookPathTable> extraBookList = bookOperate.getBookbyPublisher(findBookFrame.searchBook.getText());
                if (extraBookList != null && extraBookList.size() != 0)
                    checkList(extraBookList,findBookFrame);
                else
                    errAlert.findErrAlert((int)(findBookFrame.Frame.getLocation().getX()+200),(int)(findBookFrame.Frame.getLocation().getY()+100),"找不到：【出版社：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        //处理通过清除当前查找的按钮点击事件
        findBookFrame.clearBookBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                findBookFrame.curBookList = null;
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
                findBookFrame.curBookList = null;
                findBookFrame.showBookList(null,findBookFrame.Frame);
                clearConditionLabel(findBookFrame);
            }
        });
    }
    public void clearFindBookFrame(FindBookFrame findBookFrame){
        findBookFrame.showBookList(null,findBookFrame.Frame);
        findBookFrame.curBookList = null;
        findBookFrame.searchBook.setText("");
        placeholderHandle.addingPlaceholder(findBookFrame.searchBook,"请输入书名/书号/作者/出版社/类别进行搜索");
        clearConditionLabel(findBookFrame);
    }
    public void setDate(){
        System.out.print(getDate.getDate(globalActionDetector.getDays()));
    }

}
