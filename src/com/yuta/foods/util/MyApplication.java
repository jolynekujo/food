package com.yuta.foods.util;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application{
	//获得context的静态方法
	public static Context getContext(){
		Context context = getContext();
		return context;
	}
	
}
