package com.test.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.test.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ListView_Adapter extends Activity {
	private static final String[] strs = new String[]{
		"first","second","third","fourth","fifth"
	};
	private ListView lv_arrayAdapter;
	
	private String[] mListTitle = { "姓名", "性别", "年龄", "居住地","邮箱"};
	private String[] mListStr = { "雨松MOMO", "男", "25", "北京",  
    "xuanyusong@gmail.com" }; 
	private ListView lv_simpleAdapter;
	ArrayList<Map<String,Object>> mData= new ArrayList<Map<String,Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_adapter);
		//用ArrayAdapter适配器填充数据的ListView start
//		lv_arrayAdapter = (ListView) findViewById(R.id.lv);
//		lv_arrayAdapter.setAdapter(new ArrayAdapter<String>(this,
//				android.R.layout.simple_list_item_1, strs));
//		lv_arrayAdapter.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub
//				int index = position+1;
//				setTitle("你点击了第"+index+"行");
//			}
//        });	
//		//end
		lv_simpleAdapter = (ListView) findViewById(R.id.lv_simpleAdapter);
		int length = mListTitle.length;
		for(int i =0; i < length; i++) {  
	        Map<String,Object> item = new HashMap<String,Object>(); 
	        item.put("image", android.R.drawable.ic_btn_speak_now);
	        item.put("title", mListTitle[i]);  
	        item.put("text", mListStr[i]);  
	        mData.add(item);   
	    }  

		
//	    SimpleAdapter adapter = new SimpleAdapter(this,mData,android.R.layout.simple_list_item_2,  
//		        new String[]{"title","text"},new int[]{android.R.id.text1,android.R.id.text2}); 
	    SimpleAdapter adapter = new SimpleAdapter(this,mData,R.layout.iconlist,  
	            new String[]{"image","title","text"},new int[]{R.id.image,R.id.title,R.id.text});   
	        //lv_simpleAdapter.setListAdapter(adapter);
	    	lv_simpleAdapter.setAdapter(adapter);
	        lv_simpleAdapter.setOnItemClickListener(new OnItemClickListener() {  
	        @Override  
	        public void onItemClick(AdapterView<?> adapterView, View view, int position,  
	            long id) {  
	        Toast.makeText(ListView_Adapter.this,"您选择了标题：" + mListTitle[position] + "内容："+mListStr[position], Toast.LENGTH_LONG).show();  
	        }  
	    });  
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_view__adapter, menu);
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
