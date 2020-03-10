package com.mytest.widget.layoutmanager

import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.util.SparseArray
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mytest.widget.layoutmanager.Constants

class OverLayCardLayoutManager: RecyclerView.LayoutManager() {

    var maxShowCount: Int = Constants.SHOW_MAX_COUNT
        set(value) {
            field = if (value < Constants.SHOW_MAX_COUNT){
                Constants.SHOW_MAX_COUNT
            }else{
                value
            }
        }

    private var mItemFrames = SparseArray<Rect>()

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        //设置为WRAP_CONTENT，表示RecyclerView的布局由Item大小决定
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        Log.d("DEBUG", "onLayoutChildren() called with: recycle=$recycler, state=$state")
        recycler?.let {
            val itemCount = itemCount
            //如果没有item直接返回
            if (itemCount < 1){
                return@let
            }
            //如果是在PreLayout，直接返回
            state?.let { itState->
                if (itState.isPreLayout){
                    return@let
                }
            }

            //清除所有item的位置信息
            mItemFrames.clear()

            //需要绘制可以看到的子view的个数
            val positionCount = if (itemCount < maxShowCount){
                itemCount
            }else{
                maxShowCount
            }

            //获取item的宽高，使用一套item布局，只需要测量一次
            val childView = it.getViewForPosition(0)
            addView(childView)
            measureChildWithMargins(childView, 0, 0)
            //item布局的宽
            val childWidth = getDecoratedMeasuredWidth(childView)
            //item布局的高
            val childHeight = getDecoratedMeasuredHeight(childView)

            //item的起始坐标点
            var offset = 0

            //存储需要展示的item的位置信息
            for (position in 0 until positionCount){
                val frame = Rect()
                frame.set(offset, 0, offset + childWidth, childHeight)
                mItemFrames.put(position, frame)
                //下一个item的起始坐标,
                offset += 50
            }

            detachAndScrapAttachedViews(it)

            layoutItems(it, state, positionCount)
        }
    }

    private fun layoutItems(recycler: RecyclerView.Recycler, state: RecyclerView.State?, positionCount: Int) {
        state?.let {
            if (it.isPreLayout){
                return@layoutItems
            }

            for (position in 0 until positionCount){
                val itemView = recycler.getViewForPosition(position)
                measureChildWithMargins(itemView, 0, 0)
                addView(itemView, 0)
                layoutItem(itemView, mItemFrames[position], position)
            }
        }
    }

    private fun layoutItem(itemView: View, rect: Rect?, position: Int) {
        rect?.let {
            layoutDecorated(itemView, it.left, it.top, it.right, it.bottom)
            //X方向不缩放
            itemView.scaleX = 1f
            //Y方向根据现实的item进行比例缩放
            itemView.scaleY = 1 - 0.09f * position
        }
    }
}