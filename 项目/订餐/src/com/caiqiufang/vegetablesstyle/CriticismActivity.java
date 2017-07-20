package com.caiqiufang.vegetablesstyle;

import com.example.caiqiufang.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class CriticismActivity extends Activity {
	
	private ImageView img_criticism_back;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_ctriticism);
		
		initView();
		setViewListener();
	}
	
	public  void initView(){
		img_criticism_back = (ImageView)findViewById(R.id.img_criticism_back);
	}
	
	public void setViewListener(){
		img_criticism_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
}
