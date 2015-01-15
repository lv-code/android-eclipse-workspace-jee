package com.beta.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class PasswdChangeActivity extends Activity {

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
