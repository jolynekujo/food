package com.yuta.foods;

import com.yuta.foods.util.MyApplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CookclassSubActivity extends BaseActivity implements OnItemClickListener{
	private String[] cookclass1 = new String[]{"����","����","�Ŷ�","����","����","���","����Ȧ","�","���","��ʪ","����"};
	private String[] cookclass2 = new String[]{"����","����","����","����","����","����","����"};
	private String[] cookclass3 = new String[]{"����","��Ѫ","����","���","����","��θ","����","��Ƣ","����","��ѹ","��θ","����","����","��Ŀ","����","׳��","����","����","����","�峦","ֹ��","����","����","����","����","��Ѫ","����","����","������","������","���������","��ǿ������","��п","��˥��","����","����"};
	private String[] cookclass4 = new String[]{"����","Ů��","�и�","����","����","����","����","̥��","������"};
	private String[] cookclass5 = new String[]{"����","����","����","����","��ʱ","���","���","ҹ��","˯ǰ","��ǰ","�ո�"};
	private String[] cookclass6 = new String[]{"����ǰ","׼������","����","����","�и�","����","����Ż��","����ˮ��","��̥","���г���","��̥","����","������","����","������","������","����","�����¶����","�������"};
	private String[] cookclass7 = new String[]{"��ǻ��","�״��쳣","��������","�ӹ���Ѫ","���¾�","������","�¾��ڼ�","�¾�����","ʹ��","�վ�","�¾���ǰ","����","�¾��Ƴ�","�¾�����","�¾�����","�¾�����","�¾�����","������"};
	private String[] cookclass8 = new String[]{"��й","�ž�","����","����","����","׳��","������","������","ǰ������","�����ӹ���","���ӻ�������","����"};
	private String[] cookclass9 = new String[]{"���Ĳ�","���ಡ","�ļ�","��Ѫѹ","ƶѪ","Ѫ֬��","Ѫ�Ǹ�","Ѫѹ��"};
	private String[] cookclass10 = new String[]{"Ƥ���ֲ�","Ƥ������","Ƥ������","Ƥ���ɳ�","ȸ��","�۾�","����","����","��Ѫ��","Ѫ�Ǹ�","ǰ����"};
	private String[] cookclass11 = new String[]{"����","�ڳ�","θʹ","θ��","��к","�̴�","θ��","�᳦��","θ����","��������","θ�����","С����к","����"};
	private String[] cookclass12 = new String[]{"��ǻ����","��ʹ","��ð","����","����","����","����","����ʹ","����","����","������","�ν��","������","֧������","����֧������","����֧������","֧��������"};
	private String[] cookclass13 = new String[]{"��ʹ","��ʪ","��׵��","�ؽ���","��������","����","˯�߲���","ƫͷʹ","��˥��","ʧ��"};
	private String[] cookclass14 = new String[]{"����","�׿�","�ϻ�","������","ʪ��","Ż��","��Ƶ","ˮ��","ʹ��","���","����","ʪ��","����","�ڷ���ʧ��","�ΰ�","�ΰ�","���ٰ�","θ��","������","ʳ�ܰ�","����","���װ�","Ƥ����"};
	private int[] countPerId = new int[]{20,30,37,73,82,93,112,129,137,145,156,169,186,196};
	Intent intent;
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ListActivity.startActivity(parent.getContext(), getCookclassSubId(position));
	}
	
	private int getCookclassSubId(int position){
		int foodId = intent.getIntExtra("cookclass_id", 0);
		Log.d("getCookclassSubId", foodId+"");
		int subFoodId = countPerId[foodId] + position;
		return subFoodId;
	}

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.cookclass_layout);
		ListView listView = (ListView)findViewById(R.id.cookclass_main);
		intent = getIntent();
		int data = intent.getIntExtra("cookclass_id", 0);
		ArrayAdapter<String> adapter = null;
		switch (data){
		case 0:
			adapter = new ArrayAdapter<String>(CookclassSubActivity.this, android.R.layout.simple_list_item_1, cookclass1);
			break;
		case 1:
			adapter = new ArrayAdapter<String>(CookclassSubActivity.this, android.R.layout.simple_list_item_1, cookclass2);
			break;
		case 2:
			adapter = new ArrayAdapter<String>(CookclassSubActivity.this, android.R.layout.simple_list_item_1, cookclass3);
			break;
		case 3:
			adapter = new ArrayAdapter<String>(CookclassSubActivity.this, android.R.layout.simple_list_item_1, cookclass4);
			break;
		case 4:
			adapter = new ArrayAdapter<String>(CookclassSubActivity.this, android.R.layout.simple_list_item_1, cookclass5);
			break;
		case 5:
			adapter = new ArrayAdapter<String>(CookclassSubActivity.this, android.R.layout.simple_list_item_1, cookclass6);
			break;
		case 6:
			adapter = new ArrayAdapter<String>(CookclassSubActivity.this, android.R.layout.simple_list_item_1, cookclass7);
			break;
		case 7:
			adapter = new ArrayAdapter<String>(CookclassSubActivity.this, android.R.layout.simple_list_item_1, cookclass8);
			break;
		case 8:
			adapter = new ArrayAdapter<String>(CookclassSubActivity.this, android.R.layout.simple_list_item_1, cookclass9);
			break;
		case 9:
			adapter = new ArrayAdapter<String>(CookclassSubActivity.this, android.R.layout.simple_list_item_1, cookclass10);
			break;
		case 10:
			adapter = new ArrayAdapter<String>(CookclassSubActivity.this, android.R.layout.simple_list_item_1, cookclass11);
			break;
		case 11:
			adapter = new ArrayAdapter<String>(CookclassSubActivity.this, android.R.layout.simple_list_item_1, cookclass12);
			break;
		case 12:
			adapter = new ArrayAdapter<String>(CookclassSubActivity.this, android.R.layout.simple_list_item_1, cookclass13);
			break;
		case 13:
			adapter = new ArrayAdapter<String>(CookclassSubActivity.this, android.R.layout.simple_list_item_1, cookclass14);
			break;
		default:
			break;
		}
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}
	
	public static void startActivity(Context context, int data){
		Intent intent = new Intent(context, CookclassSubActivity.class);
		intent.putExtra("cookclass_id", data);
		context.startActivity(intent);
	}
}
