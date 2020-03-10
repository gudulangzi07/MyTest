package com.mytest.widget.layoutmanagerjava;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * 介绍：人人影视效果的Callback
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 16/12/18.
 */

public class OverLayCardItemCallback extends ItemTouchHelper.SimpleCallback {

    protected RecyclerView mRv;
    protected List mDatas;
    protected RecyclerView.Adapter mAdapter;

    public OverLayCardItemCallback(RecyclerView rv, RecyclerView.Adapter adapter, List datas) {
//        this(0,
//                ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
//                rv, adapter, datas);

        this(0,
                ItemTouchHelper.LEFT,
                rv, adapter, datas);
    }

    public OverLayCardItemCallback(int dragDirs, int swipeDirs
            , RecyclerView rv, RecyclerView.Adapter adapter, List datas) {
        super(dragDirs, swipeDirs);
        mRv = rv;
        mAdapter = adapter;
        mDatas = datas;
    }

    //水平方向是否可以被回收掉的阈值
    public float getThreshold(RecyclerView.ViewHolder viewHolder) {
        //2016 12 26 考虑 探探垂直上下方向滑动，不删除卡片，这里参照源码写死0.5f
//        return mRv.getWidth() * /*getSwipeThreshold(viewHolder)*/ 0.5f;
        return mRv.getWidth() * getSwipeThreshold(viewHolder);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //Log.e("swipecard", "onSwiped() called with: viewHolder = [" + viewHolder + "], direction = [" + direction + "]");
        //rollBack(viewHolder);
        //★实现循环的要点
        Object remove = mDatas.remove(viewHolder.getLayoutPosition());
        mDatas.add(0, remove);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        //先根据滑动的dxdy 算出现在动画的比例系数fraction
        double swipValue = Math.sqrt(dX * dX + dY * dY);
        double fraction = swipValue / getThreshold(viewHolder);
        //边界修正 最大为1
        if (fraction > 1) {
            fraction = 1;
        }
        //对每个ChildView进行缩放 位移
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = recyclerView.getChildAt(i);
            //第几层,举例子，count =7， 最后一个TopView（6）是第0层，
            int level = childCount - i - 1;
            if (level > 0) {
                child.setScaleY((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));

                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    child.setScaleX((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
                    child.setTranslationX((float) (CardConfig.TRANS_Y_GAP * level - fraction * CardConfig.TRANS_Y_GAP));
                } else {
                    //child.setTranslationY((float) (mTranslationYGap * (level - 1) - fraction * mTranslationYGap));
                }
            }
        }

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            float value = 1 - Math.abs(dX) / viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(value);
            viewHolder.itemView.setScaleY(value);
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        viewHolder.itemView.setAlpha(1);
        viewHolder.itemView.setScaleY(1);
    }
}
