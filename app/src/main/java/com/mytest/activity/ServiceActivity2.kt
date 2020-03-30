package com.mytest.activity

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.mytest.R
import com.mytest.service.MyService

class ServiceActivity2: AppCompatActivity(), ServiceConnection {

    private var myService: MyService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_service_2)

        //绑定Service
        val bindIntent = Intent(this, MyService::class.java)
        val b = bindService(bindIntent, this, BIND_AUTO_CREATE)
        println("service  $b")

        findViewById<AppCompatButton>(R.id.sendMsg).setOnClickListener{
            myService?.refresh()
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        myService = (service as MyService.MyServiceBinder).service
        //响应Service中的接口
        myService?.setRefreshListener(object : MyService.RefreshListener {
            override fun refresh() {
                Handler().postDelayed({
                    println("service  执行到了方法ServiceActivity2")
                }, 300)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(this)
    }
}