package mvvm.com.git1;

import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by stf on 2020/3/31.
 */

public class MyServer extends Service {
    private static final String TAG = "MyServer";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: MyServer 启动了");

        return Service.START_STICKY;
    }
}
