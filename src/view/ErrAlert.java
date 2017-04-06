package view;

import javax.swing.*;

/**
 * Created by ghoskno on 4/6/17.
 */
public class ErrAlert {
    private volatile static ErrAlert instance;
    private  ErrAlert(){
        initErrAlert();
    }

    public static ErrAlert getInstance(){
        synchronized (ErrAlert.class) {
            if(instance == null) {
                instance = new ErrAlert();
            }
            return instance;
        }
    }
    public JFrame errFrame = new JFrame("Err!");
    public JLabel errMsg = new JLabel();

    public void findErrAlert(int x,int y,String err){
        errMsg.setText(err);
        errFrame.setLocation(x,y);
        errFrame.setVisible(true);
    }
    private void initErrAlert(){
        errFrame.setSize(300,80);
        errFrame.setResizable(false);
        errFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        errMsg.setSize(200,80);
        //修改字体颜色
        Box errBox = Box.createHorizontalBox();
        errBox.add(Box.createHorizontalGlue());
        errBox.add(errMsg);
        errBox.add(Box.createHorizontalGlue());
        errFrame.getContentPane().add(errBox);
        errFrame.setVisible(false);
    }
}
