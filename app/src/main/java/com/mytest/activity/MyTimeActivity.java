package com.mytest.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mytest.R;
import com.mytest.widget.MyClockView;
import com.mytest.widget.MyWatchView;
import com.mytest.widget.TextViewUtil;

public class MyTimeActivity extends AppCompatActivity implements MyClockView.DownCountTimerListener {

    private MyClockView clockView;
    private MyWatchView myWatchView;
    private TextView tv_1;

    private float secondDegrees;
    private float minuteDegrees;
    private float hourDegrees;

    private int seconds;
    private int minutes;

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

        myWatchView = findViewById(R.id.myWatch);
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (seconds == 59){
                    seconds = 0;
                    minuteDegrees += 6;
                    if (minutes == 59){
                        minutes = 0;
                        hourDegrees += 6;
                    }else {
                        minutes ++;
                    }
                }else{
                    seconds ++;
                }

                handler.postDelayed(this, 1000);
                if (secondDegrees == 360){
                    secondDegrees = 0;
                }

                if (minuteDegrees == 360){
                    minuteDegrees = 0;
                }
                myWatchView.setSecondDegrees(secondDegrees += 6);
                myWatchView.setMinuteDegrees(minuteDegrees);
                myWatchView.setHourDegrees(hourDegrees);
            }
        };
        handler.postDelayed(runnable, 1000);

        myWatchView.setHourDegrees(0);
        myWatchView.setMinuteDegrees(0);
        myWatchView.setSecondDegrees(0);
    }

    @Override
    public void stopDownCountTimer() {
        Toast.makeText(this, "结束了", Toast.LENGTH_SHORT).show();
    }
}
