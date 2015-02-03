package com.beta.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.beta.adapter.FriendListAdapter;
import com.beta.entity.FriendEntity;
import com.beta.mybase.MyBaseActivity;
import com.beta.mybase.MyBaseApplication;
import com.beta.xmpp.XmppFriendManager;

public class FriendListActivity extends MyBaseActivity {

	Context context = FriendListActivity.this;
	ListView lv;
	// 好友列表
	private List<FriendEntity> friends;
	// FriendList适配器
	private FriendListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CustomTitleBar.getTitleBar(FriendListActivity.this, "项目联系人");
		setContentView(R.layout.activity_friend_list);
		goBack();
		initViews();
		initListVew();
		//从XMPPServer获取好友列表
		initGroup();
	}
	
	public void initGroup() {
		   Roster roster = MyBaseApplication.xmppConnection.getRoster();  
           Collection<RosterGroup> entriesGroup = roster.getGroups();  
           Log.i("---in the initGroup ---", entriesGroup.toString()); 
           for(RosterGroup group: entriesGroup){  
               Collection<RosterEntry> entries = group.getEntries();  
               Log.i("---", group.getName());  
               for (RosterEntry entry : entries) {  
                   //Presence presence = roster.getPresence(entry.getUser());  
                   //Log.i("---", "user: "+entry.getUser());  
                   Log.i("---", "name: "+entry.getName());  
                   //Log.i("---", "tyep: "+entry.getType());  
                   //Log.i("---", "status: "+entry.getStatus());  
                   //Log.i("---", "groups: "+entry.getGroups());  
               }  
           } 
	}

	public void initViews() {
		lv = (ListView) findViewById(R.id.lvFriend);
	}

	public void initListVew() {
		XmppFriendManager xManager = XmppFriendManager.getInstance();
		friends = xManager.getFriends();
		
//		SimpleAdapter sa = new SimpleAdapter(context, getData(),
//				R.layout.friend_list_item, new String[] { "avatar", "job", "name" },
//				new int[] { R.id.ivAvatar, R.id.tvJob, R.id.tvName });
		
		adapter = new FriendListAdapter(context, friends);
		
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				FriendEntity userInfo = adapter.getFriends().get(position);
				Bundle bundle = new Bundle();
				bundle.putSerializable("user", userInfo);
				// 调用MyBaseActivity方法
				startActivity(ChatActivity.class, bundle);
				finish();
			}
		});
	}

	private ArrayList<Map<String, Object>> getData() {
		ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
		int[] avatar = {R.drawable.tmp_touxiang01, R.drawable.tmp_touxiang02};
//		String[] job = { "设计师", "工长" };
//		String[] name = { "王建国", "孙国庆" };
//		for (int i = 0; i < job.length; i++) {
//			HashMap<String, Object> item = new HashMap<String, Object>();
//			item.put("job", job[i]);
//			item.put("name", name[i]);
//			item.put("avatar", avatar[i]);
//			mData.add(item);
//		}
		
		for (FriendEntity friend : friends) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("job", friend.getIndustry());
			item.put("name", friend.getName());
			item.put("avatar", R.drawable.tmp_touxiang01);
			mData.add(item);
		}
		return mData;
	}
}
