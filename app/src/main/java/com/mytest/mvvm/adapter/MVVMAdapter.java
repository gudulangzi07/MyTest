package com.mytest.mvvm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.recyclerview.widget.RecyclerView;

import com.mytest.R;
import com.mytest.databinding.ItemMvvmBinding;
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
        ItemMvvmBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_mvvm, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemBinding.setViewModel(mLists.get(position));
        holder.itemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mLists != null ? mLists.size() : 0;
    }

    public MVVMModel getItemObject(int position){
        if (position < 0 || position >= mLists.size()){
            throw new RuntimeException("输入的大小不对");
        }
        return mLists.get(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ItemMvvmBinding itemBinding;

        public MyViewHolder(ItemMvvmBinding binding) {
            super(binding.getRoot());
            this.itemBinding = binding;
        }
    }
}
