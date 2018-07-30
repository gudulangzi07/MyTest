package com.mytest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mytest.R;
import com.mytest.widget.MyNineView;

public class MyNineActivity extends AppCompatActivity {


    private MyNineView myNineView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_nine);

        myNineView = findViewById(R.id.myNineView);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myNineView.startAnimator();
            }
        });
    }


}
