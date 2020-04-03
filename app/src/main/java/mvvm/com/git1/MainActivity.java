package mvvm.com.git1;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView viewById = findViewById(R.id.test1);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });

        startService(new Intent(this, MyServer.class));


        UserManager.sUserId = 2;
        Log.i(TAG, "onCreate: sUserId" + UserManager.sUserId);
    }

    private void initTest() {
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        Hashtable<String, String> stringStringHashtable = new Hashtable<>();

        Object clone = stringIntegerHashMap.clone();

        String s = new String();

        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        ArrayList<String> strings = new ArrayList<>(1);
        strings.add("1");

        List<String> strings1 = new ArrayList<>();
        List<String> string2 = new LinkedList<>();


        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };

        MyHandler myHandler = new MyHandler(this);
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 100);

    }

    public void openBookListener(View view) {
        startActivity(new Intent(this, Main3Activity.class));
    }

    public void openBookListener2(View view) {
        startActivity(new Intent(this, Main5Activity.class));
    }

    public void intentMain6Act(View view) {
        startActivity(new Intent(this, Main6Activity.class));
    }

    private static class MyHandler extends Handler {
        private WeakReference<MainActivity> weakReference;

        public MyHandler(MainActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Main2Act 是否被启动过
                if (Main2Activity.isFlag) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Mian2没有被启动过", Toast.LENGTH_SHORT).show();
                }
            }
        }, 100);


    }
}
