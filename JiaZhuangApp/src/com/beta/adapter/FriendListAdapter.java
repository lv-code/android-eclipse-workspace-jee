package com.beta.adapter;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beta.entity.FriendEntity;
import com.beta.main.R;
import com.beta.util.CustomConst;

/**
 * 好友列表项适配器
 * @author WHF 2014-03-24
 *
 */
public class FriendListAdapter extends BaseListViewAdapter{
	
	private List<FriendEntity> mFriends;
	
	public FriendListAdapter(Context context,List<FriendEntity> mFriends){
		super(context, mFriends);
		this.context = context;
		FriendEntity [] arr = mFriends.toArray(new FriendEntity[mFriends.size()]);
		// 将好友按照拼音排序
//		Arrays.sort(arr, new PinYinComparator());
		this.mFriends = (List<FriendEntity>)Arrays.asList(arr);
	}
	
	public List<FriendEntity> getFriends(){
		return mFriends;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder = null;
		if(view == null){
			view = LayoutInflater.from(context).inflate(R.layout.friend_list_item, null);
			holder = new ViewHolder();
			view.setTag(holder);
			holder.avatar = (ImageView)view.findViewById(R.id.ivAvatar);
			holder.job = (TextView)view.findViewById(R.id.tvJob);
			holder.name = (TextView)view.findViewById(R.id.tvName);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.avatar.setBackgroundResource(R.drawable.tmp_touxiang01);
		holder.name.setText(mFriends.get(position).getName());
		holder.job.setText(mFriends.get(position).getIndustry());
		
		return view;
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
	}

	@Override
	protected void initImageFetcher() {
		
//		imageFetcher = new ImageFetcher(context, 60 ,60);
//		imageFetcher.setImageCache(CacheUtils.getImageCache(context, "imageCache"));
//		imageFetcher.setLoadingImage(R.drawable.position_selecter);
//		imageFetcher.setImageFadeIn(true);
		
	}
	
}
