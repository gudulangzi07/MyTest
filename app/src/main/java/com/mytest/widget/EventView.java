package com.mytest.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class EventView extends android.support.v7.widget.AppCompatTextView {
    private static final String TAG = EventView.class.getSimpleName();

    public EventView(Context context) {
        this(context, null);
    }

    public EventView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EventView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "EventView dispatchTouchEvent Action down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "EventView dispatchTouchEvent Action move");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "EventView dispatchTouchEvent Action up");
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "EventView onTouchEvent Action down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "EventView onTouchEvent Action move");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "EventView onTouchEvent Action up");
                break;
        }
        return super.onTouchEvent(event);
    }
}
