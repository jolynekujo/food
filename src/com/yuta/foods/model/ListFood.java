package com.yuta.foods.model;

public class ListFood {
	private String img;
	private String name;
	private String tag;
	private int defImg;
	
	public ListFood(){}
	
	public ListFood(String img, String name, String tag){
		this.img = img;
		this.name = name;
		this.tag = tag;
	}
	
	public void setDefImg(int defImg){
		this.defImg = defImg;
	}
	public int getDefImg(){
		return defImg;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
