package com.example.test;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Button;

/*
 * Handler: 联系了Activity之间使用Bundle和Intent传值；
 */
public class MainActivity extends Activity {
	private static final String EXTRA_MESSAGE = null;
	TextView tv;
	Button btn;
	// ListView相关
	private static final String[] strs = new String[] { "Handler详细机制", "HttpClient联系",
			"解析XML", "侧边栏字母导航", "Fragment ViewPager碎片管理", "滑动菜单demo" };
	private static final String[] desc = new String[] {"第一个小例子完成，但是不理解意思呢？？？",
		"编写HttpClientTools","从网上找个小例子练习","从网上找的字母检索例子","需要不断练习", "SlidingMenu从github" };
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
//		initListView1();
		 initListView2();
	}

	private void init() {
		btn = (Button) findViewById(R.id.button1);

		MyButton listener = new MyButton();
		// btn.setOnClickListener(listener);

	}

	private void initListView1() {
		// 得到ListView对象的引用
		lv = (ListView) findViewById(R.id.lv);
		// 为ListView设置Adapter来绑定数据
		lv.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, strs));
		// lv.setAdapter(new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_checked, strs));
		// lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		// lv.setAdapter(new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_multiple_choice, strs));
		// lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		lv.setOnItemClickListener(new MyOnItemClickListener());
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
			map.put("itemImage", R.drawable.xin);
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
			switch (position) {
			case 0:
				intent.setClass(getApplicationContext(), HandlerActivity.class);
				bundle.putString("from", "from MainActivity item 0 click");
				break;
			case 1:
				intent.setClass(getApplicationContext(), HttpClientActivity.class);
				break;
			case 2:
				intent.setClass(getApplicationContext(), ParseXML.class);
				break;
			case 3:
				intent.setClass(getApplicationContext(), SideBarTest.class);
				break;
			case 4:
				intent.setClass(getApplicationContext(), Switchfragment.class);
				break;
			case 5:
				intent.setClass(getApplicationContext(), SlidingMenuDemo.class);
				break;
			default:
				break;
			}
			
			if (null == intent) 
				setTitle("你点击了第" + position + "行"); // 点击屏幕后显示第几行
			intent.putExtras(bundle);
			startActivity(intent);

		}
	}

	class MyButton implements OnClickListener {

		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, CustomTitleBarActivity.class);
			startActivity(intent);
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
