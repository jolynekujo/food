package com.yuta.foods.db;

import com.yuta.foods.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewCache {
	private View baseView;
	private ImageView img;
	private TextView name;
	private TextView tag;
	
	public ListViewCache(View baseView){
		this.baseView = baseView;
	}
	
	public ImageView getImg(){
		if(img==null){
			img = (ImageView)baseView.findViewById(R.id.list_image);
		}
		return img;
	}
	
	public TextView getName(){
		if(name==null){
			name = (TextView)baseView.findViewById(R.id.list_name);
		}
		return name;
	}
	
	public TextView getTag(){
		if(tag==null){
			tag = (TextView)baseView.findViewById(R.id.list_tag);
		}
		return tag;
	}
}
