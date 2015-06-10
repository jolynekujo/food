package com.yuta.foods.util;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application{
	private static Context context;
	
	@Override
	public void onCreate(){
		context = getApplicationContext();
	}
	//获得context的静态方法
	public static Context getContext(){
		return context;
	}
	
}
