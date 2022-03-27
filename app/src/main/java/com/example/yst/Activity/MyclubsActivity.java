package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.adapter.ClubAdapter;
import com.example.yst.bean.Club;
import com.example.yst.bean.Stu_Club;
import com.example.yst.bean.Student;
import com.example.yst.fragment.OrganizationFragment;
import com.example.yst.util.ImageUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class MyclubsActivity extends AppCompatActivity {
    List<Club> clubs;
    List<Club> clubs1=new ArrayList<Club>();
    List<Stu_Club> stu_clubs;
    String stu_id,club_id1,club_id5,club_id6;
    RecyclerView recyclerViewclub;
    ClubAdapter clubAdapter;
    Button club_created,club_entered;
    List<String> club_ids= new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myclubs);
        club_created=findViewById(R.id.createclub);
        club_entered=findViewById(R.id.enterclub);
        recyclerViewclub=findViewById(R.id.myclubs1);
        initialize();
        club_created.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryData();
            }
        });
        club_entered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryData1();
            }
        });

    }
    public ClubAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new ClubAdapter.OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            Intent intent = new Intent(MyclubsActivity.this,ManageClubActivity.class);
            Toast.makeText(MyclubsActivity.this," 点击了 "+position,Toast.LENGTH_SHORT).show();
            if (! clubs.isEmpty()) {
                Club club = new Club();
                club.setObjectId( clubs.get(position).getObjectId());
                club_id1=clubs.get(position).getObjectId();
                System.out.println("this is myclubsid:"+club_id1);
                intent.putExtra("clubid",club_id1);
            }
            startActivity(intent);
            finish();
        }
    };
    private void initialize() {
        clubAdapter = new ClubAdapter(this,clubs,onRecyclerviewItemClickListener);
        recyclerViewclub.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        queryData();
    }

    private void queryData() {
        Student userInfo = BmobUser.getCurrentUser(Student.class);
        stu_id=userInfo.getObjectId();
        BmobQuery<Club> clubBmobQuery = new BmobQuery<>();
        clubBmobQuery.addWhereEqualTo("stu_id", stu_id);
        clubBmobQuery.findObjects(new FindListener<Club>() {
            @Override
            public void done(List<Club> object, BmobException e) {
                if (e == null) {
                    clubs = object;
                    clubAdapter.setClubList(clubs);
                    recyclerViewclub.setAdapter(clubAdapter);
                } else {
                    Log.e("查询失败", "原因: ", e);
                }
            }
        });
    }
    private void queryData1() {
        Student userInfo = BmobUser.getCurrentUser(Student.class);
        stu_id=userInfo.getObjectId();
        BmobQuery<Stu_Club> clubBmobQuery = new BmobQuery<>();
        clubBmobQuery.addWhereEqualTo("stu_id", stu_id);
        clubBmobQuery.findObjects(new FindListener<Stu_Club>() {
            @Override
            public void done(List<Stu_Club> object, BmobException e) {
                if (e == null) {
                    if (!object.isEmpty()) {
                        for (Stu_Club stu_club :object) {
                            club_id5=String.valueOf(stu_club.getClub_id());
                            club_ids.add(club_id5);
                        }
                        }
                    Iterator it=club_ids.iterator();
                    while (it.hasNext()){
                        club_id6= (String) it.next();
                        System.out.println("this is clubid6:"+club_id6);
                        BmobQuery<Club> bmobQuery = new BmobQuery<Club>();
                        bmobQuery.getObject(club_id6, new QueryListener<Club>() {
                            @Override
                            public void done(Club object,BmobException e) {
                                if(e==null){
                                    clubs1.add(object);
                                }else{
                                    Toast.makeText(MyclubsActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    System.out.println("this is clubs888888888:"+clubs1);
                    clubAdapter.setClubList(clubs1);
                    recyclerViewclub.setAdapter(clubAdapter);
                } else {
                    Log.e("查询失败", "原因: ", e);
                }
            }
        });
    }
}
