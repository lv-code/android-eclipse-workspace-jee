package com.beta.jiazhuang.adapter;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beta.jiazhuang.entity.OneFriendEntity;
import com.beta.jiazhuang.util.CustomConst;
import com.beta.main.R;

/**
 * @author zg
 * 
 * 好友列表项适配器
 *
 */
public class FriendListAdapter extends BaseListViewAdapter{
	
	//聊天联系人组
	public List<OneFriendEntity> mFriendList;
	
	public FriendListAdapter(Context context,List<OneFriendEntity> mFriendList){
		super(context, mFriendList);
		this.context = context;
		OneFriendEntity [] arr = mFriendList.toArray(new OneFriendEntity[mFriendList.size()]);
		// 将好友按照拼音排序
//		Arrays.sort(arr, new PinYinComparator());
		this.mFriendList = (List<OneFriendEntity>)Arrays.asList(arr);
	}
	
	public List<OneFriendEntity> getFriends(){
		return mFriendList;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder = null;
		OneFriendEntity mOneFriendEntity = mFriendList.get(position);
		
		if(view == null){
			view = LayoutInflater.from(context).inflate(R.layout.friend_list_item, null);
			holder = new ViewHolder();
			view.setTag(holder);
			holder.avatar = (ImageView)view.findViewById(R.id.ivAvatar);
			holder.job = (TextView)view.findViewById(R.id.tvJob);
			holder.name = (TextView)view.findViewById(R.id.tvName);
			holder.tv_msgState = (TextView)view.findViewById(R.id.tv_usr_sex);
			holder.tv_msgState.setTextColor(0xff000000);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.avatar.setBackgroundResource(R.drawable.tmp_touxiang01);
		holder.name.setText(mOneFriendEntity.getName());
		holder.job.setText(mOneFriendEntity.getIndustry());

		if(mOneFriendEntity.getMsgNotReadCount()> 0 && mOneFriendEntity.getMsgNotReadCount()< 100){
			holder.tv_msgState.setTextColor(0xffffffff);
			holder.tv_msgState.setBackgroundResource(R.drawable.chatmsg_not_read_background);
			holder.tv_msgState.setText(mOneFriendEntity.getMsgNotReadCount()+"");
			
		}else{
			holder.tv_msgState.setBackgroundColor(0x00000000);;
			holder.tv_msgState.setText("");
		}
		
		return view;
	}
	
	@Override
	public int getCount() {
		return mFriendList.size();
	}

	@Override
	public Object getItem(int position) {
		return mFriendList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	private String setStateStr(int code){
		
		String state = "隐身";
		
		switch (code) {
		//设置在线
		case CustomConst.USER_STATE_ONLINE:
			state = "在线";
			break;
		//Q我吧
		case CustomConst.USER_STATE_Q_ME:
			state = "Q我";
			break;
		//忙碌
		case CustomConst.USER_STATE_BUSY:
			state = "忙碌";
			break;
		//离开	
		case CustomConst.USER_STATE_AWAY:
			state = "离开";
			break;
			
		case CustomConst.USER_STATE_OFFLINE:
			state = "下线";
			break;
			
		}
		return state;
	}
	
	class ViewHolder{
		
		 ImageView avatar;
		 TextView job;
		 TextView name;
		 //未读消息数
		 TextView tv_msgState;
	}
	
	@Override
	protected void initImageFetcher() {
		
//		imageFetcher = new ImageFetcher(context, 60 ,60);
//		imageFetcher.setImageCache(CacheUtils.getImageCache(context, "imageCache"));
//		imageFetcher.setLoadingImage(R.drawable.position_selecter);
//		imageFetcher.setImageFadeIn(true);
		
	}
	
}
