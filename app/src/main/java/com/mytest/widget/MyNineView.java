package com.mytest.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;

import com.mytest.R;

public class MyNineView extends View {

    private Paint mPaint;
    private Bitmap mBitmap;
    private int defaultSize = 800;//默认大小
    private int center;//中心点
    private int bitmapWith;
    private int bitmapHeight;
    private int duration = 3;//移动的时间（单位是毫秒）
    private int tempDuration = 0;
    private float moveX;//每移动时间内移动的X轴距离
    private float moveY;//每移动时间内移动的Y轴距离

    private Matrix matrix;

    public MyNineView(Context context) {
        this(context, null);
    }

    public MyNineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyNineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyNineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    //初始化数据
    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.white_chess);

        bitmapWith = mBitmap.getWidth();
        bitmapHeight = mBitmap.getHeight();

        matrix = new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int width, height;
        if (wMode == MeasureSpec.EXACTLY) {
            width = wSize;
        } else {
            width = Math.min(wSize, defaultSize);
        }

        if (hMode == MeasureSpec.EXACTLY) {
            height = hSize;
        } else {
            height = Math.min(hSize, defaultSize);
        }
        center = width / 2;//中心点
        setMeasuredDimension(width, height);

        moveX = (center - bitmapWith / 2) / duration;
        moveY = (center - bitmapHeight / 2) / duration;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBitmap, 0 , 0, mPaint);
        canvas.drawBitmap(mBitmap, center - bitmapWith / 2, 0, mPaint);
        canvas.drawBitmap(mBitmap, 2 * center - bitmapWith, 0, mPaint);
        canvas.drawBitmap(mBitmap, 0, center - bitmapHeight / 2, mPaint);
        canvas.drawBitmap(mBitmap, center - bitmapWith / 2, center - bitmapHeight / 2, mPaint);
        canvas.drawBitmap(mBitmap, 2 * center - bitmapWith, center - bitmapHeight / 2, mPaint);
        canvas.drawBitmap(mBitmap, 0, 2 * center - bitmapHeight, mPaint);
        canvas.drawBitmap(mBitmap, center - bitmapWith / 2, 2 * center - bitmapHeight, mPaint);
        canvas.drawBitmap(mBitmap, 2 * center - bitmapWith, 2 * center - bitmapHeight, mPaint);
    }

    public void startAnimator(){
        postDelayed(new Runnable() {
            @Override
            public void run() {
                postInvalidate();
            }
        }, 1000);
    }
}
