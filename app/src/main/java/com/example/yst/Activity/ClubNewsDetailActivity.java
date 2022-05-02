package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.yst.R;
import com.example.yst.bean.News;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class ClubNewsDetailActivity extends BaseActivity {
    TextView noticeTitle,noticeTime,noticeTxt;
    String news_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_news_detail);
        initView();
    }
    private void initView() {
        noticeTitle=findViewById(R.id.noticeTitle);
        noticeTime=findViewById(R.id.noticeTime);
        noticeTxt=findViewById(R.id.noticeTxt);
        Intent intent1=getIntent();
        news_id=intent1.getStringExtra("newsid");
        queryData();
    }

    private void queryData() {
        BmobQuery<News> bmobQuery=new BmobQuery<News>();
        bmobQuery.getObject(news_id, new QueryListener<News>() {
            @Override
            public void done(News news, BmobException e) {
                if(e==null){
                    noticeTime.setText(news.getCreatedAt());
                    noticeTitle.setText(news.getNews_title());
                    noticeTxt.setText(news.getNews_content());
                    System.out.println("Ssssssssssssssssssssssssssssssssssssss");
                }else{
                    Log.e("查询失败", "原因: ", e);

                }
            }
        });
    }
}
