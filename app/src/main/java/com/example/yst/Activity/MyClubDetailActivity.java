package com.example.yst.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.example.yst.bean.Stu_Club;
import com.example.yst.bean.Student;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class MyClubDetailActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView imageView4;
    TextView myclub_name,myclub_leader_name,myclub_leader,myclub_category,myclub_campus,myclub_rank,club_numbers;
    String club_id7,stu_id,sclub_id;
    Button Quit;
    LinearLayout look_activity,look_notice,toVote,exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_joined);
        initialize();
        queryData();
        queryData1();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Quit:
                Quitclub();
                break;
            case R.id.look_activity:
                break;
            case R.id.look_notice:
                break;
            case R.id.toVote:
                findVote();
                break;
            case R.id.exit:
                Quitclub();
                break;
        }
    }

    private void findVote() {
        Intent intent=new Intent(MyClubDetailActivity.this,CastVoteActivity.class);
        startActivity(intent);
    }

    private void initialize() {
        imageView4=findViewById(R.id.imageView4);
        myclub_name=findViewById(R.id.myclub_name);
        myclub_leader=findViewById(R.id.myclub_leader);
        myclub_category=findViewById(R.id.myclub_category);
        myclub_campus=findViewById(R.id.myclub_campus);
        myclub_rank=findViewById(R.id.myclub_rank);
        club_numbers=findViewById(R.id.club_numbers);
        myclub_leader_name=findViewById(R.id.myclub_leader_name1);
        look_activity=findViewById(R.id.look_activity);
        look_notice=findViewById(R.id.look_notice);
        toVote=findViewById(R.id.toVote);
        exit=findViewById(R.id.exit);
        Quit=findViewById(R.id. Quit);
        Quit.setOnClickListener(this);
        look_activity.setOnClickListener(this);
        look_notice.setOnClickListener(this);
        toVote.setOnClickListener(this);
        exit.setOnClickListener(this);
        Intent intent=getIntent();
        club_id7=intent.getStringExtra("clubid");
        Student userInfo = BmobUser.getCurrentUser(Student.class);
        stu_id=userInfo.getObjectId();
    }


    private void queryData1() {
        BmobQuery<Stu_Club> sclubBmobQuery = new BmobQuery<>();
        sclubBmobQuery.addWhereEqualTo("club_id",club_id7);
        sclubBmobQuery.findObjects(new FindListener<Stu_Club>() {
            @Override
            public void done(List<Stu_Club> list, BmobException e) {
                club_numbers.setText(String.valueOf(list.size()));
            }
        });
    }

    private void queryData() {
        BmobQuery<Club> bmobQuery = new BmobQuery<Club>();
        bmobQuery.getObject(club_id7, new QueryListener<Club>() {
            @Override
            public void done(Club object, BmobException e) {
                if(e==null){
                    myclub_rank.setText(object.getClub_rank());
                    myclub_campus.setText(object.getClub_campus());
                    myclub_category.setText(object.getClub_category());
                    myclub_name.setText(object.getClub_name());
                    myclub_leader_name.setText(object.getClub_president());
                    myclub_leader.setText(object.getClub_president());
                    imageView4.setImageBitmap(BitmapFactory.decodeFile(object.getLogo_url()));
                }else{
                    Toast.makeText(MyClubDetailActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void Quitclub(){
        new AlertDialog.Builder(MyClubDetailActivity.this).setTitle("退出社团")//设置对话框标题
                .setMessage("退出社团前，请确认您和社团领导人协商好。")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件，点击事件没写，自己添加
                        String bql ="select * from Stu_Club where stu_id="+stu_id+" and club_id="+club_id7;
                        BmobQuery<Stu_Club> query=new BmobQuery<>();
                        query.setSQL(bql);
                        query.doSQLQuery(new SQLQueryListener<Stu_Club>(){
                            @Override
                            public void done(BmobQueryResult<Stu_Club> result, BmobException e) {
                                if(e ==null){
                                    List<Stu_Club> list = (List<Stu_Club>) result.getResults();
                                    if(list!=null && list.size()>0){
                                        for (Stu_Club stu_club :list) {
                                            sclub_id = String.valueOf(stu_club.getStu_id());
                                            System.out.println("this is sclub_id+1111111111111111:"+sclub_id);
//                                                    stu_club.delete(new UpdateListener() {
//                                                        @Override
//                                                        public void done(BmobException e) {
//                                                            Log.i("smile", "111111111111111111111");
//                                                        }
//                                                    });
                                        }
                                    }else{
                                        Log.i("smile", "查询成功，无数据返回");
                                    }
                                }else{
                                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                                }
                            }
                        });
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件，点击事件没写，自己添加
                    }

                })
                .show();

    }


}
