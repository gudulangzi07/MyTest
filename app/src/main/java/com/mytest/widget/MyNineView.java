package com.mytest.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.mytest.R;

public class MyNineView extends View implements View.OnClickListener {

    private Context context;
    private Paint mPaint;
    private Bitmap mBitmap;
    private int defaultSize = 800;//默认大小
    private int center;//中心点
    private int bitmapWith;
    private int bitmapHeight;
    private int duration = 3000;//移动的时间（单位是毫秒）

    private CustomPoint customPoint1;
    private CustomPoint customPoint2;
    private CustomPoint customPoint3;
    private CustomPoint customPoint4;
    private CustomPoint customPoint5;
    private CustomPoint customPoint6;
    private CustomPoint customPoint7;
    private CustomPoint customPoint8;
    private CustomPoint customPoint9;

    private AnimatorSet animatorSet;

    public MyNineView(Context context) {
        this(context, null);
    }

    public MyNineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyNineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyNineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    //初始化数据
    private void init(Context context) {
        this.context = context;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.white_chess);

        bitmapWith = mBitmap.getWidth();
        bitmapHeight = mBitmap.getHeight();

        animatorSet = new AnimatorSet();
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

        customPoint1 = new CustomPoint(0, 0);
        customPoint2 = new CustomPoint(center - bitmapWith / 2, 0);
        customPoint3 = new CustomPoint(width - bitmapWith, 0);
        customPoint4 = new CustomPoint(0, center - bitmapHeight / 2);
        customPoint5 = new CustomPoint(center - bitmapWith / 2, center - bitmapHeight / 2);
        customPoint6 = new CustomPoint(width - bitmapWith, center - bitmapHeight / 2);
        customPoint7 = new CustomPoint(0, width - bitmapHeight);
        customPoint8 = new CustomPoint(center - bitmapWith / 2, width - bitmapHeight);
        customPoint9 = new CustomPoint(width - bitmapWith, width - bitmapHeight);

        setValueAnimator();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBitmap, customPoint1.getPointX(), customPoint1.getPointY(), mPaint);
        canvas.drawBitmap(mBitmap, customPoint2.getPointX(), customPoint2.getPointY(), mPaint);
        canvas.drawBitmap(mBitmap, customPoint3.getPointX(), customPoint3.getPointY(), mPaint);
        canvas.drawBitmap(mBitmap, customPoint4.getPointX(), customPoint4.getPointY(), mPaint);
        canvas.drawBitmap(mBitmap, customPoint5.getPointX(), customPoint5.getPointY(), mPaint);
        canvas.drawBitmap(mBitmap, customPoint6.getPointX(), customPoint6.getPointY(), mPaint);
        canvas.drawBitmap(mBitmap, customPoint7.getPointX(), customPoint7.getPointY(), mPaint);
        canvas.drawBitmap(mBitmap, customPoint8.getPointX(), customPoint8.getPointY(), mPaint);
        canvas.drawBitmap(mBitmap, customPoint9.getPointX(), customPoint9.getPointY(), mPaint);
    }



    public void startAnimator() {
        animatorSet.start();
    }

    private void setValueAnimator() {
        ValueAnimator valueAnimator1 = ValueAnimator.ofObject(new CustomPointEvaluator(), customPoint1, customPoint5);
        ValueAnimator valueAnimator2 = ValueAnimator.ofObject(new CustomPointEvaluator(), customPoint2, customPoint5);
        ValueAnimator valueAnimator3 = ValueAnimator.ofObject(new CustomPointEvaluator(), customPoint3, customPoint5);
        ValueAnimator valueAnimator4 = ValueAnimator.ofObject(new CustomPointEvaluator(), customPoint4, customPoint5);
        ValueAnimator valueAnimator6 = ValueAnimator.ofObject(new CustomPointEvaluator(), customPoint6, customPoint5);
        ValueAnimator valueAnimator7 = ValueAnimator.ofObject(new CustomPointEvaluator(), customPoint7, customPoint5);
        ValueAnimator valueAnimator8 = ValueAnimator.ofObject(new CustomPointEvaluator(), customPoint8, customPoint5);
        ValueAnimator valueAnimator9 = ValueAnimator.ofObject(new CustomPointEvaluator(), customPoint9, customPoint5);

        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                customPoint1 = (CustomPoint) animation.getAnimatedValue();
                invalidate();
            }
        });

        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                customPoint2 = (CustomPoint) animation.getAnimatedValue();
                invalidate();
            }
        });

        valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                customPoint3 = (CustomPoint) animation.getAnimatedValue();
                invalidate();
            }
        });

        valueAnimator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                customPoint4 = (CustomPoint) animation.getAnimatedValue();
                invalidate();
            }
        });

        valueAnimator6.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                customPoint6 = (CustomPoint) animation.getAnimatedValue();
                invalidate();
            }
        });

        valueAnimator7.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                customPoint7 = (CustomPoint) animation.getAnimatedValue();
                invalidate();
            }
        });

        valueAnimator8.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                customPoint8 = (CustomPoint) animation.getAnimatedValue();
                invalidate();
            }
        });

        valueAnimator9.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                customPoint9 = (CustomPoint) animation.getAnimatedValue();
                invalidate();
            }
        });

        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                Toast.makeText(context, "动画结束了", Toast.LENGTH_SHORT).show();
            }

        });

        animatorSet.setDuration(duration);
        animatorSet.playTogether(valueAnimator1, valueAnimator2, valueAnimator3, valueAnimator4, valueAnimator6, valueAnimator7, valueAnimator8, valueAnimator9);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
        }
    }
}
