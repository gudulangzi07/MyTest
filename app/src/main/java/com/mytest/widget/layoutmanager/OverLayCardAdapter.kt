package com.mytest.widget.layoutmanager

import android.util.Log
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mytest.R

class OverLayCardAdapter(data: MutableList<String>):
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_over_lay_card, data) {

    override fun convert(helper: BaseViewHolder, item: String?) {
        Log.d("OverLayCardAdapter", "执行加载图片：${helper.layoutPosition}")
        item?.let {
            Glide.with(mContext).load(it).into(helper.getView(R.id.vImage))
        }
    }
}