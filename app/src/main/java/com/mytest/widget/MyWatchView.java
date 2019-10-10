package com.mytest.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.mytest.R;

public class MyWatchView extends View {

    private Paint mPaint;
    private int mRadius = 180;//默认的半径
    private int mStrokeWidth = 2;
    private int mLongLine = 20;//长线
    private int mShortLine = 10;//短线

    private float hourDegrees = 0;
    private float minuteDegrees = 0;
    private float secondDegrees = 0;

    public MyWatchView(Context context) {
        this(context, null);
    }

    public MyWatchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWatchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyWatchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WatchStyle);

        mRadius = typedArray.getDimensionPixelSize(R.styleable.WatchStyle_mWatchRadius, 180);
        mStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.WatchStyle_mWatchStrokeWidth, 2);
        mLongLine = typedArray.getDimensionPixelSize(R.styleable.WatchStyle_mWatchLongLine, 20);
        mShortLine = typedArray.getDimensionPixelSize(R.styleable.WatchStyle_mWatchShortLine, 10);

        typedArray.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setStrokeWidth(mStrokeWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = widthMeasureSpec;
        int height = heightMeasureSpec;

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            width = 2 * (mRadius + mStrokeWidth);
            height = 2 * (mRadius + mStrokeWidth);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            width = heightSpecSize;
            height = heightSpecSize;
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            width = widthSpecSize;
            height = widthSpecSize;
        }

        mRadius = Math.min(width, height) / 2 - mStrokeWidth;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawWatch(canvas);
        drawHours(canvas);
        drawMinutes(canvas);
        drawSeconds(canvas);
    }

    private void drawWatch(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        //圆心坐标
        int cx = width / 2;
        int cy = height / 2;

        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(cx, cy, 2, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(cx, cy, mRadius, mPaint);

        for (int angle = 0; angle < 360; angle += 6) {
            if (angle % 30 == 0) {
                canvas.drawLine(cx, cy - mRadius, cx, cy - mRadius + mLongLine, mPaint);
                mPaint.setTextSize(22);
                if (angle / 30 == 0) {
                    canvas.drawText("12", cx - mPaint.measureText("12") / 2, cy - mRadius + mLongLine + mPaint.measureText("12"), mPaint);
                } else {
                    canvas.drawText(String.valueOf(angle / 30), cx - mPaint.measureText(String.valueOf(angle / 30)) / 2, cy - mRadius + mLongLine + mPaint.measureText("12"), mPaint);
                }
            } else {
                canvas.drawLine(cx, cy - mRadius, cx, cy - mRadius + mShortLine, mPaint);
            }
            canvas.rotate(6, cx, cy);
        }

    }

    private void drawHours(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        //圆心坐标
        int cx = width / 2;
        int cy = height / 2;

        canvas.drawLine(cx, cy, cx + Float.parseFloat(String.valueOf(Math.sin(Math.toRadians(hourDegrees)) * (mRadius * 1 / 3))), cy - Float.parseFloat(String.valueOf(Math.cos(Math.toRadians(hourDegrees)) * (mRadius * 1 / 3))), mPaint);
    }

    private void drawMinutes(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        //圆心坐标
        int cx = width / 2;
        int cy = height / 2;

        canvas.drawLine(cx, cy, cx + Float.parseFloat(String.valueOf(Math.sin(Math.toRadians(minuteDegrees)) * (mRadius * 1 / 2))), cy - Float.parseFloat(String.valueOf(Math.cos(Math.toRadians(minuteDegrees)) * (mRadius * 1 / 2))), mPaint);
    }

    private void drawSeconds(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        //圆心坐标
        int cx = width / 2;
        int cy = height / 2;

        canvas.drawLine(cx, cy, cx + Float.parseFloat(String.valueOf(Math.sin(Math.toRadians(secondDegrees)) * (mRadius * 2 / 3))), cy - Float.parseFloat(String.valueOf(Math.cos(Math.toRadians(secondDegrees)) * (mRadius * 2 / 3))), mPaint);
    }

    public void setHourDegrees(float hourDegrees) {
        this.hourDegrees = hourDegrees;
        invalidate();
    }

    public void setMinuteDegrees(float minDegrees) {
        this.minuteDegrees = minDegrees;
        invalidate();
    }

    public void setSecondDegrees(float secondDegrees) {
        this.secondDegrees = secondDegrees;
        invalidate();
    }
}
