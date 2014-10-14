package com.example.jiazhuangapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private EditText mUser; // 帐号编辑框
	private EditText mPassword; // 密码编辑框

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ---------------------------------------------------
		// 自定义Activity标题栏
		CustomTitleBar.getTitleBar(this, "登陆");
		// CustomTitleBar.hideBackBtn();
		// ---------------------------------------------------
		setContentView(R.layout.activity_login);
		/* 返回按钮 */
		Button titleBackBtn = (Button) this
				.findViewById(R.id.head_TitleBackBtn);
		titleBackBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				LoginActivity.this.finish();
			}
		});

		mUser = (EditText) findViewById(R.id.phone);
		mPassword = (EditText) findViewById(R.id.passwd);

	}

	//提交登陆信息
	public void login_submit(View v) {
		if ("123456".equals(mUser.getText().toString())
				&& "abc".equals(mPassword.getText().toString())) // 判断 帐号和密码
		{
			// Intent intent = new Intent();
			// intent.setClass(LoginActivity.this, LoadingActivity.class);
			// startActivity(intent);
			Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT)
					.show();
		} else if ("".equals(mUser.getText().toString())
				|| "".equals(mPassword.getText().toString())) // 判断 帐号和密码
		{
			new AlertDialog.Builder(LoginActivity.this)
					.setIcon(
							getResources().getDrawable(
									android.R.drawable.stat_notify_error))
					.setTitle("登录错误").setMessage("手机号或者密码不能为空，\n请输入后再登录！")
					.create().show();
		} else {

			new AlertDialog.Builder(LoginActivity.this)
					.setIcon(
							getResources().getDrawable(
									android.R.drawable.stat_notify_error))
					.setTitle("登录失败").setMessage("手机号或者密码不正确，\n请检查后重新输入！")
					.create().show();
		}

		// 登录按钮
		/*
		 * Intent intent = new Intent();
		 * intent.setClass(Login.this,Whatsnew.class); startActivity(intent);
		 * Toast.makeText(getApplicationContext(), "登录成功",
		 * Toast.LENGTH_SHORT).show(); this.finish();
		 */
	}

	public void goHome(View v) {
		LoginActivity.this.finish();
	}
	
	public void goLogin(View v){
		
	}
	
	public void login_back(View v) { // 标题栏 返回按钮
		this.finish();
	}
}
