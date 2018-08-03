package com.mytest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mytest.R;
import com.mytest.widget.MyClockView;

public class MyTimeActivity extends AppCompatActivity implements MyClockView.DownCountTimerListener{

    private MyClockView clockView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_time);

        clockView = findViewById(R.id.clockView);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clockView.setDownCountTime(1000L * 60L * 60L * 24L * 3L);
                clockView.startDownCountTimer();
            }
        });
    }

    @Override
    public void stopDownCountTimer() {
        Toast.makeText(this,"结束了",Toast.LENGTH_SHORT).show();
    }
}
