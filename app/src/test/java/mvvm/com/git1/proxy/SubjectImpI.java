package mvvm.com.git1.proxy;

public class SubjectImpI implements Subject {
    @Override
    public void hello(String param) {
        System.out.print("hello" + param);
    }
}
