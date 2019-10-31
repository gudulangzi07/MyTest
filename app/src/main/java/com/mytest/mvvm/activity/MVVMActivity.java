package com.mytest.mvvm.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mytest.R;
import com.mytest.databinding.ActivityMvvmBinding;
import com.mytest.mvvm.adapter.MVVMAdapter;
import com.mytest.mvvm.vmodel.MVVMViewModel;
import com.mytest.mvvm.vmodel.ToolBarViewModel;

public class MVVMActivity extends AppCompatActivity {

    private ActivityMvvmBinding activityMvvmBinding;

    private ToolBarViewModel toolBarViewModel;
    private MVVMViewModel mvvmViewModel;

    private MVVMAdapter mvvmAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMvvmBinding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);

        activityMvvmBinding.toolbar.setNavigationOnClickListener(view-> finish());

        toolBarViewModel = new ToolBarViewModel();
        mvvmViewModel = new MVVMViewModel(this);
        activityMvvmBinding.setToolBarModel(toolBarViewModel);
        activityMvvmBinding.setViewModel(mvvmViewModel);



        // 初始化RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        activityMvvmBinding.recyclerView.setLayoutManager(layoutManager);
        mvvmAdapter = new MVVMAdapter(this);
        activityMvvmBinding.recyclerView.setAdapter(mvvmAdapter);
    }

}
