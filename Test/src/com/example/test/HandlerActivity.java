package com.example.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HandlerActivity extends Activity implements OnClickListener {
	Button button = null;
	TextView text = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_handler);
        button = (Button)findViewById(R.id.btn);
        button.setOnClickListener(this);
        text = (TextView)findViewById(R.id.content);
        
        Bundle bundle = this.getIntent().getExtras();
        String str = bundle.getString("from");
        text.setText(str);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn:
			Looper looper = Looper.myLooper();// 取得当前线程里的looper
			MyHandler mHandler = new MyHandler(looper);// 构造一个handler使之可与looper通信
			// buton等组件可以由mHandler将消息传给looper后,再放入messageQueue中,同时mHandler也可以接受来自looper消息
			mHandler.removeMessages(0);
			String msgStr = "主线程不同组件通信:消息来自button";
			Message m = mHandler.obtainMessage(1, 1, 1, msgStr);// 构造要传递的消息
			mHandler.sendMessage(m);// 发送消息:系统会自动调用handleMessage方法来处理消息
			break;

		}
	}

	private class MyHandler extends Handler {
		public MyHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {// 处理消息
			text.setText(msg.obj.toString());
		}
	}

}
