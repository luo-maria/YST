package com.example.yst.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yst.Activity.Club_detailActivity;
import com.example.yst.R;
import com.example.yst.bean.Club;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;


public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.ViewHolder> implements View.OnClickListener{
    public Context mcontext;
    List<Club> mClubList;
    //声明自定义的监听接口
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;
    @Override
    public void onClick(View v) {
        //将监听传递给自定义接口
        mOnRecyclerviewItemClickListener.onItemClickListener(v, ((int) v.getTag()));

    }


    public interface OnRecyclerviewItemClickListener {
        void onItemClickListener(View v,int position);
        void onItemLongClick(View view , int pos);

    }
    public ClubAdapter(Context context,List<Club> ClubList,OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.mcontext = context;
        this.mClubList=ClubList;
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }
    public void setClubList(List<Club> clubList) {
        mClubList = clubList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.club_lists,parent,false);
        ViewHolder mholder = new ViewHolder(view);
        //这里 我们可以拿到点击的item的view 对象，所以在这里给view设置点击监听，
        view.setOnClickListener(this);
        return mholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder mholder, int position) {
        final Club club= mClubList.get(position);
        mholder.club_name.setText(club.getClub_name());
        mholder.clublevel.setText(club.getClub_rank());
        mholder.clubcampus.setText(club.getClub_campus());
        mholder.clubkind.setText(club.getClub_category());
        mholder.clubintos.setText(club.getClub_intro());
        if(!club.getLogo_url().equals("")){
            mholder.logo.setImageBitmap(BitmapFactory.decodeFile(club.getLogo_url()));
        }else{
            mholder.logo.setImageResource(R.mipmap.san);
        }
        mholder.itemView.setTag(position);//给view设置tag以作为参数传递到监听回调方法中
        mholder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnRecyclerviewItemClickListener.onItemLongClick(mholder.itemView,position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mClubList.size() == 0 ? 0 : mClubList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView club_name,clublevel,clubcampus,clubintos,clubkind;
        ImageView logo;
        public ViewHolder(View itemView) {
            super(itemView);
            club_name = itemView.findViewById(R.id.clubname);
            clublevel = itemView.findViewById(R.id.clublevel);
            clubcampus = itemView.findViewById(R.id.clubcampus);
            clubkind = itemView.findViewById(R.id.clubkind);
            clubintos = itemView.findViewById(R.id.clubintos);
            logo=itemView.findViewById(R.id.logo);
        }
    }
}
