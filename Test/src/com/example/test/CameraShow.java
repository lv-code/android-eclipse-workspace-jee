package com.example.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CameraShow extends Activity {


	private ImageView mImageView;
	private Button mButtonCamera;
	private Button mButtonPhoto;
	private Bitmap bm = null; //绘制从相册来的照片
	private TextView imgPath;
    //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
//    ContentResolver resolver = getContentResolver();
    private static String TAG = "myErr";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera_show);
		mImageView = (ImageView) this.findViewById(R.id.imageview_preview);
		mButtonCamera = (Button) this.findViewById(R.id.button_cameraButton);
		mButtonPhoto = (Button) this.findViewById(R.id.button_photoButton);
		imgPath = (TextView) this.findViewById(R.id.img_path);
		// 打开Camera
		mButtonCamera.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				intent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(new File(Environment
								.getExternalStorageDirectory(), "camera.jpg")));
				intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
				startActivityForResult(intent, 10);
			}
		});
		// 获取相册
		mButtonPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				intent.setType("image/*");
				intent.putExtra("crop", "true");
				intent.putExtra("aspectX", 1);
				intent.putExtra("aspectY", 1);
				intent.putExtra("outputX", 80);
				intent.putExtra("outputY", 80);
				intent.putExtra("return-data", true);
				startActivityForResult(intent, 11);
			}
		});
		chkSDStatus();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	private void chkSDStatus() {
		String sdStatus = Environment.getExternalStorageState();  
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用  
        	Toast.makeText(this, "SD card is not avaiable/writeable right now.", Toast.LENGTH_LONG).show();
            return;  
        } 
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 10 && resultCode == Activity.RESULT_OK && data.getExtras().get("data")!=null) {
			this.mImageView.setImageDrawable(Drawable.createFromPath(new File(
					Environment.getExternalStorageDirectory(), "camera.jpg")
					.getAbsolutePath()));
			System.out.println("data-->" + data);
//			saveImage(data);
		} else if (requestCode == 11 && resultCode == Activity.RESULT_OK) {
			System.out.println("data2-->" + data);
//			showImage(data);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void saveImage(Intent data) {
		Bundle bundle = data.getExtras();  
        Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式  
      
        FileOutputStream b = null;  
        //Environment.getExternalStorageDirectory().getPath() 
        File file = new File("/sdcard/Image/");  
        file.mkdirs();// 创建文件夹 
        new DateFormat();
        String name = DateFormat.format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA)) + ".jpg";
        String fileName = "/sdcard/Image/"+name;  

        try {  
            b = new FileOutputStream(fileName);  
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                b.flush();  
                b.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
	}
	/*
	private void showImage(Intent data) {
		 try {
	            Uri originalUri = data.getData();        //获得图片的uri 
	 
	            bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);       
	            //显得到bitmap图片
//	这里开始的第二部分，获取图片的路径：
	 
	            String[] proj = {MediaStore.Images.Media.DATA};
	 
	            //好像是android多媒体数据库的封装接口，具体的看Android文档
	            Cursor cursor = managedQuery(originalUri, proj, null, null, null); 
	            //按我个人理解 这个是获得用户选择的图片的索引值
	            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	            //将光标移至开头 ，这个很重要，不小心很容易引起越界
	            cursor.moveToFirst();
	            //最后根据索引值获取图片路径
	            String path = cursor.getString(column_index);
	            imgPath.setText(path);
	        }catch (IOException e) {
	            Log.e(TAG,e.toString()); 
	        }
	}
	*/
}