package com.yuta.foods.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.widget.Toast;

import com.yuta.foods.model.Food;
import com.yuta.foods.util.HttpCallbackListener;
import com.yuta.foods.util.MyApplication;
import com.yuta.foods.util.Utility;

public class HttpUtil {
	
	public static final int SHOW = 0;
	public static final int LIST = 1;
	public static final int SEARCH = 2;
	
	//向API接口发送请求
	public static void sendHttpRequest(final String address,  final HttpCallbackListener listener){
		new Thread(new Runnable(){

			@Override
			public void run() {
				HttpURLConnection conn = null;
				try {
					URL url = new URL(address);
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setReadTimeout(8000);
					conn.setDoOutput(true);
					conn.setDoInput(true);
					conn.connect();
					InputStream in = conn.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line = null;
					while((line = reader.readLine())!=null){
						response.append(line);
					}
					if(listener!=null){
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					if(listener!=null){
						listener.onError(e);
					}
				}finally{
					if(conn!=null){
						conn.disconnect();
					}
				}
			}
			
		}).start();
	}
	
	//通过HttpClient向服务器发送请求
	public static synchronized void sendRequestWithHttpClient(final String address, final HttpCallbackListener listener){
		new Thread(new Runnable(){

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				try{
					String response = null;
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(address);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					//从地址address中获得关键字keyword
					String[] tempWord = address.split("=");
					String keyword = tempWord[tempWord.length-1];
					params.add(new BasicNameValuePair("keyword", keyword));
					UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity(params, "utf-8");
					httpPost.setEntity(httpEntity);
					HttpResponse httpResponse = httpClient.execute(httpPost);
					if(httpResponse.getStatusLine().getStatusCode()==200){
						HttpEntity entity = httpResponse.getEntity();
						response = EntityUtils.toString(entity, "utf-8");
					}
					if(listener!=null){
						listener.onFinish(response);
					}
				}catch(Exception e){
					if(listener!=null){
						listener.onError(e);
					}
				}
			}
			
		}).start();
	}
	
	//从服务器查询,如果查询到数据则把数据保存进数据库
	public synchronized static void queryFromServer(int queryPort, String... params){
		String baseAddress = "http://api.yi18.net/";
		String showAddress = baseAddress+"show?id="+params[0];
		String listAddress = baseAddress+"list?page="+params[0]+"&limit="+params[1]+"&type="+params[2]+"&id="+params[3];
		String searchAddress = baseAddress+"search?page="+params[0]+"&limit="+params[1]+"&keyword="+params[2];
		String imgAddress = baseAddress;
		
		if(queryPort==SHOW){
			HttpUtil.sendHttpRequest(showAddress, new HttpCallbackListener(){
				boolean result = false;
				@Override
				public void onFinish(String response) {
					if(response!=null){
						result = Utility.handleShowResponse(Utility.foodDB, response);
					}
					if(!result){
						Toast.makeText(MyApplication.getContext(), "发送show请求，无数据返回", Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void onError(Exception e) {
					Toast.makeText(MyApplication.getContext(), "发送show请求错误", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				
			});
		}else if(queryPort==LIST){
			HttpUtil.sendHttpRequest(listAddress, new HttpCallbackListener() {
				
				@Override
				public void onFinish(String response) {
					List<Food> foodList = new ArrayList<Food>();
					if(response!=null){
						foodList = Utility.handleListResponse(response);
					}
					if(foodList.size()<1){
						Toast.makeText(MyApplication.getContext(), "发送list请求，无数据返回", Toast.LENGTH_SHORT).show();
					}
				}
				
				@Override
				public void onError(Exception e) {
					Toast.makeText(MyApplication.getContext(), "发送list请求错误", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			});
		}else{
			HttpUtil.sendRequestWithHttpClient(searchAddress, new HttpCallbackListener() {
				
				@Override
				public void onFinish(String response) {
					if(response!=null){
						Map<String, String> searchList = new HashMap<String, String>();
						searchList = Utility.handleSearchResponse(response);
						Set<String> foodIdSet = searchList.keySet();//怎么把foodIdSet传递给FoodDB...
						if(searchList.size()>0){
							Utility.foodDB.saveSearchResult(searchList);
						}else{
							Toast.makeText(MyApplication.getContext(), "发送search请求,无数据返回", Toast.LENGTH_SHORT).show();	
						}
					}
				}
				
				@Override
				public void onError(Exception e) {
					Toast.makeText(MyApplication.getContext(), "发送search请求错误", Toast.LENGTH_SHORT).show();			
					e.printStackTrace();
				}
			});
		}
	}
	
	
}
