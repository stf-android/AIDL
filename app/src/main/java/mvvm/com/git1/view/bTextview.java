package mvvm.com.git1.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by stf on 2020/3/19.
 */

public class bTextview extends View {
    Paint paint = new Paint();

    public bTextview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public bTextview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.GREEN);
//        paint.setAlpha(50);
        paint.setFilterBitmap(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(5);
        //画圆
//        canvas.drawCircle(100, 100, 80, paint);
//        // 修改透明度
//        canvas.drawARGB(100,100,100,150);
//        //画线
//        canvas.drawLine(10, 100, 40, 300, paint);
//        paint.setColor(Color.YELLOW);
//        canvas.drawLine(40, 300, 80, 200, paint);
//        //画半圆弧
        RectF rectf_head=new RectF(10, 10, 100, 100);//确定外切矩形范围
//        rectf_head.offset(100, 20);//使rectf_head所确定的矩形向右偏移100像素，向下偏移20像素
//        canvas.drawArc(rectf_head, -10, -160, false, paint);//绘制圆弧，不含圆心


        Path path2 = new Path(); //定义一条路径
        path2.moveTo(10, 10); //移动到 坐标20,10

        path2.lineTo(50, 60);

        path2.lineTo(200,80);

        path2.lineTo(10, 10);


        TextPaint textPaint = new TextPaint();
        path2.addRect(rectf_head , Path.Direction.CCW);
        paint.setTextSize(15);
        paint.setLinearText(true);

        canvas.drawPath(path2, paint);


//        canvas.drawColor(100);
    }
}
