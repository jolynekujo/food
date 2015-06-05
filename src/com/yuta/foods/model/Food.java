package com.yuta.foods.model;

public class Food {
	//食物的ID
	private long foodId;
	//食物名称
	private String foodName;
	//图片地址
	private String img;
	//食物标签
	private String tag;
	//食物材料
	private String material;
	//食物做法
	private String message;
	//菜谱浏览数
	private int count;
	
	public Food(){}
	
	/*
	 * 构造器，提供全部参数
	 */
	public Food(long foodId, String foodName, String img, String tag, String material, String message, int count){
		this.foodId = foodId;
		this.foodName = foodName;
		this.img = img;
		this.tag = tag;
		this.material = material;
		this.message = message;
		this.count = count;
	}
	
	/*
	 * setter和getter方法
	 */
	public void setFoodId(long foodId){
		this.foodId = foodId;
	}
	public void setFoodName(String foodName){
		this.foodName = foodName;
	}
	public void setImg(String img){
		this.img = img;
	}
	public void setTag(String tag){
		this.tag = tag;
	}
	public void setMaterial(String material){
		this.material = material;
	}
	public void setMessage(String message){
		this.message = message;
	}
	public void setCount(int count){
		this.count = count;
	}
	
	public long getFoodId(){
		return foodId;
	}
	public String getFoodName(){
		return foodName;
	}
	public String getImg(){
		return img;
	}
	public String getTag(){
		return tag;
	}
	public String getMaterial(){
		return material;
	}
	public String getMessage(){
		return message;
	}
	public int getCount(){
		return count;
	}
}
