package com.beta.jiazhuang.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.beta.jiazhuang.adapter.FriendListAdapter;
import com.beta.jiazhuang.daoService.ChattPeopleService;
import com.beta.jiazhuang.entity.OneFriendEntity;
import com.beta.jiazhuang.mybase.MyBaseActivity;
import com.beta.jiazhuang.mybase.MyBaseApplication;
import com.beta.jiazhuang.util.CustomConst;
import com.beta.jiazhuang.xmpp.MXmppConnManager;
import com.beta.jiazhuang.xmpp.XmppFriendManager;
import com.beta.main.R;

public class FriendListActivity extends MyBaseActivity {

	//聊天后台服务
	private ChattPeopleService chattPeopleService;
	//好友列表
	private List<OneFriendEntity> mFriendList;
	// FriendList适配器
	private FriendListAdapter adapter;
	
	private String hostUid = MXmppConnManager.hostUid;
	
	Context context = FriendListActivity.this;
	ListView lv;
	// 单个好友列表

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CustomTitleBar.getTitleBar(FriendListActivity.this, "项目联系人");
		setContentView(R.layout.activity_friend_list);
		goBack();

		///初始化数据库
		if(MyBaseApplication.xmppConnection != null){
			MyBaseApplication.getInstance().dbHelper.CreateSelfTable(MyBaseApplication.xmppConnection.getUser().split("@")[0]);
		}
		chattPeopleService = new ChattPeopleService();
		
		//从XMPPServer获取好友列表
		XmppFriendManager xManager = XmppFriendManager.getInstance();
		mFriendList = xManager.getFriends();
		//初始化 chattingPeoples
//		resumeAction();
		
		initViews();
		initListVew();

		MyBaseApplication.putHandler("FriendListActivity", handler);
	}
	
	// 用来处理MReceiveChatListener发送的Message
	Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			//取得这个消息表明在聊天窗口，更新聊天记录
			if (msg.what == CustomConst.HANDLER_FRIEND_LIST_UPDATE){
				
				String uid = (String)msg.obj;
				adapter.notifyDataSetChanged();
				
				new Toast(context).makeText(context, "正在 更新聊天记录ing", Toast.LENGTH_LONG).show();
			}
		}
	};
	
	public void initViews() {
		lv = (ListView) findViewById(R.id.lvFriend);
	}

	public void initListVew() {
		
//		SimpleAdapter sa = new SimpleAdapter(context, getData(),
//				R.layout.friend_list_item, new String[] { "avatar", "job", "name" },
//				new int[] { R.id.ivAvatar, R.id.tvJob, R.id.tvName });
		
		adapter = new FriendListAdapter(context, mFriendList);
		
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				OneFriendEntity userInfo = adapter.getFriends().get(position);
				Bundle bundle = new Bundle();
				bundle.putSerializable("user", userInfo);
				// 调用MyBaseActivity方法
				startActivity(ChatActivity.class, bundle);
			}
		});
	}

	@Override
	protected void onResume() {
		resumeAction();
		super.onResume();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		MyBaseApplication.removeHandler("FriendListActivity",handler);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode==KeyEvent.KEYCODE_BACK){
			moveTaskToBack(true);
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private List<String>  getUids(){
		
		List<String> uids = new ArrayList<String>();
		for(OneFriendEntity cByPeople : mFriendList){
			uids.add(cByPeople.getUid());
		}
		return uids;
	}
	
	private void resumeAction(){
		
		List<String> uids = getUids();
		String user = MyBaseApplication.xmppConnection.getUser();
		mFriendList = chattPeopleService.findAll(uids,user);
		
	}
	
	public void refreshSession(String uid){
		
		for(OneFriendEntity people : adapter.getFriends()){
			
			if(people.getUid().equals(uid)){
				adapter.getFriends().remove(people);
				people = chattPeopleService.findByUid(uid,hostUid);
				adapter.getFriends().add(0,people);
				
				break;
			}
		}
	}
	
	private ArrayList<Map<String, Object>> getData() {
		ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
		
		for (OneFriendEntity friend : mFriendList) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("job", friend.getIndustry());
			item.put("name", friend.getName());
			item.put("avatar", R.drawable.tmp_touxiang01);
			mData.add(item);
		}
		return mData;
	}
}
