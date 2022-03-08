package com.example.yst.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yst.util.ActivityCollector;
import com.example.yst.util.LoginOutBroadcastReceiver;

import cn.bmob.v3.Bmob;

public abstract class BaseActivity extends AppCompatActivity {
	public Context mContext;
	protected LoginOutBroadcastReceiver locallReceiver;
	private String Bmob_AppId = "f84563e89fdb95cdc3c135df0c5ffc25";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bmob.initialize(this, Bmob_AppId);
		mContext = this;
		setContentView(initLayout());
		initView();
		initData();
		// 创建活动时，将其加入管理器中
		ActivityCollector.addActivity(this);
	}

	Toast mToast;

	public void ShowToast(String text) {
		if (!TextUtils.isEmpty(text)) {
			if (mToast == null) {
				mToast = Toast.makeText(getApplicationContext(), text,
						Toast.LENGTH_SHORT);
			} else {
				mToast.setText(text);
			}
			mToast.show();
		}
	}

    protected abstract int initLayout();

	protected abstract void initView();

	protected abstract void initData();

	@Override
	protected void onResume() {
		super.onResume();
		// 注册广播接收器
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.gesoft.admin.loginout");
		locallReceiver = new LoginOutBroadcastReceiver();
		registerReceiver(locallReceiver, intentFilter);
	}
	@Override
	protected void onPause() {
		super.onPause();
		// 取消注册广播接收器
		unregisterReceiver(locallReceiver);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 销毁活动时，将其从管理器中移除
		ActivityCollector.removeActivity(this);
	}
}
