package com.mytest.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
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

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyButton);
    }
}
