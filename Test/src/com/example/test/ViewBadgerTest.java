package com.example.test;

import com.readystatesoftware.viewbadger.BadgeView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
