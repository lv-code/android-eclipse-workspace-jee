package com.example.jiazhuangapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

public class ViewPagerFragmentListViewTest extends ListFragment {
	
	private ListView listView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.view_pager_fragment_designer, null);
		listView = (ListView) view.findViewById(R.id.lv);
//		Button btn = new Button(getActivity());
//		//设置按钮的宽度和高度
//		RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams (100,40);  
//		
//		btn.setLayoutParams(null);
//		btn.setText("Text");
//		btn.setBackgroundColor(0Xffff00);
//		
//		getActivity().addContentView(btn, btParams);
//		
//		init();
		initListView2();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
//		FragementDetails frag = (FragementDetails) getSupportFragmentManager()  
//                .findFragmentById(R.id.);
		
//		Fragment frag = new ViewPagerFragmentListViewDetail();
	}

	private void initListView2() {
			//得到ListView对象的引用
//		listView = (ListView) findViewById(R.id.lv);
			//定义一个动态数组
			ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
			//在数组中存放数据
			for(int i=0; i<10; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("itemTitle", "第"+i+"行");
				map.put("itemText", "第"+i+"行内容");
				map.put("itemImage", R.drawable.touxiang);
				listItem.add(map);
			}
			SimpleAdapter mSimpleAdapter = new SimpleAdapter(getActivity(), 
					listItem, 
					R.layout.listview_item, 
					new String[] {"itemTitle","itemText","itemImage"}, 
					new int[] {R.id.itemTitle, R.id.itemText, R.id.itemImage});
//			SimpleAdapter mSimpleAdapter = new SimpleAdapter(this, 
//					listItem, 
//					R.menu.listview_item, 
//					new String[] {"itemTitle","itemText","itemImage"}, 
//					new int[] {R.id.itemTitle, R.id.itemText, R.id.itemImage});
			listView.setAdapter(mSimpleAdapter);
			listView.setOnItemClickListener(new MyOnItemClickListener());
		}

		class MyOnItemClickListener implements OnItemClickListener {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 点击屏幕后显示第几行
				getActivity().setTitle("你点击了第"+position+"行");
			}
		}
	

}
