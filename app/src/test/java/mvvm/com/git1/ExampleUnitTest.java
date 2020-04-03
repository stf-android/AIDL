package mvvm.com.git1;

import android.os.Environment;

import org.junit.Test;

import java.lang.reflect.Proxy;

import mvvm.com.git1.factory.SendFactory2;
import mvvm.com.git1.factory.SendFactory3;
import mvvm.com.git1.factory.Sender;
import mvvm.com.git1.proxy.Subject;
import mvvm.com.git1.proxy.SubjectImpI;
import mvvm.com.git1.proxy.SubjectProxy;
import mvvm.com.git1.proxy.user2;
import mvvm.com.git1.proxy.userProxt;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void add() {
        System.out.print(1 + 2);
    }

    // 静态代理
    @Test
    public void testUser2() {
        user2 user = new user2();
        user.setX("stff");
        userProxt userProxt = new userProxt();
        userProxt.speak(user);
    }

    //动态代理
    @Test
    public void testSub() {
        SubjectImpI subjectImpI = new SubjectImpI();
        SubjectProxy subjectProxy = new SubjectProxy(subjectImpI);
        Subject proxyInstance = (Subject) Proxy.newProxyInstance(subjectProxy.getClass().getClassLoader(), subjectImpI.getClass().getInterfaces(), subjectProxy);
        proxyInstance.hello("world");
    }

    // 工厂类设计模式
    @Test
    public void Factory() {
        SendFactory2 sendFactory2 = new SendFactory2();
        Sender sender = sendFactory2.produceMail();
        sender.Send();

        Sender sender1 = SendFactory3.produceMail();
        sender1.Send();

        boolean equals = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

    }

}