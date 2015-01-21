package com.beta.main;

import android.os.Bundle;
import android.view.View;

public class PasswdChangeActivity extends MyBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyHelper.setNoTitle(PasswdChangeActivity.this);
		setContentView(R.layout.activity_passwd_change);
	}
	
	public void goBack(View v) {
		PasswdChangeActivity.this.finish();
	}
}
