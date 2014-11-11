package com.beta.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.jiazhuangapp.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ViewPagerFragmentForeman extends Fragment {
	
	private ListView listView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.view_pager_fragment_designer, null);
		listView = (ListView) view.findViewById(R.id.lv);
//		init();
		initListView2();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	private void init() {
		List<String> items = new ArrayList<String>();
		for (int i = 0; i < 2; i++) {
			if(i == 0){
			items.add("课程表");
			}else{
			items.add("考试");
			}

		}

	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), 
			android.R.layout.simple_list_item_1, items);
	listView.setAdapter(adapter);
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
				map.put("itemImage", R.drawable.open_source_figure);
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
