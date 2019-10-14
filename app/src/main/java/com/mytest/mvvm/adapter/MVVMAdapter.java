package com.mytest.mvvm.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mytest.mvvm.model.MVVMModel;

import java.util.List;

public class MVVMAdapter extends RecyclerView.Adapter<MVVMAdapter.MyViewHolder> {

    private Context mContext;
    private List<MVVMModel> mLists;

    public MVVMAdapter(Context context) {
        this.mContext = context;
    }

    public void setmLists(List<MVVMModel> mLists) {
        this.mLists = mLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mLists != null ? mLists.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
