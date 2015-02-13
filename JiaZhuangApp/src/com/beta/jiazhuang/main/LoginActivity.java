package com.beta.jiazhuang.main;

import org.jivesoftware.smack.XMPPException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.jiazhuang.mybase.MyBaseActivity;
import com.beta.jiazhuang.mybase.MyBaseApplication;
import com.beta.jiazhuang.util.CustomConst;
import com.beta.jiazhuang.util.MyHelper;
import com.beta.jiazhuang.view.CommonDialog;
import com.beta.jiazhuang.xmpp.MXmppConnManager;
import com.beta.main.R;

public class LoginActivity extends MyBaseActivity implements OnClickListener {
	private Activity activity = LoginActivity.this;
	private SharedPreferences sharedPreferences;
	private CommonDialog dialog;
	
	private EditText mUser; // 帐号编辑框
	private EditText mPassword; // 密码编辑框
	private TextView forgetPwd;
	private CheckBox mCbShowPwd;
	private ImageView ivLogin;
	
	//登录成功
	private boolean success;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyHelper.setNoTitle(activity);
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
		
		setContentView(R.layout.activity_login);
		initViews();
		initEvents();

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
	
	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			//登录XMPPServer成功
			if (msg.what == CustomConst.XMPP_HANDLER_SUCCESS) {
				if (dialog != null)
					dialog.dismiss();

				if (mUser != null) {

					Editor editor = sharedPreferences.edit();
					editor.putString("n", mUser.getText().toString().trim());
					editor.putString("p", mPassword.getText().toString().trim());
					editor.commit();

				}
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
	
	

	private void initViews() {
		mUser = (EditText) findViewById(R.id.phone);
		mPassword = (EditText) findViewById(R.id.passwd);
		forgetPwd = (TextView) findViewById(R.id.forgetPwd);
		 // get the show/hide password Checkbox
        mCbShowPwd = (CheckBox) findViewById(R.id.cbShowPwd);
        ivLogin = (ImageView) findViewById(R.id.ivLogin);
	}
	
	private void initEvents() {
		forgetPwd.setOnClickListener(this);
		ivLogin.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 忘记密码
		case R.id.forgetPwd:
			startActivity(SMSAuthCodeActivity.class);
			break;
		// 登录
		case R.id.ivLogin:
			login_submit(v);
			break;
		}
	}

	//提交登陆信息
	public void login_submit(View v) {
		String inputUserName = mUser.getText().toString().trim();
		String inputPwd = mPassword.getText().toString().trim();
		// 判断 帐号和密码为空
		if ("".equals(inputUserName) || "".equals(inputPwd)) 
		{
			new AlertDialog.Builder(LoginActivity.this)
					.setIcon(
							getResources().getDrawable(
									android.R.drawable.stat_notify_error))
					.setTitle("登录错误").setMessage("手机号或者密码不能为空，\n请输入后再登录！")
					.create().show();
			return;
		} 
		dialog = new CommonDialog(this, R.style.Loading_Dialog,
				R.layout.common_loading_dialog_layout);

		dialog.show();
		handler.postDelayed(new loginXMPPServerRunable(), 10000);
	}

	// 链接XMPPServer
	public class loginXMPPServerRunable implements Runnable {
		public void run() {
			String name = "";
			String pwd = "";
			if (mUser != null) {

				name = mUser.getText().toString().trim();
				pwd = mPassword.getText().toString().trim();

			} else {
				name = sharedPreferences.getString("n", "");
				pwd = sharedPreferences.getString("p", "");
			}
			
			success = MXmppConnManager.getInstance().mXmppLogin(name, pwd,
					getApplicationContext(),handler);
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
