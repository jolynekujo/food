package com.yuta.foods.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yuta.foods.R;
import com.yuta.foods.db.ListViewCache;
import com.yuta.foods.model.ListFood;
import com.yuta.foods.server.AsyncImageLoader;
import com.yuta.foods.server.AsyncImageLoader.ImageCallback;

public class ListFoodAdapter extends ArrayAdapter<ListFood>{
	private ListView listView;
    private AsyncImageLoader asyncImageLoader;
    private List<ListFood> listFood;

    public ListFoodAdapter(Activity activity, List<ListFood> imageAndTexts, ListView listView) {
        super(activity, 0, imageAndTexts);
        this.listView = listView;
        asyncImageLoader = new AsyncImageLoader();
    }
    
    public void setData(List<ListFood> data){
    	listFood = data;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) getContext();

        // Inflate the views from XML
        View rowView = convertView;
        ListViewCache viewCache;
        if (rowView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.list_listview_layout, null);
            viewCache = new ListViewCache(rowView);
            rowView.setTag(viewCache);
        } else {
            viewCache = (ListViewCache) rowView.getTag();
        }
        ListFood imageAndText = getItem(position);

        // Load the image and set it on the ImageView
        String imageUrl = imageAndText.getImg();
        ImageView imageView = viewCache.getImg();
        imageView.setTag(imageUrl);
        Drawable cachedImage = asyncImageLoader.loadDrawable(imageUrl, new ImageCallback() {
             
             
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                ImageView imageViewByTag = (ImageView) listView.findViewWithTag(imageUrl);
                if (imageViewByTag != null) {
                    imageViewByTag.setImageDrawable(imageDrawable);
                }
            }
        });
        if (cachedImage == null) {
            imageView.setImageResource(R.drawable.refresh);
        }else{
            imageView.setImageDrawable(cachedImage);
        }
        // Set the text on the TextView
        TextView name = viewCache.getName();
        name.setText(imageAndText.getName());
         
        TextView tag = viewCache.getTag();
        tag.setText(imageAndText.getTag());
         
         
        return rowView;
    }
}
