package com.mytest.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;

import com.mytest.R;

public class ChangeSurfaceViewActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = ChangeSurfaceViewActivity.class.getSimpleName();

    //远端的视图
    private SurfaceView remote_sv;
    // 本地的视图
    private SurfaceView local_sv;
    private SurfaceHolder remote_holder;
    private SurfaceHolder local_holder;
    private RelativeLayout remote_rl;
    private RelativeLayout local_rl;

    private int screenWidth;
    private int screenHeight;

    private int beforRemoteweith;
    private int beforLocalweith;
    private int beforRemoteheigth;
    private int beforLocalheigth;
    private int StateAB = 0;
    private int StateBA = 1;
    private int mSate;
    private int defaultLocalHeight=200;
    private  int  defaultLocalwidth=400;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_surface_view);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - 500;
        remote_sv = (SurfaceView) findViewById(R.id.remote_view);
        remote_rl = (RelativeLayout) findViewById(R.id.remote_rl);
        local_rl = (RelativeLayout) findViewById(R.id.local_rl);
        remote_sv.setOnClickListener(this);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(screenWidth, screenHeight);
        remote_sv.setLayoutParams(params);
        remote_holder = remote_sv.getHolder();
        // 对 surfaceView 进行操作
        remote_holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Canvas c = remote_holder.lockCanvas();
                // 2.开画
                Paint p = new Paint();
                p.setColor(Color.RED);
                Rect aa = new Rect(0, 0, holder.getSurfaceFrame().width(),
                        holder.getSurfaceFrame().height());
                c.drawRect(aa, p);
                // 3. 解锁画布 更新提交屏幕显示内容
                remote_holder.unlockCanvasAndPost(c);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {/*
                Log.d(TAG,"remote_holder surfaceChanged width"+ width+"height"+height);
                Canvas c = remote_holder.lockCanvas();
                // 2.开画
                Paint p = new Paint();
                p.setColor(Color.RED);
                Rect aa = new Rect(0, 0, holder.getSurfaceFrame().width(),
                        holder.getSurfaceFrame().height());
                c.drawRect(aa, p);
                // 3. 解锁画布 更新提交屏幕显示内容
                remote_holder.unlockCanvasAndPost(c);
            */}

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });// 自动运行surfaceCreated以及surfaceChanged

        local_sv = (SurfaceView) findViewById(R.id.local_view);
        local_sv.setOnClickListener(this);
        local_sv.setOnClickListener(this);
        // sv.setZOrderOnTop(false);
        local_sv.setZOrderOnTop(true);
        // 这两个方法差不多，设置了就会浮现到顶部，但是，后面的看不见，要像下面设置为透明
        // local_sv.setZOrderOnTop(true);
        // local_sv.setZOrderMediaOverlay(true);

        local_holder = local_sv.getHolder();

        remote_holder.setFormat(PixelFormat.TRANSPARENT);
        local_holder.setFormat(PixelFormat.TRANSPARENT);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(defaultLocalHeight, defaultLocalwidth);
        local_sv.setLayoutParams(params1);
        remote_holder = remote_sv.getHolder();
        local_holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Canvas c = holder.lockCanvas();
                // 2.开画
                Paint p = new Paint();
                p.setColor(Color.YELLOW);
                Rect aa = new Rect(0, 0, holder.getSurfaceFrame().width(),
                        holder.getSurfaceFrame().height());
                c.drawRect(aa, p);
                // 3. 解锁画布 更新提交屏幕显示内容
                holder.unlockCanvasAndPost(c);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {/*
             Log.d(TAG,"local_holder surfaceChanged width"+ width+"height"+height);
                Canvas c = holder.lockCanvas();
                // 2.开画
                Paint p = new Paint();
                p.setColor(Color.YELLOW);
                Rect aa = new Rect(0, 0, holder.getSurfaceFrame().width()-50,
                        holder.getSurfaceFrame().height()-50);
                c.drawRect(aa, p);
                // 3. 解锁画布 更新提交屏幕显示内容
                holder.unlockCanvasAndPost(c);

            */}

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        zoomOpera(local_rl, local_sv, remote_sv, remote_rl, defaultLocalwidth,
                defaultLocalHeight, RelativeLayout.CENTER_IN_PARENT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.local_view:
                Log.d(TAG, " onClick local_view" + mSate);
                if (mSate == StateAB) {
                    zoomlocalViewout(beforRemoteweith, beforRemoteheigth, local_sv,
                            remote_sv);
                    zoomRemoteViewint(beforLocalweith, beforLocalheigth);
                    mSate = StateBA;
                }

                break;
            case R.id.remote_view:
                Log.d(TAG, " onClick emote_view" + mSate);
                if (mSate == StateBA) {

                    zoomRemoteout(beforRemoteweith, beforRemoteheigth, local_sv,
                            remote_sv);
                    zoomlocalViewint(beforLocalweith, beforLocalheigth);

                    mSate = StateAB;
                }

                break;
            default:
                break;
        }

    }
    //放大远端的视图
    private void zoomRemoteout(int weith2, int heigth2, SurfaceView localView,
                               SurfaceView remoteView) {

        beforLocalheigth = localView.getMeasuredHeight();
        beforLocalweith = localView.getMeasuredWidth();
        beforRemoteheigth = remoteView.getMeasuredHeight();
        beforRemoteweith = remoteView.getMeasuredWidth();
        Log.d(TAG, "zoomRemoteout beforLocalheigth" + beforLocalheigth
                + "beforLocalweith" + beforLocalweith + "beforRemoteheigth"
                + beforRemoteheigth + "beforRemoteweith" + beforLocalweith);
        zoomOpera(local_rl, local_sv, remote_sv, remote_rl, screenWidth,
                beforLocalheigth, RelativeLayout.CENTER_IN_PARENT);

    }
    //具体的视图操作
    private void zoomOpera(View sourcView, SurfaceView beforeview,
                           SurfaceView afterview, View detView, int beforLocalweith,
                           int beforLocalHeigth, int rule) {

        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        Log.w(TAG, "beforLocalheigth = " + beforLocalheigth
                + "; beforLocalweith = " + beforLocalweith);
        params1.addRule(rule, RelativeLayout.TRUE);
        afterview.setLayoutParams(params1);
        afterview.setBackgroundResource(android.R.color.transparent);
        params1 = new RelativeLayout.LayoutParams(beforLocalweith, beforLocalHeigth);
        params1.addRule(rule, RelativeLayout.TRUE);
        detView.setLayoutParams(params1);

    }
    //缩小远端的视图
    private void zoomRemoteViewint(int weith2, int heigth2) {
        RelativeLayout paretview = (RelativeLayout) local_rl.getParent();
        paretview.removeView(remote_rl);
        paretview.removeView(local_rl);
        zoomOpera(local_rl, local_sv, remote_sv, remote_rl, beforLocalweith,
                beforLocalheigth, RelativeLayout.ALIGN_PARENT_TOP);
        Log.d(TAG, "paretview" + paretview.getChildCount());
        paretview.addView(local_rl);
        paretview.addView(remote_rl);
        remote_sv.setZOrderOnTop(true);

    }
    //放大本端的视图
    private void zoomlocalViewout(int weith2, int heigth2,
                                  SurfaceView localView, SurfaceView remoteView) {
        beforLocalheigth = localView.getMeasuredHeight();
        beforLocalweith = localView.getMeasuredWidth();
        beforRemoteheigth = remoteView.getMeasuredHeight();
        beforRemoteweith = remoteView.getMeasuredWidth();
        Log.d(TAG, "zoomlocalViewout beforLocalheigth" + beforLocalheigth
                + "beforLocalweith" + beforLocalweith + "beforRemoteheigth"
                + beforRemoteheigth + "beforRemoteweith" + beforRemoteweith);
        zoomOpera(remote_rl, remote_sv, local_sv, local_rl, beforRemoteweith,
                beforRemoteheigth, RelativeLayout.CENTER_IN_PARENT);

    }
    //减小本端的视图
    private void zoomlocalViewint(int weith2, int heigth2) {
        RelativeLayout paretview = (RelativeLayout) local_rl.getParent();
        paretview.removeView(remote_rl);
        paretview.removeView(local_rl);
        zoomOpera(remote_rl, remote_sv, local_sv, local_rl, beforRemoteweith,
                beforRemoteheigth, RelativeLayout.ALIGN_PARENT_TOP);
        paretview.addView(remote_rl);
        paretview.addView(local_rl);
        local_sv.setZOrderOnTop(true);

    }
}
