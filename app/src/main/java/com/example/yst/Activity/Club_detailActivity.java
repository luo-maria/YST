package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.Club;
import com.example.yst.util.ImageUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class Club_detailActivity extends AppCompatActivity {
    Button app_btn;
    String club_id1,img_url;

    SimpleDraweeView imglogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_detail);
        imglogo = (SimpleDraweeView)findViewById(R.id.logophoto);
        TextView level = (TextView)findViewById(R.id.level1);
        TextView campus = (TextView)findViewById(R.id.campus1) ;
        TextView kind = (TextView)findViewById(R.id.kind1);
        TextView clubname = (TextView)findViewById(R.id.club_name);
        TextView time = (TextView)findViewById(R.id.time);
        TextView createname = (TextView)findViewById(R.id.createname);
        TextView club_intro = (TextView)findViewById(R.id.club_intro1);
        TextView call = (TextView)findViewById(R.id.call);

        Intent intent1=getIntent();
        club_id1=intent1.getStringExtra("clubid");
//        System.out.println("this is clubiddetail:"+club_id1);
        BmobQuery<Club> bmobQuery = new BmobQuery<Club>();
        bmobQuery.getObject(club_id1, new QueryListener<Club>() {
            @Override
            public void done(Club object,BmobException e) {
                if(e==null || object.getClub_logo().getUrl()!=null){
                    System.out.println("this is clubdetail:"+object.getClub_name());
                    level.setText(object.getClub_rank());
                    campus.setText(object.getClub_campus());
                    kind.setText(object.getClub_category());
                    clubname.setText(object.getClub_name());
                    createname.setText(object.getClub_president());
                    club_intro .setText(object.getClub_intro());
                    call.setText(object.getPre_number());
                    time.setText(object.getCreatedAt().substring(0,10));
                    img_url=object.getClub_logo().getUrl();
                    ImageUtils.setRoundImage(Club_detailActivity.this, imglogo, img_url);
                }else{
                    Toast.makeText(Club_detailActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });


        app_btn = (Button) findViewById(R.id.apply);

        app_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Club_detailActivity.this, Apply_aclub.class);
//
//                startActivity(intent);
            }
        });}



}
