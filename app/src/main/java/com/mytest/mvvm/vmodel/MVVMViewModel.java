package com.mytest.mvvm.vmodel;

import com.mytest.mvvm.activity.MVVMActivity;
import com.mytest.mvvm.db.AppDatabase;
import com.mytest.mvvm.db.model.MVVMDB;

public class MVVMViewModel {
    private MVVMActivity mActivity;

    public MVVMViewModel(MVVMActivity mActivity) {
        this.mActivity = mActivity;
    }

    public void insertData(MVVMDB mvvmdb){
        AppDatabase.getInstance(mActivity).getMVVMDao().insertData(mvvmdb);
    }

}
