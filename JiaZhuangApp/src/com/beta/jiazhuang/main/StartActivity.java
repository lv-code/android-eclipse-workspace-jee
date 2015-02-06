package com.beta.jiazhuang.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.igexin.sdk.PushManager;

public class StartActivity extends Activity {
	Boolean showWhatsnew = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent();
				if (true == showWhatsnew) {
					intent.setClass(StartActivity.this, WhatsnewActivity.class);
				} else {
					intent.setClass(StartActivity.this, LoginActivity.class);
				}
				startActivity(intent);
				StartActivity.this.finish();
			}
		}, 100);

	}
}
