package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class MyclubsActivity extends BaseActivity {
    private List<Club> clubs;
    private List<Club> clubs1=new ArrayList<Club>();
    private List<Stu_Club> stu_clubs;
    private String stu_id,club_id1,club_id5,club_id2;
    private RecyclerView recyclerViewclub;
    private ClubAdapter clubAdapter,clubAdapter1 ;
    private Button club_created,club_entered;
    private List<String> club_ids= new ArrayList<String>();
    private ImageView backPre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myclubs);
        club_created=findViewById(R.id.createclub);
        club_entered=findViewById(R.id.enterclub);
        recyclerViewclub=findViewById(R.id.myclubs1);
        backPre=findViewById(R.id.backPre);
        initialize();
        backPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyclubsActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        club_created.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryData();
                club_created.setBackgroundResource(R.color.skin_topbar_bg_color_night);
                club_entered.setBackgroundResource(R.color.colorPrimary);
            }
        });
        club_entered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(clubs1);
                queryData1();
                club_entered.setBackgroundResource(R.color.skin_topbar_bg_color_night);
                club_created.setBackgroundResource(R.color.colorPrimary);
            }
        });

    }
    public ClubAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new ClubAdapter.OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            Intent intent = new Intent(MyclubsActivity.this,ManageClubActivity.class);
            Toast.makeText(MyclubsActivity.this," ????????? "+position,Toast.LENGTH_SHORT).show();
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
            Toast.makeText(MyclubsActivity.this," ????????? "+position,Toast.LENGTH_SHORT).show();
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
        queryData();
    }

    private void queryData() {
        Student userInfo = BmobUser.getCurrentUser(Student.class);
        stu_id=userInfo.getObjectId();
        BmobQuery<Club> clubBmobQuery1 = new BmobQuery<>();
        clubBmobQuery1.addWhereEqualTo("audit_state", "????????????");
        BmobQuery<Club> clubBmobQuery = new BmobQuery<>();
        clubBmobQuery.addWhereEqualTo("stu_id", stu_id);
        List<BmobQuery<Club>> queries = new ArrayList<BmobQuery<Club>>();
        queries.add(clubBmobQuery);
        queries.add(clubBmobQuery1);
        BmobQuery<Club> query = new BmobQuery<Club>();
        query.and(queries);
        query.findObjects(new FindListener<Club>() {
            @Override
            public void done(List<Club> object, BmobException e) {
                if (e == null) {
                    clubs = object;
                    clubAdapter.setClubList(clubs);
                    recyclerViewclub.setAdapter(clubAdapter);
                } else {
                    Log.e("????????????1", "??????: ", e);
                }
            }
        });
    }
    private void queryData1() {
        System.out.println("this is clubs1111111111111111111111"+clubs1.isEmpty());
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
                    if(clubs1.isEmpty()){
                        for(String clubid:club_ids){
                            BmobQuery<Club> bmobQuery = new BmobQuery<Club>();
                            bmobQuery.getObject(clubid, new QueryListener<Club>() {
                                @Override
                                public void done(Club object,BmobException e) {
                                    if(e==null){
                                        clubs1.add(object);
                                    }else{
                                        Log.e("????????????2", "??????: ", e);
//                                    Toast.makeText(MyclubsActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                    clubAdapter1.setClubList(clubs1);
                    recyclerViewclub.setAdapter(clubAdapter1);

                } else {
                    Log.e("????????????3", "??????: ", e);
                }
            }
        });

    }
}
