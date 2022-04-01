package com.example.yst.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yst.R;
import com.example.yst.bean.ApplyToClublnfo;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MyApplyInfoAdapter extends RecyclerView.Adapter<MyApplyInfoAdapter.ViewHolder> implements View.OnClickListener{
    public Context mcontext;
    List<ApplyToClublnfo> mapplyToClublnfoList;
    //声明自定义的监听接口
    private MyApplyInfoAdapter.OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener1 = null;
    @Override
    public void onClick(View v) {
        mOnRecyclerviewItemClickListener1.onItemClickListener(v, ((int) v.getTag()));

    }
    public MyApplyInfoAdapter(Context context, List<ApplyToClublnfo> ApplyToClublnfoList, MyApplyInfoAdapter.OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.mcontext = context;
        this.mapplyToClublnfoList=ApplyToClublnfoList;
        this.mOnRecyclerviewItemClickListener1 = mOnRecyclerviewItemClickListener;
    }
    public void setApplyToClublnfoList(List<ApplyToClublnfo> ApplyToClublnfoList) {
        mapplyToClublnfoList=ApplyToClublnfoList;
    }
    @NonNull
    @Override
    public MyApplyInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_my_application_messages,parent,false);
        MyApplyInfoAdapter.ViewHolder mholder = new MyApplyInfoAdapter.ViewHolder(view);
        //这里 我们可以拿到点击的item的view 对象，所以在这里给view设置点击监听，
        view.setOnClickListener(this);
        return mholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ApplyToClublnfo applyToClublnfo= mapplyToClublnfoList.get(position);
        holder.applystatus.setText(applyToClublnfo.getApplication_status());
        holder.applicantTime.setText(applyToClublnfo.getCreatedAt().substring(0,10));
        holder.applicantClubName.setText(applyToClublnfo.getClub_name());
        holder.itemView.setTag(position);//给view设置tag以作为参数传递到监听回调方法中
    }

    @Override
    public int getItemCount() {
        return mapplyToClublnfoList.size() == 0 ? 0 : mapplyToClublnfoList.size();
    }


    public interface OnRecyclerviewItemClickListener {
        void onItemClickListener(View v,int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView applyclubname,applystatus,applicantTime,info_my,applicantClubName;
        LinearLayout applicantBack;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            applyclubname = itemView.findViewById(R.id.infoName);
            applystatus=itemView.findViewById(R.id.applicantState);
            applicantTime=itemView.findViewById(R.id.applicantTime);
            info_my=itemView.findViewById(R.id.info_my);
            applicantBack=itemView.findViewById(R.id.applicantBack);
            applicantClubName=itemView.findViewById(R.id.applicantClubName);
        }
    }
}
