package com.yuta.foods.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.yuta.foods.model.Food;
import com.yuta.foods.server.HttpUtil;
import com.yuta.foods.util.MyApplication;

/*
 * ����һЩ���ݿ���ز����ķ���
 */
public class FoodDB {
	
	public static final String DB_NAME = "food_databse";
	public static final int VERSION = 1;
	
	private static FoodDB foodDB;
	private SQLiteDatabase db;
	
	//���췽��˽�л�
	private FoodDB(Context context){
		FoodOpenHelper dbHelper = new FoodOpenHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	//���FoodDBʵ��
	public synchronized static FoodDB getInstance(Context context){
		if(foodDB==null){
			foodDB = new FoodDB(context);
		}
		return foodDB;
	}
	
	//��һ��Food���󱣴�����ݿ�
	public void saveFood(Food food){
		if(food!=null){
			ContentValues value = new ContentValues();
			value.put("food_id", food.getFoodId());
			value.put("food_name", food.getFoodName());
			value.put("img", food.getImg());
			value.put("tag", food.getTag());
			value.put("material", food.getMaterial());
			value.put("message", food.getMessage());
			value.put("count", food.getCount());
			db.insert("food", null, value);
		}
	}
	
	//����foodId�����ݿ��ж�ȡ��Food����
	public Food loadFoodFromDatabase(final long foodId) throws Exception{
		Food food = new Food();
		
		Cursor cursor = db.query("food", null, "food_id=?", new String[]{foodId+""}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				food.setFoodId(cursor.getLong(cursor.getColumnIndex("food_id")));
				food.setFoodName(cursor.getString(cursor.getColumnIndex("food_name")));
				food.setImg(cursor.getString(cursor.getColumnIndex("img")));
				food.setTag(cursor.getString(cursor.getColumnIndex("tag")));
				food.setMaterial(cursor.getString(cursor.getColumnIndex("material")));
				food.setMessage(cursor.getString(cursor.getColumnIndex("message")));
				food.setCount(cursor.getInt(cursor.getColumnIndex("count")));
			}while(cursor.moveToNext());
		}
		return food;
	}
	
	//����foodId����ѯ���ݿ�����û�����Food����
	public boolean queryFoodFromDatabase(final long foodId) throws Exception{
		
		Cursor cursor = db.query("food", null, "food_id=?", new String[]{foodId+""}, null, null, null);
		if(cursor.getCount()>0){
			return true;
		}
		return false;
	}
	
	//����id��������ݿ��л��List<Food>
	public List<Food> loadFoodList(long[] foodId, Context context) throws Exception{
		List<Food> foodList = new ArrayList<Food>();
		Log.d("loadFoodlist", foodId.length+"");
		for(int i=0; i<foodId.length; i++){
			if(queryFoodFromDatabase(foodId[i])){
				Food food = new Food();
				food = loadFoodFromDatabase(foodId[i]);
				foodList.add(food);
			}else{
				HttpUtil.queryFromServer(HttpUtil.SHOW, context, foodId[i]+"");
				if(queryFoodFromDatabase(foodId[i])){
					Food food = new Food();
					food = loadFoodFromDatabase(foodId[i]);
					foodList.add(food);
				}
			}
		}
		return foodList;
	}
	
	//����ͼƬ��ַ����ö�Ӧ�Ĳ���id
	public long getFoodIdByImg(String imageAddress){
		Cursor cursor = db.query("food", null, "img = ?", new String[]{imageAddress}, null, null, null);
		
		return cursor.getInt(cursor.getColumnIndex("id"));
	}
	
}