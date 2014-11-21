package com.test.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.test.R;

public class TimerTaskDemo extends Activity implements OnClickListener {

	private final int TIMELENGTH = 10;
	private int recLen = TIMELENGTH;
	private Boolean timeStarted = false;
	private TextView txtView;
	private Button btntime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timertaskdemo);

		txtView = (TextView) findViewById(R.id.txttime);
		btntime = (Button) findViewById(R.id.btntime);
		btntime.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if (!timeStarted) {
			btntime.setText("点击停止计时");
			Message message = handler.obtainMessage(1); // Message
			handler.sendMessageDelayed(message, 1000);
			timeStarted = true;
		} else {
			Message message = handler.obtainMessage(2);
			handler.sendMessageDelayed(message, 1000);
			timeStarted = false;
		}
	}

	final Handler handler = new Handler() {

		public void handleMessage(Message msg) { // handle message
			switch (msg.what) {
			case 1:
				
				txtView.setText("00:" + recLen);

				if (recLen > 0) {
					Message message = handler.obtainMessage(1);
					handler.sendMessageDelayed(message, 1000); // send message
					recLen--;
				} else {
					// txtView.setVisibility(View.GONE);
					txtView.setText("00:" + recLen);
					btntime.setText("点击開始计时");
					timeStarted = false;
					recLen = TIMELENGTH;
				}
				break;
			case 2:
				btntime.setText("点击開始计时");
				break;
			}

			super.handleMessage(msg);
		}
	};
}
