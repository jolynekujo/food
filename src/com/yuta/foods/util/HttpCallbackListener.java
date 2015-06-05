package com.yuta.foods.util;

public interface HttpCallbackListener {
	//成功接收到response执行onFinish方法
	void onFinish(String response);
	//抛出异常则执行onError方法
	void onError(Exception e);
}
