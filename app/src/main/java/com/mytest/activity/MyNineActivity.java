package com.mytest.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.mytest.R;
import com.mytest.widget.CustomPoint;
import com.mytest.widget.CustomPointEvaluator;

public class MyNineActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_1;
    private ImageView iv_2;
    private ImageView iv_3;
    private ImageView iv_4;
    private ImageView iv_5;
    private ImageView iv_6;
    private ImageView iv_7;
    private ImageView iv_8;
    private ImageView iv_9;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_nine);

//        myNineView = findViewById(R.id.myNineView);
//
//        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myNineView.startAnimator();
//            }
//        });

        iv_1 = findViewById(R.id.iv_1);
        iv_2 = findViewById(R.id.iv_2);
        iv_3 = findViewById(R.id.iv_3);
        iv_4 = findViewById(R.id.iv_4);
        iv_5 = findViewById(R.id.iv_5);
        iv_6 = findViewById(R.id.iv_6);
        iv_7 = findViewById(R.id.iv_7);
        iv_8 = findViewById(R.id.iv_8);
        iv_9 = findViewById(R.id.iv_9);

        iv_1.setOnClickListener(this);
        iv_2.setOnClickListener(this);
        iv_3.setOnClickListener(this);
        iv_4.setOnClickListener(this);
        iv_5.setOnClickListener(this);
        iv_6.setOnClickListener(this);
        iv_7.setOnClickListener(this);
        iv_8.setOnClickListener(this);
        iv_9.setOnClickListener(this);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAllAnimator();
            }
        });

    }

    private void startAllAnimator(){
        CustomPoint startPoint1 = new CustomPoint(iv_1.getLeft(), iv_1.getTop());
        CustomPoint startPoint2 = new CustomPoint(iv_2.getLeft(), iv_2.getTop());
        CustomPoint startPoint3 = new CustomPoint(iv_3.getLeft(), iv_3.getTop());
        CustomPoint startPoint4 = new CustomPoint(iv_4.getLeft(), iv_4.getTop());
        CustomPoint startPoint6 = new CustomPoint(iv_6.getLeft(), iv_6.getTop());
        CustomPoint startPoint7 = new CustomPoint(iv_7.getLeft(), iv_7.getTop());
        CustomPoint startPoint8 = new CustomPoint(iv_8.getLeft(), iv_8.getTop());
        CustomPoint startPoint9 = new CustomPoint(iv_9.getLeft(), iv_9.getTop());
        CustomPoint endPoint = new CustomPoint(iv_5.getLeft(), iv_5.getTop());

        ValueAnimator valueAnimator1 = getValueAnimator(iv_1, startPoint1, endPoint);
        ValueAnimator valueAnimator2 = getValueAnimator(iv_2, startPoint2, endPoint);
        ValueAnimator valueAnimator3 = getValueAnimator(iv_3, startPoint3, endPoint);
        ValueAnimator valueAnimator4 = getValueAnimator(iv_4, startPoint4, endPoint);
        ValueAnimator valueAnimator6 = getValueAnimator(iv_6, startPoint6, endPoint);
        ValueAnimator valueAnimator7 = getValueAnimator(iv_7, startPoint7, endPoint);
        ValueAnimator valueAnimator8 = getValueAnimator(iv_8, startPoint8, endPoint);
        ValueAnimator valueAnimator9 = getValueAnimator(iv_9, startPoint9, endPoint);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(valueAnimator1, valueAnimator2, valueAnimator3, valueAnimator4, valueAnimator6, valueAnimator7, valueAnimator8, valueAnimator9);
        animatorSet.setDuration(500);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                iv_1.setVisibility(View.GONE);
                iv_2.setVisibility(View.GONE);
                iv_3.setVisibility(View.GONE);
                iv_4.setVisibility(View.GONE);
                iv_6.setVisibility(View.GONE);
                iv_7.setVisibility(View.GONE);
                iv_8.setVisibility(View.GONE);
                iv_9.setVisibility(View.GONE);
                iv_5.setImageDrawable(getResources().getDrawable(R.drawable.black_chess));
                Toast.makeText(MyNineActivity.this, "动画结束", Toast.LENGTH_SHORT).show();
            }
        });

        animatorSet.start();
    }

    private ValueAnimator getValueAnimator(final View view, final CustomPoint startPoint, CustomPoint endPoint){
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new CustomPointEvaluator(), startPoint, endPoint);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                CustomPoint customPoint = (CustomPoint) animation.getAnimatedValue();
                if (startPoint.getPointX() > customPoint.getPointX()){
                    view.setTranslationX(customPoint.getPointX() - startPoint.getPointX());
                }else if(startPoint.getPointX() == customPoint.getPointX()){
                    //如果相等，不做操作
                }else{
                    view.setTranslationX(customPoint.getPointX());
                }

                if (startPoint.getPointY() > customPoint.getPointY()){
                    view.setTranslationY(customPoint.getPointY() - startPoint.getPointY());
                }else if(startPoint.getPointY() == customPoint.getPointY()){
                    //如果相等，不做操作
                }else{
                    view.setTranslationY(customPoint.getPointY());
                }

            }
        });

        return valueAnimator;
    }

    private void startAnimator(final View view, final CustomPoint startPoint, CustomPoint endPoint){
//        ValueAnimator valueAnimator = ValueAnimator.ofObject(new CustomPointEvaluator(), startPoint, endPoint);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                CustomPoint customPoint = (CustomPoint) animation.getAnimatedValue();
//                view.setTranslationY(customPoint.getPointY() - startPoint.getPointY());
//            }
//        });
//
//        valueAnimator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//
//            }
//        });
//
//        valueAnimator.setDuration(1000);
//        valueAnimator.start();

        TranslateAnimation translateAnimation = new TranslateAnimation(startPoint.getPointX(), endPoint.getPointX(), startPoint.getPointY(), endPoint.getPointY());
        translateAnimation.setFillBefore(true);

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        translateAnimation.setDuration(1000);
        view.startAnimation(translateAnimation);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_1:
            case R.id.iv_2:
            case R.id.iv_3:
            case R.id.iv_4:
            case R.id.iv_5:
            case R.id.iv_6:
            case R.id.iv_7:
            case R.id.iv_8:
            case R.id.iv_9:
                CustomPoint startPoint = new CustomPoint(view.getLeft(), view.getTop());
                CustomPoint endPoint = new CustomPoint(view.getLeft(), 0);
                startAnimator(view, startPoint, endPoint);
                break;
        }
    }
}
