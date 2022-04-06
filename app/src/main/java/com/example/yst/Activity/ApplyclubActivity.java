package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.ApplyToClublnfo;
import com.example.yst.bean.Club;
import com.example.yst.bean.Stu_Club;
import com.example.yst.bean.Student;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class ApplyclubActivity extends AppCompatActivity {
    private EditText applyname, applyclass, applynumber,apply_reason_club;
    private RadioGroup rg1 ;
    private RadioButton radioButton1;
    private String stu_id,club_id2,applygender,stu_realname,club_name;
    private Button applybutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_to_club);
        initialize();
        setInfo();
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectRadioBtn();
            }
        });
        applybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                applyTheClub();
            }
        });

    }

    private void applyTheClub() {
        ApplyToClublnfo applyToClublnfo=new ApplyToClublnfo();
        Student userInfo = BmobUser.getCurrentUser(Student.class);
        stu_id=userInfo.getObjectId();
        if(userInfo.getRealname()!=null){
            applyToClublnfo.setApply_club_name(userInfo.getRealname());
        }else {
            applyToClublnfo.setApply_club_name(applyname.getText().toString());
            userInfo.setRealname(applyname.getText().toString());
        }
        if(userInfo.getPhotoimageurl()!=null){
            applyToClublnfo.setStu_photo(userInfo.getPhotoimageurl());
        }
        applyToClublnfo.setStu_photo(userInfo.getPhotoimageurl());
        applyToClublnfo.setApply_club_name(applyname.getText().toString());
        applyToClublnfo.setApply_club_class(applyclass.getText().toString());
        applyToClublnfo.setApply_club_phone(applynumber.getText().toString());
        applyToClublnfo.setApply_club_reason(apply_reason_club.getText().toString());
        applyToClublnfo.setApply_club_sex(applygender);
        applyToClublnfo.setStudent_id(stu_id);
        applyToClublnfo.setClub_id(club_id2);
        applyToClublnfo.setClub_name(club_name);
        applyToClublnfo.setApplication_status("未审核");
        applyToClublnfo.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    Toast.makeText(ApplyclubActivity.this,"申请成功！请耐心等待审核",Toast.LENGTH_SHORT).show();
                    //刷新本页面
                    Intent intent=new Intent(ApplyclubActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(ApplyclubActivity.this,"创建数据失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void setInfo() {
        Student userInfo = BmobUser.getCurrentUser(Student.class);
        stu_realname=userInfo.getRealname();
        applyname.setText(stu_realname);
    }

    private void initialize() {
        applyname=findViewById(R.id.apply_club_name_et);
        applyclass=findViewById(R.id.apply_club_class_et);
        applynumber=findViewById(R.id.apply_phoneNumber);
        rg1= findViewById(R.id.rg1);
        apply_reason_club=findViewById(R.id.apply_reason_club);
        applybutton=findViewById(R.id.applybutton);
        Intent intent1=getIntent();
        club_id2=intent1.getStringExtra("clubid");
        club_name=intent1.getStringExtra("clubname");
    }
    private void selectRadioBtn(){
        radioButton1 = findViewById(rg1.getCheckedRadioButtonId());
        applygender = radioButton1.getText().toString();
    }
}
