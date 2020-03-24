package com.mytest.widget.layoutmanager

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MyLayoutManager constructor(val offset: Int): RecyclerView.LayoutManager()  {

    companion object{
        const val TAG = "MyLayoutManager"
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        //设置为WRAP_CONTENT，表示RecyclerView的布局由Item大小决定
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    /**
     * item的布局
     * */
    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        Log.d(TAG, "子布局的个数${itemCount}")
        recycler?.let  back@{
            val itemCount = itemCount
            //如果没有item直接返回
            if (itemCount < 1){
                return@back
            }
            //如果是在PreLayout，直接返回
            state?.let { itState->
                Log.d(TAG, "state的itemCount个数：${itState.itemCount}")
                if (itState.isPreLayout){
                    return@back
                }

                if (itState.itemCount == 0){
                    removeAndRecycleAllViews(it)
                    return@back
                }
            }

            //分离全部已有的View放入临时缓存
            detachAndScrapAttachedViews(it)

            drawChildView(it, state)
        }
    }

    /**
     * 返回true，表示能左右滑动，返回false表示禁用左右滑动
     * */
    override fun canScrollHorizontally(): Boolean {
        return true
    }

    /**
     * 返回true，表示能上下滑动，返回false表示禁用上下滑动
     * */
    override fun canScrollVertically(): Boolean {
        return super.canScrollVertically()
    }

    /**
     * 水平滑动
     * */
    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        //dx>0，手指从右向左滑动；dx<0，手指从左向右滑动
        //dx为0或者没有子view，不能滑动
        if (dx == 0 || childCount == 0){
            return 0
        }

        return super.scrollHorizontallyBy(dx, recycler, state)
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        when(state){
            RecyclerView.SCROLL_STATE_DRAGGING->{

            }
            RecyclerView.SCROLL_STATE_IDLE->{

            }
        }
    }

    private fun drawChildView(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {

    }
}