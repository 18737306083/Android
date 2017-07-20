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
	
	


	//��ʼ����������Ľ��������е����
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
	
	//�ṩһ�������ĵķ��������ⲿʹ����ʵ������Activity�е�bitmap
	public static void InstanceBitmap(Bitmap bitmap){
		DetailedIntroduceActivity.bitmap = bitmap;
	}
	
	//�õ�����д�����������
	private void getAndSetData() {
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("data");
		if (bundle!=null) {
			delicious_name.setText(bundle.getString("name"));
			detailed_price.setText(bundle.getString("price"));
			detailed_desc.setText(bundle.getString("desc"));	
		}else{
			delicious_name.setText("��������ʾ...");
			detailed_price.setText(bundle.getString("��������ʾ..."));
			detailed_desc.setText(bundle.getString("��������ʾ..."));	
		}
		
		
	}

	
	//�����е�������ϼ���
	private void initViewListener() {
		img_criticism_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//�رյ�ǰ��Activity
				finish();
			}
		});
		
		//���ü������İ�ť
		iv_minus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int num = Integer.parseInt(et_nums.getText().toString());
				if (num==1) {
					showMessage("�Բ������ٹ���һ�ݣ�");
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
					showMessage("�������˼Ӻð�ť");
			}
		});
		//����˵��ļ������������������߼��Ĵ���
		addInMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showMessage("���Ѿ����ò�������˵���");
				Map<String, Object> map = new HashMap<String, Object>();
				if (!MyMenu.isExistVs(delicious_name.getText().toString())) {
					map.put("name", delicious_name.getText().toString());
					map.put("price", detailed_price.getText().toString());
					map.put("num", et_nums.getText().toString());
					//�õ��˵�
					ArrayList<Map<String, Object>> menu = (ArrayList<Map<String, Object>>) MyMenu.getListItems();
					//���ѡ�Ĳ˵��˵�
					menu.add(map);
					MyMenu.setListItems(menu);
				}else{			//����˵��Ѿ����ڸ��ֲ�
					MyMenu.setExistVsNum(delicious_name.getText().toString(), 
							MyMenu.getExistVsNum(delicious_name.getText().toString()),
							et_nums.getText().toString());
				}
				//��������ǰ��Activity
				finish();
			}
		});
	}
	public void showMessage(String mess){
		Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_SHORT).show();
	}
}
