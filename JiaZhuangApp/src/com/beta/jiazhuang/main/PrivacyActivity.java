package com.beta.jiazhuang.main;

import com.beta.jiazhuang.mybase.MyBaseActivity;
import com.beta.main.R;

import android.os.Bundle;

public class PrivacyActivity extends MyBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 自定义Activity标题栏
		CustomTitleBar.getTitleBar(this, "隐私声明");
		setContentView(R.layout.activity_privacy);
		goBack();
	}
}
