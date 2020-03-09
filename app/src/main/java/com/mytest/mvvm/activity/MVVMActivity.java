package com.mytest.mvvm.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mytest.R;
import com.mytest.databinding.ActivityMvvmBinding;
import com.mytest.mvvm.adapter.MVVMAdapter;
import com.mytest.mvvm.db.model.MVVMDB;
import com.mytest.mvvm.model.MVVMModel;
import com.mytest.mvvm.vmodel.MVVMViewModel;
import com.mytest.mvvm.vmodel.ToolBarViewModel;

import java.util.ArrayList;
import java.util.List;

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
        activityMvvmBinding.setToolBarModel(toolBarViewModel);

        // 初始化RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        activityMvvmBinding.recyclerView.setLayoutManager(layoutManager);
        mvvmAdapter = new MVVMAdapter(this);
        activityMvvmBinding.recyclerView.setAdapter(mvvmAdapter);

        mvvmViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MVVMViewModel.class);
        mvvmViewModel.setmActivity(this);
        activityMvvmBinding.setMvvmViewModel(mvvmViewModel);

        mvvmViewModel.getData(0, 10);

        mvvmViewModel.getLists().observe(this, lists -> {
            mvvmAdapter.setmLists(lists);
            mvvmAdapter.notifyDataSetChanged();
        });

        activityMvvmBinding.btnDelete.setOnClickListener(view -> {
            MVVMModel mvvmModel = mvvmAdapter.getItemObject(0);
            mvvmViewModel.delDataById(mvvmModel.getId());
        });

    }

}
