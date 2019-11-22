package com.mytest.utils.commons;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class BeanUtils {

    public static <T, E> E clone(T source, Class<E> classType){
        if (source == null) {
            return null;
        }
        E targetInstance = null;
        try {
            targetInstance = classType.newInstance();
            copyProperties(source, targetInstance);
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

    private static void copyProperties(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException {
        BeanUtilsBean.getInstance().copyProperties(dest, orig);
    }
}
