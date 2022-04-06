package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.yst.R;
import com.example.yst.bean.Club;
import com.example.yst.bean.Stu_Club;
import com.example.yst.bean.Student;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class Club_detailActivity extends AppCompatActivity {
    private Button app_btn;
    private String club_id1,stu_id,club_state,club_name,club_stu;
    private ImageView imglogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_detail);
        initView();
        app_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<Stu_Club> bmobQuery = new BmobQuery<Stu_Club>();
                bmobQuery.addWhereEqualTo("stu_id",stu_id);
                BmobQuery<Stu_Club> bmobQuery1 = new BmobQuery<Stu_Club>();
                bmobQuery1.addWhereEqualTo("club_id",club_id1);
                List<BmobQuery<Stu_Club>> queries = new ArrayList<BmobQuery<Stu_Club>>();
                queries.add(bmobQuery);
                queries.add(bmobQuery1);
                BmobQuery<Stu_Club> query = new BmobQuery<Stu_Club>();
                query.and(queries);
                query.findObjects(new FindListener<Stu_Club>() {
                    @Override
                    public void done(List<Stu_Club> list, BmobException e) {
                        if(e==null){
                            if(list.isEmpty()){
                                appClub();
                            }else{
                                Toast.makeText(Club_detailActivity.this,"您已经是该社团成员了！" ,Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(Club_detailActivity.this,"查询失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
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
        Intent intent1=getIntent();
        club_id1=intent1.getStringExtra("clubid");
        BmobQuery<Club> bmobQuery = new BmobQuery<Club>();
        bmobQuery.getObject(club_id1, new QueryListener<Club>() {
            @Override
            public void done(Club object,BmobException e) {
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
                    Toast.makeText(Club_detailActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void appClub() {
        BmobQuery<Club> bmobQuery = new BmobQuery<Club>();
        bmobQuery.getObject(club_id1, new QueryListener<Club>() {
            @Override
            public void done(Club object,BmobException e) {
                if(e==null){
                    club_name=object.getClub_name();
                    club_state=object.getClub_state();
                    club_stu=object.getStu_id();
                    if(club_stu.equals(stu_id)){
                        Toast.makeText(Club_detailActivity.this, "您创建的该社团！", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent(Club_detailActivity.this,ApplyclubActivity.class);
                        intent.putExtra("clubid",club_id1);
                        intent.putExtra("clubname", club_name);
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(Club_detailActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}

