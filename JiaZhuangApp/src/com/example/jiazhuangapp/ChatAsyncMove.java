package com.example.jiazhuangapp;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

//异步更新布局的位置
public class ChatAsyncMove extends AsyncTask<Integer, Integer, Void> {
	private int MAX_HEIGHT = 60;
	private View mView;
	
	public ChatAsyncMove(View view) {
		this.mView = view;
	}
	@Override
	protected Void doInBackground(Integer... params) {
		int times = 0;
		int divi = Math.abs(params[0]);
		Log.i("tag1", "divi = "+ divi + "params[0] = " +params[0]);
		if (MAX_HEIGHT % divi == 0)// 整除
			times = MAX_HEIGHT / Math.abs(params[0]);
		else
			times = MAX_HEIGHT / divi + 1;// 有余数

		for (int i = 0; i < times; i++) {
			publishProgress(params[0]);
			try {
				Thread.sleep(divi);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mView.getLayoutParams();
		/*
		if (values[0] < 0) {
			Log.i("tag0", "layoutParams.bottomMargin = "+layoutParams.bottomMargin);
//			layoutParams.bottomMargin = Math.max(layoutParams.bottomMargin
//					+ values[0], -MAX_HEIGHT);
			layoutParams.bottomMargin = Math.max(layoutParams.bottomMargin
					+ values[0], -100);
			Log.i("tag1", "layoutParams.bottomMargin = "+layoutParams.bottomMargin+", values[0] = "+values[0]);
		} else {
			layoutParams.bottomMargin = Math.min(layoutParams.bottomMargin
					+ values[0], 0);
			Log.i("tag2", "layoutParams.bottomMargin = "+layoutParams.bottomMargin);
		}
		*/
		layoutParams.bottomMargin = values[0];
		mView.setLayoutParams(layoutParams);

		super.onProgressUpdate(values);
	}
}
