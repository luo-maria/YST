package com.example.yst.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yst.R;


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

        initView();

    }

    /**
     * onCreae()中布局已经摆放好了，接下来就该把layout里的东西声明、实例化对象然后有行为的赋予其行为
     * 这样就可以把视图层View也就是layout 与 控制层 Java 结合起来了
     */
    private void initView() {
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
                    Toast.makeText(this, "学号或密码不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
//                BmobQuery<Student> bmobQuery = new BmobQuery<Student>();
//                bmobQuery.addWhereEqualTo("number", number);
//                bmobQuery.addWhereEqualTo("password", password);
//                final Student student = new Student();
//                student.setStudent_number(number);
//                student.setStudent_password(password);
//                student.login(new SaveListener<Student>() {
//                    @Override
//                    public void done(Student bmobUser, BmobException e) {
//                        if (e == null) {
//                            Student user = BmobUser.getCurrentUser(Student.class);
//                            Snackbar.make(view, "登录成功：" + user.getUsername(), Snackbar.LENGTH_LONG).show();
//                        } else {
//                            Snackbar.make(view, "登录失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
//                        }
//                    }
//                });
//                /**
//                 * 账号密码登录
//                 */
//                private void login(final View view) {
//                final User user = new User();
//                //此处替换为你的用户名
//                user.setUsername("username");
//                //此处替换为你的密码
//                user.setPassword("password");
//                user.login(new SaveListener<User>() {
//                    @Override
//                    public void done(User bmobUser, BmobException e) {
//                        if (e == null) {
//                            User user = BmobUser.getCurrentUser(User.class);
//                            Snackbar.make(view, "登录成功：" + user.getUsername(), Snackbar.LENGTH_LONG).show();
//                        } else {
//                            Snackbar.make(view, "登录失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
//                        }
//                    }
//                });
            }
        }
    }
