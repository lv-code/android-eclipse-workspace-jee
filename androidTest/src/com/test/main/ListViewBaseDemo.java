package com.test.main;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewBaseDemo extends Activity implements View.OnClickListener {

	Context context = ListViewBaseDemo.this;

	private ArrayAdapter<String> mAdapter;
	private ArrayList<String> mList;

	private ListView mListView;
	private EditText mLanguageText;
	private Button mAddButton, mClearButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view_base_demo);
		initViewsById();
		initListView();
	}

	// 初始化组件
	private void initViewsById() {
		mListView = (ListView) this.findViewById(R.id.list);
		mLanguageText = (EditText) this.findViewById(R.id.addLangEdit);
		mAddButton = (Button) this.findViewById(R.id.addButton);
		mClearButton = (Button) this.findViewById(R.id.clearButton);
	}

	private void initListView() {
		// // array adapter created from string array resources
		// mAdapter = ArrayAdapter.createFromResource(this, R.array.language,
		// android.R.layout.simple_list_item_1);
		String[] mData = { "Java", "PHP", "Python" };

		mList = new ArrayList<String>();
		for (int i = 0; i < mData.length; i++) {
			mList.add(mData[i]);
		}
		mAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, mList);
		// // set the adapter
		mListView.setAdapter(mAdapter);

		// add listener
		mAddButton.setOnClickListener(this);
		mClearButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addButton:
			add();
			break;

		case R.id.clearButton:
			removeAll();
			break;

		default:
			break;
		}

	}

	private void removeAll() {
		mList.clear();
		mAdapter.notifyDataSetChanged();
	}

	private void add() {
			String text = mLanguageText.getText().toString();
			if (null == text || "".equals(text.trim())) {
				Toast.makeText(context, "输入不能为空", Toast.LENGTH_SHORT).show();
			} else {
				mAdapter.add(text);
				mAdapter.notifyDataSetChanged();
				mLanguageText.setText("");
			}
	}
}
