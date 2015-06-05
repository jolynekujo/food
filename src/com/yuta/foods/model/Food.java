package com.yuta.foods.model;

public class Food {
	//ʳ���ID
	private long foodId;
	//ʳ������
	private String foodName;
	//ͼƬ��ַ
	private String img;
	//ʳ���ǩ
	private String tag;
	//ʳ�����
	private String material;
	//ʳ������
	private String message;
	//���������
	private int count;
	
	public Food(){}
	
	/*
	 * ���������ṩȫ������
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
	 * setter��getter����
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
