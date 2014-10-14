package com.example.jiazhuangapp;

import android.app.Activity;  
import android.content.Intent;
import android.view.KeyEvent;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.view.Window;  
import android.widget.Button;  
import android.widget.TextView; 

import com.example.jiazhuangapp.R;

public class CustomTitleBar {
    private static Activity mActivity;
    private static Button titleBackBtn;
    
    /** 
     * @see [自定义标题栏] 
     * @param activity 
     * @param title 
     */  
    public static void getTitleBar(Activity activity,String title) {  
        mActivity = activity;  
      activity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
//        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.setContentView(R.layout.custom_title_bar);  
        activity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,  
                R.layout.custom_title_bar);  
        TextView textView = (TextView) activity.findViewById(R.id.head_center_text);  
        textView.setText(title);  

//        /*返回按钮*/
        titleBackBtn = (Button) mActivity.findViewById(R.id.head_TitleBackBtn);
//        titleBackBtn.setOnClickListener(new OnClickListener() {  
//        	public void onClick(View v) {
////        		KeyEvent newEvent = new KeyEvent(KeyEvent.ACTION_DOWN,  
////                    KeyEvent.KEYCODE_BACK);  
////        		mActivity.onKeyDown(KeyEvent.KEYCODE_BACK, newEvent);  
//        	}  
//        });  
        /*返回按钮*/
    }
    
    public static void hideBackBtn() {
    	titleBackBtn.setVisibility(View.INVISIBLE);
    }
}