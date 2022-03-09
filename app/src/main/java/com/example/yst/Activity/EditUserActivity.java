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
        BmobQuery<Student> bmobQuery = new BmobQuery<>();
        savetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmobQuery.findObjects(new FindListener<Student>() {
                    @Override
                    public void done(List<Student> object, BmobException e) {
                        if (e == null) {
                            //判断信号量，若查找结束count和object长度相等，则没有查找到该账号
                            int count=0;
                            for (Student student: object) {
                                String phone=student.getStudent_phone();
                                System.out.println("This is the edit phone:"+phone);
                                if (phone.equals(EditUserActivity_number)) {
                                    student.setStudent__username(reusername);
                                    student.setStudent_password(repassword);
                                    student.setStudent_class(reclass);
                                    student.setStudent_Gender(regender);
                                    student.setStudent_realname(rerealname);
                                    student.setStudent_signature(resig);
                                    student.setStudent_college(recollege);
                                }
                                count++;
                            }
                            Toast.makeText(EditUserActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditUserActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(EditUserActivity.this,"该账号不存在",Toast.LENGTH_SHORT).show();
                        }

                    }   });
            }
        });


    }
    private void initView() {
        stuusernametxt1=findViewById(R.id.stuusernametxt);
        stupasstxt=findViewById(R.id.stupasstxt);
        stuclasstxt=findViewById(R.id.stuclasstxt);
        stusigtxt=findViewById(R.id.stusigtxt);
        rg= findViewById(R.id.rg);
        stunametxt=findViewById(R.id.stunametxt);
        stucollegetxt=findViewById(R.id.stucollegetxt);

        reusername=stuusernametxt1.getText().toString();
        rerealname=stunametxt.getText().toString();
        recollege=stucollegetxt.getText().toString();
        resig=stusigtxt.getText().toString();
        reclass=stuclasstxt.getText().toString();
        repassword=stupasstxt.getText().toString();
    }
    private void selectRadioBtn(){
        radioButton = findViewById(rg.getCheckedRadioButtonId());
        regender = radioButton.getText().toString();
    }

}
