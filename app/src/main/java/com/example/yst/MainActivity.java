package com.example.yst;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yst.Activity.LoginActivity;
import com.example.yst.Activity.RegisterActivity;


public class MainActivity extends AppCompatActivity {
    private long exitTime;
    private TextView tvRegister;
    private TextView tvLogin;
    private ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initAnims();
        //取消严格模式  FileProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy( builder.build() );
        }


        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化View控件
     */
    private void initViews() {
        tvLogin = (TextView) findViewById(R.id.tv_login);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        ivLogo = (ImageView) findViewById(R.id.iv_logo);
    }

    /**
     * 初始化logo图片以及底部注册、登录的按钮动画
     */
    private void initAnims() {
        //初始化底部注册、登录的按钮动画
        //以控件自身所在的位置为原点，从下方距离原点200像素的位置移动到原点
        ObjectAnimator tranLogin = ObjectAnimator.ofFloat(tvLogin, "translationY", 200, 0);
        ObjectAnimator tranRegister = ObjectAnimator.ofFloat(tvRegister, "translationY", 200, 0);
        //将注册、登录的控件alpha属性从0变到1
        ObjectAnimator alphaLogin = ObjectAnimator.ofFloat(tvLogin, "alpha", 0, 1);
        ObjectAnimator alphaRegister = ObjectAnimator.ofFloat(tvRegister, "alpha", 0, 1);
        final AnimatorSet bottomAnim = new AnimatorSet();
        bottomAnim.setDuration(1000);
        //同时执行控件平移和alpha渐变的动画
        bottomAnim.play(tranLogin).with(tranRegister).with(alphaLogin).with(alphaRegister);

        //获取屏幕高度
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;

        //通过测量，获取ivLogo的高度
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        ivLogo.measure(w, h);
        int logoHeight = ivLogo.getMeasuredHeight();

        //初始化ivLogo的移动和缩放动画
        float transY = (screenHeight - logoHeight) * 0.28f;
        //ivLogo向上移动 transY 的距离
        ObjectAnimator tranLogo = ObjectAnimator.ofFloat(ivLogo, "translationY", 0, -transY);
        //ivLogo在X轴和Y轴上都缩放0.75倍
        ObjectAnimator scaleXLogo = ObjectAnimator.ofFloat(ivLogo, "scaleX", 1f, 0.75f);
        ObjectAnimator scaleYLogo = ObjectAnimator.ofFloat(ivLogo, "scaleY", 1f, 0.75f);
        AnimatorSet logoAnim = new AnimatorSet();
        logoAnim.setDuration(1000);
        logoAnim.play(tranLogo).with(scaleXLogo).with(scaleYLogo);
        logoAnim.start();
        logoAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //logo图片的动画结束时,开始播放底部注册、登录按钮的动画
                bottomAnim.start();
            }
        });
    }


    /**
     * 重写返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //双击退出
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.exit_hint), Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                MainActivity.this.finish();
            }
            //实现只在冷启动时显示启动页，双击返回键与点击HOME键退出效果一致，类似微信效果
           /* Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);*/
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

}