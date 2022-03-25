package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.adapter.ClubAdapter;
import com.example.yst.bean.Club;
import com.example.yst.bean.Student;
import com.example.yst.fragment.OrganizationFragment;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MyclubsActivity extends AppCompatActivity {
    List<Club> clubs;
    String stu_id,club_id1;
    RecyclerView recyclerViewclub;
    ClubAdapter clubAdapter;
    Button club_created,club_entered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myclubs);
        club_created=findViewById(R.id.createclub);
        club_entered=findViewById(R.id.enterclub);
        recyclerViewclub=findViewById(R.id.myclubs1);
        initialize();
        club_created.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryData();
            }
        });
        club_entered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryData();
            }
        });

    }
    public ClubAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new ClubAdapter.OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            Intent intent = new Intent(MyclubsActivity.this,ManageClubActivity.class);
            if (! clubs.isEmpty()) {
                Club club = new Club();
                club.setObjectId( clubs.get(position).getObjectId());
                club_id1=clubs.get(position).getObjectId();
                intent.putExtra("clubid",club_id1);
            }
            startActivity(intent);
            finish();
        }
    };
    private void initialize() {
        clubAdapter = new ClubAdapter(this,clubs,onRecyclerviewItemClickListener);
        recyclerViewclub.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        queryData();
    }

    private void queryData() {
        Student userInfo = BmobUser.getCurrentUser(Student.class);
        stu_id=userInfo.getObjectId();
        BmobQuery<Club> clubBmobQuery = new BmobQuery<>();
        clubBmobQuery.addWhereEqualTo("stu_id", stu_id);
        clubBmobQuery.findObjects(new FindListener<Club>() {
            @Override
            public void done(List<Club> object, BmobException e) {
                if (e == null) {
                    clubs = object;
                    clubAdapter.setClubList(clubs);
                    recyclerViewclub.setAdapter(clubAdapter);
                } else {
                    Log.e("查询失败", "原因: ", e);
                }
            }
        });
    }
}
