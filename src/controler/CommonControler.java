package controler;

import bean.Book;
import bean.BookPathTable;
import service.BookOperate;
import view.FindBookFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Created by ghoskno on 4/6/17.
 */
public class CommonControler {
    FindBookFrame findBookFrame = FindBookFrame.getInstance();
    BookOperate bookOperate = BookOperate.getInstance();
    private List<BookPathTable> curBookList = null;
    public CommonControler(List<BookPathTable> bookList) {
            curBookList = bookList;
    }
    private void checkList(List<BookPathTable> extraBookList){
        if(curBookList != null) {
            curBookList.retainAll(extraBookList);
            if(curBookList != null) {
                findBookFrame.showBookList(curBookList);
                addConditionLabel(findBookFrame.searchBook.getText());
            }
            else
                findBookFrame.findErrAlert("没有找到符合条件图书！");
        }
        else{
            findBookFrame.showBookList(extraBookList);
            curBookList = extraBookList;
            addConditionLabel(findBookFrame.searchBook.getText());
        }
    }
    private void addConditionLabel(String con){
        findBookFrame.ConditionsLabel.setText(findBookFrame.ConditionsLabel.getText() +"  \"  "+ con + "  \"  ");
        findBookFrame.ConditionsLabel.invalidate();
    }
    private void clearConditionLabel(){
        findBookFrame.ConditionsLabel.setText("");
    }
    public void findBook(){
        //处理查找书的按钮点击事件
        findBookFrame.findBookByAuthor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> extraBookList = bookOperate.getBookbyWriter(findBookFrame.searchBook.getText());
                if (extraBookList != null )
                    checkList(extraBookList);
                else
                    findBookFrame.findErrAlert("【作者：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        findBookFrame.findBookByName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> extraBookList = bookOperate.getBookbyName(findBookFrame.searchBook.getText());
                if (extraBookList != null)
                    checkList(extraBookList);
                else
                    findBookFrame.findErrAlert("【书名：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        findBookFrame.findBookByKind.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> extraBookList = bookOperate.getBookbyKind(findBookFrame.searchBook.getText());
                if (extraBookList != null)
                    checkList(extraBookList);
                else
                    findBookFrame.findErrAlert("【种类：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        findBookFrame.findBookByPublisher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<BookPathTable> extraBookList = bookOperate.getBookbyPublisher(findBookFrame.searchBook.getText());
                if (extraBookList != null)
                    checkList(extraBookList);
                else
                    findBookFrame.findErrAlert("【出版社：" + findBookFrame.searchBook.getText() + "】");
            }
        });
        findBookFrame.clearBookBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                curBookList = null;
                findBookFrame.showBookList(curBookList);
                clearConditionLabel();
            }
        });
    }
}
