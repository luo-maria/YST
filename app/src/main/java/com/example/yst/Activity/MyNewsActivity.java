package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.yst.R;
import com.example.yst.adapter.NewsAdapter;
import com.example.yst.bean.News;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MyNewsActivity extends AppCompatActivity {
    private ImageView addnews;
    private RecyclerView recyclerViewnews;
    private NewsAdapter newsAdapter;
    private List<News> newslist=new ArrayList<News>();
    private String club_id,news_id,club_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_news);
        initView();
        addnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyNewsActivity.this,AddNewsActivity.class);
                intent.putExtra("clubid",club_id);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        Intent intent = getIntent();
        club_id = intent.getStringExtra("clubid");
        addnews=findViewById(R.id.addnews);
        recyclerViewnews=findViewById(R.id.recyclerviewmynews);
        newsAdapter=new NewsAdapter(MyNewsActivity.this,newslist,onRecyclerviewItemClickListener);
        recyclerViewnews.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        queryNewsData();
    }
    public NewsAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener=new NewsAdapter.OnRecyclerviewItemClickListener(){
        @Override
        public void onItemClickListener(View v, int position) {
            Intent intent=new Intent(MyNewsActivity.this,News_detailActivity.class);
            if(!newslist.isEmpty()){
                News news=new News();
                news.setObjectId(newslist.get(position).getObjectId());
                news_id=newslist.get(position).getObjectId();
                intent.putExtra("newsid",news_id);
            }
            startActivity(intent);
        }
    };
    private void queryNewsData() {
        BmobQuery<News> bmobQuery=new BmobQuery<News>();
        bmobQuery.addWhereEqualTo("club_id",club_id);
        bmobQuery.findObjects(new FindListener<News>() {
            @Override
            public void done(List<News> list, BmobException e) {
                if(e==null){
                    newslist=list;
                    newsAdapter.setNewsList(newslist);
                    recyclerViewnews.setAdapter(newsAdapter);
                }else{
                    Log.e("查询失败1", "原因: ", e);
                }
            }
        });
    }
}
