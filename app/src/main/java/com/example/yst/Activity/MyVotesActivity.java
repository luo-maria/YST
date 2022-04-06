package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.yst.R;

import com.example.yst.adapter.VoteAdapter;
import com.example.yst.bean.Club;
import com.example.yst.bean.Stu_Vote;
import com.example.yst.bean.Stu_activity;
import com.example.yst.bean.Student;
import com.example.yst.bean.Vote;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class MyVotesActivity extends AppCompatActivity {
    private ImageView addvotes;
    private RecyclerView recyclerViewvote;
    private VoteAdapter voteAdapter;
    private List<Vote> voteslist=new ArrayList<Vote>();
    private String club_id,vote_id,club_name,stu_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_votes);
        initView();
        addvotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyVotesActivity.this,AddVoteActivity.class);
                intent.putExtra("clubname",club_name);
                intent.putExtra("clubid",club_id);
                startActivity(intent);
            }
        });
    }
    public VoteAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener=new VoteAdapter.OnRecyclerviewItemClickListener(){
        @Override
        public void onItemClickListener(View v, int position) {
            if(!voteslist.isEmpty()){
                Vote vote=new Vote();
                vote.setObjectId(voteslist.get(position).getObjectId());
                vote_id=voteslist.get(position).getObjectId();
            }
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
                        if(!list.isEmpty()){
                            Intent intent=new Intent(MyVotesActivity.this,VoteResultsActivity.class);
                            intent.putExtra("voteid",vote_id);
                            startActivity(intent);
                        }else{
                            BmobQuery<Club> bmobQuery=new BmobQuery<Club>();
                            bmobQuery.getObject(club_id, new QueryListener<Club>() {
                                @Override
                                public void done(Club club, BmobException e) {
                                    if(e==null){
                                        if(club.getStu_id().equals(stu_id)){
                                            Intent intent=new Intent(MyVotesActivity.this,VoteResultsActivity.class);
                                            intent.putExtra("voteid",vote_id);
                                            startActivity(intent);
                                        }else{
                                            Intent intent=new Intent(MyVotesActivity.this,CastVoteActivity.class);
                                            intent.putExtra("voteid",vote_id);
                                            intent.putExtra("clubid",club_id);
                                            startActivity(intent);
                                        }
                                    }else{
                                        Log.e("查询失败3", "原因: ", e);
                                    }
                                }
                            });
                        }
                    }else{
                        Log.e("查询失败2", "原因: ", e);
                    }
                }
            });

        }
    };
    private void initView() {
        Student student=Student.getCurrentUser(Student.class);
        stu_id=student.getObjectId();
        Intent intent = getIntent();
        club_name = intent.getStringExtra("clubname");
        club_id = intent.getStringExtra("clubid");
        addvotes=findViewById(R.id.addvotes);
        recyclerViewvote=findViewById(R.id.recyclerview_my_vote);
        voteAdapter=new VoteAdapter(MyVotesActivity.this,voteslist,onRecyclerviewItemClickListener);
        recyclerViewvote.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        queryVoteData();
    }

    private void queryVoteData() {
        BmobQuery<Vote> bmobQuery=new BmobQuery<Vote>();
        bmobQuery.addWhereEqualTo("club_id",club_id);
        bmobQuery.findObjects(new FindListener<Vote>() {
            @Override
            public void done(List<Vote> list, BmobException e) {
                if(e==null){
                    voteslist=list;
                    voteAdapter.setVoteList(voteslist);
                    recyclerViewvote.setAdapter(voteAdapter);
                }else{
                    Log.e("查询失败1", "原因: ", e);
                }
            }
        });
    }
}

