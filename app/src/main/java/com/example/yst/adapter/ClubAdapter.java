package com.example.yst.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yst.Activity.Club_detailActivity;
import com.example.yst.R;
import com.example.yst.bean.Club;

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

    }
    public ClubAdapter(Context context,List<Club> ClubList,OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.mcontext = context;
        this.mClubList=ClubList;
//        inflater = LayoutInflater.from(context);
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
        mholder.itemView.setTag(position);//给view设置tag以作为参数传递到监听回调方法中
//        mholder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mcontext, Club_detailActivity.class);
//                if (! mClubList.isEmpty()) {
//                    Club club = new Club();
//                    club.setObjectId( mClubList.get(position).getObjectId());
//                    club_id1=mClubList.get(position).getObjectId();
//                    intent.putExtra("clubid",club_id1);
//                }
//                mcontext.startActivity(intent);
//                ((Activity)mcontext).finish();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mClubList.size() == 0 ? 0 : mClubList.size();
//        return arr2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView club_name,clublevel,clubcampus,clubintos,clubkind;
        public ViewHolder(View itemView) {
            super(itemView);
            club_name = itemView.findViewById(R.id.clubname);
            clublevel = itemView.findViewById(R.id.clublevel);
            clubcampus = itemView.findViewById(R.id.clubcampus);
            clubkind = itemView.findViewById(R.id.clubkind);
            clubintos = itemView.findViewById(R.id.clubintos);
        }
    }
}
