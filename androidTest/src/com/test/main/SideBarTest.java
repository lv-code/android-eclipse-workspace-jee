package com.test.main;

import com.example.test.R;
import com.test.main.SideBar;
import com.test.main.SideBar.OnTouchingChangedListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SideBarTest extends Activity {
	SideBar sideBar;
	TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_side_bar_test);
		
		sideBar = (SideBar) findViewById(R.id.sideBar);
		textView = (TextView) findViewById(R.id.textview);
		sideBar.setShowChooseText(textView);
		sideBar.setOnTouchingChangedListener(new OnTouchingChangedListener() {

			@Override
			public void onTouchingChanged(String s) {
				// TODO Auto-generated method stub
			}
		});

	}

}
