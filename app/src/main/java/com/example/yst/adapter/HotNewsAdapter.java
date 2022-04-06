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
import com.example.yst.bean.News;

import java.util.List;

public class HotNewsAdapter extends RecyclerView.Adapter<HotNewsAdapter.ViewHolder> implements View.OnClickListener{
    public Context mcontext;
    private List<News> mNewsList;
    //声明自定义的监听接口
    private HotNewsAdapter.OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;
    @Override
    public void onClick(View v) {
        //将监听传递给自定义接口
        mOnRecyclerviewItemClickListener.onItemClickListener(v, ((int) v.getTag()));
    }
    public interface OnRecyclerviewItemClickListener {
        void onItemClickListener(View v,int position);

    }
    public HotNewsAdapter(Context context, List<News> NewsList, HotNewsAdapter.OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.mcontext = context;
        this.mNewsList=NewsList;
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }
    public void setNewsList(List<News> newsList) {
        mNewsList = newsList;
    }
    @Override
    public HotNewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_news_hot,parent,false);
        HotNewsAdapter.ViewHolder mholder = new HotNewsAdapter.ViewHolder(view);
        //这里 我们可以拿到点击的item的view 对象，所以在这里给view设置点击监听，
        view.setOnClickListener(this);
        return mholder;
    }
    @Override
    public void onBindViewHolder(HotNewsAdapter.ViewHolder mholder, int position) {
        final News news= mNewsList.get(position);
        mholder.news_titlehot.setText(news.getNews_title());
        mholder.news_authorhot.setText(news.getClub_name());
        mholder.news_heathot1.setText(String.valueOf(news.getHeat()));
        mholder.itemView.setTag(position);//给view设置tag以作为参数传递到监听回调方法中
    }
    @Override
    public int getItemCount() {
        return mNewsList.size() == 0 ? 0 : mNewsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView news_titlehot,news_authorhot,news_heathot1;
        public ViewHolder(View itemView) {
            super(itemView);
            news_titlehot = itemView.findViewById(R.id.news_titlehot);
            news_authorhot = itemView.findViewById(R.id.news_authorhot);
            news_heathot1 = itemView.findViewById(R.id.news_heathot1);
        }
    }
}
