package listener;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lecoan on 2017/3/10.
 */
public class GlobalActionDetector {

    private static final long eventMask = AWTEvent.KEY_EVENT_MASK + AWTEvent.MOUSE_EVENT_MASK+AWTEvent.MOUSE_MOTION_EVENT_MASK;

    private static GlobalActionDetector instance;
    private MyTimer myTimer;

    private GlobalActionDetector(){}

    public static GlobalActionDetector getInstance() {
        if(instance == null) {
            instance = new GlobalActionDetector();
        }
        return instance;
    }
    public void startGlobalActionDetector() {
        myTimer = new MyTimer();
        myTimer.start();
        Toolkit.getDefaultToolkit()
                .addAWTEventListener(event -> {
                    myTimer.pause();
                },eventMask);
    }

    public int getDays() {
        return myTimer.getDays();
    }

    private class MyTimer {

        private Timer dayTimer;

        private Timer tempTimer;

        private int count = 0;

        private boolean isStart;

        private int days;

        public MyTimer() {
            tempTimer = new Timer();
            count = 0;
            days = 0;
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
                    System.out.println(days);
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
