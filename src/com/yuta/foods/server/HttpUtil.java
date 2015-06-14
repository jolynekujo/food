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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.yuta.foods.db.FoodDB;
import com.yuta.foods.model.SearchFood;
import com.yuta.foods.util.Params;
import com.yuta.foods.util.Utility;

public class HttpUtil {
	
	public static final int SHOW = 0;
	public static final int LIST = 1;
	public static final int SEARCH = 2;
	private static FoodDB foodDB;
	
	//发送HttpRequest,直接返回response,不用回调函数处理
	public static String sendHttpRequestWithCallable(String address) throws Exception{
		HttpURLConnection conn = null;
		StringBuilder response = new StringBuilder();
		
		try{
			Log.d("sendRequest", "0");
			URL url = new URL(address);
			Log.d("sendRequest", "1");
			conn = (HttpURLConnection) url.openConnection();
			Log.d("sendRequest", "2");
			conn.setRequestMethod("GET");
			Log.d("sendRequest", "3");
			conn.connect();
			String line = null;
			InputStream in = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			while((line = reader.readLine())!=null){
				response.append(line);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return response.toString();
	}
	
	//发送GET请求
	public static String sendHttpRequest(final String address){
		String result = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(address);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
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
			result = response.toString();		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(conn!=null){
				conn.disconnect();
			}
		}
		return result;
	}
	
	//通过HttpClient向服务器发送请求
	@SuppressWarnings("deprecation")
	public static synchronized String sendRequestWithHttpClient(final String address){
		String result = null;
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
			result = response;		
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}
	
	//从服务器查询,如果查询到数据则把数据保存进数据库
	@SuppressWarnings("unchecked")
	public synchronized static void queryFromServer(int queryPort, final Context context, String... params){
		String response = null;
		
		if(queryPort==SHOW){
			String showAddress = Params.rootAddress+"show?id="+params[0];
			Map<String, Context> map = new HashMap<String, Context>();
			map.put(showAddress, context);
			new ShowTask().execute(map);
		}else if(queryPort==LIST){
			String listAddress = Params.rootAddress+"list?page="+params[0]+"&limit="+params[1]+"&id="+params[2];
			Map<String, Context> map = new HashMap<String, Context>();
			map.put(listAddress, context);
			new ListTask().execute(map);
		}else{
			List<SearchFood> data = new ArrayList<SearchFood>();
			String searchAddress = Params.rootAddress+"search?page="+params[0]+"&limit="+params[1]+"&keyword="+params[2];
			response = HttpUtil.sendRequestWithHttpClient(searchAddress);
			data = Utility.handleSearchResponse(response);
		}
	}
	
	static class ShowTask extends AsyncTask<Map<String, Context>, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Map<String, Context>... params) {
			boolean result = false;
			String address = (String) params[0].keySet().toArray()[0];
			String response = HttpUtil.sendHttpRequest(address);
			result = Utility.handleShowResponse(response, params[0].get(address));
			return result;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if(result){
				
			}
		}
	}
	
	
	static class ListTask extends AsyncTask<Map<String, Context>, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Map<String, Context>... params) {
			boolean result = false;
			String address = (String) params[0].keySet().toArray()[0];
			String response = HttpUtil.sendHttpRequest(address);
			result = Utility.handleListResponse(response, params[0].get(address));
			return result;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if(result){
				
			}
		}
	}
	
	public synchronized static List<SearchFood> searchFromServer(final Context context, String... params){
		String response = null;
		List<SearchFood> data = new ArrayList<SearchFood>();
		String searchAddress = Params.rootAddress+"search?page="+params[0]+"&limit="+params[1]+"&keyword="+params[2];
		response = HttpUtil.sendRequestWithHttpClient(searchAddress);
		data = Utility.handleSearchResponse(response);
		return data;
	}
	
}
