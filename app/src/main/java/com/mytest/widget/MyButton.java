package com.mytest.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Button;

import com.mytest.R;

/**
 * @ClassName: MyButton
 * @author: 张京伟
 * @date: 2016/10/26 14:57
 * @Description: 自定义的按钮
 * @version: 1.0
 */
public class MyButton extends Button {

    private int xRadius;//默认的圆角宽度
    private int yRadius;//默认的圆角高度
    private int myButtonBackgroud;//按钮的北京颜色
    private int myButtonTextSize;//按钮上的字体大小
    private int myButtonTextColor;//按钮上的字体颜色

    private Paint paint;

    public MyButton(Context context) {
        this(context, null);
    }

    public MyButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @SuppressLint("NewApi")
    public MyButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        paint = new Paint();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyButton);

        xRadius = typedArray.getDimensionPixelSize(R.styleable.MyButton_myButtonXRadius, 0);
        yRadius = typedArray.getDimensionPixelSize(R.styleable.MyButton_myButtonYRadius, 0);
        myButtonBackgroud = typedArray.getColor(R.styleable.MyButton_myButtonBackgroud, context.getColor(R.color.color_myButtonBackgroudDefault));
        myButtonTextColor = typedArray.getColor(R.styleable.MyButton_myButtonTextColor, context.getColor(R.color.color_myButtonTextColorDefault));
        myButtonTextSize = typedArray.getDimensionPixelSize(R.styleable.MyButton_myButtonTextSize, context.getResources().getDimensionPixelSize(R.dimen.myButtonTextSize));

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        getWidth();
//        getHeight();
//        getLeft();
//        getRight();
//
//        RectF rectF = new RectF();
//        rectF.set();
//        canvas.drawRect();
    }
}