package com.beta.jiazhuang.main;

import com.beta.jiazhuang.mybase.MyBaseActivity;
import com.beta.main.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author zg
 * 密码修改成功
 *
 */
public class PasswdChangeSuccessActivity extends MyBaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CustomTitleBar.getTitleBar(PasswdChangeSuccessActivity.this, "修改密码成功");
		setContentView(R.layout.activity_passwd_change_success);
		goBack();
		// 重新登录
		findViewById(R.id.btnLoginAgain).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent(PasswdChangeSuccessActivity.this, LoginActivity.class));
		
	}
}
