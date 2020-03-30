package com.mytest.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mytest.R

class MySecondFragment: BaseFragment()  {
    override fun onLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater?.inflate(R.layout.fragment_my_second, container)
    }

    override fun initBundleData() {
    }

    override fun initView(view: View) {
    }

    override fun loadData() {
        println("==============MySecondFragment")
    }
}