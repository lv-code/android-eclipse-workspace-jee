package backup;

import java.util.ArrayList;
import java.util.List;

import com.beta.main.CustomTitleBar;
import com.beta.main.LoginActivity;
import com.beta.main.R;
import com.beta.main.R.id;
import com.beta.main.R.layout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LjljActivity extends FragmentActivity {
	Context context;
	private ViewPager mViewPager;
	private TextView textView1, textView2, textView3;
	/** 页面list **/
	List<Fragment> fragmentList = new ArrayList<Fragment>();
	Fragment fragment1, fragment2, fragment4;

	/** 页面title list **/
	// List<String> titleList = new ArrayList<String>();

	// private ImageView imageView;// 动画图片
	// private int offset = 0;// 动画图片偏移量
	// private int currIndex = 0;// 当前页卡编号
	// private int bmpW;// 动画图片宽度

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		context = LjljActivity.this;
		super.onCreate(savedInstanceState);
		// ---------------------------------------------------
		// 自定义Activity标题栏
		CustomTitleBar.getTitleBar(this, "蓝景丽家家装");
		// CustomTitleBar.hideBackBtn();
		// ---------------------------------------------------
		/* 返回按钮 */
		Button titleBackBtn = (Button) this
				.findViewById(R.id.head_TitleBackBtn);
		titleBackBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LjljActivity.this.finish();
			}
		});
		setContentView(R.layout.activity_ljlj);
		// InitImageView();
		// Log.i("break point 1", "11111");
		initTextView();
		initViewPager();
		// initListView2();
	}

	public void goHome(View v) {
		Toast.makeText(getApplicationContext(), "这是一个Toast", Toast.LENGTH_LONG)
				.show();
		LjljActivity.this.finish();
	}

	// 判断是否登陆
	public void goLogin(View v) {
		Intent intent = new Intent(context, LoginActivity.class);
		startActivity(intent);
	}

	// 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
	// private void InitImageView() {
	// imageView= (ImageView) findViewById(R.id.cursor);
	// bmpW = BitmapFactory.decodeResource(getResources(),
	// R.drawable.bar_1_03).getWidth();// 获取图片宽度
	// DisplayMetrics dm = new DisplayMetrics();
	// getWindowManager().getDefaultDisplay().getMetrics(dm);
	// int screenW = dm.widthPixels;// 获取分辨率宽度
	// offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
	// Matrix matrix = new Matrix();
	// matrix.postTranslate(offset, 0);
	// imageView.setImageMatrix(matrix);// 设置动画初始位置
	// }

	// 替换fragment
	public void replaceFragment(View v) {

		Log.i("replace", "替换1");
		Log.i("replace", "替换2");
		// fragment2.getFragmentManager().beginTransaction().add(fragment4,
		// null).commit();

		Fragment FB = new ViewPagerFragmentListViewDetail();
		// ViewPagerFragmentListViewDetail FB = new
		// ViewPagerFragmentListViewDetail();
		// fragmentList.set(1, FB);
		// mViewPager.setCurrentItem(2);
		// Bundle args = new Bundle();
		// // args.putString("code", smenu.getCode());
		// args.putString("code", "123code");
		// args.putDouble("amount", 1);
		// FB.setArguments(args);

		// FragmentTransaction ft = getFragmentManager().beginTransaction();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

		// ft.hide(fragment2);
		// Bundle bundle = new Bundle();
		// bundle.putString("key", "12341234");
		// FB.setArguments(bundle);
		// ft.add(R.id.viewPager, FB);
		// ft.addToBackStack(null);
		// ft.show(fragment1);
		// ft.commit();

		// ft.replace(R.id.container, FB);

		ft.replace(fragment2.getId(), FB);
		// // ft.replace(R.id.left, FB);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}

	// 初始化标头
	private void initTextView() {
		textView1 = (TextView) findViewById(R.id.text1);
		textView2 = (TextView) findViewById(R.id.text2);
		textView3 = (TextView) findViewById(R.id.text3);

		textView1.setOnClickListener(new MyOnClickListener(0));
		textView2.setOnClickListener(new MyOnClickListener(1));
		textView3.setOnClickListener(new MyOnClickListener(2));
	}

	// 头标点击监听
	private class MyOnClickListener implements OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			if (3 != index) {
				mViewPager.setCurrentItem(index);
				setDefaultBackgroundColor(index);
			}
		}

	}

	public void initViewPager() {
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		// 设置每个tab对应的 fragment
		fragment1 = new ViewPagerFragment("@我的fragment", 1);
		fragmentList.add(fragment1);
		fragment2 = new ViewPagerFragmentListView();
		fragmentList.add(fragment2);
		Fragment fragment3 = new ViewPagerFragmentForeman();
		// Fragment fragment3 = new ViewPagerFragmentListViewDetail();
		fragmentList.add(fragment3);

		// fragment4 = new ViewPagerFragmentListViewDetail();
		//
		// fragmentList.add(fragment4);
		// titleList.add("title 1 ");
		// titleList.add("title 2 ");
		// titleList.add("title 3 ");

		// mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),
		// fragmentList, titleList));
		mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),
				fragmentList, null));
		mViewPager.setCurrentItem(0);
		textView1.setBackgroundColor(Color.parseColor("#7ba853"));
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	// 用于初始化和还原背景色
	private void setDefaultBackgroundColor(int index) {
		if (null != textView1)
			textView1.setBackgroundColor(Color.parseColor("#96cc68"));
		if (null != textView2)
			textView2.setBackgroundColor(Color.parseColor("#96cc68"));
		if (null != textView3)
			textView3.setBackgroundColor(Color.parseColor("#96cc68"));
		View tab = null;
		int flag = 0;
		switch (index) {
		case 0:
			tab = textView1;
			break;
		case 1:
			tab = textView2;
			break;
		case 2:
			tab = textView3;
			break;

		default:
			flag = 1;
			break;
		}
		if (1 != flag)
			tab.setBackgroundColor(Color.parseColor("#7ba853"));
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		// int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		// int two = one * 2;// 页卡1 -> 页卡3 偏移量
		// * 两种方法，这个是一种，下面还有一种，显然这个比较麻烦
		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {

			setDefaultBackgroundColor(arg0);
			/*
			 * Animation animation = null; switch (arg0) { case 0: if (currIndex
			 * == 1) { animation = new TranslateAnimation(one, 0, 0, 0); } else
			 * if (currIndex == 2) { animation = new TranslateAnimation(two, 0,
			 * 0, 0); } break; case 1: if (currIndex == 0) { animation = new
			 * TranslateAnimation(offset, one, 0, 0); } else if (currIndex == 2)
			 * { animation = new TranslateAnimation(two, one, 0, 0); } break;
			 * case 2: if (currIndex == 0) { animation = new
			 * TranslateAnimation(offset, two, 0, 0); } else if (currIndex == 1)
			 * { animation = new TranslateAnimation(one, two, 0, 0); } break;
			 * 
			 * } animation = new TranslateAnimation(one*currIndex, one*arg0, 0,
			 * 0);//显然这个比较简洁，只有一行代码。 currIndex = arg0;
			 * animation.setFillAfter(true);// True:图片停在动画结束位置
			 * animation.setDuration(300); imageView.startAnimation(animation);
			 * Toast.makeText(LjljActivity.this, "您选择了"+
			 * mViewPager.getCurrentItem()+"页卡", Toast.LENGTH_SHORT).show(); //
			 */
		}

	}

	/**
	 * 定义适配器
	 * 
	 */
	public class MyPagerAdapter extends FragmentPagerAdapter {

		private List<Fragment> fragmentList;
		private List<String> titleList;

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		public MyPagerAdapter(FragmentManager fm, List<Fragment> fragmentList,
				List<String> titleList) {
			super(fm);
			this.fragmentList = fragmentList;
			this.titleList = titleList;
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
		 * 每个页面的title
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return (titleList.size() > position) ? titleList.get(position) : "";
		}

		/**
		 * 页面的总个数
		 */
		@Override
		public int getCount() {
			return fragmentList == null ? 0 : fragmentList.size();
		}
	}
}
