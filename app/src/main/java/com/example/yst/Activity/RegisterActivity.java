package com.example.yst.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.yst.R;
import com.example.yst.bean.Student;
import com.example.yst.util.Code;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private String realCode;

    private Button mBtRegisteractivityRegister;
    private Button mBtRegisteractivityLogin;
    private RelativeLayout mRlRegisteractivityTop;
    private ImageView mIvRegisteractivityBack;
    private LinearLayout mLlRegisteractivityBody;
    private EditText mEtRegisteractivitySNumber;
    private EditText mEtRegisteractivityUsername;
    private EditText mEtRegisteractivityPassword1;
    private EditText mEtRegisteractivityPassword2;
    private EditText mEtRegisteractivityPhonecodes;
    private ImageView mIvRegisteractivityShowcode;
    private RelativeLayout mRlRegisteractivityBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

//        mDBOpenHelper = new DBOpenHelper(this);


        //将验证码用图片的形式显示出来
        mIvRegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
    }

    private void initView(){
        mBtRegisteractivityLogin = findViewById(R.id.bt_registeractivity_login);
        mBtRegisteractivityRegister = findViewById(R.id.bt_registeractivity_register);
        mLlRegisteractivityBody = findViewById(R.id.ll_registeractivity_body);
        mEtRegisteractivityUsername = findViewById(R.id.et_registeractivity_username);
        mEtRegisteractivitySNumber = findViewById(R.id.et_registeractivity_SNumber);
        mEtRegisteractivityPassword1 = findViewById(R.id.et_registeractivity_password1);
        mEtRegisteractivityPassword2 = findViewById(R.id.et_registeractivity_password2);
        mEtRegisteractivityPhonecodes = findViewById(R.id.et_registeractivity_phoneCodes);
        mIvRegisteractivityShowcode = findViewById(R.id.iv_registeractivity_showCode);
        mRlRegisteractivityBottom = findViewById(R.id.rl_registeractivity_bottom);

        /**
         * 注册页面能点击的就三个地方
         * top处返回箭头、刷新验证码图片、注册按钮
         */
        mBtRegisteractivityLogin.setOnClickListener(this);
        mIvRegisteractivityShowcode.setOnClickListener(this);
        mBtRegisteractivityRegister.setOnClickListener(this);
    }



    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_registeractivity_login: //返回登录页面
                Intent intent1 = new Intent(this,LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.iv_registeractivity_showCode:    //改变随机验证码的生成
                mIvRegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
                break;
            case R.id.bt_registeractivity_register:    //注册按钮
                //获取用户输入的用户名、学号、密码、验证码
                String username = mEtRegisteractivityUsername.getText().toString().trim();
                String sNumber = mEtRegisteractivitySNumber.getText().toString().trim();
                String password = mEtRegisteractivityPassword1.getText().toString().trim();
                String password2 = mEtRegisteractivityPassword2.getText().toString().trim();
                String phoneCode = mEtRegisteractivityPhonecodes.getText().toString().toLowerCase();
                //注册验证
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(phoneCode) ) {
                    if (phoneCode.equals(realCode)) {
                        //将用户名和密码加入到数据库中
//                        Register();
                        Student s2 = new Student();
                        s2.setStudent__username(username);
                        s2.setStudent_password(password);
                        s2.setStudent_number(sNumber);
                        s2.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String objectId, BmobException e) {
                                        if (e == null) {
                                            ShowToast("添加数据成功，返回objectId为：" + objectId);
                                            System.out.println("创建数据成功");
                                        } else {
                                            System.out.println("创建数据失败");
                                            ShowToast("创建数据失败：" + e.getMessage());
                                            Toast.makeText(RegisterActivity.this, "shibai!!!", Toast.LENGTH_LONG).show();

                                        }
                                    }
                        });
                        //直接跳转到首页——————
                        Intent intent2 = new Intent(this, LoginActivity.class);
                        startActivity(intent2);
                        finish();
                        Toast.makeText(this,  "验证通过，注册成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "验证码错误,注册失败", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "未完善信息，注册失败", Toast.LENGTH_SHORT).show();
                }
                break;


        }


    }

//    private void Register() {
//        Student s2 = new Student();
//        s2.setStudent__username(username);
//        s2.setStudent_password(password);
//        s2.setStudent_number(sNumber);
//        s2.save(new SaveListener<String>() {
//            @Override
//            public void done(String objectId, BmobException e) {
//                if(e==null){
//                    ShowToast("添加数据成功，返回objectId为："+objectId);
//                    System.out.println("创建数据成功");
//                }else{
//                    System.out.println("创建数据失败");
//                    ShowToast("创建数据失败：" + e.getMessage());
//                    Toast.makeText(RegisterActivity.this, "shibai!!!", Toast.LENGTH_LONG).show();
//
//                }
//            }
//        });
    }




