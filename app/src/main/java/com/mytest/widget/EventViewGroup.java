package com.mytest.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class EventViewGroup extends RelativeLayout {
    private static final String TAG = EventViewGroup.class.getSimpleName();


    public EventViewGroup(Context context) {
        this(context, null);
    }

    public EventViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EventViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EventViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "EventViewGroup dispatchTouchEvent Action down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "EventViewGroup dispatchTouchEvent Action move");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "EventViewGroup dispatchTouchEvent Action up");
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "EventViewGroup onInterceptTouchEvent Action down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "EventViewGroup onInterceptTouchEvent Action move");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "EventViewGroup onInterceptTouchEvent Action up");
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "EventViewGroup onTouchEvent Action down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "EventViewGroup onTouchEvent Action move");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "EventViewGroup onTouchEvent Action up");
                break;
        }
        return super.onTouchEvent(event);
    }
}
