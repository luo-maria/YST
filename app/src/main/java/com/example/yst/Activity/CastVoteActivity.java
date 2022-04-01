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
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class CastVoteActivity extends AppCompatActivity {
    TextView voteHead,voteIntro;
    RadioGroup rg;
    RadioButton radioButton1,radioButton2,radioButton3,radioButton;
    Button voteBut;
    String club_id9,vote_id,choice,stu_id,end_time;
    Date endTime;
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
        club_id9 = intent.getStringExtra("clubid");
        BmobQuery<Vote> bmobQuery = new BmobQuery<Vote>();
        bmobQuery.addWhereEqualTo("club_id",club_id9);
        bmobQuery.findObjects(new FindListener<Vote>() {
            @Override
            public void done(List<Vote> list, BmobException e) {
                if(e == null){
                    if(!list.isEmpty()){
                        for(Vote vote: list){
                            vote_id=vote.getObjectId();
                        }
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
                                    endTime=vote.getEnd();
                                    end_time=vote.getEnd_time();
                                    System.out.println("this is endtime:"+endTime);
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
                        Log.e("vote_id+++++++", vote_id+"qqqqqqqq");
                    }
                }else{
                    Log.e("查询失败", "原因: ", e);
                }
            }
        });

    }

    private void selectRadioBtn(){
        radioButton = findViewById(rg.getCheckedRadioButtonId());
        choice = radioButton.getText().toString();
    }
    private void voted(){
        BmobQuery<Stu_Vote> bmobQuery = new BmobQuery<Stu_Vote>();
        bmobQuery.addWhereEqualTo("stu_id",stu_id);
        BmobQuery<Stu_Vote> bmobQuery1 = new BmobQuery<Stu_Vote>();
        bmobQuery1.addWhereEqualTo("vote_id",vote_id);
        List<BmobQuery<Stu_Vote>> queries = new ArrayList<BmobQuery<Stu_Vote>>();
        queries.add(bmobQuery);
        queries.add(bmobQuery1);
        BmobQuery<Stu_Vote> query = new BmobQuery<Stu_Vote>();
        query.and(queries);
        query.findObjects(new FindListener<Stu_Vote>() {
            @Override
            public void done(List<Stu_Vote> list, BmobException e) {
                if(e==null){
                    if(list.isEmpty()){
                        toVote();
                    }else{
                        new AlertDialog.Builder(CastVoteActivity.this).setTitle("信息提示")//设置对话框标题
                                .setMessage("您已经投过票了。")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件，点击事件没写，自己添加
                                        Intent intent=new Intent(CastVoteActivity.this,MyClubDetailActivity.class);
                                        intent.putExtra("clubid",club_id9);
                                        startActivity(intent);
                                    }
                                }).show();//在按键响应事件中显示此对话框
                    }
                }else{
                    Log.e("查询投票情况失败", "原因: ", e);
                }
            }
        });
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
                    Toast.makeText(CastVoteActivity.this,"投票成功！",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(CastVoteActivity.this,MyClubDetailActivity.class);
                    intent.putExtra("clubid",club_id9);
                    startActivity(intent);
                }else {
                    Log.e("投票失败3", "原因: ", e);
                }
            }
        });
        Vote vote1=new Vote();
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
                    Log.e("计票成功！", "原因: ", e);
                }
                Log.e("投票失败4", "原因: ", e);
            }
        });
    }
    private void voteTime(){
        System.out.println("this is endtime222:"+end_time);
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date temp=null;
        try {
            temp= simpleDateFormat.parse(end_time);
            Log.e("temp:", temp.toString());
        }catch (Exception e1)
        {
            e1.printStackTrace();
        }

        //比较投票的结束时间和系统的当前时间的大小，判断当前用户是否逾期投票
        Date date=new Date();
        Long currentTimes=date.getTime()+1 * 60 * 60 *1;
        Long endTimes=temp.getTime();
        Log.e("currentTimes:", currentTimes.toString());
        Log.e("endTimes:", endTimes.toString());
        if(currentTimes>endTimes)
        {
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
        }
//        voted();
        toVote();
    }

}
