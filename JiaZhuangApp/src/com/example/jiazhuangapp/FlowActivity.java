package com.example.jiazhuangapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FlowActivity extends FragmentActivity {
	Context context = FlowActivity.this;
	List<Fragment> fragmentList = new ArrayList<Fragment>();
	Fragment fragment1, fragment2, fragment3;
	private ViewPager viewpager;
	private ImageView page0;
	private ImageView page1;
	private ImageView page2;
	public int currIndex = 0;
	private SlidingMenu menu;
	// 进度名称
	private TextView flow_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyHelper.setNoTitle(FlowActivity.this);
		// 给返回按钮绑定监听器
		setContentView(R.layout.activity_flow);
		goBack();
		flow_name = (TextView) findViewById(R.id.flow_name);

		initSlidingMenu();
		initListView();
		initViewPager();
		
	}


//抽屉导航--侧边栏的列表数据
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
				menu.showContent();
				// Toast.makeText(context, "您选择了标题：" + mListTitle[position],
				// Toast.LENGTH_LONG).show();
			}
		});
	}

	// 右侧滑动菜单
	public void initSlidingMenu() {
		// configure the SlidingMenu
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.RIGHT);
		// menu.setTouchModeAbove(SlidingMenu.RIGHT);
		// menu.setShadowWidthRes(R.dimen.shadow_width);
		// menu.setShadowDrawable(R.drawable.shadow);
		// menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setBehindWidth(500);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.flow_slidingmenu_fragment);

		// 给titlebar添加按钮
		/*
		 * Button titleRightBtn = new Button(this);
		 * titleRightBtn.setBackgroundResource(R.drawable.title_right_btn);
		 * titleRightBtn.setBackgroundColor(R.color.style_blue);
		 * RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
		 * LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		 * params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
		 * RelativeLayout.TRUE); params.addRule(RelativeLayout.ALIGN_PARENT_TOP,
		 * RelativeLayout.TRUE); titleRightBtn.setLayoutParams(params);
		 * 
		 * myTitleBar.addView(titleRightBtn);
		 */
		Button title_right_btn = (Button) findViewById(R.id.title_right_btn);
		title_right_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				menu.toggle();
				// menu.showContent();
			}
		});
		// RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
		// titleRightBtn.getLayoutParams();
	}

	public void initViewPager() {
		viewpager = (ViewPager) findViewById(R.id.flow_viewpager);
		// 设置每个tab对应的 fragment
		// fragment1 = new UserinfoActivity();
		fragment1 = FragmentTest.newInstance("测试Fragment1");
		fragment2 = FragmentTest.newInstance("测试Fragment2");
		fragment3 = FragmentTest.newInstance("测试Fragment3");

		fragmentList.add(fragment1);
		fragmentList.add(fragment2);
		fragmentList.add(fragment3);
		viewpager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),
				fragmentList));
		viewpager.setCurrentItem(0);

		page0 = (ImageView) findViewById(R.id.page0);
		page1 = (ImageView) findViewById(R.id.page1);
		page2 = (ImageView) findViewById(R.id.page2);

		viewpager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	//定义内部类
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				page0.setImageDrawable(getResources().getDrawable(
						R.drawable.page_now));
				page1.setImageDrawable(getResources().getDrawable(
						R.drawable.page));
				break;
			case 1:
				page1.setImageDrawable(getResources().getDrawable(
						R.drawable.page_now));
				page0.setImageDrawable(getResources().getDrawable(
						R.drawable.page));
				page2.setImageDrawable(getResources().getDrawable(
						R.drawable.page));
				break;
			case 2:
				page2.setImageDrawable(getResources().getDrawable(
						R.drawable.page_now));
				page1.setImageDrawable(getResources().getDrawable(
						R.drawable.page));
				break;

			}
			currIndex = arg0;
			// animation.setFillAfter(true);// True:图片停在动画结束位置
			// animation.setDuration(300);
			// pageImg.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	/**
	 * 定义适配器
	 * 
	 */
	public class MyPagerAdapter extends FragmentPagerAdapter {

		private List<Fragment> fragmentList;

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		public MyPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
			super(fm);
			this.fragmentList = fragmentList;
		}

		/**
		 * 得到每个页面
		 */
		@Override
		public Fragment getItem(int arg0) {
			return (fragmentList == null || fragmentList.size() == 0) ? null
					: fragmentList.get(arg0);
		}

		/**
		 * 页面的总个数
		 */
		@Override
		public int getCount() {
			return fragmentList == null ? 0 : fragmentList.size();
		}
	}

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.next, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
