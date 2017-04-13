import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Created by lecoan on 17-3-1.
 */

public class App {

    private final static String PROTOCOL = "smtp";

    private final static String HOST = "smtp.gmail.com";

    private final static String PORT = "587";

    private final static String IS_AUTH = "true";

    private final static String IS_ENABLED_DEBUG_MOD = "true";

    private static String from = "yangbupt0647@gmail.com";

    private static String to = "yangbupt0647@outlook.com";

    private static Properties props = null;

    static {
        props = new Properties();
        //props.setProperty("mail.transport.protocol", PROTOCOL);
        //props.setProperty("mail.smtp.host", HOST);
        props.setProperty("mail.smtp.port", PORT);
        props.setProperty("mail.smtp.auth", IS_AUTH);
        props.setProperty("mail.debug",IS_ENABLED_DEBUG_MOD);
        props.setProperty("mail.smtp.starttls.enable", "true");
    }

    public static void main(String args[]) throws Exception {
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage message = new MimeMessage(session);
        message.setSubject("test");
        message.setSentDate(new Date());
        message.addRecipients(Message.RecipientType.CC,from);
        message.addRecipients(Message.RecipientType.TO,to);
        message.setContent("<span style='color:red;'>html邮件测试...</span>","text/html;charset=gbk");
        message.saveChanges();
        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.gmail.com", "yangbupt0647@gmail.com", "qrS539Lglt");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}
