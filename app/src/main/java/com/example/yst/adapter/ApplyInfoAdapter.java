package com.example.yst.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yst.R;
import com.example.yst.bean.ApplyToClublnfo;
import com.facebook.drawee.view.SimpleDraweeView;

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
        holder.infoname.setText(applyToClublnfo.getApply_club_name());
        holder.infotext.setText(applyToClublnfo.getApply_club_reason());
        holder.infotime.setText(applyToClublnfo.getCreatedAt());
        if(!applyToClublnfo.getApplication_status().equals("未审核")){
            holder.redpoint.setVisibility(View.GONE);
        }
        if(!applyToClublnfo.getStu_photo().equals("")){
            holder.img_profile.setImageBitmap(BitmapFactory.decodeFile(applyToClublnfo.getStu_photo()));
        }else{
            holder.img_profile.setImageResource(R.mipmap.poy);
        }
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
        TextView infoname,infotime,infotext,redpoint;
       ImageView img_profile;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            infoname = itemView.findViewById(R.id.infoName);
            infotime = itemView.findViewById(R.id.infoTime);
            infotext = itemView.findViewById(R.id.infotext);
            img_profile = itemView.findViewById(R.id.logo_apply);
            redpoint=itemView.findViewById(R.id.redPoint);
        }
    }
}
