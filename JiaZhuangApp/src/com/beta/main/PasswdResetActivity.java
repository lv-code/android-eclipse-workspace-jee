package com.beta.main;

import android.os.Bundle;
import android.view.View;

public class PasswdResetActivity extends MyBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyHelper.setNoTitle(PasswdResetActivity.this);
		setContentView(R.layout.activity_passwd_reset);
	}
	
	public void goBack(View v) {
		PasswdResetActivity.this.finish();
	}
}
