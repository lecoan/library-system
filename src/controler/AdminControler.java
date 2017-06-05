package controler;

import bean.Book;
import bean.Student;
import bean.Teacher;
import constance.CustomerConstance;
import listener.GlobalActionDetector;
import service.BookOperate;
import service.CustomerService;
import service.Log;
import view.AddPlaceHolder;
import view.AdminView;
import view.ErrAlert;

import java.awt.event.*;
import java.util.regex.Pattern;

/**
 * Created by ghoskno on 3/29/17.
 */
public class AdminControler {
    Log log = Log.getInstance();
    BookOperate bookOperate = BookOperate.getInstance();
    CustomerService customerService = CustomerService.getInstance();
    ErrAlert errAlert = ErrAlert.getInstance();
    AddPlaceHolder placeHolder = AddPlaceHolder.getInstance();
    CommonControler commonControler = CommonControler.getInstance();
//    AdminView adminPanel = null;

//    Book curBookItem = null;    //正在查看的图书

    private volatile static AdminControler instance;

    public static AdminControler getInstance(){
        synchronized (AdminControler.class) {
            if(instance == null) {
                instance = new AdminControler();
            }
            return instance;
        }
    }
    private AdminControler() {   //初始化管理员界面
//        initAdminView();
    }
    private void showBookItem(Book bookItem,AdminView adminPanel){   //显示图书信息
        //显示搜索到的单本书
        adminPanel.findBookFrame.curBookItem = bookItem;
        adminPanel.showBookInfoFrame(bookItem,bookOperate.getBookpathtable(bookItem.getIsbn()));
    }
    private void ModifyBook(AdminView adminPanel){  //修改图书
        //监听确认添加/修改图书按钮事件
        //校验信息后调用BookOperate方法添加书
        String[] bookInfo = adminPanel.submitBook();
        if(bookInfo[0].equals("请输入书名") ||  bookInfo[0].length() == 0){
            errAlert.findErrAlert((int)(adminPanel.modifyBookFrame.getLocation().getX() + 100),(int)(adminPanel.modifyBookFrame.getLocation().getY() + 100),"图书书名不能为空！");
            return;
        }
        if(bookInfo[1].equals("请输入出版社名") ||  bookInfo[1].length() == 0){
            errAlert.findErrAlert((int)(adminPanel.modifyBookFrame.getLocation().getX() + 100),(int)(adminPanel.modifyBookFrame.getLocation().getY() + 100),"出版社名不能为空！");
            return;
        }
        if(bookInfo[2].equals("请输入作者") ||  bookInfo[2].length() == 0){
            errAlert.findErrAlert((int)(adminPanel.modifyBookFrame.getLocation().getX() + 100),(int)(adminPanel.modifyBookFrame.getLocation().getY() + 100),"作者不能为空！");
            return;
        }
        if(bookInfo[3].equals("请输入种类") ||  bookInfo[3].length() == 0){
            errAlert.findErrAlert((int)(adminPanel.modifyBookFrame.getLocation().getX() + 100),(int)(adminPanel.modifyBookFrame.getLocation().getY() + 100),"种类不能为空！");
            return;
        }
        if(bookInfo[4].equals("请输入数量") ||  bookInfo[4].length() == 0 || !Pattern.matches("^[1-9][0-9]*$",bookInfo[4])){
            errAlert.findErrAlert((int)(adminPanel.modifyBookFrame.getLocation().getX() + 100),(int)(adminPanel.modifyBookFrame.getLocation().getY() + 100),"请输入合法数量！");
            return;
        }

        //生成新的Book对象
        Book newBook = new Book();
        newBook.setName(bookInfo[0]);
        newBook.setPublishername(bookInfo[1]);
        newBook.setWritername(bookInfo[2]);
        newBook.setIntroduction(bookInfo[5]);
        newBook.setKind(bookInfo[3]);
        newBook.setBoughttime();
        newBook.setIsbn();
        /*当前查看图书对象不为空，则为更新操作，删除原有图书对象
        否则为添加新图书
         */
        if(adminPanel.findBookFrame.curBookItem != null) {
//            int borrowNum = BookOperate.getInstance().getBookpathtable(adminPanel.findBookFrame.curBookItem.getIsbn()).getTotalnum() - BookOperate.getInstance().getBookpathtable(adminPanel.findBookFrame.curBookItem.getIsbn()).getRestnum();
//            bookInfo[4] = (new Integer(bookInfo[4])<borrowNum)?(""+borrowNum):bookInfo[4];
            bookOperate.deleteBook(adminPanel.findBookFrame.curBookItem.getIsbn());
            Log.getInstance().CreateLog("admin",6,"修改图书 " + newBook.getIsbn());
            errAlert.findErrAlert((int)(adminPanel.modifyBookFrame.getLocation().getX() + 100),(int)(adminPanel.modifyBookFrame.getLocation().getY() + 100),"成功修改图书：" + newBook.getName());
        }
        else{
            Log.getInstance().CreateLog("admin",2,"添加图书 " + newBook.getIsbn());
            errAlert.findErrAlert((int)(adminPanel.modifyBookFrame.getLocation().getX() + 100),(int)(adminPanel.modifyBookFrame.getLocation().getY() + 100),"成功添加新书：" + newBook.getName());
        }
        bookOperate.addBook(newBook,new Integer(bookInfo[4]));
        adminPanel.modifyBookFrame.dispose();
        adminPanel.bookInfoFrame.dispose();
        adminPanel.findBookFrame.curBookItem = null;
        commonControler.clearFindBookFrame(adminPanel.findBookFrame);
    }
    private void findUser(AdminView adminPanel){
        System.out.print(adminPanel.searchUserField.getText());
        adminPanel.curCustomer =customerService.getCustomerById(adminPanel.searchUserField.getText());
        if(adminPanel.curCustomer == null){
            errAlert.findErrAlert((int)adminPanel.adminFrame.getLocation().getX()+50,(int)adminPanel.adminFrame.getLocation().getY() + 100,"找不到用户：" + adminPanel.searchUserField.getText());
        }
        else{
            if("student".equals(adminPanel.curCustomer.getType() )){
                adminPanel.curCustomer = (Student)adminPanel.curCustomer;
                adminPanel.userStuNum.setText(adminPanel.curCustomer.getId());
                adminPanel.userCollege.setText(((Student) adminPanel.curCustomer).getColleage());
                adminPanel.userName.setText(adminPanel.curCustomer.getUsername());
                adminPanel.userMoney.setText(""+adminPanel.curCustomer.getMoney());
                adminPanel.userDelay.setText(""+customerService.getDelayedTimes(adminPanel.curCustomer));
                adminPanel.userLimit.setText(""+adminPanel.curCustomer.getMaxNumForRent());
                adminPanel.userStatus.setText(adminPanel.curCustomer.isFreezed()?"冻结":"正常");
            }
            else{
                adminPanel.curCustomer = (Teacher)adminPanel.curCustomer;
                adminPanel.userStuNum.setText(adminPanel.curCustomer.getId());
                adminPanel.userName.setText(adminPanel.curCustomer.getUsername());
                adminPanel.userMoney.setText(""+adminPanel.curCustomer.getMoney());
                adminPanel.userDelay.setText(""+customerService.getDelayedTimes(adminPanel.curCustomer));
                adminPanel.userLimit.setText(""+adminPanel.curCustomer.getMaxNumForRent());
                adminPanel.userStatus.setText(adminPanel.curCustomer.isFreezed()?"冻结":"正常");
            }
            if(adminPanel.curCustomer.isFreezed())
                adminPanel.unfreezeBtn.setEnabled(true);
            adminPanel.changeLimitBtn.setEnabled(true);
            adminPanel.lookBookListBtn.setEnabled(true);
        }
    }
    public void initAdminView(){
        AdminView adminPanel = new AdminView();
        commonControler.findBook(adminPanel.findBookFrame);     //初始化查找图书界面
        //处理通过Isbn查找图书按钮点击事件
        adminPanel.findBookFrame.findBookByIsbn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(adminPanel.findBookFrame.searchBook.getText().equals("请输入书名/书号/作者/出版社/类别进行搜索")){
                    errAlert.findErrAlert((int)(adminPanel.findBookFrame.Frame.getLocation().getX()+200),(int)(adminPanel.findBookFrame.Frame.getLocation().getY()+100),"请输入查询内容");
                    return;
                }
                Book bookItem = bookOperate.getBookbyIsbn(adminPanel.findBookFrame.searchBook.getText());
                if (bookItem != null) { //找到图书
                    adminPanel.findBookFrame.curBookList = null;
                    showBookItem(bookItem,adminPanel);
                }
                else
                    errAlert.findErrAlert((int)(adminPanel.findBookFrame.Frame.getLocation().getX()+200),(int)(adminPanel.findBookFrame.Frame.getLocation().getY()+100),"找不到：【ISBN ： " + adminPanel.findBookFrame.searchBook.getText() + "】");
            }
        });
        //添加图书界面关闭时保存添加图书信息
//        adminPanel.modifyBookFrame.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                bookOperate.SaveData();
//            }
//        });
        //点击查找图书按钮，显示查找图书界面
        adminPanel.findBookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //查找图书按钮点击
                adminPanel.findBookFrame.showFindBookField();
            }
        });
        adminPanel.adminFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                adminPanel.destroyAdminView();
            }
        });
        //双击图书列表中某行时，显示图书详细信息
        adminPanel.findBookFrame.bookListTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    showBookItem(bookOperate.getBookbyIsbn(adminPanel.findBookFrame.curBookList.get(adminPanel.findBookFrame.bookListTable.getSelectedRow()).getIsbn()),adminPanel);
//                    showBookItem(Frame.bookListTable.getSelectedRow());
                }
            }
        });
        //点击更新图书按钮，调用添加图书界面显示修改界面
        adminPanel.bookUpdateBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!adminPanel.bookDeleBtn.isEnabled())
                    return;
                adminPanel.showModifyBookField(adminPanel.findBookFrame.curBookItem,bookOperate.getBookpathtable(adminPanel.findBookFrame.curBookItem.getIsbn()));
            }
        });
        //点击删除图书界面,修改当前查看图书
        adminPanel.bookDeleBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!adminPanel.bookDeleBtn.isEnabled())
                    return;
                bookOperate.deleteBook(adminPanel.findBookFrame.curBookItem.getIsbn());
                Log.getInstance().CreateLog("admin",3,"删除图书 " + adminPanel.findBookFrame.curBookItem.getIsbn());
                errAlert.findErrAlert((int)(adminPanel.bookInfoFrame.getLocation().getX() + 100),(int)(adminPanel.bookInfoFrame.getLocation().getY() + 100),"成功删除图书：" + adminPanel.findBookFrame.curBookItem.getName());
                adminPanel.bookInfoFrame.dispose();
                adminPanel.findBookFrame.curBookItem = null;
                commonControler.clearFindBookFrame(adminPanel.findBookFrame);
            }
        });
        //点击添加图书按钮，显示添加图书界面
        adminPanel.addBookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //添加图书按钮点击
                adminPanel.showModifyBookField(null,null);
            }
        });
        //点击查找用户按钮
        adminPanel.searchUserBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //搜索用户按钮点击
                findUser(adminPanel);
            }
        });
        //点击修改/添加图书按钮
        adminPanel.modifyBookBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ModifyBook(adminPanel);
            }
        });
        //点击查看图书的借阅历史
        adminPanel.lookBorrowHistory.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                adminPanel.showBookBorrowFrame(adminPanel.findBookFrame.curBookItem);
            }
        });
        //点击查看用户借书情况
        adminPanel.lookBookListBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(adminPanel.lookBookListBtn.isEnabled()){
                    //查看用户借书情况
                    adminPanel.showUserBookListFrame();
                }
            }
        });
        //点击解冻用户
        adminPanel.unfreezeBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(adminPanel.unfreezeBtn.isEnabled()){
                    adminPanel.curCustomer.setFreezed(false);
                    Log.getInstance().CreateLog("admin",1,"解冻用户 " + adminPanel.curCustomer.getUsername());
                    errAlert.findErrAlert((int)adminPanel.adminFrame.getLocation().getX()+50,(int)adminPanel.adminFrame.getLocation().getY() + 100,"成功解冻用户：" +adminPanel.curCustomer.getUsername());
                }
            }
        });
        //点击修改用户权限
        adminPanel.changeLimitBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!Pattern.matches("^[1-9][0-9]*$",adminPanel.userLimit.getText())){
                    errAlert.findErrAlert((int)adminPanel.adminFrame.getLocation().getX()+50,(int)adminPanel.adminFrame.getLocation().getY() + 100,"输入借书权限值非法");
                    return;
                }
                int limit = new Integer(adminPanel.userLimit.getText());
                if(limit>0&&limit< CustomerConstance.MAX_RENT_BOOK_NUM){
                    adminPanel.curCustomer.setMaxNumForRent(limit);
                    customerService.updateCustomer(adminPanel.curCustomer);
                    Log.getInstance().CreateLog("admin",7,"修改用户 " + adminPanel.curCustomer.getUsername() + " 权限为 " + adminPanel.userLimit.getText() + "本");
                    errAlert.findErrAlert((int)adminPanel.adminFrame.getLocation().getX()+50,(int)adminPanel.adminFrame.getLocation().getY() + 100,"成功修改用户：" +adminPanel.curCustomer.getUsername() + "权限");
                }
                else
                    errAlert.findErrAlert((int)adminPanel.adminFrame.getLocation().getX()+50,(int)adminPanel.adminFrame.getLocation().getY() + 100,"输入借书权限值非法,仅可为0~" + CustomerConstance.MAX_RENT_BOOK_NUM + "本！");
            }
        });
        //点击退出按钮
        adminPanel.signOutBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                adminPanel.adminFrame.dispose();
                adminPanel.destroyAdminView();
            }
        });
        adminPanel.visitLogBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                adminPanel.initLogPanel();
            }
        });
        GlobalActionDetector.getInstance().addEvent(new GlobalActionDetector.Event() {
            @Override
            public void handle(int days) {
                adminPanel.refreshAdminView();
            }
        });
    }
    public static void main(String[] args){
        AdminControler test = new AdminControler();
    }

}
