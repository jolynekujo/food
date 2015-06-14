package com.yuta.foods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.yuta.foods.adapter.MainActivityAdapter;
import com.yuta.foods.db.FoodDB;
import com.yuta.foods.model.Food;
import com.yuta.foods.model.MainFood;
import com.yuta.foods.server.HttpUtil;
import com.yuta.foods.util.Params;
import com.yuta.foods.util.Utility;
import com.yuta.foods.widiget.XListView;
import com.yuta.foods.widiget.XListView.IXListViewListener;

public class MainActivity extends BaseActivity implements OnItemClickListener, IXListViewListener{
	//ListView控件对象
	private XListView listView;
	//适配器对象
	private MainActivityAdapter adapter;
	//数据源
	private static List<MainFood> foodList = new ArrayList<MainFood>();
	ExecutorService executor = Executors.newCachedThreadPool();
	private static int pageCount = 1;
	private Handler mHandler;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		listView = (XListView)findViewById(R.id.listview_main);
		listView.setPullLoadEnable(true);
//		for(int i=0; i<Params.limit; i++){
//			MainFood food = new MainFood();
//			foodList.add(food);
//		}
		NetTask task = new NetTask();
		task.executeOnExecutor(executor, new String[]{pageCount+"", Params.limit+""});
		adapter = new MainActivityAdapter(MainActivity.this, foodList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		listView.setXListViewListener(this);
		mHandler = new Handler();
	}

	class NetTask extends AsyncTask<String, Void, List<MainFood>>{

		@Override
		protected List<MainFood> doInBackground(String... params) {
			String address = Params.rootAddress+"list?page="+params[0]+"&limit="+params[1];
			List<MainFood> mFoodList = new ArrayList<MainFood>();
			try{
				String response = HttpUtil.sendHttpRequest(address);
				long[] result = Utility.handleListResponseToId(response);
				List<Food> list = new ArrayList<Food>();
				list = FoodDB.getInstance(getApplicationContext()).loadFoodList(result, getApplicationContext());
				for(int i=0; i<list.size(); i++){
					MainFood mFood = new MainFood();
					Food food = list.get(i);
					mFood.setImg(food.getImg());
					mFood.setName(food.getFoodName());
					mFood.setTag(food.getTag());
					mFood.setMater(food.getMaterial());
					mFoodList.add(mFood);
//					Log.d("MainActivity AsyncTask", food.getFoodName());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return mFoodList;
		}

		@Override
		protected void onPostExecute(List<MainFood> result) {
			for(MainFood food: result){
				foodList.add(food);
			}
//			adapter = (MainActivityAdapter) listView.getAdapter();
			adapter.notifyDataSetChanged();
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		MainFood listFood = foodList.get(position-1);
		Log.d("position", listFood.getName()+":"+position);
		long foodId = FoodDB.getInstance(parent.getContext()).getFoodIdByImg(listFood.getImg());
		if(foodId!=0){
			ShowActivity.startActivity(this, foodId);
		}
	}
	
	public static void startActivity(Context context){
		Intent intent = new Intent(context, MainActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		ActivityCollector.finishAll();
	}

	@Override
	public void onRefresh() {
		
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable(){

			@Override
			public void run() {
				new NetTask().executeOnExecutor(executor, new String[]{(++pageCount)+"", Params.limit+""});
				onLoad();
			}
			
		}, 2000);
	}
	
	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime("刚刚");
	}
	
	
}
