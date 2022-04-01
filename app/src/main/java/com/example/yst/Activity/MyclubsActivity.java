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
    String stu_id,club_id1,club_id5,club_id2;
    RecyclerView recyclerViewclub;
    ClubAdapter clubAdapter,clubAdapter1 ;
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
                if(clubs1.isEmpty()){
                    queryData1();
                }else{
                    System.out.println(clubs1);
                }

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
    public ClubAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener1 = new ClubAdapter.OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            Intent intent = new Intent(MyclubsActivity.this,MyClubDetailActivity.class);
            Toast.makeText(MyclubsActivity.this," 点击了 "+position,Toast.LENGTH_SHORT).show();
            if (! clubs1.isEmpty()) {
                Club club = new Club();
                club.setObjectId( clubs1.get(position).getObjectId());
                club_id2=clubs1.get(position).getObjectId();
                System.out.println("this is myclubsid:"+club_id2);
                intent.putExtra("clubid",club_id2);
            }
            startActivity(intent);
            finish();
        }
    };
    private void initialize() {
        clubAdapter = new ClubAdapter(this,clubs,onRecyclerviewItemClickListener);
        clubAdapter1 = new ClubAdapter(this,clubs,onRecyclerviewItemClickListener1);
        recyclerViewclub.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        queryData1();
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
                    for(String clubid:club_ids){
                        BmobQuery<Club> bmobQuery = new BmobQuery<Club>();
                        bmobQuery.getObject(clubid, new QueryListener<Club>() {
                            @Override
                            public void done(Club object,BmobException e) {
                                if(e==null){
                                    clubs1.add(object);
                                    clubAdapter1.setClubList(clubs1);
                                    recyclerViewclub.setAdapter(clubAdapter1);
                                }else{
                                    Toast.makeText(MyclubsActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                } else {
                    Log.e("查询失败", "原因: ", e);
                }
            }
        });

    }
}
