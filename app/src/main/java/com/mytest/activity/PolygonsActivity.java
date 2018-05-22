package com.mytest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mytest.R;
import com.mytest.widget.Polygonsview;

public class PolygonsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polygons);

        Polygonsview polygonsView = (Polygonsview)findViewById(R.id.polygonsView);
    }
}
