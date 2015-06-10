package com.yuta.foods;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class ShowActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.show_layout);
	}
	
	public static void startActivity(Context context, long foodId){
		Intent intent = new Intent(context, ShowActivity.class);
		intent.putExtra("foodId", foodId);
		context.startActivity(intent);
	}
}
