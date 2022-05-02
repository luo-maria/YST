package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yst.R;
import com.example.yst.bean.Stu_Club;
import com.example.yst.bean.Stu_activity;
import com.example.yst.bean.Student;
import com.example.yst.util.HonorDialog;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class MyHonorActivity extends BaseActivity implements View.OnClickListener{
    private ImageView my_image_honor,level1,level22,level3,level4,level5,level6,level_one,level_two,level_three;
    private String stu_id,honor_club,honor_act;
    private TextView myname,my_honor_club,my_honor_act;
    private HonorDialog m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_honor);
        initView();
        level1.setOnClickListener(this);
        level22.setOnClickListener(this);
        level3.setOnClickListener(this);
        level4.setOnClickListener(this);
        level5.setOnClickListener(this);
        level6.setOnClickListener(this);
        level_one.setOnClickListener(this);
        level_two.setOnClickListener(this);
        level_three.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.level11:
                m=new HonorDialog(MyHonorActivity.this, "见习学子", "1", "活动", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        m.dismiss();
                    }
                });
                m.show();
                break;
            case R.id.level22:
                m=new HonorDialog(MyHonorActivity.this, "活动新秀", "3", "活动", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        m.dismiss();
                    }
                });
                m.show();
                break;
            case R.id.level33:
                m=new HonorDialog(MyHonorActivity.this, "小有名气", "5", "活动", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        m.dismiss();
                    }
                });
                m.show();
                break;
            case R.id.level44:
                m=new HonorDialog(MyHonorActivity.this, "乘风破浪", "10", "活动", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        m.dismiss();
                    }
                });
                m.show();
                break;
            case R.id.level55:
                m=new HonorDialog(MyHonorActivity.this, "荣耀黄金", "20", "活动", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        m.dismiss();
                    }
                });
                m.show();
                break;
            case R.id.level66:
                m=new HonorDialog(MyHonorActivity.this, "最强王者", "30", "活动", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        m.dismiss();
                    }
                });
                m.show();
                break;
            case R.id.level_one:
                m=new HonorDialog(MyHonorActivity.this, "初入江湖", "1", "社团", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        m.dismiss();
                    }
                });
                m.show();
                break;
            case R.id.level_two:
                m=new HonorDialog(MyHonorActivity.this, "江湖少侠", "2", "社团", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        m.dismiss();
                    }
                });
                m.show();
                break;
            case R.id.level_three:
                m=new HonorDialog(MyHonorActivity.this, "雄霸天下", "4", "社团", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        m.dismiss();
                    }
                });
                m.show();
                break;
        }
    }
    private void initView() {
        my_image_honor=findViewById(R.id.my_image_honor);
        myname=findViewById(R.id.my_name);
        my_honor_act=findViewById(R.id.my_honor_act);
        my_honor_club=findViewById(R.id.my_honor_club);
        level1=findViewById(R.id.level11);
        level22=findViewById(R.id.level22);
        level3=findViewById(R.id.level33);
        level4=findViewById(R.id.level44);
        level5=findViewById(R.id.level55);
        level6=findViewById(R.id.level66);
        level_one=findViewById(R.id.level_one);
        level_two=findViewById(R.id.level_two);
        level_three=findViewById(R.id.level_three);
        Student userInfo = BmobUser.getCurrentUser(Student.class);
        stu_id=userInfo.getObjectId();
        getHonorAct();
        getHonorClub();
        BmobQuery<Student> studentQuery = new BmobQuery<Student>();
        studentQuery.getObject(stu_id, new QueryListener<Student>() {
            @Override
            public void done(Student student, BmobException e) {
                if(e==null){
                    if(student.getPhotoimageurl()!=null){
                        my_image_honor.setImageBitmap(BitmapFactory.decodeFile(student.getPhotoimageurl()));
                        myname.setText(student.getNickname());
                    }else{
                        my_image_honor.setImageResource(R.mipmap.poy);
                    }
                }else{
                    Log.e("查询失败2","原因："+e);
                }
            }
        });
    }

    private void getHonorAct() {
        BmobQuery<Stu_activity> activityQuery = new BmobQuery<>();
        activityQuery.addWhereEqualTo("stu_id", stu_id);
        activityQuery.findObjects(new FindListener<Stu_activity>() {
            @Override
            public void done(List<Stu_activity> list, BmobException e) {
                if(e==null){
                    if(1<=list.size()){
                        level1.setImageResource(R.mipmap.level_1_light);
                        honor_act="见习学子";
                        my_honor_act.setText(honor_act);
                        if(3<=list.size()){
                            level22.setImageResource(R.mipmap.level_2_light);
                            honor_act="活动新秀";
                            my_honor_act.setText(honor_act);
                            if(5<=list.size()){
                                level3.setImageResource(R.mipmap.level_3_light);
                                honor_act="小有名气";
                                my_honor_act.setText(honor_act);
                                if(10<=list.size()){
                                    level4.setImageResource(R.mipmap.level_4_light);
                                    honor_act="乘风破浪";
                                    my_honor_act.setText(honor_act);
                                    if(20<=list.size()){
                                        level5.setImageResource(R.mipmap.level_5_light);
                                        honor_act="荣耀黄金";
                                        my_honor_act.setText(honor_act);
                                        if(30<=list.size()){
                                            level6.setImageResource(R.mipmap.level_6_light);
                                            honor_act="最强王者";
                                            my_honor_act.setText(honor_act);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else{
                    Log.e("查询失败3","原因："+e);
                }
            }
        });
    }
    private void getHonorClub() {
        BmobQuery<Stu_Club> sclubBmobQuery=new BmobQuery<>();
        sclubBmobQuery.addWhereEqualTo("stu_id",stu_id);
        sclubBmobQuery.findObjects(new FindListener<Stu_Club>() {
            @Override
            public void done(List<Stu_Club> list, BmobException e) {
                if(e==null){
                    if(1<=list.size()){
                        level_one.setImageResource(R.mipmap.level_one_light);
                        my_honor_club.setText("初入江湖");
                        if(2<=list.size()){
                            level_two.setImageResource(R.mipmap.level_two_light);
                            my_honor_club.setText("江湖少侠");
                            if(3<list.size()){
                                level_three.setImageResource(R.mipmap.level_three_light);
                                my_honor_club.setText("雄霸天下");
                            }
                        }
                    }
                }else{
                    Log.e("查询失败4","原因："+e);
                }
            }
        });
    }


}
