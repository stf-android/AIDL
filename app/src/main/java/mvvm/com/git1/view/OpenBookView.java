package mvvm.com.git1.view;

import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Keep;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

//import androidx.annotation.Keep;

/**
 * Copyright © 2013-2020 Yeyu. All Rights Reserved.
 * Author: hasee
 * Email: 879689064@qq.com
 * Date: 2020/3/14
 * Time: 20:00
 * Desc:
 */
public class OpenBookView extends View {

    private final static int DURATION = 800;

    private Camera camera;
    private Paint pageBackgroundPaint;

    private float coverLeft;
    private float coverTop;
    private float coverWidth;
    private float coverHeight;
    private float scale;
    private float maxScaleWidth;
    private float maxScaleHeight;
    private float viewScaleWidth;
    private float viewScaleHeight;

    private int width;
    private int height;


    private Bitmap coverBitmap;
    private Rect bookRect;

    private boolean isOpen;

    public OpenBookView(Context context) {
        super(context);
    }

    public OpenBookView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        camera = new Camera();
        pageBackgroundPaint = new Paint();
        pageBackgroundPaint.setColor(Color.GREEN);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float newZ = -displayMetrics.density * 6;
        Log.i("newZ",newZ+"");
        camera.setLocation(0, 0, newZ);
        refreshData();
    }

    private void refreshData() {
        if (coverBitmap == null) {
            return;
        }

        viewScaleWidth = coverWidth / coverBitmap.getWidth();
        viewScaleHeight = coverHeight / coverBitmap.getHeight();
        Log.e("refreshData1", "refreshData: "+coverBitmap.getWidth() );
        Log.e("refreshData2", "refreshData: "+coverBitmap.getHeight());
        bookRect = new Rect(0, 0, coverBitmap.getWidth(), coverBitmap.getHeight());
        resetWidthHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (coverBitmap == null) {
            return;
        }
        canvas.save();

        canvas.translate(coverLeft - coverLeft * scale, coverTop - coverTop * scale);
        float scaleX = viewScaleWidth + (maxScaleWidth - viewScaleWidth) * scale;
        float scaleY = viewScaleHeight + (maxScaleHeight - viewScaleHeight) * scale;

        Log.e("scale", "scaleX:" + scaleX);
        Log.e("scale", "scaleY:" + scaleY);
        Log.e("scale", "scale:" + scale);
        Log.e("scale", "viewScaleHeight:" + viewScaleHeight);
        Log.e("scale", "maxScaleHeight:" + maxScaleHeight);
        Log.e("scale", "viewScaleHeight:" + viewScaleHeight);
        Log.e("scale", "sum " + ((maxScaleHeight - viewScaleHeight) * scale));
        canvas.scale(scaleX, scaleY);
        canvas.drawRect(bookRect, pageBackgroundPaint);
        camera.save();

        canvas.save();
        Log.e("coverHeight DY1", "" + (-coverHeight / 2));
        canvas.translate(0, -coverHeight / 2);
        camera.rotateY(-180 * scale);
        camera.applyToCanvas(canvas);
        Log.e("coverHeight DY2", "" + (coverHeight / 2));
        canvas.translate(0, coverHeight / 2);

        canvas.drawBitmap(coverBitmap, 0, 0, pageBackgroundPaint);
        camera.restore();
        canvas.restore();
        canvas.restore();


    }

    private void resetWidthHeight() {
        if (coverBitmap != null) {
            maxScaleWidth = (float) (Math.min(width, height)) / coverBitmap.getWidth();
            Log.e("scale", "width:" + width+"--height->"+height+"-coverBitmap--->"+coverBitmap.getHeight());
            Log.e("scale", "Math:" +Math.max(width, height));
            maxScaleHeight = (float) (Math.max(width, height)) / coverBitmap.getHeight();
        }
    }

    public void openAnimation(Bitmap coverBitmap, float left, float top, float width, float height, AnimatorListenerAdapter adapter) {
        this.coverBitmap = coverBitmap;
        coverWidth = width;
        coverHeight = height;
        coverLeft = left;
        coverTop = top;
        isOpen = true;
        refreshData();
        startAnim(adapter);
    }

    public void closeAnimation(Bitmap coverBitmap, float width, float height, AnimatorListenerAdapter adapter) {
        this.coverBitmap = coverBitmap;
        coverWidth = width;
        coverHeight = height;
        isOpen = false;
        refreshData();
        startAnim(adapter);
    }

    private void startAnim(AnimatorListenerAdapter adapter) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "scaleX", 0f, 1f);
        animator.setDuration(DURATION);
        animator.addListener(adapter);
        animator.start();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getHeight();
        height = getWidth();
        Log.e("width", width + "");
        Log.e("height", height + "");
    }

    @Keep
    public void setScaleX(float scale) {
        Log.e("setScaleX21", scale + "--->"+isOpen);
        if (isOpen) {
            this.scale = scale;
        } else {
            this.scale = 1f - scale;
        }
        postInvalidate();
    }

}
