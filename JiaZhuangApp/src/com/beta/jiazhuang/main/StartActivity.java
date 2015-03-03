package com.beta.jiazhuang.main;

import org.jivesoftware.smack.XMPPException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.beta.jiazhuang.main.LoginActivity.loginXMPPServerRunable;
import com.beta.jiazhuang.mybase.MyBaseApplication;
import com.beta.jiazhuang.util.CustomConst;
import com.beta.jiazhuang.view.CommonDialog;
import com.beta.jiazhuang.xmpp.MXmppConnManager;
import com.beta.main.R;

public class StartActivity extends Activity {
	Boolean showWhatsnew = false;
	private SharedPreferences sharedPreferences;
	private CommonDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去掉标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_start);
		// 加载配置，注释掉可以关闭“自动登录”
		sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent();
				if (true == showWhatsnew) {
					intent.setClass(StartActivity.this, WhatsnewActivity.class);
				} else {
					try {
						// 如果登录过，就自动登录
						if (sharedPreferences != null && autoLogin()) {
							intent.setClass(StartActivity.this,
									FlowActivity.class);
						} else {
							intent.setClass(StartActivity.this,
									LoginActivity.class);
						}
					} catch (XMPPException e) {
						e.printStackTrace();
					}
				}
				startActivity(intent);
				StartActivity.this.finish();
			}
		}, 1000);

	}

	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			// 登录XMPPServer成功
			if (msg.what == CustomConst.XMPP_HANDLER_SUCCESS) {
				startActivity(new Intent(StartActivity.this, FlowActivity.class));
				finish();
			} else if (msg.what == CustomConst.XMPP_HANDLER_ERROR) {
				if (msg.arg1 == CustomConst.XMPP_ERROR_LOGINFAIL) {
					Toast.makeText(StartActivity.this, "账号或者密码错误,请检查",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(StartActivity.this, "网络存在异常,请检查",
							Toast.LENGTH_SHORT).show();

					// handler.postDelayed(new loginXMPPServerRunable(), 10000);
				}
			}
		};

	};

	/**
	 * 自动登录
	 * 
	 * @return
	 * @throws XMPPException
	 */
	private boolean autoLogin() throws XMPPException {

		if (sharedPreferences.getString("n", "").equals("")
				&& sharedPreferences.getString("p", "").equals("")) {
			return false;
		} else {

			try {
				if (MyBaseApplication.xmppConnection == null
						|| !MyBaseApplication.xmppConnection.isConnected()) {

					MXmppConnManager.getInstance().new InitXmppConnectionTask(
							handler).execute().get();
//					 new Thread(new Runnable() {
//					 @Override
//					 public void run() {
//					
//					 while (!success) {
//					 try {
//					 Thread.sleep(100);
//					 } catch (InterruptedException e) {
//					 e.printStackTrace();
//					 }
//					 }
//					
//					 }
//					 }).start();

				}
				MXmppConnManager.getInstance().mXmppLogin(
						sharedPreferences.getString("n", ""),
						sharedPreferences.getString("p", ""),
						getApplicationContext(), handler);
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(this, "账号或者密码错误", Toast.LENGTH_SHORT).show();
			}
			return true;
		}
	}
}
