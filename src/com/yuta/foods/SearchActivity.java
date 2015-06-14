package com.yuta.foods;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

public class SearchActivity extends BaseActivity implements OnClickListener{
	private String keyword;
	private EditText searchInput;
	private ImageView search;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_title);
		searchInput = (EditText)findViewById(R.id.search_input);
		
		
		search = (ImageView)findViewById(R.id.search_);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = LayoutParams.FILL_PARENT;
		lp.gravity = Gravity.TOP;
		getWindow().setAttributes(lp);
		searchInput.setOnClickListener(this);
		search.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	public static void startActivity(Context context){
		Intent intent = new Intent(context, SearchActivity.class);
		context.startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.search_:
			searchInput.clearFocus();
			keyword = searchInput.getText().toString();
			if(keyword!=null){
				SearchResultActivity.startActivity(SearchActivity.this, keyword);
				
				searchInput.setText("");
				this.finish();
			}
			searchInput.clearFocus();
			Timer timer2 = new Timer();
			timer2.schedule(new TimerTask() {
	
				public void run() {
					InputMethodManager inputManager = (InputMethodManager) searchInput.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
					inputManager.hideSoftInputFromWindow(searchInput.getWindowToken(), 0);
				}
	
			}, 0);
		break;
		case R.id.search_input:
			searchInput.setFocusable(true);
			searchInput.setFocusableInTouchMode(true);
			searchInput.requestFocus();
			Timer timer1 = new Timer();
			timer1.schedule(new TimerTask() {
	
				public void run() {
					InputMethodManager inputManager = (InputMethodManager) searchInput.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
					inputManager.showSoftInputFromInputMethod(searchInput.getWindowToken(),0);
				}
	
			}, 1000);
		break;
		default:
		}
				
				
	}
}
