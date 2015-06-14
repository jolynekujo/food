package com.yuta.foods.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuta.foods.R;
import com.yuta.foods.model.MainFood;
import com.yuta.foods.util.Params;

public class MainActivityAdapter extends BaseAdapter{
	private static LayoutInflater layoutInflater = null;
	private List<MainFood> foodList;
	private Activity activity;
	public ImageLoader loader;
	
	public MainActivityAdapter(Activity a, List<MainFood> b) {
		foodList = b;
		activity = a;
		layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		loader = new ImageLoader(activity.getApplicationContext());
	}

	@Override
	public int getCount() {
		return foodList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
		if(convertView==null){
			view = layoutInflater.inflate(R.layout.main_listview, null);
		}
		ImageView img = (ImageView)view.findViewById(R.id.main_img);
		TextView name = (TextView)view.findViewById(R.id.main_name);
		TextView tag = (TextView)view.findViewById(R.id.main_tag);
		TextView mater = (TextView)view.findViewById(R.id.main_mater);
		
		loader.DisplayImage(Params.ImgBaseAddress+foodList.get(position).getImg(), img);
		name.setText(foodList.get(position).getName());
		tag.setText(foodList.get(position).getTag());
		mater.setText(foodList.get(position).getMater());
//		Log.d("Adapter", foodList.get(position).getName());
		return view;
	}

}
