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
         * ???????????????????????????????????????
         * top??????????????????????????????????????????????????????
         */
        mBtRegisteractivityLogin.setOnClickListener(this);
        mIvRegisteractivityGetcode.setOnClickListener(this);
        mBtRegisteractivityRegister.setOnClickListener(this);
    }


    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_registeractivity_login: //??????????????????
                Intent intent1 = new Intent(this,LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.iv_registeractivity_getCode:    //???????????????
                //??????????????????????????????
                final String Account = mEtRegisteractivityUsername.getText().toString().trim();
                //isEmpty()????????????????????????
                if (TextUtils.isEmpty(Account)) {
                    Toast.makeText(RegisterActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
                } else if (Check.PhoneCheck(Account.trim()) != true) {
                    Toast.makeText(RegisterActivity.this, "??????????????????????????????", Toast.LENGTH_SHORT).show();
                } else {
                    SendSMS(Account);
                }
            case R.id.bt_registeractivity_register:    //????????????
                //????????????????????????????????????????????????????????????
                final String phone = mEtRegisteractivityUsername.getText().toString().trim();
                String sNumber = mEtRegisteractivitySNumber.getText().toString().trim();
                final String password = mEtRegisteractivityPassword1.getText().toString().trim();
                String password2 = mEtRegisteractivityPassword2.getText().toString().trim();
                String phoneCode = smEtRegisteractivityPhonecode.getText().toString().toLowerCase();
                //????????????
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this,"???????????????",Toast.LENGTH_SHORT).show();
                }else if (password.length()<1){
                    Toast.makeText(RegisterActivity.this,"??????????????????1??????",Toast.LENGTH_SHORT).show();
                }else if (password.length()>16){
                    Toast.makeText(RegisterActivity.this,"??????????????????16??????",Toast.LENGTH_SHORT).show();
                }else if (!password.equals(password2)){
                    Toast.makeText(RegisterActivity.this,"?????????????????????????????????",Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(smEtRegisteractivityPhonecode.getText().toString().trim())){
                    Toast.makeText(RegisterActivity.this,"??????????????????",Toast.LENGTH_SHORT).show();
                } else {
                    //?????????????????????
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
//                                        { Toast.makeText(RegisterActivity.this,"???????????????????????????",Toast.LENGTH_SHORT).show();
//                                            Intent intent2 = new Intent(RegisterActivity.this, LoginActivity.class);
//                                            startActivity(intent2);
//                                            finish(); }
//                                        else { Log.e("????????????", "??????: ",e );
//                                        } }
//                                });
//                            }else {
//                                smEtRegisteractivityPhonecode.setText("");
//                                Toast.makeText(RegisterActivity.this,"???????????????"+e.getErrorCode(),Toast.LENGTH_SHORT).show();
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
                            { Toast.makeText(RegisterActivity.this,"???????????????????????????",Toast.LENGTH_SHORT).show();
                                Intent intent2 = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent2);
                                finish(); }
                            else { Log.e("????????????", "??????: ",e );
                            } }
                    });
                }
        }
    }


    /**
     * ???????????????
     * @param account????????????????????????
     *  SMS ???Bmob??????????????????????????????????????????
     */
    private void SendSMS(String account){
        BmobSMS.requestSMSCode(account, "?????????", new QueryListener<Integer>() {
            @Override
            public void done(Integer smsId, BmobException e) {
                if (e == null) {
                    Toast.makeText(RegisterActivity.this,"??????????????????",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegisterActivity.this,"????????????????????????" + e.getErrorCode() + "-" + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**
         * ????????????60s??????
         * onTick()????????????>>????????????????????????
         *      ???????????????????????????????????????????????????
         * onFinish()????????????>>????????????????????????
         *      ???????????????????????????????????????????????????
         */
        CountDownTimer timer =new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mIvRegisteractivityGetcode.setEnabled(false);
                mIvRegisteractivityGetcode.setText("????????????("+millisUntilFinished/1000+"s)");
            }

            @Override
            public void onFinish() {
                mIvRegisteractivityGetcode.setEnabled(true);
                mIvRegisteractivityGetcode.setText("???????????????");
            }
        }.start();
    }
    }




