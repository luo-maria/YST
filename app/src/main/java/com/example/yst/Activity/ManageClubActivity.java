package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.Club;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class ManageClubActivity extends BaseActivity {
    private LinearLayout audit,addvote,addActivity,manage_club,manage_people,manage_news,union,auditclub,
            manageclubs;
    private String club_id3,club_name;
    private TextView button_ma;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_club);
        Intent intent1=getIntent();
        club_id3=intent1.getStringExtra("clubid");
        initialize();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageClubActivity.this,MyclubsActivity.class);
                startActivity(intent);
            }
        });
        manageclubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageClubActivity.this,ClubListActivity.class);
                intent.putExtra("clubid",club_id3);
                startActivity(intent);
            }
        });
        manage_people=findViewById(R.id.manage_people);
        manage_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageClubActivity.this,AllMembersActivity.class);
                intent.putExtra("clubid",club_id3);
                startActivity(intent);
            }
        });
        manage_club.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageClubActivity.this,EditClubActivity.class);
                intent.putExtra("clubid",club_id3);
                startActivity(intent);
            }
        });
        audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageClubActivity.this,ApplyInfoListActivity.class);
                intent.putExtra("clubid",club_id3);
                startActivity(intent);
            }
        });
        addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageClubActivity.this,ActivitiesInClubsActivity.class);
                intent.putExtra("clubid",club_id3);
                startActivity(intent);
            }
        });
        addvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageClubActivity.this,ManageVotesActivity.class);
                intent.putExtra("clubid",club_id3);
                startActivity(intent);
            }
        });
        manage_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageClubActivity.this,ManageNewsActivity.class);
                intent.putExtra("clubid",club_id3);
                startActivity(intent);
            }
        });
        auditclub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageClubActivity.this,ApplyClubListActivity.class);
                intent.putExtra("clubid",club_id3);
                startActivity(intent);
            }
        });
    }

    private void initialize() {
        back=findViewById(R.id.back);
        manageclubs=findViewById(R.id.manageclub);
        auditclub=findViewById(R.id.auditclub);
        union=findViewById(R.id.union);
        manage_news=findViewById(R.id.manage_news);
        audit=findViewById(R.id.audit);
        addvote=findViewById(R.id.addvote);
        addActivity=findViewById(R.id.addActivity);
        manage_club=findViewById(R.id.manage_club);
        button_ma=findViewById(R.id.button_ma);
        if(club_id3.equals("92ec346797")){
            union.setVisibility(View.VISIBLE);
        }
        BmobQuery<Club> bmobQuery = new BmobQuery<Club>();
        bmobQuery.getObject(club_id3, new QueryListener<Club>() {
            @Override
            public void done(Club object, BmobException e) {
                if(e==null){
                    club_name=object.getClub_name();
                    button_ma.setText(club_name);
                }else{
                    Log.e("查询失败","原因：",e);                }
            }
        });
    }
}
