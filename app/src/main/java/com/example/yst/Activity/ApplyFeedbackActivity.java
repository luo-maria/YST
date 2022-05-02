package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.ApplyToClublnfo;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class ApplyFeedbackActivity extends BaseActivity {
    private String apply_id,status,feedbackinfo;
    private TextView feedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent1=getIntent();
        apply_id=intent1.getStringExtra("applyToClublnfo_id");
        BmobQuery<ApplyToClublnfo> bmobQuery = new BmobQuery<ApplyToClublnfo>();
        bmobQuery.getObject(apply_id, new QueryListener<ApplyToClublnfo>() {
            @Override
            public void done(ApplyToClublnfo object, BmobException e) {
                if(e==null ){
                    System.out.println("this is getApply_club_name:"+object.getApply_club_name());
                    status=object.getApplication_status();
                    feedbackinfo=object.getFeedback();
                    if(status.equals("通过")){
                        setContentView(R.layout.application_feedback_success);

                    }
                    if(status.equals("拒绝")){
                        setContentView(R.layout.application_feedback_fail);
                        feedback=findViewById(R.id.feedbackview);
                        feedback.setText(feedbackinfo);
                    }
                }else{
                    Log.e("查询失败","原因：",e);                }
            }
        });
    }
}
