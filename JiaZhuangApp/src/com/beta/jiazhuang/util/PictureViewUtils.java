package com.beta.jiazhuang.util;

import android.content.Intent;
import android.graphics.BitmapFactory.Options;
import android.provider.MediaStore;

/**
 * @author zg
 * 
 * 打开图片浏览器
 *
 */
public class PictureViewUtils {
	
	public static Intent getPictureIntent(){
//		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//		intent.setType("image/*");
//		intent.putExtra("crop", true);
//		intent.putExtra("return-data", true);
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		return intent;
		
	}
	
}


