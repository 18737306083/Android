package com.caiqiufang.mine;


import com.example.caiqiufang.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MyOrderFormActivity extends Activity {
	//img_order_form_back
	private ImageView img_order_form_back;
	//bt_not_login
	private Button bt_not_login;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_order_form_activity);
		initView();
		initViewListener();
	}

	/**
	 * 功能：初始化所有该界面的组件view
	 */
	private void initView() {
		img_order_form_back = (ImageView)findViewById(R.id.img_order_form_back);
		bt_not_login = (Button)findViewById(R.id.bt_not_login);
	}
	
	/**
	 * 功能：为该界面中的组件添加 相应的监听
	 */
	private void initViewListener() {
		
		img_order_form_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();		//结束当前的饿Activity，返回到进入的页面	
			}
		});
		bt_not_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyOrderFormActivity.this,LoginActivity.class);
				startActivity(intent);
			}
		});
		
	}
}
