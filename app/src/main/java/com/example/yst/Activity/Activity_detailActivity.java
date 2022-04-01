package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.Activities;
import com.example.yst.bean.Stu_Vote;
import com.example.yst.bean.Stu_activity;
import com.example.yst.bean.Student;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class Activity_detailActivity extends AppCompatActivity {
    ImageView image;
    TextView activity_name,activity_create_name,activity_place,start_time,end_time,activity_intro;
    String act_id,stu_id;
    Button appacbut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_detail);
        initialize();
        appacbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stu_activity stu_activity=new Stu_activity();
                stu_activity.setActivity_id(act_id);
                stu_activity.setStu_id(stu_id);
                stu_activity.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            Toast.makeText(Activity_detailActivity.this,"您已加入该活动！",Toast.LENGTH_LONG);
                        }else{
                            Toast.makeText(Activity_detailActivity.this,"加入活动失败！",Toast.LENGTH_LONG);
                        }
                    }
                });
            }
        });
    }

    private void initialize() {
        Student student=Student.getCurrentUser(Student.class);
        stu_id=student.getObjectId();
        image=findViewById(R.id.image);
        activity_name=findViewById(R.id.activity_name);
        activity_create_name=findViewById(R.id.activity_create_name);
        activity_place=findViewById(R.id.activity_place);
        start_time=findViewById(R.id.start_time);
        end_time=findViewById(R.id.end_time);
        activity_intro=findViewById(R.id.activity_intro);
        appacbut=findViewById(R.id.apply_but);
        Intent intent1=getIntent();
        act_id=intent1.getStringExtra("activityid");
        BmobQuery<Activities> bmobQuery = new BmobQuery<Activities>();
        bmobQuery.getObject(act_id, new QueryListener<Activities>() {
            @Override
            public void done(Activities association, BmobException e) {
                if(e==null){
                    image.setImageBitmap(BitmapFactory.decodeFile(association.getActivity_imgurl()));
                    activity_name.setText(association.getActivity_name());
                    activity_create_name.setText(association.getActivity_leader());
                    start_time.setText(association.getStart_time());
                    end_time.setText(association.getEnd_time());
                    activity_place.setText(association.getPlace());
                    activity_intro.setText(association.getActivity_info());
                }else{
                    Toast.makeText(Activity_detailActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
