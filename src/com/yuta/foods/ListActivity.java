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

import com.yuta.foods.adapter.ListAdapter;
import com.yuta.foods.db.FoodDB;
import com.yuta.foods.model.Food;
import com.yuta.foods.model.ListFood;
import com.yuta.foods.server.HttpUtil;
import com.yuta.foods.util.Params;
import com.yuta.foods.util.Utility;
import com.yuta.foods.widiget.XListView;
import com.yuta.foods.widiget.XListView.IXListViewListener;

public class ListActivity extends BaseActivity implements OnItemClickListener, IXListViewListener{
	
	private XListView listView;
	public List<ListFood> mFoodList = new ArrayList<ListFood>();
	private int cookclassSubId;
	private Intent intent;
	private ListAdapter adapter;
	ExecutorService executor;
	NetTask task;
	static FoodDB foodDB;
	private static int pageCount = 1;
	private Handler mHandler;
	
//	{
//		for(int i=0; i<Params.limit; i++){
//			ListFood food = new ListFood();
//			mFoodList.add(food);
//		}
//	}
	

	
	
	@Override
	protected void onCreate(Bundle instanceBundleState){
		super.onCreate(instanceBundleState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.list_layout);
		executor = Executors.newCachedThreadPool();
		listView = (XListView)findViewById(R.id.list_listview);
		intent = getIntent();
		listView.setPullLoadEnable(true);
		cookclassSubId = intent.getIntExtra("cookclassSubId", 0);
		task = new NetTask();
		task.executeOnExecutor(executor, new String[]{pageCount+"", Params.limit+"", cookclassSubId+""});
		adapter = new ListAdapter(ListActivity.this, mFoodList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		listView.setXListViewListener(this);
		mHandler = new Handler();
	}
	
	class NetTask extends AsyncTask<String, Void, List<ListFood>>{
		
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.d("onPreExecute", "in preExecute");
		}
				
		@Override
		protected List<ListFood> doInBackground(String... params) {
			Log.d("doInBackground", "start do in background");
			long[] idArray = null;
			List<ListFood> tmpFoodList = new ArrayList<ListFood>();
			try{
				List<Food> foodList= new ArrayList<Food>();
				String address = Params.rootAddress + "list?page=" + params[0] + "&limit="+params[1]+"&id="+params[2];
				String result = HttpUtil.sendHttpRequestWithCallable(address);
				idArray = Utility.handleListResponseToId(result);
				foodList = FoodDB.getInstance(ListActivity.this).loadFoodList(idArray, ListActivity.this);
				for(int i=0; i<foodList.size(); i++){
					ListFood food1 = new ListFood();
					Food food2 = foodList.get(i);
					food1.setImg(food2.getImg());
					food1.setName(food2.getFoodName());
					food1.setTag(food2.getTag());
					tmpFoodList.add(food1);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return tmpFoodList;
		}
		
		@Override
		protected void onPostExecute(List<ListFood> result) {
			for(ListFood food: result){
				mFoodList.add(food);
			}
//			adapter = (ListAdapter) listView.getAdapter();
			adapter.notifyDataSetChanged();
		}

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
			ListFood listFood = mFoodList.get(position-1);
			long foodId = FoodDB.getInstance(parent.getContext()).getFoodIdByImg(listFood.getImg());
			if(foodId!=0){
				ShowActivity.startActivity(parent.getContext(), foodId);
			}
		}

		@Override
		protected void onDestroy() {
			super.onDestroy();
		}

		@Override
		public void onRefresh() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onLoadMore() {
			mHandler.postDelayed(new Runnable(){

				@Override
				public void run() {
					new NetTask().executeOnExecutor(executor, new String[]{(++pageCount)+"", Params.limit+"", cookclassSubId+""});
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
