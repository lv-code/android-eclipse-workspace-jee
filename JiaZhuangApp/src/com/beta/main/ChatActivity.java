package com.beta.main;

import static android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beta.adapter.ChatAdapter;
import com.beta.adapter.ChatMsgViewAdapter;
import com.beta.entity.CommonMessage;
import com.beta.entity.FriendEntity;
import com.beta.main.MsgEume.MSG_CONTENT_TYPE;
import com.beta.main.MsgEume.MSG_DERATION;
import com.beta.main.MsgEume.MSG_STATE;
import com.beta.util.RecordUtil;
import com.beta.view.CommonChatListView;
import com.beta.view.EmotionEditText;
import com.beta.xmpp.MChatManager;
import com.beta.xmpp.MXmppConnManager;
import com.whf.pilin.R;

public class ChatActivity extends MyBaseChatActivity {

	private SwipeRefreshLayout mRfLayout;
	
	private CommonMessage message;
	private Button mBtnSend;

	private LinearLayout mPanelAddition;
	private RelativeLayout chatVoicePanel;
	private EditText mEmtMsg;
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();

	// 从相册选择照片后，裁剪保存的路径
	private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg"; // temp
																					// file
	private static final int SHOW_ALBUM = 11;
	private Uri imageUri = Uri.parse(IMAGE_FILE_LOCATION);// The Uri to store
	private Bitmap imgBitmap; // 获取发送的图片转化为Bitmap

	private Uri photoUri;
	private final static int TAKE_PHOTO = 10;
	private final static String PHOTO_URI = "photoUri";

	private final String MY_NAME = "麦兜";

	private int mVoicePanelVisible = 0;
	private int mLayoutVisible = 0;
	private int mBtnSendVisible = 0;

	private ImageView microhandler;
	private MediaPlayer mMediaPlayer;
	private RecordUtil mRecordUtil;

	private static final int RECORD_NO = 0; // 不在录音
	private static final int RECORD_ING = 1; // 正在录音
	private static final int RECORD_ED = 2; // 完成录音
	private int mRecord_State = 0; // 录音的状态
	private double mRecord_Volume;// 麦克风获取的音量值

	// 录音存储路径
	private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/jiaZhuangApp/";
	private String mRecordPath;// 录音的存储名称
	
	private FriendEntity userInfo;
	private Chat mChat;
	private MChatManager mChatManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CustomTitleBar.getTitleBar(this, "和他（她）聊天");
		setContentView(R.layout.activity_chat);
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		goBack();
		initViews();
		initListener();
		initData();
		// 获取从上面获取来的用户数据
		Bundle bundle = getIntent().getExtras();
		userInfo = (FriendEntity) bundle.getSerializable("user");
		mChatManager = new MChatManager(MXmppConnManager.getInstance().getChatManager());
		Log.d("--->userInfo----->", userInfo.toString());
		mChat = mChatManager.createChat(userInfo.getUid(),MXmppConnManager.getInstance().getChatMListener().new MsgProcessListener());
	}

	public void initViews() {
		//表情多媒体布局
//		mLayoutEmotionMedia = (FrameLayout)findViewById(R.id.fl_emotion_media);
		mLoPlusBarPic = (View)findViewById(R.id.include_chat_plus_pic);
		mLoPlusBarCarema = (View)findViewById(R.id.include_chat_plus_camera);
		mIvPlusBarPic = (ImageView)mLoPlusBarPic.findViewById(R.id.iv_chat_plus_image);
		mIvPlusBarCarema = (ImageView)mLoPlusBarCarema.findViewById(R.id.iv_chat_plus_image);
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
		mIvEmotion = (ImageView) findViewById(R.id.iv_chat_biaoqing);
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
		
		mRfLayout = (SwipeRefreshLayout)findViewById(R.id.srfl_chat);
		
		
		mBtnSend = (Button) findViewById(R.id.btnChatSend);
		mBtnSend.setOnClickListener(this);

		mEmtMsg = (EditText) findViewById(R.id.et_chat_msg);
	}

	private void initListener() {

		mEmtMsg.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (mBtnSendVisible == 0 && hasFocus) {
					mBtnSend.setVisibility(View.VISIBLE);
					mBtnSendVisible = 1;
				} else {
					mBtnSend.setVisibility(View.INVISIBLE);
					mBtnSendVisible = 0;
				}

			}
		});
//		mListView.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				Toast.makeText(getApplicationContext(), "ListView focuse",
//						Toast.LENGTH_SHORT).show();
//				mListView.requestFocus();
//				mEmtMsg.clearFocus();
//			}
//		});
		
		mLvCommonMsg.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mLvCommonMsg.requestFocus();
				mEmtMsg.clearFocus();
			}

		});
		
		microhandler.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (mRecord_State != RECORD_ING) {
						// 修改录音状态
						mRecord_State = RECORD_ING;
						// 设置录音保存路径
						mRecordPath = PATH + UUID.randomUUID().toString() + ".3gp";
						Log.d("debug", "mRecordPaht:  " + mRecordPath);
						// 实例化录音工具类
						 mRecordUtil = new RecordUtil(mRecordPath);
						Log.i("info", "start");
						try {
							// 开始录音
							mRecordUtil.start();
						} catch (IOException e) {
							// 区别于System.out.println(e);
							e.printStackTrace();
						}
						return true;
					}
					// 停止录音
				case MotionEvent.ACTION_UP:
					if (mRecord_State == RECORD_ING) {
						// 修改录音状态
						mRecord_State = RECORD_ED;
						Log.i("info", "end");
						try {
							// 停止录音
							mRecordUtil.stop();
							// 初始录音音量
							mRecord_Volume = 0;
						} catch (IOException e) {
							e.printStackTrace();
						}
						// 显示提醒
						Toast.makeText(ChatActivity.this, "录音成功",
								Toast.LENGTH_SHORT).show();
						sendVoice();
					}
					v.performClick();

				}
				return true;
			}
		});
		chkSDStatus();
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
		
		//添加多媒体窗口内的多媒体图表--位置
		bitmap = getBitmap(getString(R.string.plusbar_location),R.drawable.ic_chat_plusbar_location_normal);
		mIvPlusBarLocation.setImageBitmap(bitmap);
		
	}
	
	private void togglePanleVoice() {

		if (mVoicePanelVisible == 0) {
			chatVoicePanel.setVisibility(View.VISIBLE);
			mVoicePanelVisible = 1;
		} else {
			chatVoicePanel.setVisibility(View.GONE);
			mVoicePanelVisible = 0;
			// togglePanelAddition();
		}
	}

	private void togglePanelAddition() {
		if (mLayoutVisible == 0) {
			mPanelAddition.setVisibility(View.VISIBLE);
			mLayoutVisible = 1;
			chatVoicePanel.setVisibility(View.GONE);
		} else {
			mPanelAddition.setVisibility(View.GONE);
			mLayoutVisible = 0;
		}
		/*
		 * // MyHelper.setSoftInputMode(ChatActivity.this); InputMethodManager
		 * imm = (InputMethodManager)
		 * getSystemService(Context.INPUT_METHOD_SERVICE);
		 * imm.hideSoftInputFromWindow(mEmtMsg.getWindowToken(), 0); //
		 * 强制隐藏键盘 RelativeLayout.LayoutParams layoutParams =
		 * (RelativeLayout.LayoutParams) mLayout .getLayoutParams(); int a = 0;
		 * if (layoutParams.bottomMargin < 0) { layoutParams.bottomMargin = 0; a
		 * = 0; } else { layoutParams.bottomMargin = -158; a = -158; } // TODO
		 * 做动画，让位置变化更流畅 .animate() // ObjectAnimator.ofInt((View)mLayout,
		 * "bottomMargin", // a).setDuration(100).start();
		 * mLayout.setLayoutParams(layoutParams);
		 */
	}

	private void chkSDStatus() {
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
			Toast.makeText(this,
					"SD card is not avaiable/writeable right now.",
					Toast.LENGTH_LONG).show();
			return;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case SHOW_ALBUM:

				/*
				 * 用 intent.putExtra("crop", "true"); 会产生缩略图保存。
				 * 不能用data.getData()获取数据
				 */
				// ImageView imageView = (ImageView)
				// findViewById(R.id.imageView1);
				if (imageUri != null) {
					imgBitmap = decodeUriAsBitmap(imageUri);// decode bitmap
					// imageView.setImageBitmap(imgBitmap);
				}
				send();
				togglePanelAddition();

				break;
			case TAKE_PHOTO:
				try {
					InputStream stream = getContentResolver().openInputStream(
							photoUri);
					if (photoUri != null) {
						imgBitmap = BitmapFactory.decodeStream(stream);
					}
					send();
					togglePanelAddition();
				} catch (FileNotFoundException e) {
					Log.e("TitlePageActivity", "FileNotFound", e);
				}
				break;
			default:
				System.out.println("data33333-->" + data);
				break;
			}
			System.out.println("data2-->" + data);
			// showImage(data);
		}
	}

	private Bitmap decodeUriAsBitmap(Uri uri) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getContentResolver()
					.openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
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

	/*
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
				entity.setName(MY_NAME);
				entity.setMsgType(true);
			} else {
				entity.setName("麦太");
				entity.setMsgType(false);
			}

			entity.setText(msgArray[i]);
			mDataArrays.add(entity);
		}

		mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mLvCommonMsg.setAdapter(mAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//发送消息
		case R.id.btn_send:
			send();
			mBtnSend.setVisibility(View.INVISIBLE);
			mBtnSendVisible = 0;
			break;
			
		case R.id.btn_addition_menu:
			togglePanelAddition();
			mAdapter.notifyDataSetChanged();
			mLvCommonMsg.setSelection(mLvCommonMsg.getCount() - 1);
			break;
			
		case R.id.chat_phone_album:
			// Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			Intent intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			// intent.addCategory(Intent.CATEGORY_OPENABLE);
			intent.setType("image/*");
			intent.putExtra("crop", "true");
			intent.putExtra("aspectX", 2);
			intent.putExtra("aspectY", 2);
			intent.putExtra("outputX", 280);
			intent.putExtra("outputY", 280);
			intent.putExtra("return-data", true);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			intent.putExtra("outputFormat",
					Bitmap.CompressFormat.JPEG.toString());
			intent.putExtra("noFaceDetection", true); // no face detection
			startActivityForResult(intent, SHOW_ALBUM);
			break;
		case R.id.chat_camera:
			Intent intent2 = new Intent("android.media.action.IMAGE_CAPTURE");
			photoUri = getContentResolver().insert(EXTERNAL_CONTENT_URI,
					new ContentValues());
			intent2.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
			startActivityForResult(intent2, TAKE_PHOTO);
			break;
		case R.id.chat_microphone:
			togglePanleVoice();
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
			
//			messageDAO.save(message,hostUid);
			messages.add(message);
			refreshAdapter();
		}
//		
//		if (contString.length() > 0) {
//			ChatMsgEntity entity = new ChatMsgEntity();
//			entity.setDate(getDate());
//			entity.setName(MY_NAME);
//			entity.setMsgType(false);
//			entity.setText(contString);
//			entity.setImgBitmap(imgBitmap);
//
//			mDataArrays.add(entity);
//			mAdapter.notifyDataSetChanged();
//			mEmtMsg.setText("");
//			mListView.setSelection(mListView.getCount() - 1);
//		}
//
//		if (imgBitmap != null) {
//			ChatMsgEntity entity = new ChatMsgEntity();
//			entity.setDate(getDate());
//			entity.setName(MY_NAME);
//			entity.setMsgType(false);
//			entity.setText("");
//			entity.setImgBitmap(imgBitmap);
//
//			mDataArrays.add(entity);
//			mAdapter.notifyDataSetChanged();
//			mEmtMsg.setText("");
//			mListView.setSelection(mListView.getCount() - 1);
//			System.out.println("ListView Count is : " + mListView.getCount());
//			imgBitmap = null;
//		}
		
	}

	private void sendVoice() {
		ChatMsgEntity entity = new ChatMsgEntity();
		entity.setDate(getDate());
		entity.setName(MY_NAME);
		entity.setMsgType(false);
		entity.setText("");
		Bitmap bmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.globle_player_btn_play);
		entity.setImgBitmap(bmp);

		mDataArrays.add(entity);
		mAdapter.notifyDataSetChanged();
		mEmtMsg.setText("");
		mListView.setSelection(mListView.getCount() - 1);
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

	@Override
	public boolean onTouch(View v, MotionEvent event) {
//
//		switch (v.getId()) {
//		case R.id.et_chat_msg:
//			if (event.getAction() == MotionEvent.ACTION_UP) {
//				if (mLayoutEmotionMedia.isShown()) {
//					mLayoutEmotionMedia.setVisibility(View.GONE);
//				}
//			}
//			break;
//
//		case R.id.lv_chat:
//			if (event.getAction() == MotionEvent.ACTION_UP) {
//				if (mLayoutEmotionMedia.isShown()) {
//					mLayoutEmotionMedia.setVisibility(View.GONE);
//				}
//				mInputManager.hideSoftInputFromWindow(
//						mEmtMsg.getWindowToken(), 0);
//			}
//			break;
//		}

		return false;
	}
//
//	Handler handler = new Handler() {
//
//		public void handleMessage(android.os.Message msg) {
//			
//			switch(msg.what){
//				case CustomConst.HANDLER_MGS_ADD:
////					long rowid = (long)msg.obj;
//					Long rowid = (Long)msg.obj;
//					message = messageDAO.findByRownum(rowid,hostUid);
//					messages.add(message);
//					refreshAdapter();
//					break;
//				
//				case CustomConst.HANDLER_MSG_FILE_SUCCESS:
//					Long mills = (Long)msg.obj;
//					updateMsgByMills(mills);
//					refreshAdapter();
//					break;
//			}
//		};
//	};
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long mills) {
		
//		String text = PilinApplication.mEmotions_Zme.get(position);
//		if(!TextUtils.isEmpty(text)){
//			int start = mEmtMsg.getSelectionStart();
//			CharSequence content = mEmtMsg.getText().insert(start, text);
//			mEmtMsg.setText(content);
//			//定位光标
//			CharSequence info = mEmtMsg.getText();
//			if(info instanceof Spannable){
//				Spannable spanText = (Spannable)info;
//				Selection.setSelection(spanText, start + text.length());
//			}
//		}
	}
	public void refreshAdapter() {
		mAdapter.notifyDataSetChanged();
		mLvCommonMsg.setSelection(messages.size()-1);
	}
	
	@Override
	protected void onDestroy() {
//		messageDAO.closeDB();
//		PilinApplication.removeHandler(userInfo.getUid(),handler);
		super.onDestroy();
	}

	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onPause() {
		super.onPause();

		try {
			if(null!=mRecordUtil) mRecordUtil.stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

}