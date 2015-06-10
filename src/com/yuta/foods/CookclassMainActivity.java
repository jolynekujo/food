package com.yuta.foods;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CookclassMainActivity extends BaseActivity implements OnItemClickListener{
	private String[] cookclassMain = new String[]{"美容养颜","减肥瘦身","保健养生","适宜人群","餐食时节","孕产哺乳","女性养生","男性养生","心脏血管","皮肤器官","肠胃消化","口腔呼吸","肌肉神经","癌症其他"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.cookclass_layout);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(CookclassMainActivity.this, android.R.layout.simple_list_item_1, cookclassMain);
		ListView listView = (ListView)findViewById(R.id.cookclass_main);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		CookclassSubActivity.startActivity(view.getContext(), position);
//		Log.d("CookclassMainActivity", position+"");
	}
	
	public static void startActivity(Context context){
		Intent intent = new Intent(context, CookclassMainActivity.class);
		context.startActivity(intent);
	}
	
}
