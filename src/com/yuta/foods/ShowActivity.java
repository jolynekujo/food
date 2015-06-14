package com.yuta.foods;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuta.foods.adapter.ImageLoader;
import com.yuta.foods.db.FoodDB;
import com.yuta.foods.model.Food;
import com.yuta.foods.server.HttpUtil;
import com.yuta.foods.util.Params;
import com.yuta.foods.util.Utility;

public class ShowActivity extends BaseActivity implements OnClickListener{
	private long foodId;
	private Food food = new Food();
	private ImageView home;
	private TextView title;
	private ImageView refresh;
	private ImageView img;
	private TextView name;
	private TextView count;
	private TextView content;
	private ImageLoader loader = new ImageLoader(ShowActivity.this);
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.show_layout);
		Log.d("activity", "onCreate");
		home = (ImageView) getView(R.id.show_home);
		title = (TextView)getView(R.id.show_title);
		refresh = (ImageView)getView(R.id.show_refresh);
		img = (ImageView)getView(R.id.show_img);
		name = (TextView)getView(R.id.show_name);
		count = (TextView)getView(R.id.show_count);
		content = (TextView)getView(R.id.show_content);
		foodId = getId();
		new NetTask().execute(Long.valueOf(foodId));
//		new RefreshThread().start();
		home.setOnClickListener(this);
		refresh.setOnClickListener(this);
	}
	
	public static void startActivity(Context context, long foodId){
		Intent intent = new Intent(context, ShowActivity.class);
		intent.putExtra("foodId", foodId);
		context.startActivity(intent);
		
	}
	
	private View getView(int id){
		View view = null;
		view = (View)findViewById(id);
		return view;
	}
	
	class NetTask extends AsyncTask<Long, Void, Food>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
//			food = new Food();
		}
		
		@Override
		protected Food doInBackground(Long... params) {
			Food tempFood = new Food();
			long id = params[0];
			try {
				if(FoodDB.getInstance(getApplicationContext()).queryFoodFromDatabase(id)){
					tempFood = FoodDB.getInstance(getApplicationContext()).loadFoodFromDatabase(id);
				}else{
					HttpUtil.queryFromServer(HttpUtil.SHOW, ShowActivity.this, id+"");
					if(FoodDB.getInstance(getApplicationContext()).queryFoodFromDatabase(id)){
						tempFood = FoodDB.getInstance(getApplicationContext()).loadFoodFromDatabase(id);
					}
				}
				Log.d("data1", tempFood.getFoodName()+";"+tempFood.getImg());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return tempFood;
		}


		@Override
		protected void onPostExecute(Food result) {
			super.onPostExecute(result);
			
			food.setCount(result.getCount());
			food.setFoodId(result.getFoodId());
			food.setFoodName(result.getFoodName());
			food.setImg(result.getImg());
			food.setMaterial(result.getMaterial());
			food.setMessage(result.getMessage());
			food.setTag(result.getTag());
			Log.d("data1", food.getFoodName()+";"+food.getImg());
//			loader.DisplayImage(Params.ImgBaseAddress+food.getImg(), img);
//			name.setText(food.getFoodName());
//			count.setText("已被浏览"+food.getCount()+"次");
//			if(food.getMessage()!=null){
//				content.setText(Utility.getNormalText(food.getMessage()));
//			}else{
//				content.setText("");
//			}
			

		}
		
		
	}
	
	class RefreshThread extends Thread{
		
		@Override
		public void run() {
			try {
				while (true) {
					Thread.sleep(1000);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							loader.DisplayImage(Params.ImgBaseAddress+food.getImg(), img);
							name.setText(food.getFoodName());
							if(food.getMessage()!=null){
								count.setText("已被浏览"+food.getCount()+"次");
								content.setText(Utility.getNormalText(food.getMessage()));
							}else{
								count.setText("");
								content.setText("无数据返回,刷新一下");
							}
						}
					});
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				}
		}
	}

	
	private long getId(){
		long id = 0;
		Intent intent = getIntent();
		id = intent.getLongExtra("foodId", 0);
		return id;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.show_home:
			MainActivity.startActivity(ShowActivity.this);
			finish();
			break;
		case R.id.show_refresh:
			new NetTask().execute(Long.valueOf(foodId));
			new RefreshThread().start();
			break;
		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	@Override
	protected void onStart() {
		Log.d("activity", "onStart");
		super.onRestart();
		new NetTask().execute(Long.valueOf(foodId));
		new RefreshThread().start();
	}
	
	
	
}
