package com.beta.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyBaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	

	// 返回
	public void goBack() {
		Button titleBackBtn = (Button) this
				.findViewById(R.id.head_TitleBackBtn);
		titleBackBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//将活动推向后台，并没有立即释放内存，活动的资源并没有被清理
				//finish();
				//杀死了整个进程，这时候活动所占的资源也会被释放。
				System.exit(0);
			}
		});
	}

}
