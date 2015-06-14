package com.yuta.foods.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.yuta.foods.db.FoodDB;
import com.yuta.foods.model.Food;
import com.yuta.foods.model.SearchFood;
import com.yuta.foods.server.HttpUtil;

public class Utility {
	
	
	//解析从API接口获得的response,然后保存进数据库，成功解析返回true,解析数据不完整返回false
	public synchronized static boolean handleShowResponse(String response, Context context){
		boolean result = false;
		Food food = new Food();
		try{
			JSONObject jo1 = new JSONObject(response);
			JSONObject jo2 = jo1.getJSONObject("yi18");
			
			food.setFoodId(jo2.getLong("id"));
			food.setFoodName(jo2.getString("name"));
			food.setImg(jo2.getString("img"));
			food.setTag(jo2.getString("tag"));
			food.setMaterial(jo2.getString("food"));
			food.setMessage(jo2.getString("message"));
			food.setCount(jo2.getInt("count"));
			FoodDB.getInstance(context).saveFood(food);
			result = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	//解析list的数据,得到所有list里面的foodId,根据这些id查询数据库里有没有对应的Food对象
	//没有的话再查询服务器得到Food对象并且保存到数据库
	public synchronized static boolean handleListResponse(String response, Context context){
		boolean result = false;
		try{
			JSONObject jo1 = new JSONObject(response);
			JSONArray ja1 = jo1.getJSONArray("yi18");
			long[] foodIdList = new long[ja1.length()];
			for(int i=0; i<ja1.length(); i++){
				JSONObject jo2 = ja1.getJSONObject(i);
				long foodId = jo2.getLong("id");
				foodIdList[i] = foodId;
			}
			result = Utility.saveFoodNotInDB(foodIdList, context);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	//根据一个long的id数组，得到相对应的FoodList,此时对应的所有Food对象已经都在数据库了
	private static boolean saveFoodNotInDB(long[] idArray, Context context){
		boolean result = false;
		int count = 0;
		try{
			for(int i=0; i<idArray.length; i++){
				if(FoodDB.getInstance(context).queryFoodFromDatabase(idArray[i])){
					count++;
				}else{
					//向服务器查询,服务器将查询到的结果保存到数据库
					HttpUtil.queryFromServer(HttpUtil.SHOW, context, idArray[i]+"");
					if(FoodDB.getInstance(context).queryFoodFromDatabase(idArray[i])){
						count++;
					}
				}
			}
			if(count==idArray.length){
				result = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	//处理list请求的返回response,得到对应的foodId array
	public synchronized static long[] handleListResponseToId(String response){
		long[] idArray = null;
		try{
			JSONObject jo1 = new JSONObject(response); 
			JSONArray ja1 = jo1.getJSONArray("yi18");
			idArray = new long[ja1.length()];
			for(int i=0; i<ja1.length(); i++){
				JSONObject jo2 = ja1.getJSONObject(i);
				idArray[i] = jo2.getLong("id");
			}
		}catch(Exception e){
			Toast.makeText(MyApplication.getContext(), "没有数据返回", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		return idArray;
	}
	
	//解析search请求返回的数据,返回Map<String, String>,将id转换为String
	public synchronized static List<SearchFood> handleSearchResponse(String response){
		List<SearchFood> data = new ArrayList<SearchFood>();
		try{
			JSONObject jo1 = new JSONObject(response);
			JSONArray ja1 = jo1.getJSONArray("yi18");
			if(ja1.length()>0){
				for(int i=0; i<ja1.length(); i++){
					SearchFood food = new SearchFood();
					JSONObject jo2 = ja1.getJSONObject(i);
					long id = jo2.getLong("id");
					String name = jo2.getString("name");
					String content = jo2.getString("content");
					food.setId(id);
					food.setName(name);
					food.setContent(content);
					data.add(food);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return data;
	}
	
	//判断response的yi18参数后面是否为空
	public static boolean ResponseIsEmpty(String response){
		boolean result = true;
		try {
			JSONObject jo1 = new JSONObject(response);
			JSONArray ja1 = jo1.getJSONArray("yi18");
			if(ja1.length()>0){
				result = true;
			}
		} catch (JSONException e) {
			Toast.makeText(MyApplication.getContext(), "解析错误", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		return result;
	}
	
	public static String getNormalText(String Text){
		char[] a = Text.toCharArray();
		String result = "";
		if(a.length>0){
			int length = a.length;
			for(int i=0; i<length; i++){
				if(a[i]=='<'){
					while(true){
						if(a[i]!='>'){
							for(int j=i+1; j<length; j++){
								a[j-1] = a[j];
							}
							length--;
						}
						else{
							for(int j=i+1; j<length; j++){
								a[j-1] = a[j];
							}
							length--;
							break;
						}
					}
				}
			}
			result = new String(a,0,length-1);
		}
		return result;
	}
}
