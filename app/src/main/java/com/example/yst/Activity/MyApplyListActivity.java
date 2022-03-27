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
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.adapter.ApplyInfoAdapter;
import com.example.yst.adapter.MyApplyInfoAdapter;
import com.example.yst.bean.ApplyToClublnfo;
import com.example.yst.bean.Student;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class MyApplyListActivity extends AppCompatActivity {
    RecyclerView recyclerViewclub;
    MyApplyInfoAdapter myapplyInfoAdapter;
    List<ApplyToClublnfo> myinfos;
    String applyToClublnfo_id,club_id4, stu_id,app_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_apply_list);
        initialize();
    }
    private void initialize() {
        recyclerViewclub=findViewById(R.id.myapplys);
        myapplyInfoAdapter = new MyApplyInfoAdapter(this,myinfos,onRecyclerviewItemClickListener);
        recyclerViewclub.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        queryData();
    }

    private void queryData() {
        Student userInfo = BmobUser.getCurrentUser(Student.class);
        stu_id=userInfo.getObjectId();
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
                                        }).show();//在按键响应事件中显示此对话框
                            }else{
                                applyToClublnfo.setObjectId( myinfos.get(position).getObjectId());
                                applyToClublnfo_id=myinfos.get(position).getObjectId();
                                intent.putExtra("applyToClublnfo_id",applyToClublnfo_id);
                                startActivity(intent);
                                finish();
                            }
                        }else{
                            Toast.makeText(MyApplyListActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        }
    };
}
