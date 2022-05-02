package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.yst.R;
import com.example.yst.adapter.NewsAdapter;
import com.example.yst.adapter.NoticeAdapter;
import com.example.yst.bean.News;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class NewsInClubActivity extends BaseActivity {
    private RecyclerView recyclerViewnotice;
    private NoticeAdapter noticeAdapter;
    private List<News> newslist=new ArrayList<News>();
    private String club_id,news_id,club_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_in_club);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        club_id = intent.getStringExtra("clubid");
        recyclerViewnotice=findViewById(R.id.recyclerviewnotice);
        noticeAdapter=new NoticeAdapter(NewsInClubActivity.this,newslist,onRecyclerviewItemClickListener);
        recyclerViewnotice.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        queryNewsData();
    }

    private void queryNewsData() {
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
                    recyclerViewnotice.setAdapter(noticeAdapter);
                }else{
                    Log.e("查询失败1", "原因: ", e);
                }
            }
        });
    }


    public NoticeAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener=new NoticeAdapter.OnRecyclerviewItemClickListener(){
        @Override
        public void onItemClickListener(View v, int position) {
            Intent intent=new Intent(NewsInClubActivity.this,ClubNewsDetailActivity.class);
            if(!newslist.isEmpty()){
                News news=new News();
                news.setObjectId(newslist.get(position).getObjectId());
                news_id=newslist.get(position).getObjectId();
                intent.putExtra("newsid",news_id);
            }
            startActivity(intent);
        }
    };
}
