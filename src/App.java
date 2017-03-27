import view.LoginView;

/**
 * Created by lecoan on 17-3-1.
 */

public class App {
    public static void main(String args[]) {
        LoginView view = new LoginView();
        view.setLoginListener(new LoginView.LoginInfo() {
            @Override
            public void handleLoginInfo(String username, String password) {

            }
        });
    }
}
