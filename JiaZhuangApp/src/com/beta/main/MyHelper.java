package com.beta.main;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.util.Linkify;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MyHelper {
	private static Activity mActivity;
//
//	public MyHelper(Activity activity) {
//		 mActivity = activity;
//	}

	/** 
	 * 将字符串转成MD5值 
	 *  
	 * @param string 
	 * @return 
	 */  
	public static String stringToMD5(String string) {  
	    byte[] hash;  
	  
	    try {  
	        hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));  
	    } catch (NoSuchAlgorithmException e) {  
	        e.printStackTrace();  
	        return null;  
	    } catch (UnsupportedEncodingException e) {  
	        e.printStackTrace();  
	        return null;  
	    }  
	  
	    StringBuilder hex = new StringBuilder(hash.length * 2);  
	    for (byte b : hash) {  
	        if ((b & 0xFF) < 0x10)  
	            hex.append("0");  
	        hex.append(Integer.toHexString(b & 0xFF));  
	    }  
	  
	    return hex.toString();  
	}  
	
	// 启动activity时，不自动弹出软键盘
	public static void setSoftInputMode(Activity activity) {
		mActivity = activity;
		// 启动activity时，不自动弹出软键盘
		mActivity.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	public static void extractNumber2Link(Activity activity, TextView myTextView) {
		// To Do
		int flags = Pattern.CASE_INSENSITIVE;
		Pattern p = Pattern.compile("\\bquake[0-9]*\\b", flags);
		Linkify.addLinks(myTextView, p,
				"content://com.paad.earthquake/earthquakes/");
	}

	public static void setNoTitle(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
	}
	
	public static void fullScreen() {
		mActivity.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // 全屏显示
	}

	// 读取sharedPreferences的配置信息，如：用户名、密码，返回也可以用HashMap
	public ArrayList<String> ReadSharedPreferences() {
		String strName, strPassword;
		ArrayList<String> aList = new ArrayList<String>();
		SharedPreferences user = mActivity.getSharedPreferences("user_info", 0);
		strName = user.getString("NAME", "");
		strPassword = user.getString("PASSWORD", "");
		aList.add(strPassword);
		aList.add(strName);
		return aList;
	}

	public void WriteSharedPreferences(String strName, String strPassword) {
		SharedPreferences user = mActivity.getSharedPreferences("user_info", 0);
		Editor userEditor = user.edit();
		userEditor.putString("NAME", strName);
		userEditor.putString("PASSWORD", strPassword);
		userEditor.commit();
	}
}
