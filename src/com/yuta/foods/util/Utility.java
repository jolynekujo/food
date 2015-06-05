package com.yuta.foods.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.widget.Toast;

import com.yuta.foods.db.FoodDB;
import com.yuta.foods.model.Food;
import com.yuta.foods.server.HttpUtil;

public class Utility {
	
	//创建一个public的FoodDB实例
	public static final FoodDB foodDB = FoodDB.getInstance(MyApplication.getContext()); 
	
	//解析从API接口获得的response,然后保存进数据库，成功解析返回true,解析数据不完整返回false
	public synchronized static boolean handleShowResponse(FoodDB foodDB, String response){
		boolean result = false;
		Food food = new Food();
		try{
			JSONObject jo1 = new JSONObject(response);
			JSONObject jo2 = jo1.getJSONObject("yi18");
			try{
				String success = jo2.getString("success");
				if(success.equals("false")){
					throw new Exception("没有这个id的食物");
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				result = false;
			}
			food.setFoodId(jo2.getLong("id"));
			food.setFoodName(jo2.getString("name"));
			food.setImg(jo2.getString("img"));
			food.setTag(jo2.getString("tag"));
			food.setMaterial(jo2.getString("food"));
			food.setMessage(jo2.getString("message"));
			food.setCount(jo2.getInt("count"));
			foodDB.saveFood(food);
			result = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	//解析list的数据,并返回一个List<Food>
	public synchronized static List<Food> handleListResponse(String response){
		List<Food> foodList = new ArrayList<Food>();
		
		try{
			JSONObject jo1 = new JSONObject(response);
			JSONArray ja1 = jo1.getJSONArray("yi18");
			long[] foodIdList = new long[ja1.length()];
			for(int i=0; i<ja1.length(); i++){
				JSONObject jo2 = ja1.getJSONObject(i);
				long foodId = jo2.getLong("id");
				foodIdList[i] = foodId;
			}
			foodList = Utility.getFoodListById(foodIdList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return foodList;
	}
	
	//根据一个long的id数组，得到相对应的FoodList
	private static List<Food> getFoodListById(long[] idArray){
		List<Food> foodList = new ArrayList<Food>();
		for(int i=0; i<idArray.length; i++){
			if(foodDB.queryFoodFromDatabase(idArray[i])){
				Food food = foodDB.loadFoodFromDatabase(idArray[i]);
				foodList.add(food);
			}else{
				//向服务器查询,服务器将查询到的结果保存到数据库
				HttpUtil.queryFromServer(HttpUtil.SHOW, idArray[i]+"");
				if(foodDB.queryFoodFromDatabase(idArray[i])){
					Food food = foodDB.loadFoodFromDatabase(idArray[i]);
					foodList.add(food);
				}else{
					Toast.makeText(MyApplication.getContext(), "发送show请求，无数据返回", Toast.LENGTH_SHORT).show();
				}
			}
		}
		return foodList;
	}
	
	//解析search请求返回的数据,返回Map<String, String>,将id转换为String
	public synchronized static Map<String, String> handleSearchResponse(String response){
		Map<String, String> map = new HashMap<String, String>();
		try{
			JSONObject jo1 = new JSONObject(response);
			JSONArray ja1 = jo1.getJSONArray("yi18");
			if(ja1.length()<1){
				Toast.makeText(MyApplication.getContext(), "无搜索结果", Toast.LENGTH_SHORT).show();
			}else{
				for(int i=0; i<ja1.length(); i++){
					JSONObject jo2 = ja1.getJSONObject(i);
					String id = jo2.getLong("id")+"";
					String content = jo2.getString("content");
					map.put(id, content);
				}
			}
		}catch(Exception e){
			Toast.makeText(MyApplication.getContext(), "解析错误", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		return map;
	}
}
