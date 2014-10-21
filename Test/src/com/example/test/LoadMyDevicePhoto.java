package com.example.test;

import android.app.Dialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;

/**
 * Android实现获取本机中所有图片
 * @Description: Android实现获取本机中所有图片
 * @FileName: MyDevicePhotoActivity.java 
 * @Package com.device.photo 
 * @Author Hanyonglu
 * @Date 2012-5-10 下午04:43:55 
 * @Version V1.0
 */
public class LoadMyDevicePhoto extends FragmentActivity implements LoaderCallbacks<Cursor>{
	private Bitmap bitmap = null;
	private byte[] mContent = null;
	
	private ListView listView = null;
	private SimpleCursorAdapter simpleCursorAdapter = null;
	
	private static final String[] STORE_IMAGES = {
        MediaStore.Images.Media.DISPLAY_NAME,
        MediaStore.Images.Media.LATITUDE,
        MediaStore.Images.Media.LONGITUDE,
        MediaStore.Images.Media._ID
	};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_my_device_photo);
        
        listView = (ListView)findViewById(android.R.id.list);
        
        simpleCursorAdapter = new SimpleCursorAdapter(
        		this, 
        		R.layout.load_my_device_photo_simple_list_item, 
        		null, 
        		STORE_IMAGES, 
//        		new String[] {STORE_IMAGES[0], STORE_IMAGES[1]},
        		new int[] { R.id.item_title, R.id.item_value}, 
        		0 //这个标志用来决定该适配器的行为，不带该参数的已经弃用
        		);
        
        simpleCursorAdapter.setViewBinder(new ImageLocationBinder());
        listView.setAdapter(simpleCursorAdapter);
        // 注意此处是getSupportLoaderManager()，而不是getLoaderManager()方法。
        getSupportLoaderManager().initLoader(0, null, this);
        
        // 单击显示图片
        listView.setOnItemClickListener(new ShowItemImageOnClickListener());
    }
    
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
    	// 为了查看信息，需要用到CursorLoader。
    	CursorLoader cursorLoader = new CursorLoader(
    			this, 
    			MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 
    			STORE_IMAGES, 
    			null, 
    			null, 
    			null);
    	return cursorLoader;
    }
    
    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
    	simpleCursorAdapter.swapCursor(null);
    }
    
    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
    	// 使用swapCursor()方法，以使旧的游标不被关闭．
    	simpleCursorAdapter.swapCursor(cursor);
    }
    
    // 将图片的位置绑定到视图
    private class ImageLocationBinder implements ViewBinder{ 
    	@Override
    	public boolean setViewValue(View view, Cursor cursor, int arg2) {
    		if (arg2 == 1) {
    			// 图片经度和纬度
                double latitude = cursor.getDouble(arg2);
                double longitude = cursor.getDouble(arg2 + 1);
                
                if (latitude == 0.0 && longitude == 0.0) {
                    ((TextView)view).setText("位置：未知");
                } else {
                    ((TextView)view).setText("位置：" + latitude + ", " + longitude);
                }
                
                // 需要注意：在使用ViewBinder绑定数据时，必须返回真；否则，SimpleCursorAdapter将会用自己的方式绑定数据。
                return true;
            } else {
                return false;
            }
    	}
    }
    
    // 单击项显示图片事件监听器
    private class ShowItemImageOnClickListener implements OnItemClickListener{
    	@Override
    	public void onItemClick(AdapterView<?> parent, View view, int position,
    			long id) {
    		final Dialog dialog = new Dialog(LoadMyDevicePhoto.this);
    		// 以对话框形式显示图片
			dialog.setContentView(R.layout.load_my_device_photo_image_show);
			dialog.setTitle("图片显示");

			ImageView ivImageShow = (ImageView) dialog.findViewById(R.id.ivImageShow);
			Button btnClose = (Button) dialog.findViewById(R.id.btnClose);

			btnClose.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					
					// 释放资源
					if(bitmap != null){
						bitmap.recycle();
					}
				}
			});
			
			Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon().
					  appendPath(Long.toString(id)).build();
			FileUtil file = new FileUtil();
			ContentResolver resolver = getContentResolver();
			
			// 从Uri中读取图片资源
			try {
				mContent = file.readInputStream(resolver.openInputStream(Uri.parse(uri.toString())));
				bitmap = file.getBitmapFromBytes(mContent, null);
				ivImageShow.setImageBitmap(bitmap);
			} catch (Exception e) {
				e.printStackTrace();
			}

			dialog.show();
    	}
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	
    	if(bitmap != null){
			bitmap.recycle();
		}
    }
}
