package com.test.main;

import com.test.util.MyHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


/**
 * @author zg
 * 如何动态设置UI
 */
public class DynamicCreateUIDemo extends Activity {

	Button button1, button2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dynamic_create_uidemo);
		initViewById();
	}

	private void initViewById() {
		Button button1 = (Button) this.findViewById(R.id.button1);
		button1.setOnClickListener(onClickListener1);
		Button button2 = (Button) this.findViewById(R.id.button2);
		button2.setOnClickListener(onClickListener2);
	}
	
	OnClickListener onClickListener1 = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			changePropertyOfWidget();
		}
	};
	
	OnClickListener onClickListener2 = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			MyHelper myHelper = new MyHelper();
			myHelper.refreshActivity();
		}
	};

	//改变TextView的属性
	private void changePropertyOfWidget() {
		TextView textView = (TextView) this.findViewById(R.id.textView);
		textView.setAlpha(0.5f);
		textView.setBackgroundColor(0x112233);
		// 十六进制数值表见：http://wear.techbrood.com/reference/android/widget/TextView.html#attr_android:gravity
		textView.setGravity(0x05);
		textView.setWidth(700);
	}
	
	//layoutParams的使用
	
}
