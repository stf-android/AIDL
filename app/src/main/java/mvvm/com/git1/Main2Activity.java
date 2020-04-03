package mvvm.com.git1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnSeekCompleteListener, SeekBar.OnSeekBarChangeListener {

    private MediaPlayer mediaPlayer;
    private MediaPlayer mMediaPlayer;
    private boolean isInitFinish = false;
    private SurfaceView mSurfaceView;
    private TextView mPalyOrStopTv;
    private SurfaceHolder mSurfaceHolder;
    private static final String TAG = "Main2Activity";
    private SeekBar seekBar;
    private Timer timer;
    private TimerTask timerTask;
    public static Boolean isFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        isFlag = true;

        initView();
        initReady();
        initSurfaceviewStateListener();
    }

    private void initView() {
        mSurfaceView = findViewById(R.id.video_play_surfaceview);
        mPalyOrStopTv = findViewById(R.id.videoPlayTv);
        seekBar = findViewById(R.id.video_play_seekBar);
    }

    private void initSurfaceviewStateListener() {
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.i(TAG, "surfaceCreated: ");
                mMediaPlayer.setDisplay(holder);
                setMediaPlayer();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.i(TAG, "surfaceChanged: width=" + width + "height" + height);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        mPalyOrStopTv.setOnClickListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnInfoListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnSeekCompleteListener(this);
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        pausePlay();
    }

    public void onPlay(View view) {
        if (mediaPlayer == null) {
            ready();
        }
        mediaPlayer.start();//播放
    }

    public void onStop(View view) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void onPuse(View view) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    // 准备创建 mediaplay
    // 准备播放
    private void ready() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
//            Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "pager,mp3"));
            try {
                String resourceName = "android.resource://" + this.getPackageName() + "/" + R.raw.pager;
                mediaPlayer.setDataSource(this, Uri.parse(resourceName));
//                mediaPlayer.setDataSource(this, uri);
                mediaPlayer.prepare();
            } catch (Exception e) {
                Log.i("stf", "--e-->" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // 初始化 播放器
    private void initReady() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }
    }

    // 设置播放器
    private void setMediaPlayer() {
        try {
            String resourceName = "android.resource://" + this.getPackageName() + "/" + R.raw.a;
            Uri parse = Uri.parse(resourceName);
            if (mMediaPlayer != null) {
                mMediaPlayer.setDataSource(this, parse);
                mMediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);//缩放模式
                mMediaPlayer.setLooping(true);
                mMediaPlayer.prepareAsync();
            }
        } catch (Exception e) {
            e.getMessage();
            Log.i("stf", "--e-->" + e.getMessage());
        }
    }

    private void startPlay() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
            Log.i(TAG, "startPlay: ");
        }
    }

    private void pausePlay() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            Log.i(TAG, "pausePlay: ");
        }
    }

    private void stopPlay() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            Log.i(TAG, "stopPlay: ");
        }
    }

    private void seekTo(int time) {
        Log.i(TAG, "seekTo: " + time);
        mMediaPlayer.seekTo(time);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        if (timer != null) {
            timer.cancel();
            timer.purge();
            if (timerTask != null) {
                timerTask.cancel();
            }
        }

        isFlag = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.videoPlayTv:
                if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                    pausePlay();
                    mPalyOrStopTv.setText("播放");
//                    seekBar.setVisibility(View.VISIBLE);
                } else if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
                    startPlay();
                    mPalyOrStopTv.setText("暂停");
                }
                break;
        }
    }

    //发生错误时回调
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.i(TAG, "onError: " + what);
        return false;
    }


    //信息监听

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        //what 对应返回的值如下
//                public static final int MEDIA_INFO_UNKNOWN = 1;  媒体信息未知
//                public static final int MEDIA_INFO_VIDEO_TRACK_LAGGING = 700; 媒体信息视频跟踪滞后
//                public static final int MEDIA_INFO_VIDEO_RENDERING_START = 3; 媒体信息\视频渲染\开始
//                public static final int MEDIA_INFO_BUFFERING_START = 701; 媒体信息缓冲启动
//                public static final int MEDIA_INFO_BUFFERING_END = 702; 媒体信息缓冲结束
//                public static final int MEDIA_INFO_NETWORK_BANDWIDTH = 703; 媒体信息网络带宽（703）
//                public static final int MEDIA_INFO_BAD_INTERLEAVING = 800; 媒体-信息-坏-交错
//                public static final int MEDIA_INFO_NOT_SEEKABLE = 801; 媒体信息找不到
//                public static final int MEDIA_INFO_METADATA_UPDATE = 802; 媒体信息元数据更新
//                public static final int MEDIA_INFO_UNSUPPORTED_SUBTITLE = 901; 媒体信息不支持字幕
//                public static final int MEDIA_INFO_SUBTITLE_TIMED_OUT = 902; 媒体信息字幕超时
        Log.i(TAG, "onInfo: what" + what);
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.i(TAG, "onPrepared: ");
        mp.start();
        seekBar.setMax(mp.getDuration());
        try {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    seekBar.setProgress(mMediaPlayer.getCurrentPosition());
                }
            };
            timer.schedule(timerTask, 0, 150);
        } catch (Exception e) {
            e.getMessage();
        }
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.i(TAG, "onCompletion: ");
        timer.cancel();
        timerTask.cancel();
        timer.purge();
        seekBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        Log.i(TAG, "onSeekComplete: ");
        mp.start();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        Log.i(TAG, "onStopTrackingTouch: " + progress);
        if (mMediaPlayer != null) {

            mMediaPlayer.seekTo(progress);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "onNewIntent: ");
    }
}
