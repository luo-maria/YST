package com.example.yst.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.Activities;
import com.example.yst.bean.CalenderEvent;
import com.example.yst.bean.Club;
import com.example.yst.bean.Stu_Club;
import com.example.yst.bean.Stu_Vote;
import com.example.yst.bean.Stu_activity;
import com.example.yst.bean.Student;
import com.example.yst.util.FormatUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class Activity_detailActivity extends AppCompatActivity {
    private ImageView image;
    private TextView activity_name,activity_create_name,activity_place,start_time,end_time,activity_intro,numtxt,numact;
    private String act_id,stu_id,club_id, activityName,startTime;
    private Button appacbut,act_edit;
    private Integer applynum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_detail);
        initialize();
        act_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Activity_detailActivity.this,EditActivitiesActivity.class);
                intent.putExtra("activityid",act_id);
                intent.putExtra("clubid",club_id);
                startActivity(intent);
            }
        });
        appacbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<Activities> activitiesBmobQuery=new BmobQuery<>();
                activitiesBmobQuery.getObject(act_id, new QueryListener<Activities>() {
                    @Override
                    public void done(Activities activities, BmobException e) {
                        if(e==null){
                            if(activities.getAct_status().equals("在招募")){
                                BmobQuery<Stu_activity> bmobQuery = new BmobQuery<Stu_activity>();
                                bmobQuery.addWhereEqualTo("stu_id",stu_id);
                                BmobQuery<Stu_activity> bmobQuery1 = new BmobQuery<Stu_activity>();
                                bmobQuery1.addWhereEqualTo("activity_id",act_id);
                                List<BmobQuery<Stu_activity>> queries = new ArrayList<BmobQuery<Stu_activity>>();
                                queries.add(bmobQuery);
                                queries.add(bmobQuery1);
                                BmobQuery<Stu_activity> query = new BmobQuery<Stu_activity>();
                                query.and(queries);
                                query.findObjects(new FindListener<Stu_activity>() {
                                    @Override
                                    public void done(List<Stu_activity> list, BmobException e) {
                                        if(e==null){
                                            if(list.size()>0){
                                                new AlertDialog.Builder(Activity_detailActivity.this).setTitle("信息提示")//设置对话框标题
                                                        .setMessage("您已加入该活动,请勿重复参加。")
                                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件，点击事件没写，自己添加
                                                            }
                                                        }).show();//在按键响应事件中显示此对话框
                                            }else{
                                                addActivity();
                                            }
                                        }else{
                                            Log.e("查询失败1","原因："+e);
                                        }
                                    }
                                });

                            }else {
                                new AlertDialog.Builder(Activity_detailActivity.this).setTitle("信息提示")//设置对话框标题
                                        .setMessage("该活动已招募完，看看其他活动吧！")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件，点击事件没写，自己添加
                                            }
                                        }).show();//在按键响应事件中显示此对话框
                            }
                        }else {
                            Log.e("查找失败","原因：",e);
                        }
                    }
                });
    }
});
    }

    private void addActivity() {
        Stu_activity stu_activity=new Stu_activity();
        stu_activity.setActivity_id(act_id);
        stu_activity.setStu_id(stu_id);
        stu_activity.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    addNumber();
                    CalenderEvent actcal=new CalenderEvent();
                    Calendar calendar = Calendar.getInstance();
                    System.out.println("this is my activity:"+activityName);
                    actcal.setMessage(activityName);
                    actcal.setMesstime(startTime);
                    actcal.setStud_id(stu_id);
                    actcal.setTime(FormatUtils.getDateTimeString(calendar.getTime(), FormatUtils.template_Time));
                    actcal.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                Toast.makeText(Activity_detailActivity.this,"您已加入该活动！",Toast.LENGTH_LONG);
                                new AlertDialog.Builder(Activity_detailActivity.this).setTitle("信息提示")//设置对话框标题
                                        .setMessage("您已加入成功该活动！")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件，点击事件没写，自己添加

                                            }
                                        }).show();//在按键响应事件中显示此对话框
//
                            }else{
                                Toast.makeText(Activity_detailActivity.this,"加入活动失败！",Toast.LENGTH_LONG);
                            }
                        }
                    });
                    Toast.makeText(Activity_detailActivity.this,"您已加入该活动！",Toast.LENGTH_LONG);

                }else{
                    Toast.makeText(Activity_detailActivity.this,"加入活动失败！",Toast.LENGTH_LONG);
                }
            }
        });
    }

    private void addNumber() {
        Activities activity = new Activities();
        activity.setApplynum(applynum+1);
        activity.update(act_id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    System.out.println("参加活动人数加一！");
                }else{
                    Log.e("查询失败3","原因",e);
                }
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
        act_edit=findViewById(R.id.act_edit);
        numtxt=findViewById(R.id.numtxt);
        numact=findViewById(R.id.numact);
        Intent intent1=getIntent();
        act_id=intent1.getStringExtra("activityid");
        club_id = intent1.getStringExtra("clubid");
        Statistics();
        BmobQuery<Activities> bmobQuery = new BmobQuery<Activities>();
        bmobQuery.getObject(act_id, new QueryListener<Activities>() {
            @Override
            public void done(Activities association, BmobException e) {
                if(e==null){
                    image.setImageBitmap(BitmapFactory.decodeFile(association.getActivity_imgurl()));
                    activityName=association.getActivity_name();
                    activity_name.setText(activityName);
                    activity_create_name.setText(association.getActivity_leader());
                    startTime=association.getStart_time();
                    start_time.setText(startTime);
                    end_time.setText(association.getEnd_time());
                    activity_place.setText(association.getPlace());
                    activity_intro.setText(association.getActivity_info());
                    applynum=association.getApplynum();
                    numact.setText(String.valueOf(applynum));
                }else{
                    Toast.makeText(Activity_detailActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Statistics() {
        BmobQuery<Club> clubbmobQuery=new BmobQuery<>();
        clubbmobQuery.getObject(club_id, new QueryListener<Club>() {
            @Override
            public void done(Club club, BmobException e) {
                if(e==null){
                    if(club.getStu_id().equals(stu_id)){
                        numact.setVisibility(View.VISIBLE);
                        numtxt.setVisibility(View.VISIBLE);
                        appacbut.setVisibility(View.GONE);
                        act_edit.setVisibility(View.VISIBLE);
                    }else{
                        System.out.println("不是管理员！");
                    }
                }else{
                    Log.e("查询失败","原因",e);
                }
            }
        });
    }
}
