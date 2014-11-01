package com.example.jiazhuangapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class ChatActivity extends Activity implements OnClickListener {

	private Button mBtnSend;
	private Button mBtnBack;
	private ImageView mImgAddition;
	private RelativeLayout mLayout;
	private EditText mEditTextContent;
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CustomTitleBar.getTitleBar(this, "和他（她）聊天");
		setContentView(R.layout.activity_chat);
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		initView();
		initListener();
		initData();
	}

	public void initView() {
		mListView = (ListView) findViewById(R.id.listview);
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
		mBtnBack = (Button) findViewById(R.id.head_TitleBackBtn);
		mBtnBack.setOnClickListener(this);

		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
		mImgAddition = (ImageView) findViewById(R.id.btn_addition_menu);
		mLayout = (RelativeLayout) findViewById(R.id.rl_bottom);

	}

	private void initListener() {
		
		mImgAddition.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				MyHelper.setSoftInputMode(ChatActivity.this);
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
				imm.hideSoftInputFromWindow(mEditTextContent.getWindowToken(), 0); //强制隐藏键盘  
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLayout
						.getLayoutParams();
				int a = 0;
				if (layoutParams.bottomMargin < 0) {
					layoutParams.bottomMargin = 0;
					a = 0;
				} else {
					layoutParams.bottomMargin = -158;
					a = -158;
				}
				Log.i("tag1", "a = "+a);
				// TODO 做动画，让位置变化更流畅 .animate()
				ObjectAnimator.ofInt((View)mLayout, "bottomMargin", a).setDuration(100).start();
//				mLayout.setLayoutParams(layoutParams);
			}
		});
	}

	/*
	 * "即使是一块牛肉，也应该有自己的态度，要慎其独，要善其身，要知道精彩的人生不能只有精肉，还要有适宜的肥油做调配，有雪白的肉筋做环绕，并且还要掌握跳进煎锅时的角度，姿势，以及火候，才能最终成为一块优秀道地的西冷牛排~"
	 * , "不开心睡一觉，就让他过去吧，伤心还好，伤胃就不好了。", "要懂得珍惜守护身边的每个人，因为前世扭断脖子的回眸，我们才换来了今生的相遇；",
	 * "我们可以慢慢向前走，但绝对不能后退。永远不要后退，退到最后是无路可退；", "感谢身材，即使臃肿，我也能到世界各地去旅游；",
	 * "感谢那些曾让我伤心难过的日子，我知道快乐已经离我不远了；", "蓬莱（pénglái）文章建安骨"
	 */
	private String[] msgArray = new String[] { "我的志愿是", "做一名校长",
			"每天收集了学生的学费之后就去吃火锅", "今天吃麻辣火锅", "明天吃酸菜鱼火锅", "后天吃猪骨头火锅", "老师直夸我：",
			"麦兜你终于找到生命的真谛了！", };

	private String[] dataArray = new String[] { "2012-09-01 18:00",
			"2012-09-01 18:10", "2012-09-01 18:11", "2012-09-01 18:20",
			"2012-09-01 18:30", "2012-09-01 18:35", "2012-09-01 18:40",
			"2012-09-01 18:50" };
	private final static int COUNT = 8;

	public void initData() {
		for (int i = 0; i < COUNT; i++) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(dataArray[i]);
			if (i % 2 == 0) {
				entity.setName("麦兜");
				entity.setMsgType(true);
			} else {
				entity.setName("麦太");
				entity.setMsgType(false);
			}

			entity.setText(msgArray[i]);
			mDataArrays.add(entity);
		}

		mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_send:
			send();
			break;
		case R.id.head_TitleBackBtn:
			finish();
			break;
		}
	}

	private void send() {
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(getDate());
			entity.setName("人马");
			entity.setMsgType(false);
			entity.setText(contString);

			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();
			mEditTextContent.setText("");
			mListView.setSelection(mListView.getCount() - 1);
		}
	}

	private String getDate() {
		Calendar c = Calendar.getInstance();

		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH));
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));

		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
				+ mins);

		return sbBuffer.toString();
	}

	public void head_xiaohei(View v) { // 标题栏 返回按钮
		// Intent intent = new Intent (ChatActivity.this,InfoXiaohei.class);
		// startActivity(intent);
	}
}