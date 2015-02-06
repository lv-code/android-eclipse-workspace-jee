package com.beta.jiazhuang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author zg
 * 
 * 聊天界面ListView
 *
 */
public class CommonChatListView extends ListView {
	
	public CommonChatListView(Context context){
		super(context);
		init();
	}
	
	public CommonChatListView(Context context, AttributeSet attrs){
		super(context, attrs);
		init();
	}
	
	public CommonChatListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	/**
	 * 使ListView是从下开始网上的
	 */
	private void init(){
		
		setStackFromBottom(true);
		//setFastScrollEnabled(true);
		
	}
}
