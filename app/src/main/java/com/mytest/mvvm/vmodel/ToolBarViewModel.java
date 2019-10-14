package com.mytest.mvvm.vmodel;

import com.mytest.R;
import com.mytest.mvvm.model.ToolBarModel;

public class ToolBarViewModel {

    public ToolBarModel getToolBar(){
        ToolBarModel toolBarModel = new ToolBarModel();
        toolBarModel.setBackResId(R.mipmap.back_arraw);
        toolBarModel.setLogoResId(R.mipmap.ic_launcher);
        toolBarModel.setTitle("标题");
        return toolBarModel;
    }
}