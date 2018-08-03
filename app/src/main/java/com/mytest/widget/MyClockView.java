package com.mytest.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.mytest.R;

public class MyClockView extends RelativeLayout {
    private float dayTextSize, hourTextSize, minTextSize, secTextSize;
    private FlipClockView dayTextView, hourTextView, minTextView, secTextView;
    private LayoutParams dayLayoutParams, hourLayoutParams, minLayoutParams, secLayoutParams;
    private DownCountTimerListener mDownCountTimerListener;
    private Handler mHandler;
    private Runnable mRunnable;
    private long totalTime = 0;
    private boolean isRunning = true;
    private long outNumber = 0;//超过的最大计数的时间（秒数）

    public MyClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public MyClockView(Context context) {
        this(context, null);
    }

    public MyClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public void initView(Context context, AttributeSet attrs) {
        TypedArray tArray = context.obtainStyledAttributes(attrs, R.styleable.MyClock);
        dayTextSize = tArray.getDimensionPixelSize(R.styleable.MyClock_dayTextSize, 26);
        hourTextSize = tArray.getDimensionPixelSize(R.styleable.MyClock_hourTextSize, 26);
        minTextSize = tArray.getDimensionPixelSize(R.styleable.MyClock_minTextSize, 26);
        secTextSize = tArray.getDimensionPixelSize(R.styleable.MyClock_secTextSize, 26);
        int dayTextColor = tArray.getColor(R.styleable.MyClock_dayTextColor, 0xffffff);
        int hourTextColor = tArray.getColor(R.styleable.MyClock_hourTextColor, 0xffffff);
        int minTextColor = tArray.getColor(R.styleable.MyClock_minTextColor, 0xffffff);
        int secTextColor = tArray.getColor(R.styleable.MyClock_secTextColor, 0xffffff);

        Drawable dayTextBg = tArray.getDrawable(R.styleable.MyClock_dayTextBackground);
        Drawable hourTextBg = tArray.getDrawable(R.styleable.MyClock_hourTextBackground);
        Drawable minTextBg = tArray.getDrawable(R.styleable.MyClock_minTextBackground);
        Drawable secTextBg = tArray.getDrawable(R.styleable.MyClock_secTextBackground);

        tArray.recycle();

        dayTextView = new FlipClockView(context);
        hourTextView = new FlipClockView(context);
        minTextView = new FlipClockView(context);
        secTextView = new FlipClockView(context);

        dayTextView.setId(R.id.dayTextView);
        hourTextView.setId(R.id.hourTextView);
        minTextView.setId(R.id.minTextView);
        secTextView.setId(R.id.secTextView);

        dayTextView.setClockBackground(dayTextBg);
        dayTextView.setClockTextSize(dayTextSize);
        dayTextView.setClockTextColor(dayTextColor);
        hourTextView.setClockBackground(hourTextBg);
        hourTextView.setClockTextSize(hourTextSize);
        hourTextView.setClockTextColor(hourTextColor);
        minTextView.setClockBackground(minTextBg);
        minTextView.setClockTextSize(minTextSize);
        minTextView.setClockTextColor(minTextColor);
        secTextView.setClockBackground(secTextBg);
        secTextView.setClockTextSize(secTextSize);
        secTextView.setClockTextColor(secTextColor);
        //secTextView.setFlipDirection(false);

        dayLayoutParams = new LayoutParams(70, 70);
        dayLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        dayLayoutParams.setMargins(0, 0, 0, 0);
        addView(dayTextView, dayLayoutParams);

        hourLayoutParams = new LayoutParams(70,70);
        hourLayoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.dayTextView);
        hourLayoutParams.setMargins(10, 0, 0, 0);
        addView(hourTextView, hourLayoutParams);

        minLayoutParams = new LayoutParams(70, 70);
        minLayoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.hourTextView);
        minLayoutParams.setMargins(10, 0, 0, 0);
        addView(minTextView, minLayoutParams);

        secLayoutParams = new LayoutParams(70, 70);
        secLayoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.minTextView);
        secLayoutParams.setMargins(10, 0, 0, 0);
        addView(secTextView, secLayoutParams);

        dayTextView.setClockTime("00");
        hourTextView.setClockTime("00");
        minTextView.setClockTime("00");
        secTextView.setClockTime("00");

        mHandler = new Handler();
    }

    public interface DownCountTimerListener {

        void stopDownCountTimer();

    }

    /**
     * 暂停计时
     */
    public void pauseDownCountTimer() {
        if (mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
            dayTextView.setClockTime("00");
            hourTextView.setClockTime("00");
            minTextView.setClockTime("00");
            secTextView.setClockTime("00");
        }
    }

    public void setDownCountTimerListener(DownCountTimerListener listener) {
        this.mDownCountTimerListener = listener;
    }

    /**
     * 获取设置倒计时的总时间
     *
     * @return
     */
    public long getDownCountTime() {
        return totalTime;
    }

    /**
     * 设定需要倒计时的总共时间
     *
     * @param totalDownCountTimes
     */
    public void setDownCountTime(long totalDownCountTimes) {
        this.totalTime = totalDownCountTimes;
        pauseDownCountTimer();
    }

    /**
     * 设定倒计时的时间开始时间和结束时间
     *
     * @param startTime
     * @param endTime
     */
    public void setDownCountTime(long startTime, long endTime) {
        this.totalTime = endTime - startTime;
        pauseDownCountTimer();
    }

    /**
     * 开始倒计时
     */
    public void startDownCountTimer() {
        isRunning = true;
        setTime2Text(getDownCountTime());
        mRunnable = new Runnable() {

            @Override
            public void run() {
                int i = secTextView.getCurrentValue();
                // Log.e("sec时间----->",i + "");
                outNumber--;
                if (outNumber <= 0) {
                    if (i > 0) {
                        secTextView.smoothFlip();
                    } else {
                        int j = getClockMinValue();
                        j--;
                        if (j >= 0 && i == 0) {
                            //Log.e("分钟时间----->", j + "");
                            minTextView.smoothFlip();
                            secTextView.setClockTime("60");
                            secTextView.smoothFlip();
                        } else {
                            int k = getClockHourValue();
                            k--;

                            if (k >= 0 && j < 0 && i == 0) {
                                hourTextView.smoothFlip();
                                minTextView.setClockTime("60");
                                minTextView.smoothFlip();
                                secTextView.setClockTime("60");
                                secTextView.smoothFlip();
                            } else {
                                int d = getClockDayValue();
                                d--;
                                if (d < 0) {
                                    //	Log.e("时间结束----->", j + "    " + i);
                                    isRunning = false;
                                    if (null != mDownCountTimerListener) {
                                        mDownCountTimerListener.stopDownCountTimer();
                                    }
                                } else {
                                    Log.e("day----->", d + "    ");
                                    secTextView.setClockTime("60");
                                    minTextView.setClockTime("60");
                                    hourTextView.setClockTime("24");
                                    d++;
                                    dayTextView.setClockTime("" + d);
                                    secTextView.smoothFlip();
                                    minTextView.smoothFlip();
                                    hourTextView.smoothFlip();
                                    dayTextView.smoothFlip();
                                }
                            }
                        }
                    }
                }
                if (isRunning) {
                    mHandler.postDelayed(this, 1000);
                } else {
                    mHandler.removeCallbacks(this);
                }
            }
        };
        mHandler.postDelayed(mRunnable, 1000);

    }

    /**
     * 获取倒计时剩余的时间，数组的4个元素分别代表剩余的天、时、分、秒
     *
     * @return
     */
    public String[] getClockRestTime() {
        String[] restTime = new String[4];
        restTime[0] = String.valueOf(getClockDayValue());
        restTime[1] = String.valueOf(getClockHourValue());
        restTime[2] = String.valueOf(getClockMinValue());
        restTime[3] = String.valueOf(getClockSecValue());
        return restTime;
    }

    /**
     * 根据给定的时间转换为 天、时、分
     *
     * @param startTime
     */
    private void setTime2Text(long startTime) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = startTime / dd;
        long hour = (startTime - day * dd) / hh;
        long minute = (startTime - day * dd - hour * hh) / mi;
        long second = (startTime - day * dd - hour * hh - minute * mi) / ss;

        String strDay = day < 10 ? "0" + day : "" + day; // 天
        String strHour = hour < 10 ? "0" + hour : "" + hour;// 小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;// 分钟
        String strSecond = second < 10 ? "0" + second : "" + second;// 秒

        Log.e("时间----》", strDay + "  " + strHour + "  " + strMinute + "  " + strSecond);
        if (Integer.parseInt(strDay) >= 100) {
            dayTextView.setClockTime("99");
            hourTextView.setClockTime("23");
            minTextView.setClockTime("59");
            secTextView.getmInvisibleTextView().setText("59");
            secTextView.getmVisibleTextView().setText("59");
            outNumber = ((startTime) - 1000L * 60L * 60L * 24L * 100L) / 1000L;
            //	Log.e("多余的时间----》",outNumber+"");
        } else {
            dayTextView.setClockTime(strDay);
            hourTextView.setClockTime(strHour);
            minTextView.setClockTime(strMinute);
            secTextView.getmVisibleTextView().setText(strSecond);
            secTextView.getmInvisibleTextView().setText(strSecond);
            outNumber = 0;
        }
    }

    public int getClockDayValue() {
        return dayTextView.getCurrentValue();
    }

    public int getClockHourValue() {
        return hourTextView.getCurrentValue();
    }

    public int getClockMinValue() {
        return minTextView.getCurrentValue();
    }

    public int getClockSecValue() {
        return secTextView.getCurrentValue();
    }

}
