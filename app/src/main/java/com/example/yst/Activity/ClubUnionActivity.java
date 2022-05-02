package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.Club;
import com.example.yst.bean.Stu_Club;
import com.example.yst.bean.Student;
import com.example.yst.fragment.HomePageFragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class ClubUnionActivity extends BaseActivity {
    private Button manage_union;
    private String club_id1,stu_id,club_state,club_name,club_stu;
    private ImageView imglogo,backPre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_union);
        initView();
        backPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClubUnionActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        manage_union.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClubUnionActivity.this, ManageClubActivity.class);
                intent.putExtra("clubid","92ec346797");
                startActivity(intent);
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
        backPre=findViewById(R.id.backPre);
        imglogo =findViewById(R.id.logophoto);
        manage_union = (Button) findViewById(R.id.manage_union);
        Intent intent1=getIntent();
        BmobQuery<Club> bmobQuery = new BmobQuery<Club>();
        bmobQuery.getObject("92ec346797", new QueryListener<Club>() {
            @Override
            public void done(Club object,BmobException e) {
                if(e==null){
                    level.setText(object.getClub_rank());
                    campus.setText(object.getClub_campus());
                    kind.setText(object.getClub_category());
                    clubname.setText(object.getClub_name());
                    createname.setText(object.getClub_president());
                    club_intro .setText(object.getClub_intro());

                    time.setText(object.getCreatedAt().substring(0,10));
                    club_name=object.getClub_name();
                    if(object.getLogo_url()!=null){
                        imglogo.setImageBitmap(BitmapFactory.decodeFile(object.getLogo_url()));
                    }
                }else{
                    Log.e("查询失败","原因：",e);                }
            }
        });
    }

}
