package mvvm.com.git1;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by stf on 2020/3/18.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.i("stf", "onActivityCreated->" + activity.getLocalClassName());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.i("stf", "onActivityStarted->" + activity.getLocalClassName());
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.i("stf", "onActivityResumed->" + activity.getLocalClassName());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.i("stf", "onActivityPaused->" + activity.getLocalClassName());
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.i("stf", "onActivityStopped->" + activity.getLocalClassName());
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.i("stf", "onActivitySaveInstanceState->" + activity.getLocalClassName());
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.i("stf", "onActivityDestroyed->" + activity.getLocalClassName());
            }
        });
    }
}
