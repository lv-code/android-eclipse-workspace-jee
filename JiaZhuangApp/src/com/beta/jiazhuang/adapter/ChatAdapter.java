package com.beta.jiazhuang.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.beta.jiazhuang.bitmap.cache.CacheUtils;
import com.beta.jiazhuang.bitmap.cache.ImageFetcher;
import com.beta.jiazhuang.dao.MessageDAO;
import com.beta.jiazhuang.entity.CommonMessage;
import com.beta.jiazhuang.main.ChatCommonMessage;
import com.beta.jiazhuang.main.MsgEume.MSG_DERATION;
import com.beta.jiazhuang.main.MsgEume.MSG_STATE;
import com.beta.jiazhuang.mybase.MyBaseApplication;
import com.beta.jiazhuang.util.CustomConst;
import com.beta.jiazhuang.xmpp.MXmppConnManager;
import com.beta.main.R;

public class ChatAdapter extends BaseAdapter {
	
	private Context context;
	private List<CommonMessage> messages;
	private MessageDAO messageDAO;
	private String uid;
	private ImageFetcher mImageFetcher;
	
	public ChatAdapter(Context context,List<CommonMessage> messages){
		
		messageDAO = (MessageDAO)MyBaseApplication.getInstance().dabatases.get(CustomConst.DAO_MESSAGE);
		uid = MXmppConnManager.hostUid;
		this.context = context;
		this.messages = messages;
		initImageFetcher();
	}
	
	@Override
	public int getCount() {
		return messages.size();
	}

	@Override
	public Object getItem(int position) {
		
		CommonMessage msgs = messages.get(position);
		MSG_STATE state = msgs.getState();
		
		if(!state.equals(MSG_STATE.READED) &&
		   !state.equals(MSG_STATE.RECEIVEING) && 
		   !state.equals(MSG_STATE.SENDDING) && 
		   msgs.getMsgComeFromType().equals(MSG_DERATION.RECEIVE)){
			messageDAO.updateById(msgs.getId(),uid,1);
		}
		return msgs;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		CommonMessage msg = (CommonMessage)getItem(position);
		ChatCommonMessage commonMessage = ChatCommonMessage.getInstance(msg, context, mImageFetcher);
		commonMessage.fillContent();
		View view = commonMessage.getRootView();
		return view;
	}

	protected void initImageFetcher() {
		
		mImageFetcher = new ImageFetcher(context, 40 ,40);
		mImageFetcher.setImageCache(CacheUtils.getImageCache(context, "imageCache/"));
		mImageFetcher.setLoadingImage(R.drawable.people_icon_selector);
		mImageFetcher.setImageFadeIn(true);
		
	}
	
}
