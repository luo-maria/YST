package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.Vote;
import com.example.yst.util.DateUtils;

import java.util.Calendar;
import java.util.Date;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AddVoteActivity extends AppCompatActivity implements View.OnClickListener{
    EditText vote_title,vote_intro,vote_item1,vote_item2,vote_item3;
    Button vote_but;
    TextView choose_day1,choose_time1;
    String club_id8,dayTime,theTime,endTime;
    //选择日期Dialog
    private DatePickerDialog datePickerDialog;
    //选择时间Dialog
    private TimePickerDialog timePickerDialog;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        initialize();
    }

    private void initialize() {
        vote_title=findViewById(R.id.vote_title);
        vote_intro=findViewById(R.id.vote_intro);
        vote_item1=findViewById(R.id.vote_item1);
        vote_item2=findViewById(R.id.vote_item2);
        vote_item3=findViewById(R.id.vote_item3);
        vote_but=findViewById(R.id.vote_but);
        choose_day1=findViewById(R.id.chooseDay);
        choose_time1=findViewById(R.id.chooseTime);
        calendar = Calendar.getInstance();
        Intent intent1=getIntent();
        club_id8=intent1.getStringExtra("clubid");
        choose_time1.setOnClickListener(this);
        choose_day1.setOnClickListener(this);
        vote_but.setOnClickListener(this);
    }
    private void showDailog() {
        datePickerDialog = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //monthOfYear 得到的月份会减1所以我们要加1
                dayTime = String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1) + "-" + Integer.toString(dayOfMonth);
                Log.d("测试", dayTime);
                choose_day1.setText(dayTime);
            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        //自动弹出键盘问题解决
        datePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
    private void showTime() {
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                theTime=Integer.toString(hourOfDay)+":"+Integer.toString(minute)+":"+"00";
                Log.d("测试",  theTime);
                choose_time1.setText(theTime);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
        timePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chooseDay:
                showDailog();
                break;
            case R.id.chooseTime:
                showTime();
                break;
            case R.id.vote_but:
               addvote();
                break;
        }
    }

    private void addvote() {
        Vote vote=new Vote();
        vote.setHeadline(vote_title.getText().toString());
        vote.setIntroduction(vote_intro.getText().toString());
        vote.setIntroduction(vote_intro.getText().toString());
        vote.setOption1(vote_item1.getText().toString());
        vote.setOption2(vote_item2.getText().toString());
        vote.setOption3(vote_item3.getText().toString());
        vote.setOption1_num(0);
        vote.setOption2_num(0);
        vote.setOption3_num(0);
        vote.setClub_id(club_id8);
        endTime=dayTime+" "+theTime;
        Date date = DateUtils.strToDate(dayTime+ " " +theTime);
        System.out.println("222222222222222222222222:"+date);
        vote.setEnd_time(endTime);
        vote.setEnd(date);
        vote.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    Toast.makeText(AddVoteActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
                    //刷新本页面
                    Intent intent=new Intent(AddVoteActivity.this, ManageClubActivity.class);
                    intent.putExtra("clubid",club_id8);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(AddVoteActivity.this,"创建数据失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
