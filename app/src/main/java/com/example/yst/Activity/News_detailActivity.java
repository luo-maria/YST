package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yst.R;
import com.example.yst.bean.News;
import com.example.yst.fragment.HomePageFragment;
import com.example.yst.fragment.NewsFragment;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class News_detailActivity extends BaseActivity {
    private TextView newsTitle,news_txt,views,likes;
    private ImageView news_image,zan,backPre;
    private String news_id;
    private Integer newlikes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initView();
        backPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new Thread() {
//                    public void run() {
//                        try {
//                            Instrumentation inst = new Instrumentation();
//                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
//                        } catch (Exception e) {
//                        }
//                    }
//                }.start();
            Intent intent=new Intent(News_detailActivity.this,HomeActivity.class);
            startActivity(intent);
            }
        });
        zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zan.setImageResource(R.mipmap.likes);
                likes.setText(String.valueOf(newlikes+1));
                News news = new News();
                news.setLikes(newlikes+1);
                news.update(news_id, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            System.out.println("您很喜欢这条资讯！");
                        }else{
                            Log.e("更新失败", "原因: ", e);
                        }
                    }
                });
            }
        });
    }

    private void initView() {
        backPre=findViewById(R.id.backPre);
        newsTitle=findViewById(R.id.newsTitle);
        news_txt=findViewById(R.id.news_txt);
        views=findViewById(R.id.views);
        likes=findViewById(R.id.likes);
        news_image=findViewById(R.id.news_image);
        zan=findViewById(R.id.zan);
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
                    newlikes=news.getLikes();
                    newsTitle.setText(news.getNews_title());
                    news_txt.setText(news.getNews_content());
                    if(!news.getNews_image().equals("")){
                        news_image.setImageBitmap(BitmapFactory.decodeFile(news.getNews_image()));
                    }else{
                        news_image.setImageResource(R.mipmap.a);
                    }
                    likes.setText(String.valueOf(newlikes));
                    views.setText(String.valueOf(news.getHeat()));
                }else{
                    Log.e("查询失败", "原因: ", e);

                }
            }
        });
    }
}
