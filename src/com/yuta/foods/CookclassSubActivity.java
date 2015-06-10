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
	private String[] cookclass1 = new String[]{"美容","养颜","排毒","美白","抗皱","祛斑","黑眼圈","祛痘","润肤","保湿","护眼"};
	private String[] cookclass2 = new String[]{"瘦身","减肥","瘦脸","瘦腿","瘦腰","瘦臀","丰胸"};
	private String[] cookclass3 = new String[]{"补钙","补血","补铁","润肺","养肝","养胃","补肾","健脾","降火","降压","开胃","御寒","补脑","明目","滋阴","壮阳","益寿","益智","防癌","清肠","止咳","护眼","护肝","润燥","补铁","养血","解暑","清热","防辐射","长个子","提高免疫力","增强记忆力","补锌","抗衰老","养肾","护肝"};
	private String[] cookclass4 = new String[]{"宝宝","女性","孕妇","产妇","男人","考生","白领","胎儿","老年人"};
	private String[] cookclass5 = new String[]{"春天","夏天","秋天","冬天","节时","早餐","晚餐","夜宵","睡前","饭前","空腹"};
	private String[] cookclass6 = new String[]{"怀孕前","准备怀孕","不孕","怀孕","孕妇","孕吐","妊娠呕吐","妊娠水肿","安胎","怀孕初期","保胎","产妇","坐月子","产后","哺乳期","乳腺炎","下奶","产后恶露不尽","产后便秘"};
	private String[] cookclass7 = new String[]{"盆腔炎","白带异常","乳腺增生","子宫出血","来月经","来例假","月经期间","月经不调","痛经","闭经","月经提前","经期","月经推迟","月经过少","月经量少","月经过多","月经量多","更年期"};
	private String[] cookclass8 = new String[]{"早泄","遗精","肾虚","阳痿","阴虚","壮阳","肾阳虚","肾阴虚","前列腺炎","死精子过多","精子活力低下","不育"};
	private String[] cookclass9 = new String[]{"冠心病","心脏病","心悸","高血压","贫血","血脂高","血糖高","血压高"};
	private String[] cookclass10 = new String[]{"皮肤粗糙","皮肤干燥","皮肤瘙痒","皮肤松弛","雀斑","眼睛","牙齿","大脑","心血管","血糖高","前列腺"};
	private String[] cookclass11 = new String[]{"便秘","口臭","胃痛","胃病","腹泻","痔疮","胃寒","结肠炎","胃溃疡","消化不良","胃酸过多","小儿腹泻","肠炎"};
	private String[] cookclass12 = new String[]{"口腔溃疡","牙痛","感冒","咳嗽","鼻塞","鼻炎","咽炎","喉咙痛","哮喘","肺炎","肺气肿","肺结核","气管炎","支气管炎","急性支气管炎","慢性支气管炎","支气管哮喘"};
	private String[] cookclass13 = new String[]{"腰痛","风湿","颈椎病","关节炎","骨质疏松","骨折","睡眠不好","偏头痛","神经衰弱","失眠"};
	private String[] cookclass14 = new String[]{"近视","甲亢","上火","白内障","湿疹","呕吐","尿频","水肿","痛风","解酒","过敏","湿热","中暑","内分泌失调","肺癌","肝癌","乳腺癌","胃癌","宫颈癌","食管癌","化疗","膀胱癌","皮肤癌"};
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
