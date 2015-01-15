package com.beta.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * @author zg
 * 列表现在采用FrameLayout中显示不用组件，之后可以用不同的Layout来填充，如：activity_user_collect_img，activity_user_collect_voice
 */
public class UserCollectActivity extends Activity {

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
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}


	// 初始化组件
	private void initViewById() {
		lv = (ListView) findViewById(R.id.listview);
	}

	
	private void initListView() {
		//测试数据
		final String[] word = {"不客气。那就说好后天下午2点，我带主材的人过去...", "", "", "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试"};
		final int[] img = {0, R.drawable.collect_zhanwei01, 0, 0};
		final String[] voiceTxt = {"", "", "47’’", ""};
		final int[] voiceImg = {0, 0, R.drawable.collect_player_start, 0};
		
		ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < word.length; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("avatar", R.drawable.collect_zhanwei03_touxiang);
			item.put("word", word[i]);
			item.put("img", img[i]);
			item.put("voiceTxt", voiceTxt[i]);
			item.put("voiceImg", voiceImg[i]);
			item.put("date", "2014.11.08");
			mData.add(item);
		}
		
		SimpleAdapter sa = new SimpleAdapter(context, mData,
				R.layout.activity_user_collect_list_item_word, new String[] {
 "avatar", "word", "img", "voiceTxt", "voiceImg",
						"date" }, new int[] { R.id.ivAvatar, R.id.tvWord,
						R.id.tvImg, R.id.tvVoiceTxt, R.id.tvVoiceImg, R.id.tvDate});
		lv.setAdapter(sa);
	}
}
