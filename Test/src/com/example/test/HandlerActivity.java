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

/*
 * 在android中有两种实现线程thread的方法：

 一种是，扩展java.lang.Thread类 
 另一种是，实现Runnable接口
 相对而言，AsyncTask更轻量级
 new Thread的弊端如下：
 a. 每次new Thread新建对象性能差。
 b. 线程缺乏统一管理，可能无限制新建线程，相互之间竞争，及可能占用过多系统资源导致死机或oom。
 c. 缺乏更多功能，如定时执行、定期执行、线程中断。
 相比new Thread，Java提供的四种线程池的好处在于：
 a. 重用存在的线程，减少对象创建、消亡的开销，性能佳。
 b. 可有效控制最大并发线程数，提高系统资源的使用率，同时避免过多资源竞争，避免堵塞。
 c. 提供定时执行、定期执行、单线程、并发数控制等功能。
 http://www.trinea.cn/android/java-android-thread-pool/
 */
public class HandlerActivity extends Activity implements OnClickListener {
	Button button = null;
	TextView text = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_handler);
		button = (Button) findViewById(R.id.btn);
		button.setOnClickListener(this);
		text = (TextView) findViewById(R.id.content);

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

	/*
	 * 在Android开发过程中，常需要更新界面的UI。
	 * 而更新UI是要主线程来更新的，即UI线程更新。
	 * 如果在主线线程之外的线程中直接更新页面显示常会报错。
	 */
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
