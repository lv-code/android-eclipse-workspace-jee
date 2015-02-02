package com.beta.main;

import com.beta.mybase.MyBaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class PasswdResetActivity extends MyBaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CustomTitleBar.getTitleBar(PasswdResetActivity.this, "密码重置");
		setContentView(R.layout.activity_passwd_reset);
		goBack();
		findViewById(R.id.btnNext).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		startActivity(new Intent(PasswdResetActivity.this, PasswdChangeSuccessActivity.class));
	}

}
