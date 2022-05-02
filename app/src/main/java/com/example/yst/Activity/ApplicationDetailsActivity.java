package com.example.yst.Activity;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.ApplyToClublnfo;
import com.example.yst.bean.Club;
import com.example.yst.bean.Stu_Club;
import com.example.yst.util.ImageUtils;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class ApplicationDetailsActivity extends BaseActivity {
    private TextView applicantName,applicantGender,applicantNumber,applicantReason;
    private EditText feedback;
    private Button pass,refuse;
    private String apply_id,stu_id,club_id;
    private Integer club_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_details);
        initialize();

    }

    private void initialize() {
        applicantName=findViewById(R.id.applicantName);
        applicantGender=findViewById(R.id.applicantGender);
        applicantNumber=findViewById(R.id.applicantNumber);
        applicantReason=findViewById(R.id.applicantReason);
        feedback=findViewById(R.id.feedback);
        Intent intent1=getIntent();
        apply_id=intent1.getStringExtra("applyToClublnfo_id");
        BmobQuery<ApplyToClublnfo> bmobQuery = new BmobQuery<ApplyToClublnfo>();
        bmobQuery.getObject(apply_id, new QueryListener<ApplyToClublnfo>() {
            @Override
            public void done(ApplyToClublnfo object, BmobException e) {
                if(e==null ){
                    System.out.println("this is getApply_club_name:"+object.getApply_club_name());
                    applicantName.setText(object.getApply_club_name());
                    applicantGender.setText(object.getApply_club_sex());
                    applicantNumber.setText(object.getApply_club_phone());
                    applicantReason.setText(object.getApply_club_reason());
                    stu_id=object.getStudent_id();
                    club_id=object.getClub_id();
                }else{
                    Log.e("查询失败","原因：",e);                }
            }
        });
        pass=findViewById(R.id.pass);
        refuse=findViewById(R.id.refuse);
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Stu_Club stuClub=new Stu_Club();
                    stuClub.setClub_id(club_id);
                    stuClub.setStu_id(stu_id);
                    stuClub.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if(e==null){
                                Toast.makeText(ApplicationDetailsActivity.this,"新成员已加入您的社团",Toast.LENGTH_SHORT).show();
                                BmobQuery<Club> bmobQuery = new BmobQuery<Club>();
                                bmobQuery.getObject(club_id, new QueryListener<Club>() {
                                    @Override
                                    public void done(Club club, BmobException e) {
                                        if(e==null){
                                            club_num=club.getClub_number();
                                            club.setClub_number(club_num+1);
                                            club.update(club_id, new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if(e==null){
                                                        System.out.println("参加社团人数加一");
                                                    }else{
                                                        Log.e("查询失败3","原因",e);
                                                    }
                                                }
                                            });
                                        }else{
                                            System.out.println("查询失败");
                                        }
                                    }
                                });



                            }else{
                                Log.e("创建失败","原因：",e);                            }
                        }
                    });
                    ApplyToClublnfo applyToClublnfo=new ApplyToClublnfo();
                    applyToClublnfo.setApplication_status("通过");
                    applyToClublnfo.update(apply_id, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                System.out.println("status修改成功：通过");
                                Intent intent1 = new Intent(ApplicationDetailsActivity.this, ManageClubActivity.class);
                                intent1.putExtra("clubid",club_id);
                                startActivity(intent1);
                            } else {
                                Log.e("修改失败","原因：",e);                            }
                        }
                    });
                }


        });

        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(feedback.getText().toString().equals("")){
                new AlertDialog.Builder(ApplicationDetailsActivity.this).setTitle("信息提示")//设置对话框标题
                        .setMessage("请写下您拒绝的原因。")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件，点击事件没写，自己添加
                            }
                        }).show();//在按键响应事件中显示此对话框
            }else{
                ApplyToClublnfo applyToClublnfo=new ApplyToClublnfo();
                applyToClublnfo.setApplication_status("拒绝");
                applyToClublnfo.setFeedback(feedback.getText().toString());
                applyToClublnfo.update(apply_id, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            System.out.println("status修改成功：拒绝");
                            Intent intent1 = new Intent(ApplicationDetailsActivity.this, ManageClubActivity.class);
                            intent1.putExtra("clubid",club_id);
                            startActivity(intent1);
                        } else {
                            Log.e("修改失败","原因：",e);                        }
                    }
                });
            }

            }
        });
    }
}
