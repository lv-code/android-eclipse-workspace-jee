package com.test.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import com.test.adapter.ListViewAndRadioButtonAdapter;

public class ListViewAndRadioButton extends Activity {

	List<String> userList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_radiobutton_main);
		
		ListView listview = (ListView) this.findViewById(R.id.listView1);
		
		userList = new ArrayList<String>();
		userList.add("David");
		
		ListViewAndRadioButtonAdapter adapter = new ListViewAndRadioButtonAdapter(this, userList);  
		listview.setAdapter(adapter);  
		listview.setVisibility(View.VISIBLE);  
		setListViewHeightBasedOnChildren(listview);  
	}

	public void setListViewHeightBasedOnChildren(ListView listView) {  
		  
	    Adapter listAdapter = listView.getAdapter();  
	  
	    if (listAdapter == null) {  
	        return;  
	    }  
	  
	    int totalHeight = 0;  
	    int viewCount = listAdapter.getCount();  
	    for (int i = 0; i < viewCount; i++) {  
	        View listItem = listAdapter.getView(i, null, listView);  
	        listItem.measure(0, 0);  
	        totalHeight += listItem.getMeasuredHeight();  
	    }  
	  
	    ViewGroup.LayoutParams params = listView.getLayoutParams();  
	  
	    params.height = totalHeight  
	            + (listView.getDividerHeight() * (listAdapter.getCount()-1)) + 10;//加10是为了适配自定义背景  
	  
	    listView.setLayoutParams(params);  
	}  
	
}
