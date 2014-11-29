package com.test.main;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/*
 * Handler: 联系了Activity之间使用Bundle和Intent传值；
 */
public class MainActivity extends Activity {
	private static final String EXTRA_MESSAGE = null;
	TextView tv;
	Button btn;
	// ListView相关
	private static String[] strs;
	private static String[] desc;
	/*
	 * 为了去除警告：Class is a raw type. References to generic type Class<T> should be
	 * parameterized 把ArrayList<Class> 改为 ArrayList<Class<?>>，使用“泛型通配符”
	 */
	private static ArrayList<Class<?>> arraylist = new ArrayList<Class<?>>();

	private ListView lv;

	// end
	// 初始化自定义工具类
	// MyHelper myHelper = new MyHelper(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 启动activity时，不自动弹出软键盘
		MyHelper.setSoftInputMode(this);
		init();
		// initListView1();
		initListView2();
		
	}

	private void init() {
		strs = new String[] { "MediaRecorder录音测试", "android生命周期测试", "计时器", "相机、相册","9宫格布局", "Handler详细机制", "HttpClient联系", "解析XML",
				"侧边栏字母导航", "Fragment ViewPager碎片管理", "滑动菜单demo", "又上角的圆圈demo",
				"ListFragment实例", "Activity切换效果", "调用Camera和相册", "获取本机中所有图片",
				"ListView加载更多", "ListView分页","用GridView展示手机照片","发送语音","上拉菜单" };
		desc = new String[] { "MediaRecorder录音测试", "LifeCycle", "TimerTaskDemo", "网上的实例","GridView","第一个小例子完成，但是不理解意思呢？？？", "编写HttpClientTools",
				"从网上找个小例子练习", "从网上找的字母检索例子", "需要不断练习", "SlidingMenu从github",
				"badge from github", "网上例子", "新建res/anim",
				"需要配置AndroidManifext",
				"本示例演示如何在Android中使用加载器(Loader)来实现获取本机中的所有图片，并进行查看图片的效果。",
				"小demo", "用BaseAdapter","其中还有dialog的使用","send voice","pull up menu" };
		arraylist.add(AudioRecordTest.class);
		arraylist.add(LifeCycleTest.class);
		arraylist.add(TimerTaskDemo.class);
		arraylist.add(CameraAndAlbumTest.class);
		arraylist.add(GridViewDemo.class);
		arraylist.add(HandlerActivity.class);
		arraylist.add(HttpClientActivity.class);
		arraylist.add(ParseXML.class);
		arraylist.add(SideBarTest.class);
		arraylist.add(Switchfragment.class);
		arraylist.add(SlidingMenuDemo.class);
		arraylist.add(ViewBadgerTest.class);
		arraylist.add(ListFragmentDemo.class);
		arraylist.add(TransitionActivity.class);
		arraylist.add(CameraShow.class);
		arraylist.add(LoadMyDevicePhoto.class);
		arraylist.add(Loadmore.class);
		arraylist.add(ListMoreTest.class);
		arraylist.add(Thumbnail.class);
		arraylist.add(SendVoice.class);
		arraylist.add(PullUpActivity.class);
	}

	private void initListView2() {
		// 得到ListView对象的引用
		lv = (ListView) findViewById(R.id.lv);
		// 定义一个动态数组
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		// 在数组中存放数据
		int len = strs.length;
		for (int i = 0; i < len; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemTitle", "标题：" + strs[i]);
			map.put("itemText", "描述：" + desc[i]);
			// map.put("itemImage", R.drawable.xin);
			listItem.add(map);
		}

		SimpleAdapter mSimpleAdapter = new SimpleAdapter(this, listItem,
				R.layout.listview_item, new String[] { "itemTitle", "itemText",
						"itemImage" }, new int[] { R.id.itemTitle,
						R.id.itemText, R.id.itemImage });
		lv.setAdapter(mSimpleAdapter);
		lv.setOnItemClickListener(new MyOnItemClickListener());
	}

	class MyOnItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			intent.setClass(getApplicationContext(), arraylist.get(position));
			if (0 == position) {
				bundle.putString("from", "from MainActivity item 0 click");
			}
			intent.putExtras(bundle);

			startActivity(intent);
			setTitle("你点击了第" + position + "行");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 第一个参数为该子项属于哪个组
		// 第二个参数是该子项的唯一ID
		// 第三个参数是该子项在Menu菜单中显示的序号
		// 第四个参数为该子项的显示名称
		menu.add(0, 1, 1, "退出应用");
		menu.add(0, 2, 2, "关于我们");
		menu.add(0, 3, 3, "自定义标题栏");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			finish();
			break;
		case 2:
			tv.setText(item.getTitle());
			break;
		case 3:
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, ListView_Adapter.class);
			startActivity(intent);
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	/** Called when the user clicks the Send button */
	public void sendMessage(View view) {
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}

}
