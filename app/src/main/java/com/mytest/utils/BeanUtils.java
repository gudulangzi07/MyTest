package com.mytest.utils;

import com.mytest.mvvm.db.model.MVVMDB;
import com.mytest.mvvm.model.MVVMModel;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BeanUtils{

    public static <T, E> E clone(T source, Class<E> classType){
        if (source == null) {
            return null;
        }
        E targetInstance = null;
        try {
            targetInstance = classType.newInstance();
            org.apache.commons.beanutils.BeanUtils.copyProperties(targetInstance, source);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e){
            throw new RuntimeException(e);
        }
        return targetInstance;
    }

    /**
     * 拷贝数组对象
     * @param sourceList
     * @param classType
     * @return
     */
    public static <T, E> List<E> batchClone(List<T> sourceList, Class<E> classType) {
        if (sourceList == null) {
            return null;
        }
        List<E> result = new ArrayList<>();
        int size = sourceList.size();
        for (int i = 0; i < size; i++) {
            result.add(clone(sourceList.get(i), classType));
        }
        return result;
    }

    public static void main(String[] args) {
        MVVMDB mvvmdb = new MVVMDB();
        mvvmdb.setId(System.currentTimeMillis());
        mvvmdb.setTitle("标题" + Math.random() * 10);
        mvvmdb.setCreateTime(new Date());

        MVVMModel mvvmModel = clone(mvvmdb, MVVMModel.class);

        System.out.println("=============" + mvvmModel.getId());
        System.out.println("=============" + mvvmModel.getTitle());
        System.out.println("=============" + mvvmModel.getCreateTime());
    }

}
