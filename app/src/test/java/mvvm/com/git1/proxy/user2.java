package mvvm.com.git1.proxy;

import java.io.Serializable;

public class user2 implements Serializable {
    public String x;

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public void sepak(){
        System.out.print("我是大王");
    }
}
