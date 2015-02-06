package com.beta.jiazhuang.main;

import com.beta.jiazhuang.mybase.MyBaseActivity;
import com.beta.main.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SMSAuthCodeActivity extends MyBaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CustomTitleBar.getTitleBar(SMSAuthCodeActivity.this, "获取短信验证码");
		setContentView(R.layout.activity_smsauth_code);
		goBack();
		findViewById(R.id.btnNext).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent(SMSAuthCodeActivity.this, PasswdResetActivity.class));
	}
}
