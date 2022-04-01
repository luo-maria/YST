package com.example.yst.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yst.R;
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

public class AllMembersActivity extends AppCompatActivity {
    TextView President;
    EditText searchmen;
    ImageView search;
    String club_id10,stu_id;
    private RecyclerView mRecycler;
    private StudentAdapter myAdapter;
    private List<Student> studentList = new ArrayList<>();
    private List<String> studentids = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_member);
        initView();
    }
//    class MyDecoration extends RecyclerView.ItemDecoration{
//        @Override
//        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//            super.getItemOffsets(outRect, view, parent, state);
//            //outRect.set()中的参数分别对应左、上、右、下的间隔
//            outRect.set(10,10,10,10);
//        }
//    }
    private void initView() {
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
                }else{
                    Toast.makeText(AllMembersActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                }
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
                                    myAdapter = new StudentAdapter(studentList);
                                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(AllMembersActivity.this, 4);
                                    mRecycler.setLayoutManager(layoutManager);
                                    mRecycler.setAdapter(myAdapter);
                                    System.out.println("this is  Students1:"+ studentList);
                                }else{
                                    Toast.makeText(AllMembersActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }else{
                    Toast.makeText(AllMembersActivity.this, "查询失败2", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
