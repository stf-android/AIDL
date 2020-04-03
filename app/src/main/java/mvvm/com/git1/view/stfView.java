package mvvm.com.git1.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by stf on 2020/4/2.
 */

public class stfView extends View {
    private static final String TAG = "stfView";
    private GestureDetector gestureDetector;

    public stfView(Context context) {
        super(context);
        init();
    }


    public stfView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public stfView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure: ");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: ");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "onLayout: ");
        int scaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();//最小滑动距离

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 速度的检测
        VelocityTracker obtain = VelocityTracker.obtain();
        obtain.addMovement(event);// 当前华东的速度
        obtain.computeCurrentVelocity(1000); // 时间段  1秒钟
        float xVelocity = obtain.getXVelocity();
        float yVelocity = obtain.getYVelocity();
        //速度的计算公式  速度= （终点坐标 -起始坐标） /时间段

        obtain.clear();
        obtain.recycle();

        //手势检测

        boolean consume = gestureDetector.onTouchEvent(event);


        return super.onTouchEvent(event);
    }

    private void init() {
        Log.i(TAG, "init: ");
        // 手势的检测GestureDetector 单机 ，华东，长按 ，双击
        gestureDetector = new GestureDetector(new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                // 手指轻轻地膜屏幕的一瞬间，由一个ACTION_DOEMN出发

                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                // 手指轻轻触摸屏幕，尚未松开或者拖动，由一个ACTION_DOWN触发 ，
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) { //常用
                // // 手指轻轻触摸屏幕松开，伴随着1个MotionEvent ACTION_UP 而触发 ，这个是单击行为
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {//常用
                // 手指按下屏幕并拖动 ，由1个ACTION_DOWN ,多个 ACTION_MOVE 触发 ，这是拖动行为
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {//常用
                // 用户长按这屏幕不放开 ，长按
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {//常用
                //用户按下触摸 快读滑动后松开，由1个ACTION_DOWN ，多个ACTION_MOVE 和1个ACTION_UP 触发 ，快速滑动
                return false;
            }
        });

        gestureDetector.setIsLongpressEnabled(false);//解决长按屏幕后无法拖动的情况


    }

}
