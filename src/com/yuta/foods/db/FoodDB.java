package com.yuta.foods.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
	public Food loadFoodFromDatabase(long foodId){
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
	public boolean queryFoodFromDatabase(long foodId){
		Cursor cursor = db.query("food", null, "food_id=?", new String[]{foodId+""}, null, null, null);
		if(cursor.getCount()>0){
			return true;
		}
		return false;
	}
	
	//����id��������ݿ��л��List<Food>
	public List<Food> loadFoodList(long[] foodId){
		List<Food> foodList = new ArrayList<Food>();
		for(int i=0; i<foodId.length; i++){
			if(queryFoodFromDatabase(foodId[i])){
				Food food = new Food();
				food = loadFoodFromDatabase(foodId[i]);
				foodList.add(food);
			}else{
				HttpUtil.queryFromServer(HttpUtil.SHOW, foodId[i]+"");
				if(queryFoodFromDatabase(foodId[i])){
					Food food = new Food();
					food = loadFoodFromDatabase(foodId[i]);
					foodList.add(food);
				}else{
					Toast.makeText(MyApplication.getContext(), "����show���������ݷ���", Toast.LENGTH_SHORT).show();
				}
			}
		}
		return foodList;
	}
	
	//��search�õ���Map<K, V>��������ݿ��е�content����
	public void saveSearchResult(Map<String, String> map){
		Map<String, String> searchList = map;
		for(String foodId: searchList.keySet()){
			ContentValues value = new ContentValues();
			value.put("food_id", Long.getLong(foodId));
			value.put("content", searchList.get(foodId));
			db.insert("content", null, value);
			if(!queryFoodFromDatabase(Long.getLong(foodId))){
				HttpUtil.queryFromServer(HttpUtil.SHOW, foodId);
			}
		}
	}
	
	//�����ݿ��е�content���ѯ�Ƿ���foodId��Ӧ������
	public boolean queryContentFromDatabase(long foodId){
		Cursor cursor = db.query("content", null, "food_id=?", new String[]{foodId+""}, null, null, null);
		if(cursor.getCount()>0){
			return true;
		}
		return false;
	}
	
	//����foodId��content���еõ���Ӧ��content
	public String loadContentFromDatabase(long foodId){
		String content = null;
		if(queryContentFromDatabase(foodId)){
			Cursor cursor = db.query("content", null, "food_id=?", new String[]{foodId+""}, null, null, null);
			do{
				content = cursor.getString(cursor.getColumnIndex("content"));
			}while(cursor.moveToNext());
		}
		return content;
	}
	
	//�����ݿ�����ȡ��search֮������Ҫ�Ĳ���
	public Map<Food, String> loadSearchResult(long[] foodIdArray){
		Map<Food, String> searchResultMap = new HashMap<Food, String>();
		for(int i=0; i<foodIdArray.length; i++){
			if(queryFoodFromDatabase(foodIdArray[i]) && queryContentFromDatabase(foodIdArray[i])){
				Food food = loadFoodFromDatabase(foodIdArray[i]);
				String content = loadContentFromDatabase(foodIdArray[i]);
				searchResultMap.put(food, content);
			}else{
				if(!queryFoodFromDatabase(foodIdArray[i])){
					HttpUtil.queryFromServer(HttpUtil.SHOW, foodIdArray[i]+"");
				}
				if(!queryContentFromDatabase(foodIdArray[i])){
				}
			}
		}
		return null;
	}
	
	//����keyword�õ���Ӧ��foodIdList
	public long[] getIdArrayByKeyword(String keyword, int page, int limit){
		HttpUtil.queryFromServer(HttpUtil.SEARCH, new String[] {page+"", limit+"", keyword});
		return null;
	}
}
