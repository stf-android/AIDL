package mvvm.com.git1.factory;

public class MailSender implements Sender {
    @Override
    public void Send() {
        System.out.print("发送个小计");
    }
}
