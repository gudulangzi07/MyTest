package com.mytest.mvvm.vmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mytest.mvvm.activity.MVVMActivity;
import com.mytest.mvvm.db.AppDatabase;
import com.mytest.mvvm.db.model.MVVMDB;
import com.mytest.mvvm.model.MVVMModel;
import com.mytest.utils.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MVVMViewModel extends ViewModel {

    private static final String TAG = MVVMViewModel.class.getSimpleName();

    private MutableLiveData<List<MVVMModel>> lists = new MutableLiveData<>();

    private MVVMActivity mActivity;

    public MVVMViewModel() {

    }

    public void setmActivity(MVVMActivity mActivity) {
        this.mActivity = mActivity;
    }

    public MutableLiveData<List<MVVMModel>> getLists() {
        return lists;
    }

    public void insertData() {
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
//                        MVVMModel mvvmModel = com.mytest.utils.commons.BeanUtils.clone(mvvmdb, MVVMModel.class);
//                        System.out.println("==================" + mvvmModel.getId());
//                        System.out.println("==================" + mvvmModel.getTitle());
//                        System.out.println("==================" + mvvmModel.getCreateTime());


                        MVVMModel mvvmModel = BeanUtils.clone(mvvmdb, MVVMModel.class);
                        System.out.println("==================" + mvvmModel.getId());
                        System.out.println("==================" + mvvmModel.getTitle());
                        System.out.println("==================" + mvvmModel.getCreateTime());
                        lists.getValue().add(mvvmModel);
//                        List<MVVMModel> list = new ArrayList<>();
//                        list.add(mvvmModel);
//
//                        lists.postValue(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });

    }

    public void getData(int page, int pageSize) {
        AppDatabase.getInstance(mActivity)
                .getMVVMDao()
                .getDataPage(page, pageSize)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MVVMDB>>(){
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<MVVMDB> mvvmdbs) {
                        List<MVVMModel> mvvmModels = BeanUtils.batchClone(mvvmdbs, MVVMModel.class);
                        lists.postValue(mvvmModels);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });

    }

    public void delData() {
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
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });

    }

    public void delDataById(Long id) {
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
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

}
