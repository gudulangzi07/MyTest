package com.mytest.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import com.mytest.R
import com.mytest.widget.layoutmanager.OverLayCardAdapter
import com.mytest.widget.layoutmanager.OverLayCardItemCallback
import com.mytest.widget.layoutmanager.OverLayCardLayoutManager
import kotlinx.android.synthetic.main.activity_over_lay_card.*

/**
 * 重叠的卡片播放
 * */
class OverLayCardActivity: AppCompatActivity() {

    private var adapter: OverLayCardAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_over_lay_card)

        val layoutManager = OverLayCardLayoutManager()
        layoutManager.maxShowCount = 4

        val lists = setData()
        adapter = OverLayCardAdapter(lists)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        val callback = OverLayCardItemCallback(0, ItemTouchHelper.LEFT, recyclerView, adapter!!, lists as MutableList<Any>)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setData(): MutableList<String>{
        val lists = mutableListOf<String>()
        lists.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1583749551623&di=97dc33d935a320cb9d3774105af74082&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201901%2F30%2F20190130160241_nvbdy.thumb.700_0.jpg")
        lists.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1583749601029&di=6c3a49bd30ff95fe83935456a10b66a2&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201809%2F08%2F20180908131132_evbte.jpg")
        lists.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1583749571373&di=cd9fa8529c3a2757c8acad23ff6595b4&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn12%2F318%2Fw512h606%2F20180823%2Ff3cf-hhzsnec6351238.jpg")
        lists.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1583749485778&di=93e2ad64816df8fa5f40a5aa6c7ed708&imgtype=0&src=http%3A%2F%2F00.minipic.eastday.com%2F20170525%2F20170525151413_87ad5f4c0d7a5798892bdf21d6323cf6_6.jpeg")
        lists.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1583749584008&di=c13ad7fb8e26d70201d12ab809853b9b&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201808%2F01%2F20180801011615_vrevr.jpg")
        lists.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1583749584008&di=c13ad7fb8e26d70201d12ab809853b9b&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201808%2F01%2F20180801011615_vrevr.jpg")
        lists.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1583749584008&di=c13ad7fb8e26d70201d12ab809853b9b&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201808%2F01%2F20180801011615_vrevr.jpg")
        lists.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1583749584008&di=c13ad7fb8e26d70201d12ab809853b9b&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201808%2F01%2F20180801011615_vrevr.jpg")
        lists.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1583749584008&di=c13ad7fb8e26d70201d12ab809853b9b&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201808%2F01%2F20180801011615_vrevr.jpg")
        lists.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1583749584008&di=c13ad7fb8e26d70201d12ab809853b9b&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201808%2F01%2F20180801011615_vrevr.jpg")

        return lists
    }
}