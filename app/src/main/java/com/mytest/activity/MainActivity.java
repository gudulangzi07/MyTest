package com.mytest.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mytest.R;
import com.mytest.mvvm.activity.MVVMActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, PolygonsActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.button2).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.button3).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MyNineActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.button4).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MyTimeActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.button5).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MoveActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.button6).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TimeLineActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.button7).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EventPassActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.button8).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ChangeSurfaceViewActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.button9).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TwoSurfaceViewActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.button10).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MVVMActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.button11).setOnClickListener(view->{
            Intent intent = new Intent(MainActivity.this, ChildRefreshUIActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.button12).setOnClickListener(view->{
            Intent intent = new Intent(MainActivity.this, OverLayCardActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.button13).setOnClickListener(view->{
            Intent intent = new Intent(MainActivity.this, ServiceActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.button14).setOnClickListener(view->{
            Intent intent = new Intent(MainActivity.this, SrcScrollActivity.class);
            startActivity(intent);
        });
    }
}
