package com.mytest.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.mytest.R
import com.mytest.adapter.MyFragmentAdapter
import com.mytest.fragment.MyFirstFragment
import com.mytest.fragment.MySecondFragment
import kotlinx.android.synthetic.main.activity_my_fragment.*

class MyFragmentActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_fragment)

        vTabLayout.addTab(vTabLayout.newTab().setText("标签一"), true)
        vTabLayout.addTab(vTabLayout.newTab().setText("标签二"))

        val fragments = mutableListOf<Fragment>()
        fragments.add(MyFirstFragment())
        fragments.add(MySecondFragment())

        vViewPager.adapter = MyFragmentAdapter(this, fragments)

        TabLayoutMediator(vTabLayout, vViewPager, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = vTabLayout.getTabAt(position)?.text
        }).attach()
    }
}