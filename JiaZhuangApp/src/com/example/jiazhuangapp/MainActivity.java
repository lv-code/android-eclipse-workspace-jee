package com.example.jiazhuangapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ImageView imgV0, imgV1;
	Context context = MainActivity.this;
	//否是要求登陆
	int requestLogin = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        // ---------------------------------------------------  
        // 自定义Activity标题栏  
        CustomTitleBar.getTitleBar(this,"我的自定义标题栏");
        Log.d("a break point 1", "消息1111");
        CustomTitleBar.hideBackBtn();
        // ---------------------------------------------------
        Log.d("a break point 2", "消息2222");
		setContentView(R.layout.activity_main);
		init();

	}
	
	//响应 xml文件的onClick事件
	public void goHome(View v) {
    	
    }
	
	//判断是否登陆
	public void goLogin(View v) {
		Intent intent = new Intent();
		if(1==requestLogin){
		intent.setClass(context, LoginActivity.class);
		}else{
		intent.setClass(context, UserinfoActivity.class);
		}
		startActivity(intent);
		//若已经登陆跳至用户中心
		
    }
	
    protected void onStart() {  
        super.onStart();  
        init();  
    }  
  
    protected void onResume() {  
        super.onResume();  
    } 
	
	private void init(){
		imgV0 = (ImageView) findViewById(R.id.imageView0);
		imgV0.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, FlowActivity.class);
				startActivity(intent);
				//finish();//停止当前的Activity,如果不写,则按返回键会跳转回原来的Activity 
			}
		});
		
		imgV1 = (ImageView) findViewById(R.id.imageView1);
		imgV1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, LjljActivity.class);
				startActivity(intent);
				//finish();//停止当前的Activity,如果不写,则按返回键会跳转回原来的Activity 
			}
		});
	}
	
}
