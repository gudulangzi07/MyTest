package com.mytest.mvvm.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mytest.R;
import com.mytest.databinding.ActivityMvvmBinding;
import com.mytest.mvvm.adapter.MVVMAdapter;
import com.mytest.mvvm.model.MVVMModel;
import com.mytest.mvvm.vmodel.ToolBarViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MVVMActivity extends AppCompatActivity {

    private ActivityMvvmBinding activityMvvmBinding;

    private ToolBarViewModel toolBarViewModel;

    private MVVMAdapter mvvmAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMvvmBinding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);

        activityMvvmBinding.toolbar.setNavigationOnClickListener(view-> finish());

        toolBarViewModel = new ToolBarViewModel();
        activityMvvmBinding.setToolBarModel(toolBarViewModel);

        // 初始化RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        activityMvvmBinding.recyclerView.setLayoutManager(layoutManager);
        mvvmAdapter = new MVVMAdapter(this);
        activityMvvmBinding.recyclerView.setAdapter(mvvmAdapter);

        List<MVVMModel> lists = new ArrayList<>();
        MVVMModel mvvmModel = new MVVMModel();
        mvvmModel.setId(1L);
        mvvmModel.setTitle("这是一个");
        mvvmModel.setCreateTime(new Date());
        lists.add(mvvmModel);

        mvvmAdapter.setmLists(lists);
    }

}
