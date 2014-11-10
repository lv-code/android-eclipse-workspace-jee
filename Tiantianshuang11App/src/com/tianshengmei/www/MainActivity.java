package com.tianshengmei.www;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	private final static String TAG = "myErr";
	// "天猫双十一购物入口",
	private String[] mListTitle = { "天猫双十一购物入口", "女装", "男装", "内衣", "美妆", "女鞋",
			"男鞋", "箱包", "居家", "美食", "零食", "母婴", "皮草", "珠宝手表", "电器", "家纺车品",
			"生活百货" };
	private String[] mListImg = { "img", "img0", "img1", "img2", "img3",
			"img4", "img5", "img6", "img7", "img8", "img9", "img10", "img11",
			"img12", "img13", "img14", "img15" };
	private String[] mListUri = { "www.baidu.com" };

	ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_inflater);

		int lengh = mListTitle.length;
		for (int i = 0; i < lengh; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("title", mListTitle[i]);
			item.put(
					"img",
					getResources().getIdentifier(mListImg[i], "drawable",
							getPackageName()));
			mData.add(item);
		}

		ListView mListView = (ListView) findViewById(R.id.listview);
//		mListView.setOverscrollHeader(getDrawable(R.drawable.img));
		
		SimpleAdapter adapter = new SimpleAdapter(this, mData,
				R.layout.listview_item, new String[] { "title", "img" },
				new int[] { R.id.title, R.id.imgview });
		mListView.setAdapter(adapter);

		// 注册单击ListView中的Item响应的事件
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterview, View view,
					int position, long id) {
//				Toast.makeText(getApplicationContext(), "item : " + position,
//						Toast.LENGTH_SHORT).show();
				Uri uri = Uri.parse("http://"+mListUri[0]);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
	}

	// @Override
	// protected void onResume() {
	// super.onResume();
	// Intent intent = new Intent();
	// intent.setAction("android.intent.action.VIEW");
	// Uri content_url = Uri.parse("http://play.jb51.net");
	// intent.setData(content_url);
	// startActivity(intent);
	// Log.e(TAG, "start onResume~~~");
	// }

	@Override
	protected void onStop() {
		super.onStop();
		this.finish();
		// Log.e(TAG, "start onStop~~~");
	}

	// @Override
	// protected void onListItemClick(ListView l, View v, int position, long id)
	// {
	// Toast.makeText(getApplicationContext(), "item: " + position,
	// Toast.LENGTH_SHORT).show();
	// }

}
