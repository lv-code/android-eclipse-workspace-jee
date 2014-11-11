package com.beta.main;

import com.example.jiazhuangapp.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UserinfoActivity extends Activity {
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ---------------------------------------------------
		// 自定义Activity标题栏
		CustomTitleBar.getTitleBar(this, "个人主页");
		// ---------------------------------------------------
		setContentView(R.layout.activity_userinfo);
		init();
		goBack();
	}

	public void init() {
context = UserinfoActivity.this;
	}

	public void goBack() {
		Button titleBackBtn = (Button) this
				.findViewById(R.id.head_TitleBackBtn);
		titleBackBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				UserinfoActivity.this.finish();
			}
		});
	}
	public void goHome(View v) {
		UserinfoActivity.this.finish();
	}

	// 判断是否登陆
	public void goLogin(View v) {
		Intent intent = new Intent(context, LoginActivity.class);
		startActivity(intent);
	}

}
