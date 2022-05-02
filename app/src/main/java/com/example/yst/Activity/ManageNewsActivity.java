package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.yst.R;
import com.example.yst.adapter.NewsAdapter;
import com.example.yst.adapter.NoticeAdapter;
import com.example.yst.bean.News;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ManageNewsActivity extends BaseActivity {
    private ImageView addnews;
    private RecyclerView recyclerViewnews;
    private NewsAdapter newsAdapter;
    private NoticeAdapter noticeAdapter;
    private List<News> newslist=new ArrayList<News>();
    private String club_id,news_id,club_name;
    private Button clubnews,clubnotice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_news);
        initView();
        addnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManageNewsActivity.this,AddNewsActivity.class);
                intent.putExtra("clubid",club_id);
                startActivity(intent);
            }
        });
        clubnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryNewsData();
                clubnews.setBackgroundResource(R.color.skin_topbar_bg_color_night);
                clubnotice.setBackgroundResource(R.color.colorPrimary);
            }
        });
        clubnotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryNoticeData();
                clubnotice.setBackgroundResource(R.color.skin_topbar_bg_color_night);
                clubnews.setBackgroundResource(R.color.colorPrimary);
            }
        });

    }

    private void initView() {
        Intent intent = getIntent();
        club_id = intent.getStringExtra("clubid");
        clubnews=findViewById(R.id.clubnews);
        clubnotice=findViewById(R.id.clubnotice);
        addnews=findViewById(R.id.addnews);
        recyclerViewnews=findViewById(R.id.recyclerviewmynews);
        newsAdapter=new NewsAdapter(ManageNewsActivity.this,newslist,onRecyclerviewItemClickListener);
        noticeAdapter=new NoticeAdapter(ManageNewsActivity.this,newslist,onRecyclerviewItemClickListener1);
        recyclerViewnews.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        queryNewsData();
    }
    public NewsAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener=new NewsAdapter.OnRecyclerviewItemClickListener(){
        @Override
        public void onItemClickListener(View v, int position) {
            Intent intent=new Intent(ManageNewsActivity.this,News_detailActivity.class);
            if(!newslist.isEmpty()){
                News news=new News();
                news.setObjectId(newslist.get(position).getObjectId());
                news_id=newslist.get(position).getObjectId();
                intent.putExtra("newsid",news_id);
            }
            startActivity(intent);
        }
    };
    public NoticeAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener1=new NoticeAdapter.OnRecyclerviewItemClickListener(){
        @Override
        public void onItemClickListener(View v, int position) {
            Intent intent=new Intent(ManageNewsActivity.this,ClubNewsDetailActivity.class);
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
        BmobQuery<News> bmobQuery1=new BmobQuery<News>();
        bmobQuery1.addWhereEqualTo("club_id",club_id);
        BmobQuery<News> bmobQuery2=new BmobQuery<News>();
        bmobQuery2.addWhereEqualTo("news_kind","公开资讯");
        List<BmobQuery<News>> queries = new ArrayList<BmobQuery<News>>();
        queries.add(bmobQuery1);
        queries.add(bmobQuery2);
        BmobQuery<News> query = new BmobQuery<News>();
        query.and(queries);
        query.findObjects(new FindListener<News>() {
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
    private void queryNoticeData() {
        BmobQuery<News> bmobQuery1=new BmobQuery<News>();
        bmobQuery1.addWhereEqualTo("club_id",club_id);
        BmobQuery<News> bmobQuery2=new BmobQuery<News>();
        bmobQuery2.addWhereEqualTo("news_kind","社团内公告");
        List<BmobQuery<News>> queries = new ArrayList<BmobQuery<News>>();
        queries.add(bmobQuery1);
        queries.add(bmobQuery2);
        BmobQuery<News> query = new BmobQuery<News>();
        query.and(queries);
        query.findObjects(new FindListener<News>() {
            @Override
            public void done(List<News> list, BmobException e) {
                if(e==null){
                    newslist=list;
                    noticeAdapter.setNewsList(newslist);
                    recyclerViewnews.setAdapter(noticeAdapter);
                }else{
                    Log.e("查询失败1", "原因: ", e);
                }
            }
        });
    }
}
