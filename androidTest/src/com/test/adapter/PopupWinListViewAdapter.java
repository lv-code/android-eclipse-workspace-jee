package com.test.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.main.R;
    
public class PopupWinListViewAdapter extends BaseAdapter {    
    private LayoutInflater mInflater;    
    private List<Map<String, Object>> mData;    
    public static Map<Integer, Boolean> isSelected;    
    
    public PopupWinListViewAdapter(Context context) {    
        mInflater = LayoutInflater.from(context);    
        init();    
    }    
    
    //初始化    
    private void init() {    
        mData=new ArrayList<Map<String, Object>>();    
        for (int i = 0; i < 5; i++) {    
            Map<String, Object> map = new HashMap<String, Object>();    
            map.put("img", R.drawable.icon);    
            map.put("title", "第" + (i + 1) + "行的标题");    
            mData.add(map);    
        }    
        //这儿定义isSelected这个map是记录每个listitem的状态，初始状态全部为false。    
        isSelected = new HashMap<Integer, Boolean>();    
        for (int i = 0; i < mData.size(); i++) {    
            isSelected.put(i, false);    
        }    
    }    
    
    @Override    
    public int getCount() {    
        return mData.size();    
    }    
    
    @Override    
    public Object getItem(int position) {    
        return null;    
    }    
    
    @Override    
    public long getItemId(int position) {    
        return 0;    
    }    
    
    @Override    
    public View getView(int position, View convertView, ViewGroup parent) {    
        ViewHolder holder = null;    
        //convertView为null的时候初始化convertView。    
        if (convertView == null) {    
            holder = new ViewHolder();    
            convertView = mInflater.inflate(R.layout.popup_win_listview_item, null);    
            holder.img = (ImageView) convertView.findViewById(R.id.img);    
            holder.title = (TextView) convertView.findViewById(R.id.title);    
            holder.cBox = (CheckBox) convertView.findViewById(R.id.cb);    
            convertView.setTag(holder);    
        } else {    
            holder = (ViewHolder) convertView.getTag();    
        }    
        holder.img.setBackgroundResource((Integer) mData.get(position).get(    
                "img"));    
        holder.title.setText(mData.get(position).get("title").toString());    
        holder.cBox.setChecked(isSelected.get(position));    
        return convertView;    
    }    
    
    public final class ViewHolder {    
        public ImageView img;    
        public TextView title;    
        public CheckBox cBox;    
    }    
}    