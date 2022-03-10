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

public class EditUserActivity extends AppCompatActivity {
    private EditText stuusernametxt1, stupasstxt, stunametxt, stucollegetxt, stuclasstxt, stusigtxt;
    private TextView savetxt,cancel;
    private RadioGroup rg;
    RadioButton radioButton;
    String reusername, repassword, rerealname, regender, recollege, reclass, resig,username1,number1,password1,student_calss1,realname1,gender1,signature1,username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        initView();
        Intent intent=getIntent();
        String EditUserActivity_number=intent.getStringExtra("input_number");
        System.out.println("这里是homepagefragment_number的number：" + EditUserActivity_number);
        savetxt=findViewById(R.id.savetxt);
        cancel=findViewById(R.id.cancel);
        setMyUser();

//        savetxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               Student student = BmobUser.getCurrentUser(Student.class);
//
//                reusername=stuusernametxt1.getText().toString();
//
//                rerealname=stunametxt.getText().toString();
//                recollege=stucollegetxt.getText().toString();
//                resig=stusigtxt.getText().toString();
//                reclass=stuclasstxt.getText().toString();
//                repassword=stupasstxt.getText().toString();
//                student.setStudent__username(reusername);
//
//                student.setStudent_password(repassword);
//                student.setStudent_class(reclass);
//                student.setStudent_Gender(regender);
//                student.setStudent_realname(rerealname);
//                student.setStudent_signature(resig);
//                student.setStudent_college(recollege);
//                Toast.makeText(EditUserActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(EditUserActivity.this, HomeActivity.class);
//                startActivity(intent);
//
//            }
//        });


    }

    private void setMyUser() {
        Student userInfo = BmobUser.getCurrentUser(Student.class);
        Student student=new Student();
        String username=student.getStudent_phone();
        System.out.println("this is username::::::"+userInfo );
        if (userInfo.getStudent_username() != null) {
            stuusernametxt1.setText(userInfo.getStudent_username());
        }else {
            System.out.println("this is null no user");
        }
        if (userInfo.getStudent_realname() != null) {
            stunametxt.setText(userInfo.getStudent_realname());
        }
        if (userInfo.getStudent_college() != null) {
            stucollegetxt.setText(userInfo.getStudent_college());
        }
        if (userInfo.getStudent_class() != null) {
            stuclasstxt.setText(userInfo.getStudent_class());
        }
        if (userInfo.getStudent_password() != null) {
            stupasstxt.setText(userInfo.getStudent_password());
        }

    }

    private void initView() {
        stuusernametxt1=findViewById(R.id.stuusernametxt);
        stupasstxt=findViewById(R.id.stupasstxt);
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
