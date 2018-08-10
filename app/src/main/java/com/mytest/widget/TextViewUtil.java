package com.mytest.widget;

import android.widget.TextView;

public class TextViewUtil {
    private TextView textView;
    private String str;
    private int length;
    private long time;
    private int n = 0;
    private int nn;

    public TextViewUtil(TextView textView, String str, long time) {
        this.textView = textView;
        this.str = str;
        this.time = time;

        this.length = str.length();
        start(n);
    }

    private void start(final int n){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String stv = str.substring(0, n);
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(stv);
                        }
                    });
                    Thread.sleep(time);
                    nn = n+1;
                    if (nn <= length){
                        start(nn);
                    }
                }catch (Exception e){

                }
            }
        }).start();
    }
}
