package com.yuta.foods.util;

public interface HttpCallbackListener {
	//�ɹ����յ�responseִ��onFinish����
	void onFinish(String response);
	//�׳��쳣��ִ��onError����
	void onError(Exception e);
}
