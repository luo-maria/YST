package com.example.yst.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yst.R;
import com.example.yst.bean.ApplyToClublnfo;

import java.util.List;

public class ApplyInfoAdapter extends RecyclerView.Adapter<ApplyInfoAdapter.ViewHolder> implements View.OnClickListener{
    public Context mcontext;
    List<ApplyToClublnfo> mapplyToClublnfoList;
    //声明自定义的监听接口
    private ApplyInfoAdapter.OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener1 = null;
    @Override
    public void onClick(View v) {
        mOnRecyclerviewItemClickListener1.onItemClickListener(v, ((int) v.getTag()));

    }
    public ApplyInfoAdapter(Context context, List<ApplyToClublnfo> ApplyToClublnfoList, OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.mcontext = context;
        this.mapplyToClublnfoList=ApplyToClublnfoList;
//        inflater = LayoutInflater.from(context);
        this.mOnRecyclerviewItemClickListener1 = mOnRecyclerviewItemClickListener;
    }
    public void setApplyToClublnfoList(List<ApplyToClublnfo> ApplyToClublnfoList) {
        mapplyToClublnfoList=ApplyToClublnfoList;
    }
    @NonNull
    @Override
    public ApplyInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.infoitem,parent,false);
        ApplyInfoAdapter.ViewHolder mholder = new ApplyInfoAdapter.ViewHolder(view);
        //这里 我们可以拿到点击的item的view 对象，所以在这里给view设置点击监听，
        view.setOnClickListener(this);
        return mholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ApplyToClublnfo applyToClublnfo= mapplyToClublnfoList.get(position);

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public interface OnRecyclerviewItemClickListener {
        void onItemClickListener(View v,int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
