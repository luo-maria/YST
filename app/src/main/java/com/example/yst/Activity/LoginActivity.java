package com.example.yst.Activity;

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
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class LoginActivity  extends BaseActivity  implements View.OnClickListener {
    /**
     * 声明自己写的 DBOpenHelper 对象
     * DBOpenHelper(extends SQLiteOpenHelper) 主要用来
     * 创建数据表
     * 然后再进行数据表的增、删、改、查操作
     //     */

    private TextView mTvLoginactivityRegister;
    private RelativeLayout mRlLoginactivityTop;
    private EditText mEtLoginactivitynumber;
    private EditText mEtLoginactivityPassword;
    private LinearLayout mLlLoginactivityTwo;
    private Button mBtLoginactivityLogin;

    /**
     * 创建 Activity 时先来重写 onCreate() 方法
     * 保存实例状态
     * super.onCreate(savedInstanceState);
     * 设置视图内容的配置文件
     * setContentView(R.layout.activity_login);
     * 上面这行代码真正实现了把视图层 View 也就是 layout 的内容放到 Activity 中进行显示
     * 初始化视图中的控件对象 initView()
     * 实例化 DBOpenHelper，待会进行登录验证的时候要用来进行数据查询
     * mDBOpenHelper = new DBOpenHelper(this);
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(LoginActivity.this,"f84563e89fdb95cdc3c135df0c5ffc25");
        initView();

    }

    @Override
    protected int initLayout() {
        return 0;
    }

    /**
     * onCreae()中布局已经摆放好了，接下来就该把layout里的东西声明、实例化对象然后有行为的赋予其行为
     * 这样就可以把视图层View也就是layout 与 控制层 Java 结合起来了
     */
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

    @Override
    protected void initData() {

    }

    public void onClick(View view) {
        switch (view.getId()) {
            // 跳转到注册界面
            case R.id.tv_loginactivity_register:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;


            /**
             * 登录验证：
             *
             * 1、从EditText的对象上获取文本编辑框输入的数据，去掉左右两边的空格————getText().toString().trim();
             * 2、进行匹配验证,先判断一下用户名密码是否为空————if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password))
             * 3、再进而for循环判断是否与数据库中的数据相匹配 （逻辑并不适合实际开发）
             * 4、一旦匹配，立即将match = true；break；否则 一直匹配到结束 match = false；
             * 5、登录成功之后，进行页面跳转————Intent intent = new Intent(this, MainActivity.class);
             * 6、 finish();//销毁此Activity
             */
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
