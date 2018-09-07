package com.mytest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.mytest.R;

public class MoveActivity extends AppCompatActivity implements View.OnTouchListener {

    private Button button;
    private float downX;
    private float downY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);

        button = findViewById(R.id.button);

        button.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float xDistance = event.getX() - downX;
                float yDistance = event.getY() - downY;
                int l, r, t, b;
                if (Math.abs(xDistance) > 10 || Math.abs(yDistance) > 10) {
                    l = (int) (view.getLeft() + xDistance);
                    r = l + view.getWidth();
                    t = (int) (view.getTop() + yDistance);
                    b = t + view.getHeight();
                    if (l < 0) {
                        l = 0;
                        r = l + view.getWidth();
                    } else if (r > getScreenWidth(this)) {
                        r = getScreenWidth(this);
                        l = r - view.getWidth();
                    }
                    if (t < 0) {
                        t = 0;
                        b = t + view.getHeight();
                    } else if (b > getAvailableScreenHeight(this)) {
                        b = getAvailableScreenHeight(this);
                        t = b - view.getHeight();
                    }
                    view.layout(l, t, r, b);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }

    /**
     * 返回屏幕的宽度
     */
    public static int getScreenWidth(AppCompatActivity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 返回屏幕可用高度
     * 当显示了虚拟按键时，会自动减去虚拟按键高度
     */
    public static int getAvailableScreenHeight(AppCompatActivity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

}
