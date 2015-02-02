package com.beta.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beta.main.R;

public class UserCollectListViewAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater mInflater;
	private List<UserCollectMsgEntity> mData;
	//条目类型，默认为 文字
	private static final int WORD = 1, IMG = 2, VOICE = 3;
	
	public UserCollectListViewAdapter(Context context, List<UserCollectMsgEntity> mData) {
		super();
		// 初始化
		mInflater = LayoutInflater.from(context);
		this.mData = mData;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = null;
		viewHolderWord viewHolderWord = null;
		viewHolderImg viewHolderImg = null;
		viewHolderVoice viewHolderVoice = null;
		
	    if (convertView == null)
	    {
	    	UserCollectMsgEntity userCollectMsgEntity = mData.get(position);
	    	switch (userCollectMsgEntity.getType()) {
			case WORD:
				convertView = (RelativeLayout) mInflater.inflate(R.layout.activity_user_collect_list_item_word, null);
				viewHolderWord = new viewHolderWord();
				viewHolderWord.ivAvatar = (ImageView) convertView.findViewById(R.id.ivAvatar);
				viewHolderWord.tvWord = (TextView) convertView.findViewById(R.id.tvWord);
				viewHolderWord.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
				
				viewHolderWord.ivAvatar.setImageDrawable(context.getResources().getDrawable(R.drawable.tmp_touxiang01));
				viewHolderWord.tvWord.setText(userCollectMsgEntity.getContent().toString());
				viewHolderWord.tvDate.setText(userCollectMsgEntity.getDate().toString());
				convertView.setTag(viewHolderWord);
				break;
			case IMG:
				convertView = mInflater.inflate(R.layout.activity_user_collect_list_item_img, null);
				viewHolderImg = new viewHolderImg();
				viewHolderImg.ivAvatar = (ImageView) convertView.findViewById(R.id.ivAvatar);
				viewHolderImg.ivImg = (ImageView) convertView.findViewById(R.id.ivImg);
				viewHolderImg.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
				
				viewHolderImg.ivAvatar.setImageDrawable(context.getResources().getDrawable(R.drawable.tmp_touxiang01));
				viewHolderImg.ivImg.setImageDrawable(context.getResources().getDrawable(R.drawable.collect_zhanwei01));
				viewHolderImg.tvDate.setText(userCollectMsgEntity.getDate().toString());
				convertView.setTag(viewHolderImg);
				break;
			case VOICE:
				convertView = mInflater.inflate(R.layout.activity_user_collect_list_item_voice, null);
				viewHolderVoice = new viewHolderVoice();
				viewHolderVoice.ivAvatar = (ImageView) convertView.findViewById(R.id.ivAvatar);
				viewHolderVoice.tvVoice = (TextView) convertView.findViewById(R.id.tvVoice);
				viewHolderVoice.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
				
				viewHolderVoice.ivAvatar.setImageDrawable(context.getResources().getDrawable(R.drawable.tmp_touxiang01));
				viewHolderVoice.tvVoice.setText(userCollectMsgEntity.getContent().toString());
				viewHolderVoice.tvDate.setText(userCollectMsgEntity.getDate().toString());
				convertView.setTag(viewHolderVoice);
				break;
			}
	    }

		// 点击变色
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 此处怎么写
			}
		});
	    
	    return convertView;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class viewHolderBase{
		ImageView ivAvatar;
		TextView tvDate;
	}
	//各个布局的控件资源  
	class viewHolderWord extends viewHolderBase{ 
	    TextView tvWord;  
	}  
	class viewHolderImg extends viewHolderBase{  
		ImageView ivImg;  
	}  
	class viewHolderVoice extends viewHolderBase{  
		//语音秒数
	    TextView tvVoice;  
	} 

}
