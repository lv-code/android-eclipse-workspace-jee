package com.example.jiazhuangapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.readystatesoftware.viewbadger.BadgeView;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FlowActivity extends Activity {
	Context context = FlowActivity.this;
	private ViewPager mViewPager;
	private ImageView mPage0;
	private ImageView mPage1;
	private ImageView mPage2;
	private int currIndex = 0;
	private SlidingMenu menu;
	//进度名称
	private TextView flow_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 自定义Activity标题栏
		CustomTitleBar.getTitleBar(this, "装修流程");
		// 给返回按钮绑定监听器
		goBack();
		setContentView(R.layout.activity_flow);
		flow_name = (TextView)findViewById(R.id.flow_name);
		
		initSlidingMenu();
		initListView();
		initViewPager();
		initViewBadger();
	}

	public void initViewBadger() {

		View target1 = findViewById(R.id.flow_name);
		BadgeView badge1 = new BadgeView(this, target1);
		badge1.setText("1");
		badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
		badge1.show(true);
		
		View target2 = mPage0.findViewById(R.id.ImageView0);
		BadgeView badge2 = new BadgeView(this, target2);
		badge2.setText("99");
		badge2.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
		badge2.show(true);
	}

	public void initListView() {
		final String[] mListImage = { "flow_1_qianqi", "flow_2_kaigongzhunbei",
				"flow_3_kaigong", "flow_4_shuidian", "flow_5_niwa",
				"flow_6_mugong", "flow_7_youqi", "flow_8_anzhuang" };
		final String[] mListTitle = { "前期", "开工准备", "开工", "水电", "泥瓦", "木工",
				"油漆", "安装" };
		ListView lv_simpleAdapter;
		ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
		lv_simpleAdapter = (ListView) findViewById(R.id.right_drawer);
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
		lv_simpleAdapter.setAdapter(adapter);
		lv_simpleAdapter.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				flow_name.setText(mListTitle[position]);
				menu.showContent();
//				Toast.makeText(context, "您选择了标题：" + mListTitle[position],
//						Toast.LENGTH_LONG).show();
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
		Button titleRightBtn = new Button(this);
		titleRightBtn.setBackgroundResource(R.drawable.title_right_btn);
		// titleRightBtn.setBackgroundColor(R.color.style_blue);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		titleRightBtn.setLayoutParams(params);

		RelativeLayout titleBar = (RelativeLayout) findViewById(R.id.titleBar);
		titleBar.addView(titleRightBtn);
		titleRightBtn.setOnClickListener(new OnClickListener() {

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
		mViewPager = (ViewPager) findViewById(R.id.flow_viewpager);
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());

		mPage0 = (ImageView) findViewById(R.id.page0);
		mPage1 = (ImageView) findViewById(R.id.page1);
		mPage2 = (ImageView) findViewById(R.id.page2);

		// 将要分页显示的View装入数组中
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.flow_page0, null);
		View view2 = mLi.inflate(R.layout.flow_page1, null);
		View view3 = mLi.inflate(R.layout.flow_page2, null);

		// 每个页面的view数据
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		// 填充ViewPager的数据适配器
		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};

		mViewPager.setAdapter(mPagerAdapter);
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				mPage0.setImageDrawable(getResources().getDrawable(
						R.drawable.page_now));
				mPage1.setImageDrawable(getResources().getDrawable(
						R.drawable.page));
				break;
			case 1:
				mPage1.setImageDrawable(getResources().getDrawable(
						R.drawable.page_now));
				mPage0.setImageDrawable(getResources().getDrawable(
						R.drawable.page));
				mPage2.setImageDrawable(getResources().getDrawable(
						R.drawable.page));
				break;
			case 2:
				mPage2.setImageDrawable(getResources().getDrawable(
						R.drawable.page_now));
				mPage1.setImageDrawable(getResources().getDrawable(
						R.drawable.page));
				break;

			}
			currIndex = arg0;
			// animation.setFillAfter(true);// True:图片停在动画结束位置
			// animation.setDuration(300);
			// mPageImg.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
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
