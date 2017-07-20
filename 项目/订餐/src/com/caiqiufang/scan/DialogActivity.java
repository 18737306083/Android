package com.caiqiufang.scan;


import org.json.JSONArray;
import org.json.JSONObject;

import com.caiqiufang.config.Config;
import com.caiqiufang.utils.WebUtil;
import com.example.caiqiufang.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DialogActivity extends Activity {
	
	//tv_dialog
	private TextView tv_dialog;
	//vsProgress
	private  ViewGroup vsProgress;
	//bt_dialog
	private Button bt_dialog;
	//img_back
	private ImageView img_back;
	private String [] goodsInf ={"��Դ:","��������:","����:","����:","���ţ�"};
	private static StringBuffer buffer =null;
	private String name;
	//��������
	private String placenum;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogutils_activity);
		initView();
		setViewListener();
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		name = bundle.getString("name");
		placenum = bundle.getString("placenum");
		new FindSource().execute(name);
	}

	private void initView() {
		tv_dialog =(TextView)findViewById(R.id.tv_dialog);
		bt_dialog = (Button)findViewById(R.id.bt_dialog);
		img_back = (ImageView)findViewById(R.id.img_back);
	}
	
	private void setViewListener() {
		bt_dialog.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				MipcaActivityCapture.getCurrentActivity().finish();
			}
		});
		img_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				MipcaActivityCapture.getCurrentActivity().finish();
			}
		});
	}
	
	//��д�첽������
	private  class FindSource extends AsyncTask<String, Void, JSONArray>{
		
		@Override
		protected void onPreExecute() {
			
			if (vsProgress==null) {
				ViewStub vs = (ViewStub)findViewById(R.id.vsProgress);
				vsProgress = (ViewGroup) vs.inflate();
			}else{
				vsProgress.setVisibility(View.VISIBLE);	//����д�ʵ����ֱ�ӽ�����ʾ
			}
		}
		
		@Override
		protected JSONArray doInBackground(String... scanNames) {
			JSONObject jsonObject;
			JSONArray jsonArray;
			try {
				jsonArray = new JSONArray().put(new JSONObject().put("goodsId",
						scanNames[0]));
				//System.out.println("************"+scanNames[0]+"******************************************");
				JSONArray result = WebUtil.getJSONArrayByWeb(Config.METHOD_FINDSOURCE,
						jsonArray);
				
				if (result != null) {
					return result;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(JSONArray result) {
			//�жϽ��յĽ���������Ϊ�գ��������Ӧ�Ľ��ת��
			if (result != null) {
				if (vsProgress != null) {
					vsProgress.setVisibility(View.INVISIBLE);
				}
				
				buffer = new StringBuffer("\n");
				JSONObject jsonObject;
				try {
					for(int i = 0; i < result.length();i++){
						jsonObject = result.getJSONObject(i);
						buffer.append("����"+(i+1)+":").append(jsonObject.get("name")+"\n");
						buffer.append(goodsInf[0]).append(jsonObject.get("source")+"\n");
						buffer.append(goodsInf[1]).append(jsonObject.get("buydate")+"\n");
						buffer.append(goodsInf[2]).append(jsonObject.get("singleprice")+"\n");
						buffer.append(goodsInf[3]).append(jsonObject.get("num")+"\n"+"\n");
					}
					buffer.append(goodsInf[4]).append(placenum+"\n");
					tv_dialog.setText(buffer.toString());	//**********************************null
					//System.out.println("****************************************************************");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				//tv_dialog.setText("�Բ����ά����ڻ����ӷ�����ʧ�ܣ�");
				if (vsProgress != null) {
					vsProgress.setVisibility(View.INVISIBLE);
				}
			}
		}
	}
	
}
