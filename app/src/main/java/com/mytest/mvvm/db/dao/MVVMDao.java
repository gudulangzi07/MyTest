package com.mytest.mvvm.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mytest.mvvm.db.model.MVVMDB;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface MVVMDao {

    //onConflict = OnConflictStrategy.REPLACE的意思是如果主键重复，用新的数据替换老数据
    //插入聊天记录的请求时间记录信息
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertData(MVVMDB mvvmdb);

    //删除请求聊天记录的请求时间记录信息
    @Delete
    Single<Integer> delData(MVVMDB mvvmdb);

    //删除请求聊天记录的请求时间记录信息
    @Query("delete from mvvm_test_db where id = :id")
    Single<Integer> delDataById(Long id);

    //删除请求聊天记录的请求时间记录信息
    @Query("select * from mvvm_test_db group by id order by id LIMIT :offset, :size")
    LiveData<List<MVVMDB>> getDataPage(int offset, int size);
}
