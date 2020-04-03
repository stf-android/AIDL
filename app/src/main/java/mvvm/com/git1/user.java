package mvvm.com.git1;

import android.util.Log;

import java.io.Serializable;

public class user  implements Serializable {
    private String x;

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public void speak(){
        Log.i("stf","我是你爸爸");

    }
}
