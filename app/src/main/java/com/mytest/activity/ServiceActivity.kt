package com.mytest.activity

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.mytest.R
import com.mytest.service.MyService

class ServiceActivity: AppCompatActivity(), ServiceConnection {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_service)

        //绑定Service
        val bindIntent = Intent(this, MyService::class.java)
        val b = bindService(bindIntent, this, BIND_AUTO_CREATE)
        println("service     $b")

        findViewById<AppCompatButton>(R.id.startService).setOnClickListener {
            val intent = Intent(this, ServiceActivity2::class.java)
            startActivity(intent)
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        println("service 执行方法onServiceDisconnected")
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val serviceRefresh = (service as MyService.MyServiceBinder).service
        //响应Service中的接口
        serviceRefresh.setRefreshListener(object : MyService.RefreshListener {
            override fun refresh() {
                Handler().postDelayed({
                    println("service  执行到了方法ServiceActivity")
                }, 300)
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(this)
        println("service 执行方法onDestroy")
    }
}