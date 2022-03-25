package com.example.yst.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.Student;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class EditUserActivity extends AppCompatActivity {
    private EditText stuusernametxt1, stuunitxt, stunametxt, stucollegetxt, stuclasstxt, stusigtxt;
    private TextView savetxt,cancel;
    private RadioGroup rg;
    RadioButton radioButton;
    String reusername, reuni, rerealname, regender, recollege, reclass, resig,renumber,number1,password1,student_calss1,realname1,gender1,signature1,username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        initView();
        savetxt=findViewById(R.id.savetxt);
        cancel=findViewById(R.id.cancel);
        setMyUser();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectRadioBtn();
            }
        });
        savetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student userInfo = BmobUser.getCurrentUser(Student.class);
                reusername=stuusernametxt1.getText().toString();
                rerealname=stunametxt.getText().toString();
                recollege=stucollegetxt.getText().toString();
                resig=stusigtxt.getText().toString();
                reclass=stuclasstxt.getText().toString();
                reuni=stuunitxt.getText().toString();
                Student new_stu =new Student();
                new_stu.setNickname(reusername);
                new_stu.setCollege(recollege);
                new_stu.setSignature(resig);
                new_stu.setSclass(reclass);
                new_stu.setUniversity(reuni);
                new_stu.setGender(regender);
                new_stu.setRealname(rerealname);
                new_stu.update(userInfo.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(EditUserActivity.this,"修改成功"+"my new nickname is:"+reusername,Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(EditUserActivity.this, HomeActivity.class);
                            startActivity(intent1);
                        } else {
                            Toast.makeText(EditUserActivity.this,"修改失败",Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(EditUserActivity.this, HomeActivity.class);
                            startActivity(intent1);
                        }
                    }
                });

            }
        });



    }

    private void setMyUser() {
        Student userInfo = BmobUser.getCurrentUser(Student.class);
        if (userInfo.getNickname()!= null) {
            stuusernametxt1.setText(userInfo.getNickname());
        }
        if (userInfo.getCollege() != null) {
            stucollegetxt.setText(userInfo.getCollege());
        }
        if (userInfo.getSclass() != null) {
            stuclasstxt.setText(userInfo.getSclass());
        }
        if (userInfo.getUniversity() != null) {
            stuunitxt.setText(userInfo.getUniversity());
        }
        if (userInfo.getSignature()!= null) {
            stusigtxt.setText(userInfo.getSignature());
        }
        if (userInfo.getRealname()!= null) {
            stunametxt.setText(userInfo.getRealname());
        }


    }

    private void initView() {
        stuusernametxt1=findViewById(R.id.stuusernametxt);
        stuunitxt = findViewById(R.id.stuunitxt);
        stuclasstxt=findViewById(R.id.stuclasstxt);
        stusigtxt=findViewById(R.id.stusigtxt);
        rg= findViewById(R.id.rg);
        stunametxt=findViewById(R.id.stunametxt);
        stucollegetxt=findViewById(R.id.stucollegetxt);


    }
    private void selectRadioBtn(){
        radioButton = findViewById(rg.getCheckedRadioButtonId());
        regender = radioButton.getText().toString();
    }

}
