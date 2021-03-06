package com.beta.jiazhuang.main;

import com.beta.main.R;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

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
        activity.setContentView(R.layout.include_title_bar);  
        activity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,  
                R.layout.include_title_bar);  
        TextView textView = (TextView) activity.findViewById(R.id.tvTitleBarCenter);  
        textView.setText(title);  

//        // 返回按钮 注：这里不是UI的主线程，所以 监听不到 onClick
//        titleBackBtn = (Button) mActivity.findViewById(R.id.head_TitleBackBtn);
//        titleBackBtn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				mActivity.finish();
//			}
//		});
//        titleBackBtn.setOnClickListener(new OnClickListener() {  
//        	public void onClick(View v) {
////        		KeyEvent newEvent = new KeyEvent(KeyEvent.ACTION_DOWN,  
////                    KeyEvent.KEYCODE_BACK);  
////        		mActivity.onKeyDown(KeyEvent.KEYCODE_BACK, newEvent);  
//        	}  
//        });  
        
    }
    
    public static void hideBackBtn() {
    	titleBackBtn.setVisibility(View.INVISIBLE);
    }
}