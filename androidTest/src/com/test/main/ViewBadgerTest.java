package com.test.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.readystatesoftware.viewbadger.BadgeView;

public class ViewBadgerTest extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_badger_test);
		
		View target = findViewById(R.id.default_target);
		BadgeView badge = new BadgeView(this, target);
		badge.setText("1");
		badge.show();
 
	}
}
