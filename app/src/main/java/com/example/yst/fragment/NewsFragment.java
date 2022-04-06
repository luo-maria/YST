package com.example.yst.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yst.Activity.Club_detailActivity;
import com.example.yst.Activity.News_detailActivity;
import com.example.yst.R;
import com.example.yst.adapter.ClubAdapter;
import com.example.yst.adapter.HotNewsAdapter;
import com.example.yst.adapter.NewsAdapter;
import com.example.yst.bean.Club;
import com.example.yst.bean.News;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    private RecyclerView recyclerViewnews,recyclerViewnewshot;
    private NewsAdapter newsAdapter;
    private HotNewsAdapter hotNewsAdapter;
    private List<News> newsList=new ArrayList<>();
    private List<News> newsListhot=new ArrayList<>();
    private String news_id;
    private Integer heat;
    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerViewnews=view.findViewById(R.id.recyclerview_news);
        recyclerViewnewshot=view.findViewById(R.id.recyclerview_new_hot);
        initView();
        return view;
    }

    private void initView() {
        newsAdapter = new NewsAdapter(getActivity(),newsList,onRecyclerviewItemClickListener);
        recyclerViewnews.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        hotNewsAdapter = new HotNewsAdapter(getActivity(),newsListhot,onRecyclerviewItemClickListener1);
        recyclerViewnewshot.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        queryData();
    }

    private void queryData() {
        BmobQuery<News> newsBmobQuery = new BmobQuery<>();
        newsBmobQuery.order("-heat");
        newsBmobQuery.findObjects(new FindListener<News>() {
            @Override
            public void done(List<News> object, BmobException e) {
                if (e == null) {
                    newsList = object;
                    hotNewsAdapter.setNewsList(newsList.subList(0,3));
                    recyclerViewnewshot.setAdapter(hotNewsAdapter);
                    newsAdapter.setNewsList(newsList.subList(3,newsList.size()));
                    recyclerViewnews.setAdapter(newsAdapter);
                } else {
                    Log.e("查询失败", "原因: ", e);
                }
            }
        });
    }
    public NewsAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new NewsAdapter.OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            Intent intent = new Intent(NewsFragment.this.getActivity(), News_detailActivity.class);
            if (! newsList.isEmpty()) {
                News news=new News();
                news.setObjectId(newsList.get(position).getObjectId());
                news_id=newsList.get(position+3).getObjectId();
                heat=newsList.get(position+3).getHeat();
                news.setHeat(heat+1);
                news.update(news_id, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            System.out.println("更新成功！111111111111111111");
                        }else{
                            Log.e("更新失败", "原因: ", e);
                        }
                    }
                });
                intent.putExtra("newsid",news_id);
            }
            NewsFragment.this.getActivity().startActivity(intent);
            (NewsFragment.this.getActivity()).finish();
        }
    };
    public HotNewsAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener1 = new HotNewsAdapter.OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            Intent intent = new Intent(NewsFragment.this.getActivity(), News_detailActivity.class);
            if (! newsList.isEmpty()) {
                News news=new News();
                news.setObjectId(newsList.get(position).getObjectId());
                news_id=newsList.get(position).getObjectId();
                heat=newsList.get(position).getHeat();
                news.setHeat(heat+1);
                news.update(news_id, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            System.out.println("更新成功！22222222222222222");
                        }else{
                            Log.e("更新失败", "原因: ", e);
                        }
                    }
                });
                intent.putExtra("newsid",news_id);
            }
            NewsFragment.this.getActivity().startActivity(intent);
            (NewsFragment.this.getActivity()).finish();
        }
    };
}
