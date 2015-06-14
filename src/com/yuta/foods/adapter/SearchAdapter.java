package com.yuta.foods.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuta.foods.R;
import com.yuta.foods.model.SearchFood;
import com.yuta.foods.util.Utility;

public class SearchAdapter extends BaseAdapter{
	private List<SearchFood> foodData = new ArrayList<SearchFood>();
	private Activity activity;
	private LayoutInflater inflater = null;
	private TextView name;
	private TextView content;
	

	public SearchAdapter(List<SearchFood> b, Activity a) {
		super();
		foodData = b;
		activity = a;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		if(convertView == null){
			view = inflater.inflate(R.layout.search_listview_layout, null);
		}
		SearchFood food = foodData.get(position);
		name = (TextView)view.findViewById(R.id.search_name);
		content = (TextView)view.findViewById(R.id.search_content);
		name.setText(Utility.getNormalText(food.getName()));
		content.setText(Utility.getNormalText(food.getContent()));
		
		return view;
	}

	

	@Override
	public int getCount() {
		return foodData.size();
	}

}
