import listener.GlobalActionDetector;
import view.StartUpView;

/**
 * Created by lecoan on 17-3-1.
 */

public class App {

    public static void main(String args[]) {
        GlobalActionDetector.getInstance().startGlobalActionDetector();
        new StartUpView();
    }
}
