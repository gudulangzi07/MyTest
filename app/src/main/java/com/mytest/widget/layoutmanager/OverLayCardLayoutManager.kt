package com.mytest.widget.layoutmanager

import android.content.Context
import android.util.Log
import android.util.TypedValue
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

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        Log.d("DEBUG", "onLayoutChildren() called with: recycle=$recycler, state=$state")
        recycler?.let {
            detachAndScrapAttachedViews(it)
            val itemCount = itemCount
            if (itemCount < 1){
                return
            }

            var rightPosition = if (itemCount < maxShowCount){
                0
            }else{
                itemCount - maxShowCount
            }

            //从可见的最底层view开始layout，依次层叠向上绘制
            for (position in rightPosition until itemCount) {
                val view = it.getViewForPosition(position)
                addView(view)
                measureChildWithMargins(view, 0, 0)
                val widthSpace = width - getDecoratedMeasuredWidth(view)
                val heightSpace = height - getDecoratedMeasuredHeight(view)

                layoutDecoratedWithMargins(view, 0 , heightSpace / 2, getDecoratedMeasuredWidth(view),
                        heightSpace / 2 + getDecoratedMeasuredHeight(view))

                val level = itemCount - position - 1
                if (level > 0){
                    //每一层需要Y方向的缩小
                    view.scaleY = 1 - level * Constants.SCALE_GAP
                    //前N层，依次向右位移和X方向的缩小
                    if (level < maxShowCount - 1){
                        view.translationX = (level * getDisplayMetrics(view.context)).toFloat()
                        view.scaleX = 1 - level * Constants.SCALE_GAP
                    }else{
                        //第N层在 向右位移和X方向的缩小的比例与 N-1层保持一致
                        view.translationX = ((level - 1) * getDisplayMetrics(view.context)).toFloat()
                        view.scaleX = 1 - (level - 1) * Constants.SCALE_GAP
                    }
                }
            }

        }
    }

    private fun getDisplayMetrics(context: Context): Int{
        Log.d("getDisplayMetrics", "Manager display:${TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Constants.TRANS_X_GAP, context.resources.displayMetrics).toInt()}")
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Constants.TRANS_X_GAP, context.resources.displayMetrics).toInt()
    }
}