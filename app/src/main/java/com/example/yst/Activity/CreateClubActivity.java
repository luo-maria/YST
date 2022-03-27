package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.Club;
import com.example.yst.bean.Stu_Club;
import com.example.yst.bean.Student;
import com.example.yst.util.ConstantConfig;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class CreateClubActivity extends AppCompatActivity {
    Button btncreate;
    EditText et_club_name,et_leader_name,et_leader_call,et_club_intro;
    SimpleDraweeView clublogo;
    public Spinner sp,sp1,sp2;
    public String club_id,stu_id;
    private byte[] image1;
    public String kind,level,campus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_club);
        initView();
    }
    private void initView() {
        et_club_name=findViewById(R.id.clubname);
        et_leader_name=findViewById(R.id.leadername);
        et_leader_call=findViewById(R.id.leadercall);
        et_club_intro=findViewById(R.id.clubintro);
        clublogo=findViewById(R.id.clublogo);
        btncreate=findViewById(R.id.create);
        String[] ltype = new String[]{"校级", "院级"};
        sp = (Spinner) super.findViewById(R.id.level);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ltype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                //获取选中值
                Spinner spinner1 = (Spinner) adapterView;
                level=spinner1.getItemAtPosition(position).toString();
//                System.out.println("这里的级别是:"+level);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sp1 = (Spinner) super.findViewById(R.id.campus);
        String[] ctype = new String[]{"燕山校区", "圣井校区","舜耕校区","莱芜校区"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter1);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                //获取选中值
                Spinner spinner1 = (Spinner) adapterView;
                campus=spinner1.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sp2 = (Spinner) super.findViewById(R.id.kind);
        String[] ktype = new String[]{"公益", "学术","文体","其他"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ktype);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adapter2);
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                //获取选中值
                Spinner spinner1 = (Spinner) adapterView;
                kind=spinner1.getItemAtPosition(position).toString();
//                System.out.println("这里的级别是:"+level);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        clublogo.setOnClickListener(new View.OnClickListener() {
            private Object SelectPhotoActivity;

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(ConstantConfig.SELECT_PHOTO, ConstantConfig.UPDATE_HEAD_IMAGES);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(CreateClubActivity.this , SelectPhotoActivity.class);
                startActivity(intent);

            }
        });

        //3保存按钮功能的实现
        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student userInfo = BmobUser.getCurrentUser(Student.class);
                stu_id=userInfo.getObjectId();
                Club club = new Club();
                club.setClub_name(et_club_name.getText().toString());
                club.setClub_campus(campus);
                club.setClub_category(kind);
                club.setClub_rank(level);
                club.setClub_president(et_leader_name.getText().toString());
                club.setPre_number(et_leader_call.getText().toString());
                club.setClub_intro(et_club_intro.getText().toString());
                club.setStu_id(stu_id);
                club.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if(e==null){
                            Toast.makeText(CreateClubActivity.this,"添加数据成功，返回objectId为："+objectId,Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(CreateClubActivity.this,"创建数据失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                Toast.makeText(CreateClubActivity.this,"创建成功",Toast.LENGTH_SHORT).show();
                //刷新本页面
                Intent intent=new Intent(CreateClubActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
