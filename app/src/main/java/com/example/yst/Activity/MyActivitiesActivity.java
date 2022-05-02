package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.adapter.ActivityAdapter;
import com.example.yst.bean.Activities;
import com.example.yst.bean.Stu_activity;
import com.example.yst.bean.Student;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class MyActivitiesActivity extends BaseActivity {
    private String stu_id,activity_id,activity_id1;
    private List<String> activities= new ArrayList<String>();
    private List<Activities> Activities1=new ArrayList<Activities>();
    private RecyclerView recyclerViewactivity;
    private ActivityAdapter activityAdapter;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_activities);
        initView();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MyActivitiesActivity.this, HomeActivity.class);
                startActivity(intent1);
            }
        });
    }

    private void initView() {
        back=findViewById(R.id.back);
        activityAdapter=new ActivityAdapter(MyActivitiesActivity.this,Activities1,onRecyclerviewItemClickListener);
        recyclerViewactivity=findViewById(R.id.recyclerview_my_activity);
        recyclerViewactivity.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        Student student=Student.getCurrentUser(Student.class);
        stu_id=student.getObjectId();
        queryMyActivityData();
    }
    public ActivityAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new ActivityAdapter.OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            Intent intent = new Intent(MyActivitiesActivity.this,Activity_detailActivity.class);
            Toast.makeText(MyActivitiesActivity.this," 点击了 "+position,Toast.LENGTH_SHORT).show();
            if (! Activities1.isEmpty()) {
                Activities myactivities=new Activities();
                myactivities.setObjectId(Activities1.get(position).getObjectId());
                activity_id1=Activities1.get(position).getObjectId();
                System.out.println("this is myActivities1id:"+activity_id1);
                intent.putExtra("activityid",activity_id1);
            }
            startActivity(intent);
            finish();
        }
    };

    private void queryMyActivityData() {
        BmobQuery<Stu_activity> activityQuery = new BmobQuery<>();
        activityQuery.addWhereEqualTo("stu_id", stu_id);
        activityQuery.findObjects(new FindListener<Stu_activity>() {
        @Override
        public void done(List<Stu_activity> list, BmobException e) {
            if(e==null){
                if (!list.isEmpty()) {
                    for (Stu_activity stu_activity :list) {
                        activity_id=String.valueOf(stu_activity.getActivity_id());
                        activities.add(activity_id);
                    }
                }
                for(String activity_id:activities){
                    BmobQuery<Activities> bmobQuery = new BmobQuery<Activities>();
                    bmobQuery.getObject(activity_id, new QueryListener<Activities>() {
                        @Override
                        public void done(Activities object,BmobException e) {
                            if(e==null){
                                Activities1.add(object);
                                activityAdapter.setActivityLists(Activities1);
                                recyclerViewactivity.setAdapter(activityAdapter);
                                System.out.println("this is  Activities1:"+ Activities1);
                            }else{
                                Log.e("查询失败","原因：",e);                            }
                        }
                    });
                }
            }else{
                Log.e("我的活动查询失败", "原因: ", e);
            }
        }
    });
}
}
