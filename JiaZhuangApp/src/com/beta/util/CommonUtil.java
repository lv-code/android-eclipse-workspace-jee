package com.beta.util;

import android.content.Context;

/*
 * 公用的方法，在包com.beta.main中有MyHelper，这两个里面都有一些
 */
public class CommonUtil {

	/**
	 * 通过字符串得到资源ID
	 * 
	 * @param context
	 * @param defType，如："drawable"
	 * @param resName
	 * @return id
	 */
	public static int getResourceIdByStr(Context context, String resName, String defType) {
		return context.getResources().getIdentifier(resName, defType,
				context.getPackageName());
	}
	

}
