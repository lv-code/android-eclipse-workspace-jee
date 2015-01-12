package com.test.main;

import android.app.Activity;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;

public class NotificationDemo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_demo);
		init();
	}

	private void init() {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.icon)
				.setContentTitle("My notification")
				.setContentText("Hello World!");
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, AudioRecordTest.class);

		// The stack builder object will contain an artificial back stack for
		// the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(AudioRecordTest.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(0, mBuilder.build());
	}

	public void showNotification2(View view) {
		Builder builder = new Notification.Builder(NotificationDemo.this);
		PendingIntent contentIntent = PendingIntent.getActivity(
				NotificationDemo.this, 0, new Intent(NotificationDemo.this,
						NotificationDemo.class),
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(contentIntent)
				.setSmallIcon(R.drawable.ic_launcher)// 设置状态栏里面的图标（小图标）
														// .setLargeIcon(BitmapFactory.decodeResource(res,
														// R.drawable.i5))//下拉下拉列表里面的图标（大图标）
														 .setTicker("this is ticker!")
														// //设置状态栏的显示的信息
				.setWhen(System.currentTimeMillis())// 设置时间发生时间
				.setAutoCancel(true)// 设置可以清除
				.setContentTitle("This is ContentTitle")// 设置下拉列表里的标题
				.setContentText("this is ContentText");// 设置上下文内容
		Notification notification = builder.build();
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// 加i是为了显示多条Notification
		notificationManager.notify(20141230, notification);
	}
}
