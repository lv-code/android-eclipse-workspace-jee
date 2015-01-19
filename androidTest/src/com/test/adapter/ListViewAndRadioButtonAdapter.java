package com.test.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.test.main.R;

public class ListViewAndRadioButtonAdapter extends BaseAdapter {

	private Context context;
	private List<String> userList;
	HashMap<String, Boolean> states = new HashMap<String, Boolean>();// 用于记录每个RadioButton的状态，并保证只可选一个

	public ListViewAndRadioButtonAdapter(Context context, List<String> userList) {
		this.context = context;
		this.userList = userList;
	}

	@Override
	public int getCount() {
		return userList.size();
	}

	@Override
	public Object getItem(int position) {
		return userList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.listview_radiobutton_main, null);
			holder = new ViewHolder();
			holder.background = (LinearLayout) convertView
					.findViewById(R.id.search_user_list_item);
			holder.userName = (TextView) convertView
					.findViewById(R.id.search_user_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final RadioButton radio = (RadioButton) convertView
				.findViewById(R.id.radio_btn);
		holder.rdBtn = radio;

		holder.userName.setText(userList.get(position));
		// 根据Item位置分配不同背景
		if (userList.size() > 0) {
			if (userList.size() == 1) {
				// holder.background.setBackgroundResource(R.drawable.more_item_press);
			} else {
				if (position == 0) {
					// holder.background.setBackgroundResource(R.drawable.more_itemtop_press);
				} else if (position == userList.size() - 1) {
					// holder.background.setBackgroundResource(R.drawable.more_itembottom_press);
				} else {
					// holder.background.setBackgroundResource(R.drawable.more_itemmiddle_press);
				}
			}
		}
		// 当RadioButton被选中时，将其状态记录进States中，并更新其他RadioButton的状态使它们不被选中
		holder.rdBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				// 重置，确保最多只有一项被选中
				for (String key : states.keySet()) {
					states.put(key, false);

				}
				states.put(String.valueOf(position), radio.isChecked());
				ListViewAndRadioButtonAdapter.this.notifyDataSetChanged();
			}
		});

		boolean res = false;
		if (states.get(String.valueOf(position)) == null
				|| states.get(String.valueOf(position)) == false) {
			res = false;
			states.put(String.valueOf(position), false);
		} else
			res = true;

		holder.rdBtn.setChecked(res);

		return convertView;
	}

	static class ViewHolder {
		LinearLayout background;
		TextView userName;
		RadioButton rdBtn;
	}

}
