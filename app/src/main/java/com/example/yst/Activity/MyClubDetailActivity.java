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
import com.example.yst.bean.Stu_Vote;
import com.example.yst.bean.Student;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class MyClubDetailActivity extends BaseActivity implements View.OnClickListener{
    private ImageView imageView4,backPre;
    private TextView myclub_name,myclub_leader_name,myclub_leader,myclub_category,myclub_campus,myclub_rank,club_numbers;
    private String club_id7,stu_id,sclub_id;
    private Button Quit;
    private LinearLayout look_activity,look_notice,toVote,exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_joined);
        initialize();
        queryData();


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Quit:
                QuitClub();
                break;
            case R.id.look_activity:
                LookActivity();
                break;
            case R.id.look_notice:
                LookNotice();
                break;
            case R.id.toVote:
                findVote();
                break;
            case R.id.exit:
                allMember();
                break;
            case R.id.backPre1:
                backPre();
                break;
        }
    }

    private void LookNotice() {
        Intent intent1 = new Intent(MyClubDetailActivity.this,NewsInClubActivity.class);
        intent1.putExtra("clubid",club_id7);
        startActivity(intent1);
    }

    private void backPre(){
        Intent intent1 = new Intent(MyClubDetailActivity.this, MyclubsActivity.class);
        intent1.putExtra("clubid",club_id7);
        startActivity(intent1);
    }

    private void allMember() {
        Intent intent=new Intent(MyClubDetailActivity.this,AllMembersActivity.class);
        intent.putExtra("clubid",club_id7);
        startActivity(intent);
    }

    private void findVote() {
        Intent intent=new Intent(MyClubDetailActivity.this,ManageVotesActivity.class);
        intent.putExtra("clubid",club_id7);
        startActivity(intent);
    }

    private void initialize() {
        backPre=findViewById(R.id.backPre1);
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
        backPre.setOnClickListener(this);
        Intent intent=getIntent();
        club_id7=intent.getStringExtra("clubid");
        Student userInfo = BmobUser.getCurrentUser(Student.class);
        stu_id=userInfo.getObjectId();
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
                    club_numbers.setText(String.valueOf(object.getClub_number()));
                    imageView4.setImageBitmap(BitmapFactory.decodeFile(object.getLogo_url()));
                }else{
                    Log.e("????????????","?????????",e);                }
            }
        });
    }
    private void QuitClub(){
        new AlertDialog.Builder(MyClubDetailActivity.this).setTitle("????????????")
                .setMessage("????????????????????????????????????????????????????????????")
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BmobQuery<Stu_Club> bmobQuery = new BmobQuery<Stu_Club>();
                        bmobQuery.addWhereEqualTo("stu_id",stu_id);
                        BmobQuery<Stu_Club> bmobQuery1 = new BmobQuery<Stu_Club>();
                        bmobQuery1.addWhereEqualTo("club_id",club_id7);
                        List<BmobQuery<Stu_Club>> queries = new ArrayList<BmobQuery<Stu_Club>>();
                        queries.add(bmobQuery);
                        queries.add(bmobQuery1);
                        BmobQuery<Stu_Club> query = new BmobQuery<Stu_Club>();
                        query.and(queries);
                        query.findObjects(new FindListener<Stu_Club>() {
                            @Override
                            public void done(List<Stu_Club> list, BmobException e) {
                                if(e==null){
                                    if (!list.isEmpty()) {
                                        for (Stu_Club stu_club :list) {
                                            stu_club.delete(new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if(e==null){
                                                        Toast.makeText(MyClubDetailActivity.this, "??????????????????????????????", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(MyClubDetailActivity.this,HomeActivity.class);
                                                        startActivity(intent);
                                                    }else{
                                                        Toast.makeText(MyClubDetailActivity.this, "????????????3", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                        }
                                    }
                                }else{
                                    Log.e("????????????2","?????????",e);                                }
                            }
                        });
                    }
                })
                .setNegativeButton("??????", new DialogInterface.OnClickListener() {//??????????????????
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//???????????????????????????????????????????????????????????????
                    }

                })
                .show();

    }

    private void LookActivity (){
        // ????????????????????????????????? ???????????????id

        Intent intent = new Intent(MyClubDetailActivity.this,ActivitiesInClubsActivity.class);
        intent.putExtra("clubid",club_id7);
        startActivity(intent);
    }

}
