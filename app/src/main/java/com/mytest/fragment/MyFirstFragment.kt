package com.mytest.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.mytest.R
import com.mytest.activity.GameActivity
import kotlinx.android.synthetic.main.fragment_my_first.*

class MyFirstFragment: BaseFragment() {
    override fun onLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater?.inflate(R.layout.fragment_my_first, container)
    }

    override fun initBundleData() {
    }

    override fun initView(view: View) {

        view.findViewById<AppCompatButton>(R.id.vButton).setOnClickListener {
            forceUpdate = true
            val intent = Intent(activity, GameActivity::class.java)
            startActivity(intent)
        }

    }

    override fun loadData() {
        forceUpdate = false
        println("=============MyFirstFragment")
    }
}