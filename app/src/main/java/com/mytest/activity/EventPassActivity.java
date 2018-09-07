package com.mytest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.mytest.R;

public class EventPassActivity extends AppCompatActivity{
    private static final String TAG = EventPassActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_pass);

//        findViewById(R.id.eventView).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(EventPassActivity.this, "点击按钮", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "EventPassActivity_down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "EventPassActivity_move");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "EventPassActivity_up");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "EventPassActivity_touch_down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "EventPassActivity_touch_move");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "EventPassActivity_touch_up");
                break;
        }
        return super.onTouchEvent(event);
    }
}
