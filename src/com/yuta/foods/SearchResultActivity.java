package com.yuta.foods;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yuta.foods.adapter.SearchAdapter;
import com.yuta.foods.model.SearchFood;
import com.yuta.foods.server.HttpUtil;
import com.yuta.foods.util.Params;
import com.yuta.foods.widiget.XListView;
import com.yuta.foods.widiget.XListView.IXListViewListener;

public class SearchResultActivity extends BaseActivity implements OnItemClickListener, OnClickListener, IXListViewListener{
	private List<SearchFood> foodData = new ArrayList<SearchFood>();
	private EditText input;
	private ImageView search;
	private TextView noResult;
	private XListView listView;
	private SearchAdapter adapter;
	private String keyword;
	private SearchTask task;
	private ExecutorService executor; 
	private Intent intent;
	private Handler mHandler;
	private static int pageCount = 1;
	
//	{
//		for(int i=0; i<Params.limit; i++){
//			SearchFood food = new SearchFood();
//			food.setName("");
//			food.setContent("");
//			foodData.add(food);
//		}
//	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_layout);
		executor = Executors.newCachedThreadPool();
		input = (EditText)findViewById(R.id.search_input_);
		search = (ImageView)findViewById(R.id.search_search);
		noResult = (TextView)findViewById(R.id.no_result);
		listView = (XListView)findViewById(R.id.search_listview);
		listView.setPullLoadEnable(true);
		noResult.setText("");
		listView.setVisibility(View.INVISIBLE);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		intent = getIntent();
		keyword = intent.getStringExtra("keyword");
		Log.d("searchkey", keyword);
		task = new SearchTask();
		task.executeOnExecutor(executor, new String[]{pageCount+"", Params.limit+"", keyword});
		
		adapter = new SearchAdapter(foodData, SearchResultActivity.this);
		listView.setAdapter(adapter);
		search.setOnClickListener(this);
		listView.setOnItemClickListener(this);
		listView.setXListViewListener(this);
		mHandler = new Handler();
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	public static void startActivity(Context context, String data){
		Intent intent = new Intent(context, SearchResultActivity.class);
		intent.putExtra("keyword", data);
		context.startActivity(intent);
	}
	
	
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_input_:
			input.setFocusable(true);
			input.setFocusableInTouchMode(true);
			input.requestFocus();
			Timer timer1 = new Timer();
			timer1.schedule(new TimerTask() {
	
				public void run() {
					InputMethodManager inputManager = (InputMethodManager) input.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
					inputManager.showSoftInputFromInputMethod(input.getWindowToken(),0);
				}
	
			}, 1000);
		case R.id.search_search:
			String keyWord = input.getText().toString();
			new SearchTask().execute(new String[]{"1", Params.limit+"", keyWord});
			input.setText("");
			input.clearFocus();
			Timer timer2 = new Timer();
			timer2.schedule(new TimerTask() {
	
				public void run() {
					InputMethodManager inputManager = (InputMethodManager) input.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
					inputManager.hideSoftInputFromWindow(input.getWindowToken(), 0);
				}
	
			}, 0);
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		SearchFood food = foodData.get(position-1);
		long foodId = food.getId();
		if(foodId!=0){
			ShowActivity.startActivity(SearchResultActivity.this, foodId);
		}
	}
	
	//
	class SearchTask extends AsyncTask<String, Void, List<SearchFood>>{
		
		
		@Override
		protected List<SearchFood> doInBackground(String... params) {
			List<SearchFood> list = HttpUtil.searchFromServer(SearchResultActivity.this, params);
			
			Log.d("size", list.size()+"");
			return list;
		}

		@Override
		protected void onPostExecute(List<SearchFood> result) {
//			for(int i=0; i<foodData.size(); i++){
//				foodData.get(i).setId(0);
//				foodData.get(i).setName("");
//				foodData.get(i).setContent("");
//			}
			for(SearchFood food: result){
				foodData.add(food);
			}
			Log.d("DataSize", result.size()+"");
			if(foodData.size()>0){
				noResult.setVisibility(View.INVISIBLE);
				listView.setVisibility(View.VISIBLE);
//				adapter = (SearchAdapter) listView.getAdapter();
				adapter.notifyDataSetChanged();
			}else{
//				noResult.setVisibility(View.INVISIBLE);
				noResult.setText("无搜索结果返回");
				noResult.setVisibility(View.VISIBLE);
				listView.setVisibility(View.INVISIBLE);
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
	}

	@Override
	public void onBackPressed() {
		this.finish();
	}


	@Override
	public void onRefresh() {
		
	}


	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable(){

			@Override
			public void run() {
				new SearchTask().executeOnExecutor(executor, new String[]{(++pageCount)+"", Params.limit+"", keyword});
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
