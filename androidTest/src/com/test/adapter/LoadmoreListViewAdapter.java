package com.test.adapter;

import java.util.List;

import com.test.main.R;
import com.test.main.R.id;
import com.test.main.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
  
public class LoadmoreListViewAdapter extends BaseAdapter {  
    private List<String> items;  
    private LayoutInflater inflater;  
      
    public LoadmoreListViewAdapter(Context context, List<String> items) {  
        this.items = items;  
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);        
    }  
      
    @Override  
    public int getCount() {  
        return items.size();  
    }  
  
    @Override  
    public Object getItem(int position) {  
        return items.get(position);  
    }  
  
    @Override  
    public long getItemId(int position) {  
        return position;  
    }  
  
    @Override  
    public View getView(int position, View view, ViewGroup parent) {  
        if (view == null) {  
            view = inflater.inflate(R.layout.loadmore_list_item, null);  
        }  
        TextView text = (TextView) view.findViewById(R.id.list_item_text);  
        text.setText(items.get(position));  
        return view;  
    }  
      
    /** 
     * 添加列表项 
     * @param item 
     */  
    public void addItem(String item) {  
        items.add(item);  
    }  
}  