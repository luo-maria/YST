package com.example.yst.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yst.R;
import com.example.yst.bean.Activities;


import java.util.List;


public class HomeActivityAdapter extends RecyclerView.Adapter<HomeActivityAdapter.ViewHolder> implements View.OnClickListener{

    public Context context;
    private List<Activities> activityLists;

    //声明自定义的监听接口
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;


    @Override
    public void onClick(View v) {
        //将监听传递给自定义接口
        mOnRecyclerviewItemClickListener.onItemClickListener(v, ((int) v.getTag()));
    }

    public interface OnRecyclerviewItemClickListener {
        void onItemClickListener(View v, int position);
    }

    public HomeActivityAdapter(Context context, List<Activities> ActivityList, OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.context = context;
        this.activityLists = ActivityList;
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }

    public void setActivityLists(List<Activities> activityList) {
        activityLists = activityList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_activity_home,parent,false);
        ViewHolder currentHolder = new ViewHolder(view);
        //这里 我们可以拿到点击的item的view 对象，所以在这里给view设置点击监听，
        view.setOnClickListener(this);
        return currentHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Activities activity= activityLists.get(position);

        holder.clubName.setText(activity.getClub_name());
        holder.clubLogo.setImageBitmap(BitmapFactory.decodeFile(activity.getClub_logo()));
        holder.activityName.setText(activity.getActivity_name());
        holder.activityIntro.setText(activity.getActivity_info());
        holder.activityImage.setImageBitmap(BitmapFactory.decodeFile(activity.getActivity_imgurl()));
        holder.itemView.setTag(position);
        //给view设置tag以作为参数传递到监听回调方法中
    }

    @Override
    public int getItemCount() {
        return activityLists.size() == 0 ? 0 : activityLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView activityName, clubName,activityIntro;
        ImageView activityImage,clubLogo;
        public ViewHolder(View itemView) {
            super(itemView);
            clubName = itemView.findViewById(R.id.clubName);
            activityName = itemView.findViewById(R.id.activityName);
            activityIntro = itemView.findViewById(R.id.activityIntro);
            activityImage=itemView.findViewById(R.id.activityImage);
            clubLogo=itemView.findViewById(R.id.clubLogo);

        }
    }
}
