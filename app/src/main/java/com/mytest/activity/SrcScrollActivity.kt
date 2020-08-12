package com.mytest.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mytest.R
import kotlinx.android.synthetic.main.activity_src_scroll.*

class SrcScrollActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_src_scroll)

        vBtnStart.setOnClickListener{
            vSrcScroll.startScroll()
        }

        vBtnStop.setOnClickListener{
            vSrcScroll.stopScroll()
        }
    }
}