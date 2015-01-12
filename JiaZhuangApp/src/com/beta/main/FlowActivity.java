package com.beta.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.adapter.FlowListViewAdapter;
import com.beta.residemenu.ResideMenu;
import com.beta.residemenu.ResideMenuItem;
import com.beta.util.CommonUtil;

public class FlowActivity extends FragmentActivity implements OnClickListener {

	Context context = FlowActivity.this;

	final static String TAG = "INFO : ";

	ImageView title_right_btn;
	// 进度名称
	TextView flow_name;
	//网格组件
	GridView gridview;
	// 生成动态数组，并且转入数据
	ArrayList<HashMap<String, Object>> lstImageItem;
	
	// 选择列表
	ListView listview;
	Boolean listview_show = false;
	// 当前选择的阶段
	int checkedItem = 0;
	
	ResideMenu resideMenu;
	//当前所在菜单
	int resideMenuNow = 0;
	// 左侧菜单的打开状态
	Boolean residemenuOpen = false;
	//GridView的适配器
	SimpleAdapter saImageItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyHelper.setNoTitle(FlowActivity.this);
		// 给返回按钮绑定监听器
		setContentView(R.layout.activity_flow);
		goBack();
		initViewById();
		initTitleBar();
		initListView();
		initGridView();
		initResideMenu();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 展开左侧菜单
		case R.id.title_right_btn:
			Log.e(TAG, "左侧菜单被点击！！！！");
			resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
			break;

		// 展开ListView选择列表
		case R.id.head_center_text:
			toggleListView();
			break;
		default:
			Log.i(TAG, "这里是default！！！！");
			break;
		}

	}

	// 左边菜单
	OnClickListener resideMenuOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case 2:
				Intent intent = new Intent(context, PasswdResetActivity.class);
				startActivity(intent);
				break;

			default:
				Log.i(TAG, "v.getId() : "+v.getId());
				break;
			}

		}
	};

	// attach to current activity;
	private void initResideMenu() {
		resideMenu = new ResideMenu(this);
//		resideMenu.setBackground(R.drawable.app_logo);
		//不显示阴影
		resideMenu.setShadowVisible(false);
		resideMenu.attachToActivity(this);

		// create menu items;
		String titles[] = { "阶段服务", "项目沟通", "修改密码"};
		//用 0 来代表：不是当前的选择条目
		int icon[] = { 0, 0, 0  };
		icon[resideMenuNow] = R.drawable.residemenu_item_now;

		for (int i = 0; i < titles.length; i++) {
			ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i]);
			item.setOnClickListener(resideMenuOnClickListener);
			item.setId(i);
			resideMenu.addMenuItem(item, ResideMenu.DIRECTION_LEFT); 
		}
	}

	private void initViewById() {
		title_right_btn = (ImageView) findViewById(R.id.title_right_btn);
		flow_name = (TextView) this.findViewById(R.id.head_center_text);
		listview = (ListView) this.findViewById(R.id.flow_listview);

	}

	// 初始化标题栏
	private void initTitleBar() {

		title_right_btn.setOnClickListener(this);
		flow_name.setOnClickListener(this);

	}

	// 控制ListView的可见
	private void toggleListView() {
		final Drawable img_pullup = getResources().getDrawable(
				R.drawable.icon_pullup);
		img_pullup.setBounds(0, 0, img_pullup.getMinimumWidth(),
				img_pullup.getMinimumHeight());
		final Drawable img_pulldown = getResources().getDrawable(
				R.drawable.icon_pulldown);
		img_pulldown.setBounds(0, 0, img_pulldown.getMinimumWidth(),
				img_pulldown.getMinimumHeight());
		if (!listview_show) {
			listview_show = true;
			listview.setVisibility(View.VISIBLE);
			flow_name.setCompoundDrawables(null, null, img_pullup, null);

		} else {
			listview_show = false;
			listview.setVisibility(View.GONE);
			flow_name.setCompoundDrawables(null, null, img_pulldown, null);
		}

	}

	// 点击标题栏，选择装修阶段
	public void initListView() {
		final String[] mListImage = { "flow_listview_icon_qianqi",
				"flow_listview_icon_kaigongzhunbei",
				"flow_listview_icon_kaigong", "flow_listview_icon_shuidian",
				"flow_listview_icon_niwa", "flow_listview_icon_mugong",
				"flow_listview_icon_youqi", "flow_listview_icon_anzhaung" };
		final String[] mListTitle = { "前期", "开工准备", "开工", "水电", "泥瓦", "木工",
				"油漆", "安装" };

		ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();

		int length = mListTitle.length;
		for (int i = 0; i < length; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("img", CommonUtil.getResourceIdByStr(context,
					mListImage[i], "drawable"));
			item.put("title", mListTitle[i]);
			if (i == checkedItem) {
				item.put("item_now", R.drawable.flow_listview_xuanzhong);
			} else {
				item.put("item_now", R.drawable.flow_listview_weixuanzhong);
			}
			mData.add(item);
		}

		final SimpleAdapter adapter = new FlowListViewAdapter(this, mData,
				R.layout.flow_listview_item, new String[] { "img", "title",
						"item_now" }, new int[] { R.id.item_img,
						R.id.item_title, R.id.item_img_now });

		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				flow_name.setText(mListTitle[position]);
				changeItemImg(adapter, position);
				toggleListView();
				changeGridView();
				// Toast.makeText(context, "您选择了标题：" + mListTitle[position],
				// Toast.LENGTH_LONG).show();
			}
		});

	}

	// 改变ListView item的选中状态
	private void changeItemImg(SimpleAdapter sa, int selectedItem) {

		HashMap<String, Object> map1 = (HashMap<String, Object>) sa
				.getItem(checkedItem);
		HashMap<String, Object> map2 = (HashMap<String, Object>) sa
				.getItem(selectedItem);
		map1.put("item_now", R.drawable.flow_listview_weixuanzhong);
		map2.put("item_now", R.drawable.flow_listview_xuanzhong);

		sa.notifyDataSetChanged();
		checkedItem = selectedItem;
	}

	//改变GridView的数据
	private void changeGridView() {
		SimpleAdapter sa = (SimpleAdapter) gridview.getAdapter();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("ItemImage", context.getResources().getIdentifier("flow_file", "drawable",
				context.getPackageName()));
		map1.put("ItemText", "test");
		lstImageItem.add(map1);
		sa.notifyDataSetChanged();
	}
	
	public void initGridView() {
		String[] icon = { "calenda", "file", "image", "mail", "note", "pick",
				"plan", "player", "shop" };
		String[] txt = { "时间排期", "合同", "图片", "邮件", "备忘", "便签", "计划", "影音", "促销" };
		gridview = (GridView) this.findViewById(R.id.gridview);

		// 生成动态数组，并且转入数据
		lstImageItem = new ArrayList<HashMap<String, Object>>();
		
		for (int i = 0; i < icon.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", CommonUtil.getResourceIdByStr(context, "flow_"
					+ icon[i], "drawable"));// 添加图像资源的ID
			map.put("ItemText", txt[i]);// 按序号做ItemText
			lstImageItem.add(map);
		}

		// 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
		saImageItems = new SimpleAdapter(this, lstImageItem,
				R.layout.flow_gridview_item,

				// 动态数组与ImageItem对应的子项
				new String[] { "ItemImage", "ItemText" },

				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.ItemImage, R.id.ItemText });
		// 添加并且显示
		gridview.setAdapter(saImageItems);
		// 添加消息处理
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(context, Html5Activity.class);
				startActivity(intent);

			}
		});
	}

	// 右侧滑动菜单

	public void goBack() {
		Button titleBackBtn = (Button) this
				.findViewById(R.id.head_TitleBackBtn);
		titleBackBtn.setOnClickListener(new OnClickListener() {
			@Override
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
