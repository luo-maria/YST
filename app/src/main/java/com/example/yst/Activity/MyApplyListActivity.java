package com.example.yst.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.adapter.ApplyClubAdapter;
import com.example.yst.adapter.ApplyInfoAdapter;
import com.example.yst.adapter.MyApplyInfoAdapter;
import com.example.yst.bean.ApplyToClublnfo;
import com.example.yst.bean.Club;
import com.example.yst.bean.Student;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class MyApplyListActivity extends BaseActivity {
    private RecyclerView recyclerViewclub;
    private MyApplyInfoAdapter myapplyInfoAdapter;
    private ApplyClubAdapter applyClubAdapter;
    private List<ApplyToClublnfo> myinfos;
    private List<Club> clubs;
    private Button applyclubs,clubapply;
    private String applyToClublnfo_id,club_id4, stu_id,app_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_apply_list);
        initialize();
        applyclubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 queryData();
                applyclubs.setBackgroundResource(R.color.skin_topbar_bg_color_night);
                clubapply.setBackgroundResource(R.color.colorPrimary);
            }
        });
        clubapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryData1();
                clubapply.setBackgroundResource(R.color.skin_topbar_bg_color_night);
                applyclubs.setBackgroundResource(R.color.colorPrimary);
            }
        });
    }
    private void initialize() {
        Student userInfo = BmobUser.getCurrentUser(Student.class);
        stu_id=userInfo.getObjectId();
        applyclubs=findViewById(R.id.applyclubs);
        clubapply=findViewById(R.id.clubapply);
        recyclerViewclub=findViewById(R.id.myapplys);
        myapplyInfoAdapter = new MyApplyInfoAdapter(this,myinfos,onRecyclerviewItemClickListener);
        applyClubAdapter=new ApplyClubAdapter(this,clubs,onRecyclerviewItemClickListener1);
        recyclerViewclub.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        queryData();
    }
    private void queryData1(){
        BmobQuery<Club> clubBmobQuery=new BmobQuery<>();
        clubBmobQuery.addWhereEqualTo("stu_id",stu_id);
        clubBmobQuery.findObjects(new FindListener<Club>() {
            @Override
            public void done(List<Club> list, BmobException e) {
                if (e == null) {
                    clubs=list;
                    applyClubAdapter.setApplyClubList(clubs);
                    recyclerViewclub.setAdapter(applyClubAdapter);
                }else{
                    Log.e("查询失败2", "原因: ", e);
                }
            }
        });
    }
    private void queryData() {
        BmobQuery<ApplyToClublnfo> clubBmobQuery = new BmobQuery<>();
        clubBmobQuery.addWhereEqualTo("student_id",  stu_id);
        clubBmobQuery.findObjects(new FindListener<ApplyToClublnfo>() {
            @Override
            public void done(List<ApplyToClublnfo> object, BmobException e) {
                if (e == null) {
                    myinfos = object;
                    myapplyInfoAdapter.setApplyToClublnfoList(myinfos);
                    recyclerViewclub.setAdapter(myapplyInfoAdapter);
                } else {
                    Log.e("查询失败", "原因: ", e);
                }
            }
        });
    }

    public MyApplyInfoAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener = new MyApplyInfoAdapter.OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {
            if (! myinfos.isEmpty()) {
                Intent intent = new Intent(MyApplyListActivity.this,ApplyFeedbackActivity.class);
                ApplyToClublnfo applyToClublnfo = new ApplyToClublnfo();
                applyToClublnfo_id=myinfos.get(position).getObjectId();
                BmobQuery<ApplyToClublnfo> bmobQuery = new BmobQuery<>();
                bmobQuery.getObject(applyToClublnfo_id, new QueryListener<ApplyToClublnfo>() {
                    @Override
                    public void done(ApplyToClublnfo object, BmobException e) {
                        if(e==null ){
                            app_status=object.getApplication_status();
                            if(app_status.equals("未审核")){
                                new AlertDialog.Builder(MyApplyListActivity.this).setTitle("信息提示")//设置对话框标题
                                        .setMessage("社团还未审核您的简历，请耐心等待")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件，点击事件没写，自己添加
                                            }
                                        }).show();
                            }else{
                                applyToClublnfo.setObjectId( myinfos.get(position).getObjectId());
                                applyToClublnfo_id=myinfos.get(position).getObjectId();
                                intent.putExtra("applyToClublnfo_id",applyToClublnfo_id);
                                startActivity(intent);
                                finish();
                            }
                        }else{
                            Log.e("查询失败","原因：",e);                        }
                    }
                });

            }

        }
    };
    public ApplyClubAdapter.OnRecyclerviewItemClickListener onRecyclerviewItemClickListener1 = new ApplyClubAdapter.OnRecyclerviewItemClickListener() {
        @Override
        public void onItemClickListener(View v, int position) {

        }


    };

}
