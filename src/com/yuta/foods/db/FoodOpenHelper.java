package com.yuta.foods.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodOpenHelper extends SQLiteOpenHelper{
	//����ʳ������ݱ�
	private static final String CREATE_FOOD = "create table food("
			+"food_id integer, "
			+"food_name text, "
			+"img text, "
			+"tag text, "
			+"material text, "
			+"message text, "
			+"count integer)";
//	���������õ���content�Ͷ�Ӧid�����ݱ�
//	private static final String CREATE_SEARCH = "create table content("
//			+"food_id integer, "
//			+"content text)";
	
	public FoodOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_FOOD);
//		db.execSQL(CREATE_SEARCH);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
