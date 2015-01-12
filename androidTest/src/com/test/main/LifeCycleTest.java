package com.test.main;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LifeCycleTest extends Activity implements OnClickListener {
	private static final String TAG = "LifeCycle";
	private Button btn1;
	private TextView txt1;
	//定义一个String 类型用来存取我们EditText输入的值
	private String mString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lifecycle_test);
		init();
		Log.e(TAG, "start onCreate~~~");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			txt1.setText("android生命周期测试");
			break;

		default:
			break;
		}
	}

	public void init() {
		txt1 = (TextView) findViewById(R.id.textView1);
		btn1 = (Button) findViewById(R.id.button1);
		btn1.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.e(TAG, "start onStart~~~");
	}

	//当按HOME键时，然后再次启动应用时，我们要恢复先前状态
	@Override
	protected void onRestart() {
		super.onRestart();
		txt1.setText(mString);
		Log.e(TAG, "start onRestart~~~");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.e(TAG, "start onResume~~~");
	}

	// 当我们按HOME键时，我在onPause方法里，将输入的值赋给mString
	@Override
	protected void onPause() {
		super.onPause();
		mString = txt1.getText().toString();
		Log.e(TAG, "start onPause~~~");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.e(TAG, "start onStop~~~");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "start onDestroy~~~");
	}

}
