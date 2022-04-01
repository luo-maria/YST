package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.Club;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class ManageClubActivity extends AppCompatActivity {
    LinearLayout audit,addvote,addActivity,manage;
    String club_id3,club_name;
    TextView button_ma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_club);
        Intent intent1=getIntent();
        club_id3=intent1.getStringExtra("clubid");
        initialize();
        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ManageClubActivity.this,EditClubActivity.class);
                Intent intent = new Intent(ManageClubActivity.this,AllMembersActivity.class);
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
                Intent intent = new Intent(ManageClubActivity.this,CreateActivity.class);
                intent.putExtra("clubname",club_name);
                intent.putExtra("clubid",club_id3);
                startActivity(intent);
            }
        });
        addvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageClubActivity.this,AddVoteActivity.class);
                intent.putExtra("clubid",club_id3);
                startActivity(intent);
            }
        });
    }

    private void initialize() {
        audit=findViewById(R.id.audit);
        addvote=findViewById(R.id.addvote);
        addActivity=findViewById(R.id.addActivity);
        manage=findViewById(R.id.manage);
        button_ma=findViewById(R.id.button_ma);
        BmobQuery<Club> bmobQuery = new BmobQuery<Club>();
        bmobQuery.getObject(club_id3, new QueryListener<Club>() {
            @Override
            public void done(Club object, BmobException e) {
                if(e==null){
                    club_name=object.getClub_name();
                    button_ma.setText(club_name);
                }else{
                    Toast.makeText(ManageClubActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
