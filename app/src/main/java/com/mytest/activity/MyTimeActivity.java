package com.mytest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mytest.R;
import com.mytest.widget.MyClockView;
import com.mytest.widget.TextViewUtil;

public class MyTimeActivity extends AppCompatActivity implements MyClockView.DownCountTimerListener{

    private MyClockView clockView;
    private TextView tv_1;

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

        tv_1 = findViewById(R.id.tv_1);

        new TextViewUtil(tv_1, getString(R.string.tv_run), 200);
    }

    @Override
    public void stopDownCountTimer() {
        Toast.makeText(this,"结束了",Toast.LENGTH_SHORT).show();
    }
}
