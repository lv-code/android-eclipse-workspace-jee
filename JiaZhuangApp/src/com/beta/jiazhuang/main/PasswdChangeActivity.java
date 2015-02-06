package com.beta.jiazhuang.main;

import com.beta.jiazhuang.mybase.MyBaseActivity;
import com.beta.main.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class PasswdChangeActivity extends MyBaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CustomTitleBar.getTitleBar(PasswdChangeActivity.this, "修改密码");
		setContentView(R.layout.activity_passwd_change);
		goBack();
		findViewById(R.id.btnNext).setOnClickListener(this);
	}	
	@Override
	public void onClick(View v) {
		startActivity(new Intent(PasswdChangeActivity.this, PasswdChangeSuccessActivity.class));
	}
}
