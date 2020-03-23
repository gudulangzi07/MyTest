package com.mytest.widget.layoutmanager

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MyLayoutManager: RecyclerView.LayoutManager()  {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        //设置为WRAP_CONTENT，表示RecyclerView的布局由Item大小决定
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    /**
     * item的布局
     * */
    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        recycler?.let {

        }
    }

    /**
     * 水平滑动
     * */
    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        return super.scrollHorizontallyBy(dx, recycler, state)
    }
}