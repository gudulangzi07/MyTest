package com.mytest.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;

import com.mytest.R;

public class TwoSurfaceViewActivity extends AppCompatActivity implements View.OnClickListener {

    private SurfaceView surface_view_1;
    private SurfaceView surface_view_2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_surface_view);

        surface_view_1 = findViewById(R.id.surface_view_1);
        surface_view_2 = findViewById(R.id.surface_view_2);

        surface_view_1.setOnClickListener(this);
        surface_view_2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.surface_view_1:

                break;
            case R.id.surface_view_2:
                smallToBig();
                break;
        }
    }

    private void smallToBig(){
        int smallWidth = surface_view_2.getMeasuredWidth();
        int smallHeight = surface_view_2.getMeasuredHeight();

        int bigWidth = surface_view_1.getMeasuredWidth();
        int bigHeight = surface_view_1.getMeasuredHeight();

        RelativeLayout.LayoutParams smallParams = new RelativeLayout.LayoutParams(smallWidth, smallHeight);
        RelativeLayout.LayoutParams bigParams = new RelativeLayout.LayoutParams(bigWidth, bigHeight);

        //原大图变小
        smallParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM | RelativeLayout.ALIGN_PARENT_RIGHT);
        surface_view_1.setLayoutParams(smallParams);
        //原小图变大
        surface_view_2.setLayoutParams(bigParams);

        //原大图前置
        surface_view_1.setZOrderOnTop(true);
        surface_view_1.setZOrderMediaOverlay(true);
    }

}
