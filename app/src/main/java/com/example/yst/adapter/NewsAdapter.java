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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> implements View.OnClickListener{
    public Context mcontext;
    private List<News> mNewsList;
    //声明自定义的监听接口
    private NewsAdapter.OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener = null;
    @Override
    public void onClick(View v) {
        //将监听传递给自定义接口
        mOnRecyclerviewItemClickListener.onItemClickListener(v, ((int) v.getTag()));
    }
    public interface OnRecyclerviewItemClickListener {
        void onItemClickListener(View v,int position);

    }
    public NewsAdapter(Context context, List<News> NewsList, NewsAdapter.OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener) {
        this.mcontext = context;
        this.mNewsList=NewsList;
        this.mOnRecyclerviewItemClickListener = mOnRecyclerviewItemClickListener;
    }
    public void setNewsList(List<News> newsList) {
        mNewsList = newsList;
    }
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_news_home,parent,false);
        NewsAdapter.ViewHolder mholder = new NewsAdapter.ViewHolder(view);
        //这里 我们可以拿到点击的item的view 对象，所以在这里给view设置点击监听，
        view.setOnClickListener(this);
        return mholder;
    }
    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder mholder, int position) {
        final News news= mNewsList.get(position);
            mholder.news_title.setText(news.getNews_title());
            mholder.news_author.setText(news.getClub_name());
            mholder.news_create_time.setText(news.getCreatedAt().substring(0,10));
            mholder.news_heat.setText(String.valueOf(news.getHeat()));
        if(!news.getNews_image().isEmpty()){
            mholder.new_image.setImageBitmap(BitmapFactory.decodeFile(news.getNews_image()));
        }else{
//            mholder.new_image.setImageResource(R.mipmap.san);
        }
        mholder.itemView.setTag(position);//给view设置tag以作为参数传递到监听回调方法中
    }
    @Override
    public int getItemCount() {
        return mNewsList.size() == 0 ? 0 : mNewsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView news_title,news_author,news_create_time,news_heat;
        ImageView new_image;
        public ViewHolder(View itemView) {
            super(itemView);
            news_title = itemView.findViewById(R.id.news_title);
            news_author = itemView.findViewById(R.id.news_author);
            news_create_time = itemView.findViewById(R.id.news_create_time);
            new_image = itemView.findViewById(R.id.new_image);
            news_heat = itemView.findViewById(R.id.news_heat);
        }
    }
}
