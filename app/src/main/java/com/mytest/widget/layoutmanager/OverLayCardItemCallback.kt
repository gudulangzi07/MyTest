package com.mytest.widget.layoutmanager

import android.content.Context
import android.graphics.Canvas
import android.util.Log
import android.util.TypedValue
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.sqrt

class OverLayCardItemCallback @JvmOverloads constructor(
        dragDirs: Int,
        swipeDirs: Int,
        private val recyclerView: RecyclerView,
        private val adapter: RecyclerView.Adapter<*>,
        private var datas: MutableList<Any>)
    : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    companion object{
        const val TAG = "OverLayCardItemCallback"
    }

    //设置水平方向是否可以被回收掉的阀值
    private fun getThreshold(viewHolder: RecyclerView.ViewHolder): Float{
        //写了一个固定的值，如果滑动的宽度是整体宽度的一半，就回收
        Log.d(TAG, "recyclerView width: ${recyclerView.width}")
        return recyclerView.width * 0.5f
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        //实现循环
        val remove = datas.removeAt(viewHolder.layoutPosition)
        datas.remove(remove)
        datas.add(0, remove)
        adapter.notifyDataSetChanged()
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        //根据滑动的dx与dy，算出现在动画的比例系数
        val swipedValue = sqrt(dX * dX + dY * dY)
        var fraction = swipedValue / getThreshold(viewHolder)
        Log.d(TAG, "滑动的系数是：${fraction}")
        //边界修正
        if (fraction > 1){
            fraction = 1f
        }
        //对每个childView进行缩放，位移
        val childCount = recyclerView.childCount
        for (i in 0 until childCount){
            val child = recyclerView.getChildAt(i)
            val level = childCount - i - 1
            if (level > 0){
                child.scaleY = 1 - Constants.SCALE_GAP * level + fraction * Constants.SCALE_GAP

                if (level < Constants.SHOW_MAX_COUNT - 1){
                    child.scaleX = 1 - Constants.SCALE_GAP * level + fraction * Constants.SCALE_GAP
                    child.translationX = getDisplayMetrics(child.context) * level - fraction * getDisplayMetrics(child.context)
                }
            }
        }
    }

    private fun getDisplayMetrics(context: Context): Int{
        Log.d("getDisplayMetrics", "Callback display:${TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Constants.TRANS_X_GAP, context.resources.displayMetrics).toInt()}")
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Constants.TRANS_X_GAP, context.resources.displayMetrics).toInt()
    }
}