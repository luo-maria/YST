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
import com.example.yst.adapter.ActivityAdapter;
import com.example.yst.bean.Activities;
import com.example.yst.bean.Club;
import com.example.yst.bean.Student;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class ActivitiesInClubsActivity extends AppCompatActivity {

    private RecyclerView recyclerviewActivities;
    private ActivityAdapter activityAdapter;
    private List<Activities> activities;
    private String activity_id, club_id,stu_id,club_name;
    private ImageView add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_in_clubs_jioned);
        recyclerviewActivities = findViewById(R.id.recyclerview_club_activity);
        add=findViewById(R.id.addact);
        Student student =Student.getCurrentUser(Student.class);
        stu_id=student.getObjectId();
        initialize();

    }

    public ActivityAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new ActivityAdapter.OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            //这里的view就是我们点击的view  position就是点击的position
            System.out.println("点击了" + position);
            Intent intent = new Intent(ActivitiesInClubsActivity.this, Activity_detailActivity.class);
            if (! activities.isEmpty()) {
                Activities activity = new Activities();
                activity.setObjectId( activities.get(position).getObjectId());
                activity_id = activities.get(position).getObjectId();
                intent.putExtra("activityid",activity_id);
                intent.putExtra("clubid",club_id);
            }
            startActivity(intent);
        }
    };

    private void initialize() {
        Intent intent = getIntent();
        club_name = intent.getStringExtra("clubname");
        club_id = intent.getStringExtra("clubid");
        activityAdapter = new ActivityAdapter(this,activities, onRecyclerviewItemClickListener);
        recyclerviewActivities.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        BmobQuery<Club> clubQuery = new BmobQuery<Club>();
        clubQuery.getObject(club_id, new QueryListener<Club>() {
            @Override
            public void done(Club club, BmobException e) {
                if(e==null){
                    if(stu_id.equals(club.getStu_id())){
                        add.setVisibility(View.VISIBLE);
                        add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ActivitiesInClubsActivity.this, CreateActivity.class);
                                intent.putExtra("clubid",club_id);
                                startActivity(intent);
                            }
                        });
                    }
                }else{
                    Log.e("查询失败2", "原因: ", e);
                }
            }
        });
        queryActivityData();

    }

    private void queryActivityData() {

        // 根据社团ID查对应的活动信息，数据回显
        BmobQuery<Activities> activityQuery = new BmobQuery<>();
        activityQuery.addWhereEqualTo("club_id", club_id);
        activityQuery.findObjects(new FindListener<Activities>() {
            @Override
            public void done(List<Activities> object, BmobException e) {
                if (e == null ) {
                        activities = object;
                        activityAdapter.setActivityLists(activities);
                        recyclerviewActivities.setAdapter(activityAdapter);
                } else {
                    Log.e("查询失败", "原因: ", e);
                }
            }
        });

    }
}
