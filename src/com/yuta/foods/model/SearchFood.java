package com.yuta.foods.model;

public class SearchFood {
	private long id;
	private String name;
	private String content;
	
	public SearchFood(long id, String name, String content) {
		super();
		this.id = id;
		this.name = name;
		this.content = content;
	}

	public SearchFood() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
