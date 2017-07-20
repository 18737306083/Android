package com.caiqiufang.vegetablesstyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.caiqiufang.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailedIntroduceActivity extends Activity {
	private static  Bitmap bitmap;
	private ImageView detailed_picture;
	private ImageView img_criticism_back;
	//delicious_name
	private TextView delicious_name;
	//detailed_price
	private TextView detailed_price;
	//detailed_desc 
	private TextView detailed_desc;
	//et_nums
	private TextView et_nums;
	//iv_minus 
	private ImageView  iv_minus;
	//iv_plus
	private ImageView iv_plus;
	//bt_submit
	private Button addInMenu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailed_introduce);
		initView();
		getAndSetData();
		initViewListener();
		
	}
	
	


	//初始化该类解析的界面中所有的组件
	private void initView() {
		detailed_picture = (ImageView)findViewById(R.id.detailed_picture);
		detailed_picture.setImageBitmap(bitmap);
		img_criticism_back = (ImageView)findViewById(R.id.img_criticism_back);
		delicious_name = (TextView)findViewById(R.id.delicious_name);
		detailed_price = (TextView)findViewById(R.id.detailed_price);
		detailed_desc = (TextView)findViewById(R.id.detailed_desc);
		et_nums = (TextView)findViewById(R.id.et_nums);
		iv_minus = (ImageView)findViewById(R.id.iv_minus);
		iv_plus = (ImageView)findViewById(R.id.iv_plus);
		addInMenu =(Button)findViewById(R.id.bt_submit);
		
	}
	
	//提供一个公共的的方法，供外部使用来实例化此Activity中的bitmap
	public static void InstanceBitmap(Bitmap bitmap){
		DetailedIntroduceActivity.bitmap = bitmap;
	}
	
	//拿到主活动中传过来的数据
	private void getAndSetData() {
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("data");
		if (bundle!=null) {
			delicious_name.setText(bundle.getString("name"));
			detailed_price.setText(bundle.getString("price"));
			detailed_desc.setText(bundle.getString("desc"));	
		}else{
			delicious_name.setText("无数据显示...");
			detailed_price.setText(bundle.getString("无数据显示..."));
			detailed_desc.setText(bundle.getString("无数据显示..."));	
		}
		
		
	}

	
	//给所有的组件加上监听
	private void initViewListener() {
		img_criticism_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//关闭当前的Activity
				finish();
			}
		});
		
		//设置减数量的按钮
		iv_minus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int num = Integer.parseInt(et_nums.getText().toString());
				if (num==1) {
					showMessage("对不起！最少购买一份！");
				}else {
					num--;
					et_nums.setText(num+"");
				}
			}
		});
		iv_plus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int num = Integer.parseInt(et_nums.getText().toString());
				num++;
					et_nums.setText(num+"");
					showMessage("您单击了加好按钮");
			}
		});
		//加入菜单的监听，并在其中做出逻辑的处理
		addInMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showMessage("您已经将该餐饮加入菜单！");
				Map<String, Object> map = new HashMap<String, Object>();
				if (!MyMenu.isExistVs(delicious_name.getText().toString())) {
					map.put("name", delicious_name.getText().toString());
					map.put("price", detailed_price.getText().toString());
					map.put("num", et_nums.getText().toString());
					//拿到菜单
					ArrayList<Map<String, Object>> menu = (ArrayList<Map<String, Object>>) MyMenu.getListItems();
					//添加选的菜到菜单
					menu.add(map);
					MyMenu.setListItems(menu);
				}else{			//如果菜单已经存在该种菜
					MyMenu.setExistVsNum(delicious_name.getText().toString(), 
							MyMenu.getExistVsNum(delicious_name.getText().toString()),
							et_nums.getText().toString());
				}
				//结束掉当前的Activity
				finish();
			}
		});
	}
	public void showMessage(String mess){
		Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_SHORT).show();
	}
}
