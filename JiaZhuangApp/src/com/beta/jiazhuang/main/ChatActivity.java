package com.beta.jiazhuang.main;

import java.io.IOException;
import java.lang.ref.SoftReference;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.jiazhuang.adapter.ChatAdapter;
import com.beta.jiazhuang.bitmap.cache.CacheUtils;
import com.beta.jiazhuang.bitmap.cache.ImageResizer;
import com.beta.jiazhuang.dao.MessageDAO;
import com.beta.jiazhuang.entity.CommonMessage;
import com.beta.jiazhuang.entity.OneFriendEntity;
import com.beta.jiazhuang.main.MsgEume.MSG_CONTENT_TYPE;
import com.beta.jiazhuang.main.MsgEume.MSG_DERATION;
import com.beta.jiazhuang.main.MsgEume.MSG_STATE;
import com.beta.jiazhuang.mybase.MyBaseApplication;
import com.beta.jiazhuang.util.CustomConst;
import com.beta.jiazhuang.util.FileUtils;
import com.beta.jiazhuang.util.PictureViewUtils;
import com.beta.jiazhuang.util.ToastUtils;
import com.beta.jiazhuang.util.TypeConverter;
import com.beta.jiazhuang.view.CommonChatListView;
import com.beta.jiazhuang.view.EmotionEditText;
import com.beta.jiazhuang.xmpp.MChatManager;
import com.beta.jiazhuang.xmpp.MXmppConnManager;
import com.beta.main.R;

public class ChatActivity extends MyBaseChatActivity implements OnRefreshListener {

	private SwipeRefreshLayout mRfLayout;
	
	private CommonMessage message;
	private MessageDAO messageDAO;

	private OneFriendEntity userInfo;
	private Chat mChat;
	private MChatManager mChatManager;
	
	private int page = 1;
	private int maxPage = 1;
	private long sendRowid = 0;
	
	private String hostUid = MXmppConnManager.hostUid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		// 启动activity时不自动弹出软键盘
//		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		// 获取从上面获取来的用户数据
		Bundle bundle = getIntent().getExtras();
		userInfo = (OneFriendEntity) bundle.getSerializable("user");
		CustomTitleBar.getTitleBar(this, "和"+userInfo.getIndustry()+"："+userInfo.getName()+" 聊天");
//		getActionBar().setTitle("与 " + userInfo.getName() + " 聊天中");
		mChatManager = new MChatManager(MXmppConnManager.getInstance().getChatManager());
		mChat = mChatManager.createChat(userInfo.getUid(),MXmppConnManager.getInstance().getChatMListener().new MsgProcessListener());
		messageDAO = (MessageDAO)MyBaseApplication.getInstance().dabatases.get(CustomConst.DAO_MESSAGE);
		setContentView(R.layout.activity_chat);
		// 定义当前好友的消息页数
		if(MyBaseApplication.mUsrChatMsgPage.get(userInfo.getUid())== null){
			MyBaseApplication.mUsrChatMsgPage.put(userInfo.getUid(), 1);
		}else{
			page = MyBaseApplication.mUsrChatMsgPage.get(userInfo.getUid());
		}
		maxPage = messageDAO.getMaxPage(userInfo.getUid(),CustomConst.MESSAGE_PAGESIZE,hostUid);
		//获取与该用户的消息
		messages.addAll(messageDAO.
				findMessageByUid(1,CustomConst.MESSAGE_PAGESIZE*page, userInfo.getUid().trim(),hostUid));
		
		MyBaseApplication.putHandler(userInfo.getUid() , handler);
		
		goBack();		
		initViews();
		initEvents();
		refreshAdapter();
	}

	public void initViews() {
		//表情多媒体布局
		mLayoutEmotionMedia = (FrameLayout)findViewById(R.id.fl_emotion_media);
		mLoPlusBarPic = (View)findViewById(R.id.include_chat_plus_pic);
		mLoPlusBarCarema = (View)findViewById(R.id.include_chat_plus_camera);
		mIvPlusBarPic = (ImageView)mLoPlusBarPic.findViewById(R.id.iv_chat_plus_image);
		mIvPlusBarCarema = (ImageView)mLoPlusBarCarema.findViewById(R.id.iv_chat_plus_image);
//		mIvPlusBarLocation = (ImageView)mLoPlusBarLocation.findViewById(R.id.iv_chat_plus_image);
		
		mTvPlusBarPic = (TextView)findViewById(R.id.include_chat_plus_pic).findViewById(R.id.tv_chat_plus_description);
		mTvPlusBarCarema = (TextView)findViewById(R.id.include_chat_plus_camera).findViewById(R.id.tv_chat_plus_description);
		mTvPlusBarLocation = (TextView)findViewById(R.id.include_chat_plus_location).findViewById(R.id.tv_chat_plus_description);
		
		mTvPlusBarCarema.setText("拍照");
		mTvPlusBarPic.setText("图片");
		mTvPlusBarLocation.setText("名片");
		
		mRfLayout = (SwipeRefreshLayout)findViewById(R.id.srfl_chat);
		mRfLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
		mBtnMsgSend = (Button) findViewById(R.id.btn_chat_send);
		mEmtMsg = (EmotionEditText) findViewById(R.id.et_chat_msg);
		//开启多媒体按钮
		mIvMedia = (ImageView)findViewById(R.id.iv_chat_media);
		//消息列表ListView
		mLvCommonMsg = (CommonChatListView) findViewById(R.id.lv_chat);
		mGvEmotion = (GridView)findViewById(R.id.gv_chat_biaoqing);
		mLayoutEmotionMedia.setVisibility(View.GONE);
		mLayoutEmotion = (LinearLayout)findViewById(R.id.ll_chat_face);
		mLayoutMedia = (LinearLayout)findViewById(R.id.ll_chat_plusbar);
		loadImage();
		
		mAdapter = new ChatAdapter(this, messages);
		mLvCommonMsg.setAdapter(mAdapter);
		//表情
//		mGvEmotion.setAdapter(new FaceGridViewAdapter(this));
		
	}

	//通过图片得到对应缓存应用栏图标的图片
	private Bitmap getBitmap(String key,int res){
		
		SoftReference<Bitmap> soft = MyBaseApplication.getInstance().mSendbarCache.get(key);
		Bitmap bitmap = null;
		if(soft == null){
			bitmap = ImageResizer.decodeSampledBitmapFromResource(getResources(), res, 50, 50);
			soft = new SoftReference<Bitmap>(bitmap);
			MyBaseApplication.getInstance().mSendbarCache.put(key,soft);
		}else{
			bitmap = soft.get();
		}
		return bitmap;
	}

	/**
	 * 加载多媒体框内的图片
	 */
	private void loadImage() {
		//添加多媒体窗口内的多媒体图表--图片
		Bitmap bitmap = getBitmap(getString(R.string.plusbar_pic),R.drawable.ic_chat_plusbar_pic_normal);
		mIvPlusBarPic.setImageBitmap(bitmap);
		
		//添加多媒体窗口内的多媒体图表--拍照
		bitmap = getBitmap(getString(R.string.plusbar_camera),R.drawable.ic_chat_plusbar_camera_normal);
		mIvPlusBarCarema.setImageBitmap(bitmap);
		
		//添加多媒体窗口内的多媒体图表--名片
		bitmap = getBitmap(getString(R.string.plusbar_card),R.drawable.ic_chat_plusbar_card_normal);
//		mIvPlusBarCard.setImageBitmap(bitmap);
		
	}
	
	protected void initEvents() {
		mRfLayout.setOnRefreshListener(this);
		mBtnMsgSend.setOnClickListener(this);
		mEmtMsg.setOnTouchListener(this);
		mLvCommonMsg.setOnTouchListener(this);
		mGvEmotion.setOnItemClickListener(this);
		mIvMedia.setOnClickListener(this);
		mLoPlusBarPic.setOnClickListener(this);
		mLoPlusBarCarema.setOnClickListener(this);
//		mLoPlusBarCard.setOnClickListener(this);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		
		if(requestCode == CustomConst.MEDIA_CODE_PICTURE && resultCode == RESULT_OK){
				
			String path = FileUtils.getPictureSelectedPath(intent, this);
			String newPath = CacheUtils.getImagePath(mApplication, "sendImage/" + TypeConverter.getUUID() + ".pilin");
			try {
			Bitmap bitmap = ImageResizer.decodeSampledBitmapFromFile(path, 400, 800);
			FileUtils.compressAndWriteFile(bitmap, mApplication, newPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			long mills = System.currentTimeMillis();
			
			message = new CommonMessage(
						userInfo.getUid().trim(),
						"nearby_people_other",
						mills,
						"0.12km-FromChatAct",
						newPath,
						MSG_STATE.SENDDING,
						MSG_CONTENT_TYPE.IMAGE,
						MSG_DERATION.SEND,
						userInfo.getName()
						) ;
				
			sendRowid = messageDAO.save(message, hostUid);
			messages.add(message);
			new SendFileThread(mills,sendRowid,newPath, userInfo.getUid(),CustomConst.FILETYPE_IMAGE).start();
			refreshAdapter();
		}else if(resultCode == CustomConst.MEDIA_CODE_CAMERA && resultCode == RESULT_OK){
			Bundle bundle = intent.getExtras();
			Bitmap bitmap = (Bitmap)bundle.get("data");
		}
	}
	
	private void initListener() {
		
		mLvCommonMsg.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mLvCommonMsg.requestFocus();
				mEmtMsg.clearFocus();
			}

		});
		
	}

	// 对分辨率较大的图片进行缩放
	public Bitmap zoomBitmap(Bitmap bitmap, float width, float height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = (width / w);
		float scaleHeight = (height / h);
		matrix.postScale(scaleWidth, scaleHeight);// 利用矩阵进行缩放不会造成内存溢出
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

	private String[] msgArray = new String[] { "我的志愿是", "做一名校长",
			"每天收集了学生的学费之后就去吃火锅", "今天吃麻辣火锅", "明天吃酸菜鱼火锅", "后天吃猪骨头火锅", "老师直夸我：",
			"麦兜你终于找到生命的真谛了！", };

	private String[] dataArray = new String[] { "2012-09-01 18:00",
			"2012-09-01 18:10", "2012-09-01 18:11", "2012-09-01 18:20",
			"2012-09-01 18:30", "2012-09-01 18:35", "2012-09-01 18:40",
			"2012-09-01 18:50" };
	private final static int COUNT = 8;

	/*
	public void initData() {
		for (int i = 0; i < COUNT; i++) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(dataArray[i]);
			if (i % 2 == 0) {
				entity.setName(MY_NAME);
				entity.setMsgType(true);
			} else {
				entity.setName("麦太");
				entity.setMsgType(false);
			}

			entity.setText(msgArray[i]);
			mDataArrays.add(entity);
		}

//		mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
//		mLvCommonMsg.setAdapter(mAdapter);
	}*/

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_chat_media:
			/*
			 * 如果素材库框显示
			 */
			if(mLayoutEmotionMedia.isShown()){
				/*
				 * 如果表情框是可视的，这时只需跳到多媒体框来显示
				 * 否则就证明当前打开的是多媒体框，就隐藏整个素材库;
				 */
				if(mLayoutEmotion.isShown()){
					mLayoutEmotion.setVisibility(View.GONE);
					mLayoutMedia.setVisibility(View.VISIBLE);
				}else{
					mLayoutEmotionMedia.setVisibility(View.GONE);
				}
			/*
			 * 如果素材库当前不显示
			 * 无论怎样，先把表情框隐藏，然后设置	
			 * 多媒体框为显示
			 */
			}else{
				mLayoutEmotion.setVisibility(View.GONE);
				mLvCommonMsg.setSelection(messages.size() - 1);
				mInputManager.hideSoftInputFromWindow(mEmtMsg.getWindowToken(),
						0);
				mLayoutEmotionMedia.setVisibility(View.VISIBLE);
				mLayoutMedia.setVisibility(View.VISIBLE);
			}
			
			break;
			
		//发送消息
		case R.id.btn_chat_send:
			send();
			break;
			
		case R.id.include_chat_plus_pic:
			Intent intent = PictureViewUtils.getPictureIntent();
			startActivityForResult(intent, CustomConst.MEDIA_CODE_PICTURE);
			break;
		}

	}
	
	//向XMPPServer openfire发送消息
	private void send() {
		String contString = mEmtMsg.getText().toString();
		String prevMsg;
		
		if ("".equals(contString)) {
			Toast.makeText(this, "先输入信息", Toast.LENGTH_SHORT).show();
		} else {
			prevMsg = mEmtMsg.getText().toString();
			mEmtMsg.setText("");
			try {
				mChat.sendMessage(prevMsg);
			} catch (XMPPException e) {
				e.printStackTrace();
			}
			
			message = new CommonMessage(
					userInfo.getUid().trim(),
					"nearby_people_other",
					System.currentTimeMillis(),
					"0.12km",
					prevMsg,
					MSG_STATE.ARRIVED,
					MSG_CONTENT_TYPE.TEXT,
					MSG_DERATION.SEND,
					userInfo.getName()
					) ;
			
			messageDAO.save(message,hostUid);
			messages.add(message);
			refreshAdapter();
		}
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.et_chat_msg:
			if (event.getAction() == MotionEvent.ACTION_UP) {
				if (mLayoutEmotionMedia.isShown()) {
					mLayoutEmotionMedia.setVisibility(View.GONE);
				}
			}
			Log.i("------>","1111111 -------");
			break;

		case R.id.lv_chat:
			if (event.getAction() == MotionEvent.ACTION_UP) {
				if (mLayoutEmotionMedia.isShown()) {
					mLayoutEmotionMedia.setVisibility(View.GONE);
				}
				mInputManager.hideSoftInputFromWindow(
						mEmtMsg.getWindowToken(), 0);
			}
			break;
		}

		return false;
	}

	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			
			switch(msg.what){
				case CustomConst.HANDLER_MGS_ADD:
					Long rowid = (Long)msg.obj;
					message = messageDAO.findByRownum(rowid,hostUid);
					messages.add(message);
					refreshAdapter();
					break;
				
				case CustomConst.HANDLER_MSG_FILE_SUCCESS:
					Long mills = (Long)msg.obj;
					updateMsgByMills(mills);
					refreshAdapter();
					break;
			}
		};
	};
	
	private void updateMsgByMills(long mills){
		
		for(int i=0;i<messages.size();i++){
			CommonMessage msg = messages.get(i);
			if(msg.getTime() == mills){
				msg.setState(MSG_STATE.ARRIVED);
				break;
			}
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long mills) {
		
		String text = MyBaseApplication.mEmotions_Zme.get(position);
		if(!TextUtils.isEmpty(text)){
			int start = mEmtMsg.getSelectionStart();
			CharSequence content = mEmtMsg.getText().insert(start, text);
			mEmtMsg.setText(content);
			//定位光标
			CharSequence info = mEmtMsg.getText();
			if(info instanceof Spannable){
				Spannable spanText = (Spannable)info;
				Selection.setSelection(spanText, start + text.length());
			}
		}
	}
	
	@Override
	public void onRefresh() {
		
		page = MyBaseApplication.mUsrChatMsgPage.get(userInfo.getUid()) + 1;
		
		if(page <= maxPage){
			MyBaseApplication.mUsrChatMsgPage.put(userInfo.getUid(), page);
			messages.addAll(0,messageDAO.
					findMessageByUid(page, CustomConst.MESSAGE_PAGESIZE, userInfo.getUid().trim(),hostUid));
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					mAdapter.notifyDataSetChanged();
					mRfLayout.setRefreshing(false);
				}
			}, 2000);
			
		}else{
			ToastUtils.createCenterNormalToast(this, "已经刷新到最后", Toast.LENGTH_SHORT);
			mRfLayout.setRefreshing(false);
		}
		
	}
	
	public void refreshAdapter() {
		mAdapter.notifyDataSetChanged();
		mLvCommonMsg.setSelection(messages.size()-1);
	}
	
	@Override
	protected void onDestroy() {
		messageDAO.closeDB();
		MyBaseApplication.removeHandler(userInfo.getUid(),handler);
		super.onDestroy();
	}

	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

//	@Override
//	protected void onPause() {
//		super.onPause();
//
//		try {
//			if(null!=mRecordUtil) mRecordUtil.stop();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	

	/**
	 * 发送文件线程
	 * @author WHF
	 *
	 */
	class SendFileThread  extends Thread{
		
		private String file;
		private String userid;
		private String type;
		private long rowid;
		private long mills;
		
		public  SendFileThread (long mills,long rowid,String file,String userid,String type){
			this.file = file;
			this.userid = userid;
			this.type = type;
			this.rowid = rowid;
			this.mills = mills;
		}
		
		@Override
		public synchronized void run() {
			
			MXmppConnManager.getInstance().sendFile(mills,rowid,type,file, handler, userid);
			
		}
	}

}