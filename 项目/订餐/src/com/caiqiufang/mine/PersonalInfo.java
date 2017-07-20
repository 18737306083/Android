package com.caiqiufang.mine;

import com.example.caiqiufang.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class PersonalInfo extends Activity {
	//imv_my_info_back
	private ImageView imv_my_info_back;
	private Button bt_not_login;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_info_activity);
		initView();
		initViewListener();
	}

	/**
	 * ���ܣ���ʼ�����иý�������view
	 */
	private void initView() {
		imv_my_info_back = (ImageView)findViewById(R.id.imv_my_info_back);
		bt_not_login = (Button)findViewById(R.id.bt_not_login);
	}
	
	/**
	 * ���ܣ�Ϊ�ý����е������� ��Ӧ�ļ���
	 */
	private void initViewListener() {
		
		imv_my_info_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();		//������ǰ�Ķ�Activity�����ص������ҳ��	
			}
		});
		bt_not_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonalInfo.this,LoginActivity.class);
				startActivity(intent);
			}
		});
		
	}
}
