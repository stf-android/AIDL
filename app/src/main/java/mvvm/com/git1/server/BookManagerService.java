package mvvm.com.git1.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by stf on 2020/4/2.
 */

public class BookManagerService extends Service {
    private static  final  String TAG = "BMS";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
