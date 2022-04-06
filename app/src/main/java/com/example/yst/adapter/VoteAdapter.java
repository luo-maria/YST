package com.example.yst.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yst.R;
import com.example.yst.bean.Vote;

import java.util.List;

public class VoteAdapter extends RecyclerView.Adapter<VoteAdapter.ViewHolder> implements View.OnClickListener{
    public Context mcontext;
    private List<Vote> mVoteList;
    //声明自定义的监听接口
    private VoteAdapter.OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;
    @Override
    public void onClick(View v) {
        //将监听传递给自定义接口
        mOnRecyclerviewItemClickListener.onItemClickListener(v, ((int) v.getTag()));
    }
    public interface OnRecyclerviewItemClickListener {
        void onItemClickListener(View v,int position);

    }
    public VoteAdapter(Context context, List<Vote> VoteList, VoteAdapter.OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.mcontext = context;
        this.mVoteList=VoteList;
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }
    public void setVoteList(List<Vote> voteList) {
        mVoteList = voteList;
    }
    @Override
    public VoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_vote,parent,false);
        VoteAdapter.ViewHolder mholder = new VoteAdapter.ViewHolder(view);
        //这里 我们可以拿到点击的item的view 对象，所以在这里给view设置点击监听，
        view.setOnClickListener(this);
        return mholder;
    }
    @Override
    public void onBindViewHolder(VoteAdapter.ViewHolder mholder, int position) {
        final Vote vote= mVoteList.get(position);
        mholder.vote_head.setText(vote.getHeadline());
        mholder.vote_endtime.setText(vote.getVoteEnd().getDate());
        mholder.itemView.setTag(position);//给view设置tag以作为参数传递到监听回调方法中
    }
    @Override
    public int getItemCount() {
        return mVoteList.size() == 0 ? 0 : mVoteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView vote_head,vote_endtime;
        public ViewHolder(View itemView) {
            super(itemView);
            vote_head = itemView.findViewById(R.id.vote_head);
            vote_endtime= itemView.findViewById(R.id.vote_endtime);
        }
    }
}
