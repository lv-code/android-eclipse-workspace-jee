package com.test.main;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.test.adapter.PopupWinListViewAdapter;
import com.test.adapter.PopupWinListViewAdapter.ViewHolder;

/**
 * @author zg
 * 
 *         全屏显示一个页面，将这个页面背景设置成半透明，菜单控件显示在屏幕下方，即可完成popupwindow菜单，并实现了父窗口遮罩
 *
 */
public class PopupWinDemo extends Activity {

	public PopupWindow window;
	public Button submit, cancel, myButton1;
	public ListView list;
	public ViewHolder vHollder;

	OnItemClickListener listClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			ViewHolder vHollder = (ViewHolder) view.getTag();
			// 在每次获取点击的item时将对于的checkbox状态改变，同时修改map的值。
			vHollder.cBox.toggle();
			PopupWinListViewAdapter.isSelected.put(position,
					vHollder.cBox.isChecked());
		}
	};

	OnClickListener submitListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// 这儿可以写提交数据的代码。
			closeWindow();
		}
	};

	OnClickListener cancelListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			closeWindow();
		}
	};

	OnClickListener bPopListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			popAwindow(v);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_popup_win_demo);

		Button myButton1 = (Button) findViewById(R.id.myButton1);
		myButton1.setOnClickListener(bPopListener);
	}

	private int getTitleBarHeight() {
		Rect frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int contentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT)
				.getTop();
		// statusBarHeight是上面所求的状态栏的高度
		int titleBarHeight = contentTop - statusBarHeight;
		System.out
				.print("titleBarHeight------------------ : " + titleBarHeight);
		return titleBarHeight;
	}

	private void popAwindow(View parent) {
		if (window == null) {
			LayoutInflater lay = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = lay.inflate(R.layout.popup_win_list, null);

			// 初始化按钮
			submit = (Button) v.findViewById(R.id.submit);
			submit.setOnClickListener(submitListener);
			cancel = (Button) v.findViewById(R.id.cancel);
			cancel.setOnClickListener(cancelListener);

			LinearLayout listLay = (LinearLayout) v.findViewById(R.id.listLay);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT);
			params.setMargins(0, getTitleBarHeight(), 0, 0);
			listLay.setLayoutParams(params);

			// 初始化listview，加载数据。
			list = (ListView) v.findViewById(R.id.lv);
			PopupWinListViewAdapter adapter = new PopupWinListViewAdapter(
					PopupWinDemo.this);
			list.setAdapter(adapter);
			list.setItemsCanFocus(false);
			list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			list.setOnItemClickListener(listClickListener);
			// ViewGroup.LayoutParams.MATCH_PARENT,
			// ViewGroup.LayoutParams.MATCH_PARENT
			window = new PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
		}

		// 设置整个popupwindow的样式。
		// window.setBackgroundDrawable(getResources().getDrawable(
		// R.drawable.rounded_corners_pop));
		// 使窗口里面的空间显示其相应的效果，比较点击button时背景颜色改变。
		// 如果为false点击相关的空间表面上没有反应，但事件是可以监听到的。
		// listview的话就没有了作用。
		window.setFocusable(true);
		window.update();
		window.showAtLocation(parent, Gravity.CENTER_VERTICAL, 0, 0);
	}

	private void closeWindow() {
		// 将每个checkbox的标记改为false，以便下次弹出window时是初始的状态。
		for (int j = 0; j < PopupWinListViewAdapter.isSelected.size(); j++) {
			PopupWinListViewAdapter.isSelected.put(j, false);
			ViewHolder vHollder = (ViewHolder) list.getChildAt(j).getTag();
			vHollder.cBox.setChecked(false);
		}
		// 提交数据时关闭popupwindow。
		if (window != null) {
			window.dismiss();
		}
	}

}
