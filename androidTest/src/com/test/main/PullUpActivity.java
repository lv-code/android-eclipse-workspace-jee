package com.test.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class PullUpActivity extends Activity {

	RelativeLayout Parent;
	PanelBottom panelBom;
	RelativeLayout.LayoutParams parentParams;
	RelativeLayout.LayoutParams paneBomParams;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parent = new RelativeLayout(getApplicationContext());
		panelBom = new PanelBottom(getApplicationContext());
		parentParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		paneBomParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT, 150);
		paneBomParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		paneBomParams.bottomMargin = -80;
		Parent.addView(panelBom, paneBomParams);
		setContentView(Parent, parentParams);
	}

}