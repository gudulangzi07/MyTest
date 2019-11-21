package com.mytest.utils.commons;

public interface DynaClass {
    String getName();

    DynaProperty getDynaProperty(String var1);

    DynaProperty[] getDynaProperties();

    DynaBean newInstance() throws IllegalAccessException, InstantiationException;
}
