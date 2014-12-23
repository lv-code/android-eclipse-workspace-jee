package com.beta.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.beta.util.CommonUtil;
import com.example.jiazhuangapp.R;

public class FlowActivity extends FragmentActivity {
	Context context = FlowActivity.this;

	// 进度名称
	private TextView flow_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyHelper.setNoTitle(FlowActivity.this);
		// 给返回按钮绑定监听器
		setContentView(R.layout.activity_flow);
		goBack();
//		initListView();
		initGridView();
	}
/*
	// 抽屉导航--侧边栏的列表数据
	public void initListView() {
		final String[] mListImage = { "flow_1_qianqi", "flow_2_kaigongzhunbei",
				"flow_3_kaigong", "flow_4_shuidian", "flow_5_niwa",
				"flow_6_mugong", "flow_7_youqi", "flow_8_anzhuang" };
		final String[] mListTitle = { "前期", "开工准备", "开工", "水电", "泥瓦", "木工",
				"油漆", "安装" };
		ListView listview;
		ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
		listview = (ListView) findViewById(R.id.right_drawer);
		Resources res = context.getResources();
		int length = mListTitle.length;
		for (int i = 0; i < length; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("image", res.getIdentifier(mListImage[i], "drawable",
					getPackageName()));
			item.put("title", mListTitle[i]);
			mData.add(item);
		}

		SimpleAdapter adapter = new SimpleAdapter(this, mData,
				R.layout.flow_listview_item, new String[] { "image", "title" },
				new int[] { R.id.itemImage, R.id.itemTitle });
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				flow_name.setText(mListTitle[position]);

				// Toast.makeText(context, "您选择了标题：" + mListTitle[position],
				// Toast.LENGTH_LONG).show();
			}
		});
	}
	*/

	public void initGridView() {
		String [] icon = {"calenda","file","image","mail","note","pick","plan","player","shop"};
		String [] txt = {"时间排期","合同","图片","邮件","备忘","便签","计划","影音","促销"};
		GridView gridview = (GridView) findViewById(R.id.gridview);

		// 生成动态数组，并且转入数据
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < icon.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", CommonUtil.getResourceIdByStr(context, "flow_"+icon[i], "drawable"));// 添加图像资源的ID
			map.put("ItemText", txt[i]);// 按序号做ItemText
			lstImageItem.add(map);
		}
		
		// 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
		SimpleAdapter saImageItems = new SimpleAdapter(this, // 没什么解释
				lstImageItem,// 数据来源
				R.layout.flow_gridview_item,// night_item的XML实现

				// 动态数组与ImageItem对应的子项
				new String[] { "ItemImage", "ItemText" },

				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.ItemImage, R.id.ItemText });
		// 添加并且显示
		gridview.setAdapter(saImageItems);
		// 添加消息处理
//		gridview.setOnItemClickListener(new ItemClickListener());
	}

	// 右侧滑动菜单


	public void goBack() {
		Button titleBackBtn = (Button) this
				.findViewById(R.id.head_TitleBackBtn);
		titleBackBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				FlowActivity.this.finish();
			}
		});
	}

	public void goHome(View v) {
		FlowActivity.this.finish();
	}

	// 判断是否登陆
	public void goLogin(View v) {
		Intent intent = new Intent(context, LoginActivity.class);
		startActivity(intent);
	}

}
