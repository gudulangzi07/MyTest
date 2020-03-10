package com.mytest.widget.layoutmanager

import android.R.attr.radius
import android.graphics.drawable.Drawable
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mytest.R
import jp.wasabeef.glide.transformations.BlurTransformation


class OverLayCardAdapter(data: MutableList<String>):
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_over_lay_card, data) {

    override fun convert(helper: BaseViewHolder, item: String?) {
        Log.d("OverLayCardAdapter", "执行加载图片：${helper.layoutPosition}")
        item?.let {
            Glide.with(mContext).load(it).into(helper.getView(R.id.vImage))
//            if (helper.adapterPosition == 1){
//
//            }else{
//                Glide.with(mContext).load(it)
//                        .apply(RequestOptions.bitmapTransform(BlurTransformation()))
//                        .into(object: SimpleTarget<Drawable>(){
//                            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
//                                helper.setImageDrawable(R.id.vImage, resource)
//                            }
//
//                        })
//
//            }
        }
    }
}