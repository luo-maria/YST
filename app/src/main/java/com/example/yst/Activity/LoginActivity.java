package com.example.yst.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yst.R;
import com.example.yst.bean.Student;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class LoginActivity extends Activity implements View.OnClickListener {


    private TextView mTvLoginactivityRegister;
    private RelativeLayout mRlLoginactivityTop;
    private EditText mEtLoginactivitynumber;
    private EditText mEtLoginactivityPassword;
    private LinearLayout mLlLoginactivityTwo;
    private Button mBtLoginactivityLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bmob.initialize(LoginActivity.this,"f84563e89fdb95cdc3c135df0c5ffc25");
        initView();

    }


    public void initView() {
        // 初始化控件
        mBtLoginactivityLogin = findViewById(R.id.bt_loginactivity_login);
        mTvLoginactivityRegister = findViewById(R.id.tv_loginactivity_register);
        mRlLoginactivityTop = findViewById(R.id.rl_loginactivity_top);
        mEtLoginactivitynumber = findViewById(R.id.et_loginactivity_username);
        mEtLoginactivityPassword = findViewById(R.id.et_loginactivity_password);
        mLlLoginactivityTwo = findViewById(R.id.ll_loginactivity_two);

        // 设置点击事件监听器
        mBtLoginactivityLogin.setOnClickListener(this);
        mTvLoginactivityRegister.setOnClickListener(this);
    }



    public void onClick(View view) {
        switch (view.getId()) {
            // 跳转到注册界面
            case R.id.tv_loginactivity_register:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;

            case R.id.bt_loginactivity_login:
                String number = mEtLoginactivitynumber.getText().toString().trim();
                String password = mEtLoginactivityPassword.getText().toString().trim();
                if (number.equals("") || password.equals("")) {
                    Toast.makeText(this, "手机号或密码不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                //账号(Account)、密码(Password)
                final String Account = mEtLoginactivitynumber.getText().toString().trim();
                System.out.println("这是account：：："+Account);
                final String Password = mEtLoginactivityPassword.getText().toString().trim();
                if (TextUtils.isEmpty(Account)) {
                    Toast.makeText(LoginActivity.this, "请填写手机号码", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(LoginActivity.this, "请填写密码", Toast.LENGTH_SHORT).show();
                } else {
                    BmobQuery<Student> bmobQuery = new BmobQuery<>();
                    bmobQuery.findObjects(new FindListener<Student>() {
                        @Override
                        public void done(List<Student> object, BmobException e) {
                            if (e == null) {
                                //判断信号量，若查找结束count和object长度相等，则没有查找到该账号
                                int count=0;
                                for (Student student: object) {
                                    String username=student.getStudent_phone();
                                    System.out.println("This is the phone:"+username);
                                    if (student.getStudent_phone().equals(Account)) {
                                        //已查找到该账号，检测密码是否正确
                                        if (student.getStudent_password().equals(Password)) {
                                            //密码正确，跳转（Home是登陆后跳转的页面）
                                            Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
//                                            String user_id= BmobUser.getCurrentUser().getObjectId();
//                                            System.out.println("这是用户ID："+user_id);
                                            intent.putExtra("input_number",number);
                                            startActivity(intent);
                                            break;
                                        }else {
                                            Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                    }
                                    count++;
                                }
                                if (count >= object.size()){
                                    Toast.makeText(LoginActivity.this,"该账号不存在",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(LoginActivity.this,"该账号不存在",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        }
    }
