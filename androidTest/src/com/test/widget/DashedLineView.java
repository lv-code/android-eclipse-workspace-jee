package com.test.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

public class DashedLineView extends View {
	private final Context context;
	
	public DashedLineView(Context context) {
		this(context, null); 
		// TODO Auto-generated constructor stub
	}

	public DashedLineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.WHITE);
		Path path = new Path();
		int height = dip2px(context, 18);
		path.moveTo(0, height);
		int width = getWidth();
		path.lineTo(width, height);
		PathEffect effects = new DashPathEffect(new float[] { 6, 6, 6, 6 }, 10);
		paint.setPathEffect(effects);
		canvas.drawPath(path, paint);
	}
	
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
