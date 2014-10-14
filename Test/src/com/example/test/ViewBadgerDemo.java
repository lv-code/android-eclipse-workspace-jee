package com.example.test;

import com.readystatesoftware.viewbadger.BadgeView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;


public class ViewBadgerDemo extends Activity {
	Context context = ViewBadgerDemo.this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewbadger_demo);
		
		ImageView myView = (ImageView)findViewById(R.id.imageView1);
		BadgeView badge = new BadgeView(this, myView);
		badge.setText(1);
		badge.show(true);
	}
}
