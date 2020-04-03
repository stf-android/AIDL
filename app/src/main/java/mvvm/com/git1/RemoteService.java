package mvvm.com.git1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by stf on 2020/4/2.
 * 服务端
 */

public class RemoteService extends Service {

    /***
     * CopyOnWriteArrayList这是一个ArrayList的线程安全的变体，其原理大概可以通俗的理解为:初始化的时候只有一个容器，
     * 很常一段时间，这个容器数据、数量等没有发生变化的时候，
     * 大家(多个线程)，都是读取(假设这段时间里只发生读取的操作)同一个容器中的数据，所以这样大家读到的数据都是唯一、一致、安全的，
     * 但是后来有人往里面增加了一个数据，这个时候CopyOnWriteArrayList 底层实现添加的原理是先copy出一个容器(可以简称副本)，
     * 再往新的容器里添加这个新的数据，最后把新的容器的引用地址赋值给了之前那个旧的的容器地址，
     * 但是在添加这个数据的期间，其他线程如果要去读取数据，仍然是读取到旧的容器里的数据。
     *
     * 在java.util.concurrent.atomic包下，有AtomicBoolean , AtomicInteger, AtomicLong, AtomicReference等类，
     * 它们的基本特性就是在多线程环境下，执行这些类实例包含的方法时，具有排他性，
     * 即当某个线程进入方法，执行其中的指令时，不会被其他线程打断，
     * 而别的线程就像自旋锁一样，一直等到该方法执行完成，才由JVM从等待队列中选择一个另一个线程进入。
     */
    private AtomicBoolean mIsServiceDestory = new AtomicBoolean(false); // 控制线程
    private CopyOnWriteArrayList<HelloMsg> mHelloList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewMsgArrivedListener> msgArrivedListeners = new RemoteCallbackList<>();

    IRemoteService.Stub stub = new IRemoteService.Stub() {
        @Override
        public HelloMsg sayHello() throws RemoteException {
            return new HelloMsg("来自服务端的消息了是1111", 1);
        }

        @Override
        public List<HelloMsg> getMsgList() throws RemoteException {
            Log.i("stf", "语言总共" + mHelloList.size() + "个");
            for (int i = 0; i < mHelloList.size(); i++) {
                HelloMsg helloMsg = mHelloList.get(i);
                Log.i("stf", "服务端语言详情--->" + helloMsg.getMsg() + "--->" + helloMsg.getPid());
            }
            return mHelloList;
        }

        @Override
        public void addMsg(HelloMsg msg) throws RemoteException {
            Log.i("stf", "服务端收到了增加的语言" + msg.getMsg() + "--->" + msg.getPid());
            mHelloList.add(msg);
        }

        @Override
        public void registerListener(IOnNewMsgArrivedListener listener) throws RemoteException {
            msgArrivedListeners.register(listener);
              // 下面两行仅仅是为了统计注册事件个数写的，后期可以删除掉
            Log.i("stf","-registerListener size-->"+msgArrivedListeners.beginBroadcast());
            msgArrivedListeners.finishBroadcast();
        }

        @Override
        public void unregisterListener(IOnNewMsgArrivedListener listener) throws RemoteException {
            msgArrivedListeners.unregister(listener);
            // 下面两行仅仅是为了统计注册事件个数写的，后期可以删除掉
            Log.i("stf","-unregisterListener size-->"+msgArrivedListeners.beginBroadcast());
            msgArrivedListeners.finishBroadcast();
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceDestory.set(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //开启子线程 创建新留言
        new Thread(new ServiceWorker()).start();
    }


    private class ServiceWorker implements Runnable {
        @Override
        public void run() {
            while (!mIsServiceDestory.get()) {
                try {
                    Thread.sleep(1000);

                    int helloId = mHelloList.size() + 1;
                    HelloMsg helloMsg = new HelloMsg("我来了，大哥" + helloId, helloId);
                    if (helloId == 10) {
                        mIsServiceDestory.set(true);
                    }
                    Log.i("stf", "服务端有新留言了:" + helloMsg.getMsg());
                    onMsgArrived(helloMsg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //通知用户 新留言来了
    private void onMsgArrived(HelloMsg helloMsg) {
        mHelloList.add(helloMsg); //收集所有留言

        final int i = msgArrivedListeners.beginBroadcast(); // 和finishBroadcast 配对使用
        for (int j = 0; j < i; j++) {
            IOnNewMsgArrivedListener broadcastItem = msgArrivedListeners.getBroadcastItem(j);
            if (broadcastItem != null) {
                try {
                    broadcastItem.OnNewMsgArrived(helloMsg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        msgArrivedListeners.finishBroadcast();
    }
}
