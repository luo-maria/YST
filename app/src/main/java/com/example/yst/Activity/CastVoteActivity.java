package com.example.yst.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.Club;
import com.example.yst.bean.Stu_Vote;
import com.example.yst.bean.Student;
import com.example.yst.bean.Vote;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class CastVoteActivity extends AppCompatActivity {
    private TextView voteHead,voteIntro;
    private RadioGroup rg;
    private RadioButton radioButton1,radioButton2,radioButton3,radioButton;
    private Button voteBut;
    private String club_id9,vote_id,choice,stu_id,end_time;
    private Date endTime;
    private BmobDate voteend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_vote);
        initView();

    }

    private void initView() {
        voteHead = findViewById(R.id.voteHead);
        voteIntro = findViewById(R.id.voteIntro);
        rg = findViewById(R.id.rg2);
        voteBut = findViewById(R.id.voteBut);
        radioButton1=findViewById(R.id.option1);
        radioButton2=findViewById(R.id.option2);
        radioButton3=findViewById(R.id.option3);
        Student userInfo =Student.getCurrentUser(Student.class);
        stu_id=userInfo.getObjectId();
        Intent intent = getIntent();
        vote_id = intent.getStringExtra("voteid");
        club_id9 = intent.getStringExtra("clubid");
        BmobQuery<Vote> bmobQuery1 = new BmobQuery<Vote>();
        bmobQuery1.getObject(vote_id, new QueryListener<Vote>() {
            @Override
            public void done(Vote vote, BmobException e) {
                if(e==null){
                    voteHead.setText(vote.getHeadline());
                    voteIntro.setText(vote.getIntroduction());
                    radioButton1.setText(vote.getOption1());
                    radioButton2.setText(vote.getOption2());
                    radioButton3.setText(vote.getOption3());
                    voteend=vote.getVoteEnd();
                }else{
                    Log.e("查询失败2", "原因: ", e);
                }
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectRadioBtn();
            }
        });
        voteBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voteTime();
            }
        });
    }

    private void selectRadioBtn(){
        radioButton = findViewById(rg.getCheckedRadioButtonId());
        choice = radioButton.getText().toString();
    }
    private void toVote(){
        Stu_Vote stu_vote=new Stu_Vote();
        stu_vote.setStu_id(stu_id);
        stu_vote.setVote_id(vote_id);
        stu_vote.setChoice(choice);
        stu_vote.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Intent intent=new Intent(CastVoteActivity.this,MyClubDetailActivity.class);
                    intent.putExtra("clubid",club_id9);
                    startActivity(intent);
                }else {
                    Log.e("投票失败3", "原因: ", e);
                }
            }
        });
        BmobQuery<Vote> bmobQuery = new BmobQuery<Vote>();
        bmobQuery.getObject(vote_id, new QueryListener<Vote>() {
            @Override
            public void done(Vote vote1, BmobException e) {
                if(e==null){
                    if(choice.equals(vote1.getOption1())){
                        vote1.setOption1_num(vote1.getOption1_num()+1);
                    }else if(choice.equals(vote1.getOption2())){
                        vote1.setOption2_num(vote1.getOption2_num()+1);
                    }else if(choice.equals(vote1.getOption3())){
                        vote1.setOption3_num(vote1.getOption3_num()+1);
                    }else{
                        Toast.makeText(CastVoteActivity.this,"投票失败！",Toast.LENGTH_SHORT).show();
                    }
                    vote1.update(vote_id,new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(CastVoteActivity.this,"投票成功2！",Toast.LENGTH_SHORT).show();
                            }else{
                                Log.e("投票失败4", "原因: ", e);
                            }
                        }
                    });
                }else{
                    Log.e("投票失败5", "原因: ", e);
                }
            }
        });
    }
    private void voteTime(){
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date temp=null;
        try {
            temp= simpleDateFormat.parse(voteend.getDate());
            Log.e("temp:", temp.toString());
        }catch (Exception e1)
        {
            e1.printStackTrace();
        }

        //比较投票的结束时间和系统的当前时间的大小，判断当前用户是否逾期投票
        Date date=new Date();
        Long currentTimes=date.getTime()+8 * 60 * 60 *1000;
        Long endTimes=temp.getTime();
        if(currentTimes>endTimes) {
            new AlertDialog.Builder(CastVoteActivity.this).setTitle("信息提示")//设置对话框标题
                    .setMessage("投票时间已经过了。")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件，点击事件没写，自己添加
                            Intent intent=new Intent(CastVoteActivity.this,MyClubDetailActivity.class);
                            intent.putExtra("clubid",club_id9);
                            startActivity(intent);
                        }
                    }).show();//在按键响应事件中显示此对话框
        }else{
            toVote();
        }

    }

}
