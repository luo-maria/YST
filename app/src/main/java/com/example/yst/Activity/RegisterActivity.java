package com.example.yst.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.yst.MainActivity;
import com.example.yst.R;
import com.example.yst.bean.Student;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private String realCode;

    private Button mBtRegisteractivityRegister;
    private Button mBtRegisteractivityLogin;
    private EditText mEtRegisteractivitySNumber;
    private EditText mEtRegisteractivityUsername;
    private EditText mEtRegisteractivityPassword1;
    private EditText mEtRegisteractivityPassword2;
    private EditText smEtRegisteractivityPhonecode;
    private Button mIvRegisteractivityGetcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bmob.initialize(RegisterActivity.this, "f84563e89fdb95cdc3c135df0c5ffc25");
        initView();
    }

    protected void initView(){
        mBtRegisteractivityLogin = findViewById(R.id.bt_registeractivity_login);
        mBtRegisteractivityRegister = findViewById(R.id.bt_registeractivity_register);
//        mLlRegisteractivityBody = findViewById(R.id.ll_registeractivity_body);
        mEtRegisteractivityUsername = findViewById(R.id.et_registeractivity_username);
        mEtRegisteractivitySNumber = findViewById(R.id.et_registeractivity_SNumber);
        mEtRegisteractivityPassword1 = findViewById(R.id.et_registeractivity_password1);
        mEtRegisteractivityPassword2 = findViewById(R.id.et_registeractivity_password2);
        smEtRegisteractivityPhonecode = findViewById(R.id.et_registeractivity_phoneCodes);
        mIvRegisteractivityGetcode = findViewById(R.id.iv_registeractivity_getCode);
//        mRlRegisteractivityBottom = findViewById(R.id.rl_registeractivity_bottom);

        /**
         * 注册页面能点击的就三个地方
         * top处返回箭头、刷新验证码图片、注册按钮
         */
        mBtRegisteractivityLogin.setOnClickListener(this);
        mIvRegisteractivityGetcode.setOnClickListener(this);
        mBtRegisteractivityRegister.setOnClickListener(this);
    }


    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_registeractivity_login: //返回登录页面
                Intent intent1 = new Intent(this,LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.iv_registeractivity_getCode:    //获取验证码
                //获取客户端输入的账号
                final String Account = mEtRegisteractivityUsername.getText().toString().trim();
                //isEmpty()方法判断是否为空
                if (TextUtils.isEmpty(Account)) {
                    Toast.makeText(RegisterActivity.this, "请填写手机号码", Toast.LENGTH_SHORT).show();
                } else if (Check.PhoneCheck(Account.trim()) != true) {
                    Toast.makeText(RegisterActivity.this, "请填写正确的手机号码", Toast.LENGTH_SHORT).show();
                } else {
                    SendSMS(Account);
                }
            case R.id.bt_registeractivity_register:    //注册按钮
                //获取用户输入的用户名、学号、密码、验证码
                final String phone = mEtRegisteractivityUsername.getText().toString().trim();
                String sNumber = mEtRegisteractivitySNumber.getText().toString().trim();
                final String password = mEtRegisteractivityPassword1.getText().toString().trim();
                String password2 = mEtRegisteractivityPassword2.getText().toString().trim();
                String phoneCode = smEtRegisteractivityPhonecode.getText().toString().toLowerCase();
                //注册验证
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this,"请填写密码",Toast.LENGTH_SHORT).show();
                }else if (password.length()<1){
                    Toast.makeText(RegisterActivity.this,"密码不得少于1位数",Toast.LENGTH_SHORT).show();
                }else if (password.length()>16){
                    Toast.makeText(RegisterActivity.this,"密码不得多于16位数",Toast.LENGTH_SHORT).show();
                }else if (!password.equals(password2)){
                    Toast.makeText(RegisterActivity.this,"密码不一致，请重新输入",Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(smEtRegisteractivityPhonecode.getText().toString().trim())){
                    Toast.makeText(RegisterActivity.this,"请填写验证码",Toast.LENGTH_SHORT).show();
                } else {
                    //短信验证码效验
//                    BmobSMS.verifySmsCode(phone, phoneCode, new UpdateListener() {
//                        @Override
//                        public void done(BmobException e) {
//                            if (e == null) {
//                                Student user = new Student();
//                                user.setUsername(phone);
//                                user.setPassword(password);
//                                user.setPhotoimageurl("/storage/emulated/0/Pictures/poy.png");
//                                user.signUp(new SaveListener<Student>() {
//                                    @Override
//                                    public void done(Student user, BmobException e) {
//                                        if(e==null)
//                                        { Toast.makeText(RegisterActivity.this,"验证通过，注册成功",Toast.LENGTH_SHORT).show();
//                                            Intent intent2 = new Intent(RegisterActivity.this, LoginActivity.class);
//                                            startActivity(intent2);
//                                            finish(); }
//                                        else { Log.e("注册失败", "原因: ",e );
//                                        } }
//                                });
//                            }else {
//                                smEtRegisteractivityPhonecode.setText("");
//                                Toast.makeText(RegisterActivity.this,"验证码错误"+e.getErrorCode(),Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
                    Student user = new Student();
                    user.setUsername(phone);
                    user.setPassword(password);
                    user.setPhotoimageurl("/storage/emulated/0/Pictures/poy.png");
                    user.signUp(new SaveListener<Student>() {
                        @Override
                        public void done(Student user, BmobException e) {
                            if(e==null)
                            { Toast.makeText(RegisterActivity.this,"验证通过，注册成功",Toast.LENGTH_SHORT).show();
                                Intent intent2 = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent2);
                                finish(); }
                            else { Log.e("注册失败", "原因: ",e );
                            } }
                    });
                }
        }
    }


    /**
     * 发送验证码
     * @param account：输入的手机号码
     *  SMS 为Bmob短信服务自定义的短信模板名字
     */
    private void SendSMS(String account){
        BmobSMS.requestSMSCode(account, "易社团", new QueryListener<Integer>() {
            @Override
            public void done(Integer smsId, BmobException e) {
                if (e == null) {
                    Toast.makeText(RegisterActivity.this,"验证码已发送",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegisterActivity.this,"发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**
         * 设置按钮60s等待
         * onTick()方法——>>计时进行时的操作
         *      ：显示倒计时，同时设置按钮不可点击
         * onFinish()方法——>>计时完成时的操作
         *      ：刷新原文本，同时设置按钮可以点击
         */
        CountDownTimer timer =new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mIvRegisteractivityGetcode.setEnabled(false);
                mIvRegisteractivityGetcode.setText("重新获取("+millisUntilFinished/1000+"s)");
            }

            @Override
            public void onFinish() {
                mIvRegisteractivityGetcode.setEnabled(true);
                mIvRegisteractivityGetcode.setText("获取验证码");
            }
        }.start();
    }
    }




