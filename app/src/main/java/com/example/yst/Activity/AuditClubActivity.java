package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.Club;
import com.example.yst.bean.Student;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class AuditClubActivity extends BaseActivity{
    private Button app_btn,passbtu,refusebtu;
    private String club_id1,stu_id,club_state,club_name,club_stu,unoin_id;
    private ImageView imglogo;
    private LinearLayout auditclub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_detail);
        initView();
        passbtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Club club=new Club();
                club.setAudit_state("审核通过");
                club.update(club_id1, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(AuditClubActivity.this, "您已审批通过。", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(AuditClubActivity.this, ManageClubActivity.class);
                            intent.putExtra("clubid",unoin_id);
                            startActivity(intent);
                        }   else{
                            Log.e("更新失败","原因：",e);
                        }
                    }
                });
            }
        });
        refusebtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Club club=new Club();
                club.setAudit_state("审核未通过");
                club.update(club_id1, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(AuditClubActivity.this, "您已审批未通过。", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(AuditClubActivity.this, ManageClubActivity.class);
                            intent.putExtra("clubid",unoin_id);
                            startActivity(intent);
                        }   else{
                            Log.e("更新失败","原因：",e);
                        }
                    }
                });
            }
        });
    }

    private void initView() {
        Student student=Student.getCurrentUser(Student.class);
        stu_id=student.getObjectId();
        TextView level = (TextView)findViewById(R.id.level1);
        TextView campus = (TextView)findViewById(R.id.campus1) ;
        TextView kind = (TextView)findViewById(R.id.kind1);
        TextView clubname = (TextView)findViewById(R.id.club_name);
        TextView time = (TextView)findViewById(R.id.time);
        TextView createname = (TextView)findViewById(R.id.createname);
        TextView club_intro = (TextView)findViewById(R.id.club_intro1);
        TextView call = (TextView)findViewById(R.id.call);
        imglogo =findViewById(R.id.logophoto);
        app_btn = (Button) findViewById(R.id.apply);
        auditclub=findViewById(R.id.auditclub);
        passbtu=findViewById(R.id.passbtu);
        refusebtu=findViewById(R.id.refusebtu);
        Intent intent1=getIntent();
        unoin_id=intent1.getStringExtra("clubid");
        club_id1=intent1.getStringExtra("clubid2");
        if(unoin_id.equals("92ec346797")){
            app_btn.setVisibility(View.GONE);
            auditclub.setVisibility(View.VISIBLE);
        }
        BmobQuery<Club> bmobQuery = new BmobQuery<Club>();
        bmobQuery.getObject(club_id1, new QueryListener<Club>() {
            @Override
            public void done(Club object, BmobException e) {
                if(e==null){
                    level.setText(object.getClub_rank());
                    campus.setText(object.getClub_campus());
                    kind.setText(object.getClub_category());
                    clubname.setText(object.getClub_name());
                    createname.setText(object.getClub_president());
                    club_intro .setText(object.getClub_intro());
                    call.setText(object.getPre_number());
                    time.setText(object.getCreatedAt().substring(0,10));
                    club_name=object.getClub_name();
                    if(object.getLogo_url()!=null){
                        imglogo.setImageBitmap(BitmapFactory.decodeFile(object.getLogo_url()));
                    }
                }else{
                    Log.e("更新失败","原因：",e);                }
            }
        });
    }
}
