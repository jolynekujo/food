package com.yuta.foods.widiget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yuta.foods.CookclassMainActivity;
import com.yuta.foods.R;
import com.yuta.foods.SearchActivity;

public class TitleLayout extends RelativeLayout implements OnClickListener{
	private ImageView menu;
	private ImageView search;
	private ImageView more;
	
	public TitleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(getContext()).inflate(R.layout.titlelayout, this);
		menu = (ImageView)findViewById(R.id.menu);
		search = (ImageView)findViewById(R.id.search_main);
		more = (ImageView)findViewById(R.id.title_more);
		menu.setOnClickListener(this);
		search.setOnClickListener(this);
		more.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.menu:
			CookclassMainActivity.startActivity(getContext());
			break;
		case R.id.search_main:
			SearchActivity.startActivity(getContext());
			break;
		case R.id.title_more:
			
			break;
		default:
			break;
		}
	}
	
}
