package com.yuta.foods.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuta.foods.R;
import com.yuta.foods.model.ListFood;
import com.yuta.foods.util.Params;

public class ListAdapter extends BaseAdapter{

	private Activity activity;
    private List<ListFood> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
	
	//¹¹ÔìÆ÷
	public ListAdapter(Activity a, List<ListFood> d){
		activity = a;
		data = d;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
	}
	
	public int getCount() {
        return data.size();
    }

    

    public long getItemId(int position) {
        return position;
    }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_listview_layout, null);
        TextView name = (TextView)vi.findViewById(R.id.list_name);
        TextView tag=(TextView)vi.findViewById(R.id.list_tag);
        ImageView image=(ImageView)vi.findViewById(R.id.list_image);
        name.setText(data.get(position).getName());
        tag.setText(data.get(position).getTag());
        imageLoader.DisplayImage(Params.ImgBaseAddress+data.get(position).getImg(), image);
        return vi;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}
}	
	