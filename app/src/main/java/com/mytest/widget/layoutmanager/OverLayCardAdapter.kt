package com.mytest.widget.layoutmanager

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mytest.R


class OverLayCardAdapter(data: MutableList<String>):
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_over_lay_card, data) {

    override fun convert(holder: BaseViewHolder, item: String) {
        Glide.with(context).load(item).into(holder.getView(R.id.vImage))
    }

}