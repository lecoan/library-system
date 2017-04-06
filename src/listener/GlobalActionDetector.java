package listener;

import util.StorageHelper;

import java.awt.*;
import java.awt.event.AWTEventListener;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

/******************************************************************
 创建人: 杨翔
 日　期: 2017/3/10
 修改人:
 日　期:
 描　述: 监听全局的鼠标和键盘事件来进行日期的模拟
 版　本: v1.00 Copyright(c).
 ******************************************************************/
public class GlobalActionDetector {

    private static final long eventMask = AWTEvent.KEY_EVENT_MASK + AWTEvent.MOUSE_EVENT_MASK+AWTEvent.MOUSE_MOTION_EVENT_MASK;

    private volatile static GlobalActionDetector instance;
    private MyTimer myTimer;
    private List<Event> eventList;

    private GlobalActionDetector(){
        eventList = new ArrayList<>();
    }

    public void addEvent(Event event) {
        eventList.add(event);
    }

    /**
     * 单例模式
     * @return 对象实例
     */
    public static GlobalActionDetector getInstance() {
        synchronized (GlobalActionDetector.class) {
            if(instance == null) {
                instance = new GlobalActionDetector();
            }
            return instance;
        }
    }

    /**
     * 启动监听器
     */
    public void startGlobalActionDetector() {
        myTimer = new MyTimer();
        myTimer.start();
        Toolkit.getDefaultToolkit()
                .addAWTEventListener(event -> myTimer.pause(), eventMask);
    }

    public interface Event{
        void handle(int days);
    }

    /**
     * @return 模拟中经过的天数
     */
    public int getDays() {
        return myTimer.getDays();
    }

    /**
     * 对Timer封装进行计数
     */
    private class MyTimer {
        private Timer dayTimer;
        private Timer tempTimer;
        private int count = 0;
        private boolean isStart;
        private int days;

        public MyTimer() {
            tempTimer = new Timer();
            count = 0;

            Integer temp = StorageHelper.getInstance().getConfig("days");
            days = temp == null? 0:temp;
            tempTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    count++;
                    if(count >= 5 && !isStart) {
                        MyTimer.this.restart();
                        count = 0;
                        isStart = true;
                    }
                }
            },0, 100);
        }

        void start() {
            dayTimer = new Timer();
            isStart = true;
            dayTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    days++;
                    eventList.forEach(event -> {
                        event.handle(days);
                    });
                }
            },10*1000,10*1000);
        }

        void pause() {
            dayTimer.cancel();
            dayTimer.purge();
            count = 0;
            isStart = false;
        }

        void restart() {
            start();
        }

        int getDays() {
            return days;
        }
    }
}
