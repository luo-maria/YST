package com.example.yst.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.adapter.ClubAdapter;
import com.example.yst.adapter.StudentAdapter;
import com.example.yst.bean.Club;
import com.example.yst.bean.Stu_Club;
import com.example.yst.bean.Student;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class AllMembersActivity extends BaseActivity {
    private TextView President;
    private EditText searchmen;
    private ImageView search;
    private String club_id10,stu_id,stu_id1,stu_id2;
    private RecyclerView mRecycler;
    private StudentAdapter myAdapter;
    private List<Student> studentList = new ArrayList<>();
    private List<String> studentids = new ArrayList<>();
    private Integer clubnum;
    private LinearLayout tips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_member);
        initView();

    }

    public StudentAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new StudentAdapter.OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
//            Toast.makeText(AllMembersActivity.this," ????????? "+position,Toast.LENGTH_SHORT).show();
            BmobQuery<Club> clubbmobQuery=new BmobQuery<>();
            clubbmobQuery.getObject(club_id10, new QueryListener<Club>() {
                @Override
                public void done(Club club, BmobException e) {
                    if(e==null){
                        if(club.getStu_id().equals(stu_id2)){
                            if (! studentList.isEmpty()) {
                                Student student=new Student();
                                student.setObjectId( studentList.get(position).getObjectId());
                                stu_id1=studentList.get(position).getObjectId();
                                System.out.println("this is stu_id1:"+stu_id1);
                                new AlertDialog.Builder(AllMembersActivity.this).setTitle("??????????????????")
                                        .setMessage("??????????????????????????????????????????????????????????????????????????????")
                                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Club club=new Club();
                                                club.setStu_id(stu_id1);
                                                club.update(club_id10, new UpdateListener() {
                                                    @Override
                                                    public void done(BmobException e) {
                                                        if(e==null){
                                                            Toast.makeText(AllMembersActivity.this,"????????????????????????",Toast.LENGTH_LONG).show();
                                                        }else{
                                                            Log.e("????????????","?????????",e);
                                                        }
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
                        }else{
                            System.out.println("??????????????????");
                        }
                    }else{
                        Log.e("????????????","??????",e);
                    }
                }
            });


        }

        @Override
        public void onItemLongClick(View view, int pos) {
            Student student=new Student();
            student.setObjectId( studentList.get(pos).getObjectId());
            stu_id1=studentList.get(pos).getObjectId();
            BmobQuery<Club> clubbmobQuery=new BmobQuery<>();
            clubbmobQuery.getObject(club_id10, new QueryListener<Club>() {
                @Override
                public void done(Club club, BmobException e) {
                    if(e==null){
                        if(club.getStu_id().equals(stu_id2)){
                            tips.setVisibility(View.VISIBLE);
                            new AlertDialog.Builder(AllMembersActivity.this).setTitle("????????????")
                                    .setMessage("?????????????????????????????????????????????")
                                    .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            BmobQuery<Stu_Club> bmobQuery = new BmobQuery<Stu_Club>();
                                            bmobQuery.addWhereEqualTo("stu_id",stu_id1);
                                            System.out.println("this is stu_id:"+stu_id1);
                                            BmobQuery<Stu_Club> bmobQuery1 = new BmobQuery<Stu_Club>();
                                            bmobQuery1.addWhereEqualTo("club_id",club_id10);
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
                                                                            Toast.makeText(AllMembersActivity.this, "??????????????????????????????", Toast.LENGTH_SHORT).show();
                                                                            Intent intent = new Intent(AllMembersActivity.this,ManageClubActivity.class);
                                                                            intent.putExtra("clubid",club_id10);
                                                                            startActivity(intent);
                                                                        }else{
                                                                            Toast.makeText(AllMembersActivity.this, "????????????3", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });

                                                            }
                                                        }
                                                    }else{
                                                        Toast.makeText(AllMembersActivity.this, "????????????2", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                            BmobQuery<Club> clubBmobQuery=new BmobQuery<>();
                                            clubBmobQuery.getObject(club_id10, new QueryListener<Club>() {
                                                @Override
                                                public void done(Club club, BmobException e) {
                                                    if(e==null) {
                                                        clubnum=club.getClub_number();
                                                        club.setClub_number(clubnum-1);
                                                        club.update(club_id10, new UpdateListener() {
                                                            @Override
                                                            public void done(BmobException e) {
                                                                if(e==null){
                                                                    System.out.println("????????????");
                                                                }else{
                                                                    Log.e("????????????","?????????",e);
                                                                }
                                                            }
                                                        });
                                                    }else{
                                                        Log.e("????????????","?????????",e);
                                                    }
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
                        }else{
                            System.out.println("??????????????????");
                        }
                    }else{
                        Log.e("????????????","??????",e);
                    }
                }
            });

        }
    };
    private void initView() {
        Student student=Student.getCurrentUser(Student.class);
        stu_id2=student.getObjectId();
        tips=findViewById(R.id.tips);
        myAdapter = new StudentAdapter(this,studentList,onRecyclerviewItemClickListener);
        President=findViewById(R.id.President);
        searchmen=findViewById(R.id.searchmen);
        search=findViewById(R.id.search);
        mRecycler=findViewById(R.id.clubmembers);
        Intent intent1=getIntent();
        club_id10=intent1.getStringExtra("clubid");
        BmobQuery<Club> bmobQuery = new BmobQuery<Club>();
        bmobQuery.getObject(club_id10, new QueryListener<Club>() {
            @Override
            public void done(Club object, BmobException e) {
                if(e==null){
                    President.setText(object.getClub_president());
                    if(object.getStu_id().equals(stu_id2)){
                        tips.setVisibility(View.VISIBLE);
                    }
                }else{
                    Log.e("????????????","?????????",e);                }
            }
        });
        BmobQuery<Stu_Club> studentQuery = new BmobQuery<Stu_Club>();
        studentQuery.addWhereEqualTo("club_id",club_id10);
        studentQuery.findObjects(new FindListener<Stu_Club>() {
            @Override
            public void done(List<Stu_Club> list, BmobException e) {
                if(e==null){
                    if (!list.isEmpty()) {
                        for (Stu_Club stu_club :list) {
                            stu_id=String.valueOf(stu_club.getStu_id());
                            studentids.add(stu_id);
                        }
                    }
                    for(String stu_id:studentids){
                        BmobQuery<Student> bmobQuery = new BmobQuery<Student>();
                        bmobQuery.getObject(stu_id, new QueryListener<Student>() {
                            @Override
                            public void done(Student object,BmobException e) {
                                if(e==null){
                                    studentList.add(object);
                                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(AllMembersActivity.this, 4);
                                    mRecycler.setLayoutManager(layoutManager);
                                    mRecycler.setAdapter(myAdapter);

                                }else{
                                    Log.e("????????????","?????????",e);                                }
                            }
                        });
                    }
                }else{
                    Log.e("????????????2","?????????",e);                }
            }
        });
    }
}
