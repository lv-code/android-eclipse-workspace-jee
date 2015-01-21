package com.beta.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener {
	private Activity activity = LoginActivity.this;
	
	private String username="123", passwd="123";
	
	private EditText mUser; // 帐号编辑框
	private EditText mPassword; // 密码编辑框
	private TextView forgetPwd;
	private CheckBox mCbShowPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyHelper.setNoTitle(activity);
		setContentView(R.layout.activity_login);
		mUser = (EditText) findViewById(R.id.phone);
		mPassword = (EditText) findViewById(R.id.passwd);
		forgetPwd = (TextView) findViewById(R.id.forgetPwd);
		 // get the show/hide password Checkbox
        mCbShowPwd = (CheckBox) findViewById(R.id.cbShowPwd);
		forgetPwd.setOnClickListener(this);
		//显示密码
		mCbShowPwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					//设定EditText的内容为可见的
					mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				} else {
					mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}
				
			}
		});
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		// 忘记密码
		case R.id.forgetPwd:
			intent.setClass(getApplicationContext(), SMSAuthCodeActivity.class);
			startActivity(intent);
			break;
		}
	}

	//提交登陆信息
	public void login_submit(View v) {
		if (username.equals(mUser.getText().toString())
				&& passwd.equals(mPassword.getText().toString())) // 判断 帐号和密码
		{
			 Intent intent = new Intent();
			 intent.setClass(LoginActivity.this, FlowActivity.class);
			 startActivity(intent);
			 this.finish();
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
	}

}
