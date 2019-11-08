package com.mytest.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.mytest.R;

public class ChildRefreshUIActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextView;

    private Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_refresh_ui);

        mTextView = findViewById(R.id.tv_text);
        mButton = findViewById(R.id.button);

        mButton.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                new Thread(() -> {
                    System.out.println("============" + Thread.currentThread());
                    new Handler(getMainLooper()).post(() -> {
                                mTextView.setText("在子线程中更新了UI");
                                Toast.makeText(this, "子线程弹出", Toast.LENGTH_SHORT).show();
                            }
                    );
                }).start();
                break;
        }
    }
}
