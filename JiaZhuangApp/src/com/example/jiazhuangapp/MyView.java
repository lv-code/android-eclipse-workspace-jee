package com.example.jiazhuangapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyView extends Fragment {

	public MyView() {
		super();
	}

	/**
	 * 覆盖此函数，先通过inflater inflate函数得到view最后返回
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.flow_page0,
				container, false);
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
//		View target1 = getActivity().findViewById(R.id.textview01);
//		BadgeView badge1 = new BadgeView(getActivity(), target1);
//		badge1.setText("1");
//		badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
//		badge1.show(true);
	}
}
