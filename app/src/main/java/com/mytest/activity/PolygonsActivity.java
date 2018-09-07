package com.mytest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mytest.R;
import com.mytest.widget.Polygonsview;
import com.mytest.widget.rating.RatingStarView;

public class PolygonsActivity extends AppCompatActivity {

    private RatingStarView rsv_rating;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polygons);

        Polygonsview polygonsView = findViewById(R.id.polygonsView);

        rsv_rating = findViewById(R.id.rsv_rating);


    }
}
