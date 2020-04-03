package mvvm.com.git1;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Random;

// 客户端
public class Main6Activity extends AppCompatActivity {
    private IRemoteService mRemoteService = null;
    private TextView mPidText;
    private MyHandler myHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        mPidText = (TextView) findViewById(R.id.my_pid_text_view);
        myHandler = new MyHandler(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 绑定远程服务
        Intent intent = new Intent(this, RemoteService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    // 客户端向服务端 建立 请求链接
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRemoteService = IRemoteService.Stub.asInterface(service);
            Toast.makeText(Main6Activity.this, "onServiceConnected ", Toast.LENGTH_SHORT).show();
            try {
                service.linkToDeath(deathRecipient, 0); // 给远程服务设置死亡代理类
                mRemoteService.registerListener(msgArrivedListener);// 注册服务端有语言时 更新事件
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteService = null;
            Toast.makeText(Main6Activity.this, "onServiceDisconnected ", Toast.LENGTH_SHORT).show();
        }
    };

    // 当绑定的service异常断开连接后，自动执行此方法
    IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mRemoteService == null) {
                toastMsg();
                return;
            }

            //解除死亡通知，如果Binder死亡了，不会在触发binderDied方法
            mRemoteService.asBinder().unlinkToDeath(deathRecipient, 0);
            // 重新绑定一次
            Intent intent = new Intent(Main6Activity.this, RemoteService.class);
            bindService(intent, mConnection, BIND_AUTO_CREATE);
        }
    };
    //创建 新语言来时的监听
    private IOnNewMsgArrivedListener msgArrivedListener = new IOnNewMsgArrivedListener.Stub() {
        @Override
        public void OnNewMsgArrived(HelloMsg msg) throws RemoteException {
            Message message = myHandler.obtainMessage(1, msg);
            message.obj = msg.getMsg();
            message.arg1 = msg.getPid();
            message.sendToTarget();

        }
    };
    // 创建静态的Handler 防止内存泄漏
    static class MyHandler extends Handler {
        WeakReference<Main6Activity> outerClass;

        MyHandler(Main6Activity activity) {
            outerClass = new WeakReference<Main6Activity>(activity);
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            Main6Activity main6Activity = outerClass.get();
            switch (msg.what) {
                case 1: {
                    //使用theClass访问外部类成员和方法
                    Log.i("stf", "客户端收到了服务的新留言 :" + msg.obj + "-->" + msg.arg1);
                    break;
                }
                default: {
                    Log.w("stf", "未知的Handler Message:" + msg.what);
                    super.handleMessage(msg);
                }
            }

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isBinderAlive()) {
            return;
        }

        try {
            Log.i("stf", "unregister lisenter :" + msgArrivedListener);
            mRemoteService.unregisterListener(msgArrivedListener);
            // 防止 服务端 链接泄漏 解除绑定
            unbindService(mConnection);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    // 增加语言内容
    public void say_add_button(View view) {
        if (!isBinderAlive()) {
            toastMsg();
            return;
        }

        try {
            int i = new Random().nextInt(100);
            HelloMsg helloMsg = new HelloMsg("羊皮卷" + i, i);
            mRemoteService.addMsg(helloMsg);
            mPidText.setText(helloMsg.getMsg() + "-->" + helloMsg.getPid());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // 获取服务端语言内容
    public void say_get_button(View view) {
        if (!isBinderAlive()) {
            toastMsg();
            return;
        }

        try {
            List<HelloMsg> helloList = mRemoteService.getMsgList();
            Log.i("stf", "服务端返回语言总数为：" + helloList.size());
            for (int i = 0; i < helloList.size(); i++) {
                HelloMsg helloMsg = helloList.get(i);
                Log.i("stf", "客户端收到服务端返回语言详情--->" + helloMsg.getMsg() + "--->" + helloMsg.getPid());
            }
            mPidText.setText("服务端返回语言总数为：" + helloList.size());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // 每次调用服务端之前 ，判断bind 是否是否存活
    public Boolean isBinderAlive() {
        return mRemoteService != null && mRemoteService.asBinder().isBinderAlive();
    }

    public void toastMsg() {
        Toast.makeText(this, "服务端异常，请稍后重试", Toast.LENGTH_SHORT).show();
    }

}
