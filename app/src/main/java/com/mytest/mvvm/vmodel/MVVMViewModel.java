package com.mytest.mvvm.vmodel;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mytest.mvvm.activity.MVVMActivity;
import com.mytest.mvvm.db.AppDatabase;
import com.mytest.mvvm.db.model.MVVMDB;

import java.util.Date;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MVVMViewModel extends ViewModel {

    private static final String TAG = MVVMViewModel.class.getSimpleName();

    private MVVMActivity mActivity;

    public MVVMViewModel() {

    }

    public void setmActivity(MVVMActivity mActivity) {
        this.mActivity = mActivity;
    }

    public void insertData() {
        Log.e(TAG, "==============insertData start===" + Thread.currentThread());

        MVVMDB mvvmdb = new MVVMDB();
        mvvmdb.setId(System.currentTimeMillis());
        mvvmdb.setTitle("标题" + Math.random() * 10);
        mvvmdb.setCreateTime(new Date());

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

        Log.e(TAG, "==============insertData end===" + Thread.currentThread());
    }

    public LiveData<List<MVVMDB>> getData(int page, int pageSize) {

        long start = System.currentTimeMillis();
        LiveData<List<MVVMDB>> listLiveData = AppDatabase.getInstance(mActivity)
                .getMVVMDao()
                .getDataPage(page, pageSize);

        long end = System.currentTimeMillis();

        Log.e(TAG, "===============耗时：" + (end - start) +"秒");

        return listLiveData;
    }

    public void delData() {

        long start = System.currentTimeMillis();
        AppDatabase.getInstance(mActivity)
                .getMVVMDao()
                .delAllData()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>(){
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer aLong) {
                        Log.e(TAG, "==============delData Success===" + Thread.currentThread());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "==============delData Error===" + Thread.currentThread());
                    }
                });
        long end = System.currentTimeMillis();

        Log.e(TAG, "===============耗时：" + (end - start) / 100 + "秒");

    }

    public void delDataById(Long id) {

        long start = System.currentTimeMillis();
        AppDatabase.getInstance(mActivity)
                .getMVVMDao()
                .delDataById(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>(){
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer aLong) {
                        Log.e(TAG, "==============delDataById Success===" + Thread.currentThread());
                        long end = System.currentTimeMillis();
                        Log.e(TAG, "=======================" + (end - start) / 100 + "秒");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "==============delDataById Error===" + Thread.currentThread());
                    }
                });
        long end = System.currentTimeMillis();

        Log.e(TAG, "===============耗时：" + (end - start) / 100 + "秒");

    }

}
