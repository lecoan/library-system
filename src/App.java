import bean.Book;
import bean.Student;
import bean.Teacher;
import listener.GlobalActionDetector;
import view.StartUpView;

public class App {
        public static void main(String args[]) {
                GlobalActionDetector.getInstance().startGlobalActionDetector();
                new StartUpView();
        }
}
