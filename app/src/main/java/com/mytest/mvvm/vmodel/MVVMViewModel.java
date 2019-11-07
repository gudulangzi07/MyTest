package com.mytest.mvvm.vmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mytest.mvvm.activity.MVVMActivity;
import com.mytest.mvvm.db.AppDatabase;
import com.mytest.mvvm.db.model.MVVMDB;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MVVMViewModel extends ViewModel{
    private MVVMActivity mActivity;

    public MVVMViewModel() {

    }

    public void setmActivity(MVVMActivity mActivity) {
        this.mActivity = mActivity;
    }

    public void insertData(MVVMDB mvvmdb) {
        System.out.println("==============insertData===" + Thread.currentThread());
        AppDatabase.getInstance(mActivity)
                .getMVVMDao()
                .insertData(mvvmdb)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Long>(){
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Long aLong) {
                        System.out.println("==============insertData Success===" + Thread.currentThread());
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("==============insertData Error===" + Thread.currentThread());
                    }
                });
    }

    public LiveData<List<MVVMDB>> getData(int page, int pageSize) {

        long start = System.currentTimeMillis();
        LiveData<List<MVVMDB>> listLiveData = AppDatabase.getInstance(mActivity)
                .getMVVMDao()
                .getDataPage(page, pageSize);
        long end = System.currentTimeMillis();

        Log.e("getData", "===============耗时：" + (end - start) / 100 + "秒");

        return listLiveData;
    }

}
