package com.mytest.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class MyService: Service() {

    private var refresh: RefreshListener? = null

    fun setRefreshListener(refresh: RefreshListener){
        this.refresh = refresh
    }

    interface RefreshListener{
        fun refresh()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return MyServiceBinder()
    }

    inner class MyServiceBinder : Binder() {
        val service: MyService
            get() = this@MyService
    }

    fun refresh(){
        refresh?.refresh()
    }
}