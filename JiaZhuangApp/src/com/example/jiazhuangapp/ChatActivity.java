package com.example.jiazhuangapp;

import static android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import com.example.Util.RecordUtil;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

public class ChatActivity extends Activity implements OnClickListener {

	private boolean isBig = false;

	private Button mBtnSend;
	private Button mBtnBack;
	private ImageView mImgAddition;
	private ImageView mImgAlbum;
	private ImageView mImgCamera;
	private ImageView mImgMic;

	private RelativeLayout mLayout;
	private LinearLayout mPanelAddition;
	private RelativeLayout chatVoicePanel;
	private EditText mEditTextContent;
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

	private static final String PATH = "/sdcard/SendVoice/Record/";// 录音存储路径
	private String mRecordPath;// 录音的存储名称
	
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
		mListView.setEmptyView(findViewById(R.id.lv_empty));
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
		mBtnBack = (Button) findViewById(R.id.head_TitleBackBtn);
		mBtnBack.setOnClickListener(this);

		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
		mImgAddition = (ImageView) findViewById(R.id.btn_addition_menu);
		mLayout = (RelativeLayout) findViewById(R.id.rl_bottom);
		mImgAlbum = (ImageView) findViewById(R.id.chat_phone_album);
		mImgCamera = (ImageView) findViewById(R.id.chat_camera);

		mPanelAddition = (LinearLayout) findViewById(R.id.chat_panel_addition);

		mImgMic = (ImageView) findViewById(R.id.chat_microphone);

		chatVoicePanel = (RelativeLayout) findViewById(R.id.chat_voicepanel);
		microhandler = (ImageView) findViewById(R.id.chat_microhandler);
	}

	private void initListener() {

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mListView.requestFocus();
				mEditTextContent.clearFocus();
			}

		});

		mImgAddition.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				togglePanelAddition();
				// TODO 重新加载Adapter，让ListView在RelativeLayout上面
				// LayoutParams vgLayoutParams = (LayoutParams) new
				// RelativeLayout.LayoutParams();
				// vgLayoutParams. = "";
				// mListView.setLayoutParams(params);
				mAdapter.notifyDataSetChanged();
				mListView.setSelection(mListView.getCount() - 1);
			}
		});

		mImgAlbum.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
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

			}
		});

		mImgCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				photoUri = getContentResolver().insert(EXTERNAL_CONTENT_URI,
						new ContentValues());
				intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
				startActivityForResult(intent, TAKE_PHOTO);
			}
		});

		mImgMic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				togglePanleVoice();
			}
		});

		mEditTextContent.setOnFocusChangeListener(new OnFocusChangeListener() {

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
		mListView.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				Toast.makeText(getApplicationContext(), "ListView focuse",
						Toast.LENGTH_SHORT).show();
				mListView.requestFocus();
				mEditTextContent.clearFocus();
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
						mRecordPath = PATH + UUID.randomUUID().toString()
								+ ".amr";
						// 实例化录音工具类
						mRecordUtil = new RecordUtil(mRecordPath);
					}
					try {
						// 开始录音
						mRecordUtil.start();
					} catch (IOException e) {
						// 区别于System.out.println(e);
						e.printStackTrace();
					}
					
					break;
					
					// 停止录音
				case MotionEvent.ACTION_UP:
					if (mRecord_State == RECORD_ING) {
						// 修改录音状态
						mRecord_State = RECORD_ED;
						try {
							// 停止录音
							mRecordUtil.stop();
							// 初始录音音量
							mRecord_Volume = 0;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					break;
				return false;
			}
		});
		chkSDStatus();
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
		 * imm.hideSoftInputFromWindow(mEditTextContent.getWindowToken(), 0); //
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

	// 对分辨率较大的图片进行缩放
	public Bitmap zoomBitmap(Bitmap bitmap, float width, float height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
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
		mListView.setAdapter(mAdapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_send:
			send();
			mBtnSend.setVisibility(View.INVISIBLE);
			mBtnSendVisible = 0;
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
			entity.setName(MY_NAME);
			entity.setMsgType(false);
			entity.setText(contString);
			entity.setImgBitmap(imgBitmap);

			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();
			mEditTextContent.setText("");
			mListView.setSelection(mListView.getCount() - 1);
		}

		if (imgBitmap != null) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(getDate());
			entity.setName(MY_NAME);
			entity.setMsgType(false);
			entity.setText("");
			entity.setImgBitmap(imgBitmap);

			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();
			mEditTextContent.setText("");
			mListView.setSelection(mListView.getCount() - 1);
			System.out.println("ListView Count is : " + mListView.getCount());
			imgBitmap = null;
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
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}