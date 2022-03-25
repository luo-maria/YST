package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.yst.R;
import com.example.yst.bean.Club;

public class ManageClubActivity extends AppCompatActivity {
    LinearLayout audit,addvote,addActivity,manage;
    String club_id3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_club);
        initialize();
        audit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageClubActivity.this,ApplyInfoListActivity.class);
                Intent intent1=getIntent();
                club_id3=intent1.getStringExtra("clubid");
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
    }
}
