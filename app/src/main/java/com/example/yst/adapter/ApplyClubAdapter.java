package com.example.yst.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yst.R;
import com.example.yst.bean.ApplyToClublnfo;
import com.example.yst.bean.Club;

import java.util.List;

public class ApplyClubAdapter extends RecyclerView.Adapter<ApplyClubAdapter.ViewHolder> implements View.OnClickListener{
    public Context mcontext;
    List<Club> mapplyClubList;
    //声明自定义的监听接口
    private ApplyClubAdapter.OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener1 = null;
    @Override
    public void onClick(View v) {
        mOnRecyclerviewItemClickListener1.onItemClickListener(v, ((int) v.getTag()));
    }
    public ApplyClubAdapter(Context context, List<Club> ApplyClubList, ApplyClubAdapter.OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.mcontext = context;
        this.mapplyClubList=ApplyClubList;
        this.mOnRecyclerviewItemClickListener1 = mOnRecyclerviewItemClickListener;
    }
    public void setApplyClubList(List<Club> ApplyClubList) {
        mapplyClubList = ApplyClubList;
    }
    @NonNull
    @Override
    public ApplyClubAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_club_apply,parent,false);
        ApplyClubAdapter.ViewHolder mholder = new ApplyClubAdapter.ViewHolder(view);
        //这里 我们可以拿到点击的item的view 对象，所以在这里给view设置点击监听，
        view.setOnClickListener(this);
        return mholder;
    }
    @Override
    public void onBindViewHolder(@NonNull ApplyClubAdapter.ViewHolder holder, int position) {
        final Club club= mapplyClubList.get(position);
        holder.applicantClubName.setText(club.getClub_name());
        holder.applicantTime.setText(club.getCreatedAt().substring(0,10));
        holder.applicantState.setText(club.getAudit_state());
        holder.itemView.setTag(position);//给view设置tag以作为参数传递到监听回调方法中

    }

    @Override
    public int getItemCount() {
        return mapplyClubList.size() == 0 ? 0 : mapplyClubList.size();
    }
    public interface OnRecyclerviewItemClickListener {
        void onItemClickListener(View v,int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView applicantClubName,applicantTime,applicantState;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            applicantClubName = itemView.findViewById(R.id.applicantClubName);
            applicantTime = itemView.findViewById(R.id.applicantTime);
            applicantState = itemView.findViewById(R.id.applicantState);
        }
    }
}
