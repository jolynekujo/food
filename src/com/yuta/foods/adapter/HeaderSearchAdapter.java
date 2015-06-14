package com.yuta.foods.adapter;

import java.util.ArrayList;

import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView.FixedViewInfo;

public class HeaderSearchAdapter extends HeaderViewListAdapter{
	private SearchAdapter adapter;
	
	public HeaderSearchAdapter(ArrayList<FixedViewInfo> headerViewInfos,
			ArrayList<FixedViewInfo> footerViewInfos, ListAdapter adapter) {
		super(headerViewInfos, footerViewInfos, adapter);
		this.adapter = (SearchAdapter) adapter;
	}

}
