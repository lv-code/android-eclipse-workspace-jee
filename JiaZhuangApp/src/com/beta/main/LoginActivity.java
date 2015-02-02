package com.beta.main;

import org.jivesoftware.smack.XMPPException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.mybase.MyBaseActivity;
import com.beta.mybase.MyBaseApplication;
import com.beta.util.CustomConst;
import com.beta.util.MyHelper;
import com.beta.xmpp.MXmppConnManager;
import com.igexin.sdk.PushManager;

public class LoginActivity extends MyBaseActivity implements OnClickListener {
	private Activity activity = LoginActivity.this;
	private SharedPreferences sharedPreferences;
	private String username="123", passwd="123";
	
	private EditText mUser; // 帐号编辑框
	private EditText mPassword; // 密码编辑框
	private TextView forgetPwd;
	private CheckBox mCbShowPwd;
	private ImageView ivLogin;
	
	//登录成功
	private boolean success;

	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			//登录XMPPServer成功
			if (msg.what == CustomConst.XMPP_HANDLER_SUCCESS) {
				startActivity(FlowActivity.class);
				finish();
			} else if (msg.what == CustomConst.XMPP_HANDLER_ERROR) {
				if (msg.arg1 == CustomConst.XMPP_ERROR_LOGINFAIL) {
					Toast.makeText(LoginActivity.this, "账号或者密码错误",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(LoginActivity.this, "网络存在异常,请检查",
							Toast.LENGTH_SHORT).show();

					handler.postDelayed(new loginXMPPServerRunable(), 60000);
				}
			}
		};

	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyHelper.setNoTitle(activity);
		setContentView(R.layout.activity_login);
		// 加载配置
		sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
		try {
			// 如果登录过，就自动登录
			if (autoLogin()) {
				startActivity(FlowActivity.class);
				finish();
				return;
			}
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		
		initViews();
		forgetPwd.setOnClickListener(this);
		ivLogin.setOnClickListener(this);
		// 初始化“个推”SDK
		PushManager.getInstance().initialize(this.getApplicationContext());
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

	private void initViews() {
		mUser = (EditText) findViewById(R.id.phone);
		mPassword = (EditText) findViewById(R.id.passwd);
		forgetPwd = (TextView) findViewById(R.id.forgetPwd);
		 // get the show/hide password Checkbox
        mCbShowPwd = (CheckBox) findViewById(R.id.cbShowPwd);
        ivLogin = (ImageView) findViewById(R.id.ivLogin);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 忘记密码
		case R.id.forgetPwd:
			startActivity(SMSAuthCodeActivity.class);
			break;
		case R.id.ivLogin:
			login_submit(v);
			break;
		}
	}

	//提交登陆信息
	public void login_submit(View v) {
		String inputUserName = mUser.getText().toString();
		String inputPwd = mPassword.getText().toString();
		if (username.equals(inputUserName)
				&& passwd.equals(inputPwd)) // 判断 帐号和密码
		{
			handler.postDelayed(new loginXMPPServerRunable(), 1000);
		} else if ("".equals(inputUserName)
				|| "".equals(inputPwd)) // 判断 帐号和密码
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

	// 链接XMPPServer
	public class loginXMPPServerRunable implements Runnable {
		public void run() {
			String name = mUser.getText().toString().trim();
			String pwd = mPassword.getText().toString().trim();
			success = MXmppConnManager.getInstance().mXmppLogin(name, pwd,
					getApplicationContext(),handler);
			Log.d("日志", "success ----> "+success+"name---->"+name+"pwd----->"+pwd);
			if (success)
				handler.sendEmptyMessage(CustomConst.XMPP_HANDLER_SUCCESS);
			else{
				Message msg = new Message();
				msg.what = CustomConst.XMPP_HANDLER_ERROR;
				msg.arg1 = CustomConst.XMPP_ERROR_LOGINFAIL;
				handler.sendMessage(msg);
			}
		}
	}
	
	private boolean autoLogin() throws XMPPException {
		if (sharedPreferences.getString("n", "").equals("")
				&& sharedPreferences.getString("p", "").equals("")) {
			Log.d("日志", "func autoLogin return false");
			return false;
		} else {
			try {
				if (MyBaseApplication.xmppConnection == null
						|| !MyBaseApplication.xmppConnection.isConnected()) {
					
					MXmppConnManager.getInstance().new InitXmppConnectionTask(
							handler).execute().get();
					
					new Thread(new Runnable() {
						@Override
						public void run() {
							
							while (!success) {
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							
						}
					}).start();

				}
				MXmppConnManager.getInstance().mXmppLogin(
						sharedPreferences.getString("n", ""),sharedPreferences.getString("p", ""),
						getApplicationContext(),handler);
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(this, "账号或者密码错误", Toast.LENGTH_SHORT).show();
			}
			return true;
		}

	}
}
