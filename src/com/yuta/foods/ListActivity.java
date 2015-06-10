package com.yuta.foods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.yuta.foods.adapter.ListAdapter;
import com.yuta.foods.db.FoodDB;
import com.yuta.foods.model.Food;
import com.yuta.foods.model.ListFood;
import com.yuta.foods.server.HttpUtil;
import com.yuta.foods.util.Params;
import com.yuta.foods.util.Utility;

public class ListActivity extends BaseActivity implements OnItemClickListener{
	
	private ListView listView;
	public List<ListFood> mFoodList = Collections.synchronizedList(new ArrayList<ListFood>());
	private int cookclassSubId;
	private Intent intent;
	private ListAdapter adapter;
	private List<ListFood> test = new ArrayList<ListFood>();
	ExecutorService executor;
	NetTask task;
	static FoodDB foodDB;
	
	{
		for(int i=0; i<Params.limit; i++){
			ListFood food = new ListFood();
			mFoodList.add(food);
		}
	}
	{
		ListFood food1 = new ListFood();
		food1.setImg("img/cook/000051198.jpg");
		food1.setName("asdadgfsa");
		food1.setTag("eqtrw");
		test.add(food1);
		ListFood food2 = new ListFood();
		food2.setImg("img/cook/000051198.jpg");
		food2.setName("asadsfd");
		food2.setTag("gfdh");
		test.add(food2);
	}
	
	
	@Override
	protected void onCreate(Bundle instanceBundleState){
		super.onCreate(instanceBundleState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.list_layout);
		executor = Executors.newCachedThreadPool();
		listView = (ListView)findViewById(R.id.list_listview);
		intent = getIntent();
		cookclassSubId = intent.getIntExtra("cookclassSubId", 0);
		Log.d("cookclassSubId", cookclassSubId+"");
//		handler = new NetHandler();
//		mThread = new NetThread(new String[]{"1", Params.limit+"", cookclassSubId+""});
//		mThread.start();
//		Log.d("before", System.currentTimeMillis()+"");
//		new NetTask().execute(new String[]{"1", Params.limit+"", cookclassSubId+""});
		task = new NetTask();
		task.executeOnExecutor(executor, new String[]{"1", Params.limit+"", cookclassSubId+""});
//		Log.d("after", System.currentTimeMillis()+"");
		adapter = new ListAdapter(ListActivity.this, mFoodList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}
	
	class NetTask extends AsyncTask<String, Void, Void>{
		
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.d("onPreExecute", "in preExecute");
		}
				
		@Override
		protected Void doInBackground(String... params) {
			Log.d("doInBackground", "start do in background");
			long[] idArray = null;
			try{
				List<Food> foodList= Collections.synchronizedList(new ArrayList<Food>());
				String address = Params.rootAddress + "list?page=" + params[0] + "&limit="+params[1]+"&id="+params[2];
				String result = HttpUtil.sendHttpRequestWithCallable(address);
				idArray = Utility.handleListResponseToId(result);
				foodList = FoodDB.getInstance(ListActivity.this).loadFoodList(idArray, ListActivity.this);
				for(int i=0; i<foodList.size(); i++){
					Log.d("foodList", foodList.get(i).getFoodName());
				}
				for(int i=0; i<mFoodList.size(); i++){
					Log.d("mFoodList", mFoodList.get(i).getName()+"空");
				}
				for(int i=0; i<foodList.size(); i++){
					ListFood food1 = mFoodList.get(i);
					Food food2 = foodList.get(i);
					String name1 = mFoodList.get(0).getName();
					
					food1.setImg(food2.getImg());
					food1.setName(food2.getFoodName());
					food1.setTag(food2.getTag());
				}
				for(int i=0; i<foodList.size(); i++){
					Log.d("foodList", foodList.get(i).getFoodName());
				}
				for(int i=0; i<mFoodList.size(); i++){
					Log.d("mFoodList", mFoodList.get(i).getName());
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			adapter = (ListAdapter)listView.getAdapter();
			
			adapter.notifyDataSetChanged();
		}

//		@Override
//		protected void onProgressUpdate(Void... values) {
//			adapter = (ListAdapter)listView.getAdapter();
//			adapter.notifyDataSetChanged();
//		}
//		
	}
	
	public static void startActivity(Context context, int id){
		Intent intent = new Intent(context, ListActivity.class);
		intent.putExtra("cookclassSubId", id);
		context.startActivity(intent);
	}
	
	//第一次点击菜谱列表显示的一定是第一页，所以page=1,转向showActivity
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			ListFood listFood = mFoodList.get(position);
			long foodId = foodDB.getFoodIdByImg(listFood.getImg());
			ShowActivity.startActivity(this, foodId);
		}
	
}
