package com.mytest.mvvm.model;

import java.io.Serializable;

public class ToolBarModel implements Serializable {
    private String title;
    private int backResId;
    private int logoResId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBackResId() {
        return backResId;
    }

    public void setBackResId(int backResId) {
        this.backResId = backResId;
    }

    public int getLogoResId() {
        return logoResId;
    }

    public void setLogoResId(int logoResId) {
        this.logoResId = logoResId;
    }
}
