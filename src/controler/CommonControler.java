package controler;

import bean.Book;
import bean.BookPathTable;
import service.BookOperate;
import view.AddPlaceHolder;
import view.ErrAlert;
import view.FindBookFrame;

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
    FindBookFrame findBookFrame = FindBookFrame.getInstance();  //获取搜索图书面板单例
    BookOperate bookOperate = BookOperate.getInstance();        //获取图书操作器单例
    ErrAlert errAlert = ErrAlert.getInstance();
    public List<BookPathTable> curBookList = null;              //设置当前查看图书列表为空
    public CommonControler() {

    }
    private void checkList(List<BookPathTable> extraBookList){
        //检查当前图书列表与新加条件查询所得图书列表集合关系
        if(curBookList != null) {   //当前列表不为空，显示列表集合交集
            curBookList.retainAll(extraBookList);
            if(curBookList != null) {
                findBookFrame.showBookList(curBookList);
                addConditionLabel(findBookFrame.searchBook.getText());
            }
            else
                errAlert.findErrAlert((int)(findBookFrame.findBookFrame.getLocation().getX()+200),(int)(findBookFrame.findBookFrame.getLocation().getY()+100),"没有找到符合条件图书！");
        }
        else{       //当前列表为空，显示新查询列表
            findBookFrame.showBookList(extraBookList);
            curBookList = extraBookList;
            addConditionLabel(findBookFrame.searchBook.getText());
        }
    }
    private void addConditionLabel(String con){     //修改查找条件显示
        findBookFrame.ConditionsLabel.setText(findBookFrame.ConditionsLabel.getText() +"  \"  "+ con + "  \"  ");
        findBookFrame.ConditionsLabel.invalidate();
    }
    private void clearConditionLabel(){     //清空当前所有查找条件
        findBookFrame.ConditionsLabel.setText("");
    }
    public void findBook(){     //查找图书面板各项按钮点击事件监听
        //处理通过作者查找书的按钮点击事件
        findBookFrame.findBookByAuthor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> extraBookList = bookOperate.getBookbyWriter(findBookFrame.searchBook.getText());
                if (extraBookList != null )
                    checkList(extraBookList);
                else
                    errAlert.findErrAlert((int)(findBookFrame.findBookFrame.getLocation().getX()+200),(int)(findBookFrame.findBookFrame.getLocation().getY()+100),"找不到：【作者：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        //处理通过书名查找书的按钮点击事件
        findBookFrame.findBookByName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> extraBookList = bookOperate.getBookbyName(findBookFrame.searchBook.getText());
                if (extraBookList != null)
                    checkList(extraBookList);
                else
                    errAlert.findErrAlert((int)(findBookFrame.findBookFrame.getLocation().getX()+200),(int)(findBookFrame.findBookFrame.getLocation().getY()+100),"找不到：【书名：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        //处理通过种类查找书的按钮点击事件
        findBookFrame.findBookByKind.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> extraBookList = bookOperate.getBookbyKind(findBookFrame.searchBook.getText());
                if (extraBookList != null)
                    checkList(extraBookList);
                else
                    errAlert.findErrAlert((int)(findBookFrame.findBookFrame.getLocation().getX()+200),(int)(findBookFrame.findBookFrame.getLocation().getY()+100),"找不到：【种类：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        //处理通过出版社查找书的按钮点击事件
        findBookFrame.findBookByPublisher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> extraBookList = bookOperate.getBookbyPublisher(findBookFrame.searchBook.getText());
                if (extraBookList != null)
                    checkList(extraBookList);
                else
                    errAlert.findErrAlert((int)(findBookFrame.findBookFrame.getLocation().getX()+200),(int)(findBookFrame.findBookFrame.getLocation().getY()+100),"找不到：【出版社：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        //处理通过清楚当前查找的按钮点击事件
        findBookFrame.clearBookBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                curBookList = null;
                findBookFrame.showBookList(null);
                findBookFrame.searchBook.setText("");
                placeholderHandle.addingPlaceholder(findBookFrame.searchBook,"请输入书名/书号/作者/出版社/类别进行搜索");
                clearConditionLabel();

            }
        });
        //处理通过查找图书面板退出事件
        findBookFrame.findBookFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                curBookList = null;
                findBookFrame.showBookList(null);
                clearConditionLabel();
            }
        });
    }
}
