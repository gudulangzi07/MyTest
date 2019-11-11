package com.mytest.utils;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;

public class BeanUtils{
//    public static <T> T toObject(Object object, Class<T> clazz) throws InstantiationException, IllegalAccessException {
//        if (object == null){
//            return null;
//        }
//        if (clazz == null){
//            throw new IllegalArgumentException("parameter clazz should not be null");
//        }
//        Class fromClass = object.getClass();
//        Object toObject = clazz.newInstance();
//        Field[] fromFields = fromClass.getDeclaredFields();
//        Field[] toFields = toObject.getClass().getDeclaredFields();
//        for (int i = 0; i < fromFields.length; i++){
//            Field fromField = fromFields[i];
//            fromField.setAccessible(true);
//            for (int j = 0; j < toFields.length; j++){
//                Field toField = toFields[j];
//                toField.setAccessible(true);
//                if (fromField.getName().equals(toField.getName())){
//
//                }
//            }
//        }
//
//        return (T) toObject;
//    }

    public static <T, E> E clone(T source, Class<E> classType) {

        if (source == null) {
            return null;
        }
        E targetInstance = null;
        try {
            targetInstance = classType.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        org.springframework.beans.BeanUtils.copyProperties(source, targetInstance);
        return targetInstance;
    }

    public static void main(String[] args) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(1L);
        studentDTO.setName("张三");
        studentDTO.setAge(27);

        StudentDO studentDO = clone(studentDTO, StudentDO.class);
        System.out.println("=============" + studentDO.getId() + "==" + studentDO.getName() + "==" + studentDO.getAge());

    }
}
