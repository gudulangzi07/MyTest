package com.mytest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {
    //是否加载数据
    private var isLoad: Boolean = false
    //是否强制刷新数据
    protected var forceUpdate: Boolean = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = onLayoutView(inflater, container)
        view?.let {
            initBundleData()
            initView(it)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        println("==============onResume")
        prepareLoadData()
    }

    private fun prepareLoadData(){
        if (!isLoad || forceUpdate){
            loadData()
            isLoad = true
            forceUpdate = false
        }
    }

    /**
     * 初始化布局
     */
    protected abstract fun onLayoutView(
            inflater: LayoutInflater?,
            container: ViewGroup?
    ): View?

    /**
     * 数据初始化
     * */
    protected abstract fun initBundleData()

    /**
     * 页面初始化
     */
    protected abstract fun initView(view: View)

    /**
     * 加载数据
     * */
    protected abstract fun loadData()
}