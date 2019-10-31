package com.mytest.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.mytest.R;

public class CircleProgress extends View {
    public CircleProgress(Context context) {
        this(context, null);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress);

//        mRadius = typedArray.getDimensionPixelSize(R.styleable.CircleProgress_circleColor, 180);
//        mStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.CircleProgress_arcColor, 2);
//        mLongLine = typedArray.getDimensionPixelSize(R.styleable.CircleProgress_maxProgress, 20);
//        mShortLine = typedArray.getDimensionPixelSize(R.styleable.CircleProgress_progress, 10);

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        drawArc(canvas);
    }

    private void drawCircle(Canvas canvas) {

    }

    private void drawArc(Canvas canvas) {

    }
}
