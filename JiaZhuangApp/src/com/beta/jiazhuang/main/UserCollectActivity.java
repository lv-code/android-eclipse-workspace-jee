package com.beta.jiazhuang.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.beta.jiazhuang.adapter.UserCollectListViewAdapter;
import com.beta.jiazhuang.adapter.UserCollectMsgEntity;
import com.beta.jiazhuang.mybase.MyBaseActivity;
import com.beta.main.R;

/**
 * @author zg
 * 列表现在采用FrameLayout中显示不用组件，之后可以用不同的Layout来填充，如：activity_user_collect_img，activity_user_collect_voice
 */
public class UserCollectActivity extends MyBaseActivity {

	Context context = UserCollectActivity.this;
	ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 自定义Activity标题栏
		CustomTitleBar.getTitleBar(this, "收藏列表");
		setContentView(R.layout.activity_user_collect);
		initViewById();
		initListView();
		goBack();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}


	// 初始化组件
	private void initViewById() {
		lv = (ListView) findViewById(R.id.listview);
	}

	
	private void initListView() {
		UserCollectListViewAdapter sa = new UserCollectListViewAdapter(this, getData());
		lv.setAdapter(sa);
	}
	
	//测试数据
	private ArrayList<UserCollectMsgEntity> getData() {
		ArrayList<UserCollectMsgEntity> mData = new ArrayList<UserCollectMsgEntity>();
		UserCollectMsgEntity m1 = new UserCollectMsgEntity("user01", "2015.1.20", 1, "不客气。那就说好后天下午2点，我带主材的人过去...");
		UserCollectMsgEntity m2 = new UserCollectMsgEntity("user01", "2015.1.20", 2, "http://localhost/img/1.png");
		UserCollectMsgEntity m3 = new UserCollectMsgEntity("user01", "2015.1.20", 3, "47s");
		mData.add(m1);
		mData.add(m2);
		mData.add(m3);

		return mData;
	}
}
