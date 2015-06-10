package com.yuta.foods.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.yuta.foods.db.FoodDB;
import com.yuta.foods.model.Food;
import com.yuta.foods.server.HttpUtil;

public class Utility {
	
	
	//������API�ӿڻ�õ�response,Ȼ�󱣴�����ݿ⣬�ɹ���������true,�������ݲ���������false
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
	
	//����list������,�õ�����list�����foodId,������Щid��ѯ���ݿ�����û�ж�Ӧ��Food����
	//û�еĻ��ٲ�ѯ�������õ�Food�����ұ��浽���ݿ�
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
	
	//����һ��long��id���飬�õ����Ӧ��FoodList,��ʱ��Ӧ������Food�����Ѿ��������ݿ���
	private static boolean saveFoodNotInDB(long[] idArray, Context context){
		boolean result = false;
		int count = 0;
		try{
			for(int i=0; i<idArray.length; i++){
				if(FoodDB.getInstance(context).queryFoodFromDatabase(idArray[i])){
					count++;
				}else{
					//���������ѯ,����������ѯ���Ľ�����浽���ݿ�
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
	
	//����list����ķ���response,�õ���Ӧ��foodId array
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
			Toast.makeText(MyApplication.getContext(), "û�����ݷ���", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		return idArray;
	}
	
	//����search���󷵻ص�����,����Map<String, String>,��idת��ΪString
	public synchronized static Map<String, String> handleSearchResponse(String response){
		Map<String, String> map = new HashMap<String, String>();
		try{
			JSONObject jo1 = new JSONObject(response);
			JSONArray ja1 = jo1.getJSONArray("yi18");
			if(ja1.length()<1){
				Toast.makeText(MyApplication.getContext(), "���������", Toast.LENGTH_SHORT).show();
			}else{
				for(int i=0; i<ja1.length(); i++){
					JSONObject jo2 = ja1.getJSONObject(i);
					String id = jo2.getLong("id")+"";
					String content = jo2.getString("content");
					map.put(id, content);
				}
			}
		}catch(Exception e){
			Toast.makeText(MyApplication.getContext(), "��������", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		return map;
	}
	
	//�ж�response��yi18���������Ƿ�Ϊ��
	public static boolean ResponseIsEmpty(String response){
		boolean result = true;
		try {
			JSONObject jo1 = new JSONObject(response);
			JSONArray ja1 = jo1.getJSONArray("yi18");
			if(ja1.length()>0){
				result = true;
			}
		} catch (JSONException e) {
			Toast.makeText(MyApplication.getContext(), "��������", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		return result;
	}
}
