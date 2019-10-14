package com.mytest.mvvm.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mytest.R;
import com.mytest.databinding.ActivityMvvmBinding;
import com.mytest.mvvm.adapter.MVVMAdapter;
import com.mytest.mvvm.vmodel.MVVMViewModel;
import com.mytest.mvvm.vmodel.ToolBarViewModel;

public class MVVMActivity extends AppCompatActivity {

    private ActivityMvvmBinding mvvmBinding;
    private MVVMViewModel viewModel;
    private ToolBarViewModel toolBarViewModel;
    private MVVMAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvvmBinding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);
        viewModel = new MVVMViewModel(this);
        toolBarViewModel = new ToolBarViewModel();
        mvvmBinding.setViewModel(viewModel);
        mvvmBinding.setToolBarModel(toolBarViewModel);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mvvmBinding.recyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new MVVMAdapter(this);
        mvvmBinding.recyclerView.setAdapter(mAdapter);

    }
}
