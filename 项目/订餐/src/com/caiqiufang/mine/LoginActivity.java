package com.caiqiufang.mine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.caiqiufang.config.Config;
import com.caiqiufang.utils.WebUtil;
import com.example.caiqiufang.R;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class LoginActivity extends Activity {
	//img_back
	private ImageView img_back;
	//tgbtn_show_psw
	private ToggleButton tgbtn_show_psw;
	//edit_psw
	private EditText edit_psw,edit_uid;
	//img_login_clear_uid
	private ImageView img_login_clear_uid,img_login_clear_psw;
	//tv_quick_sign_up
	private TextView tv_quick_sign_up;
	//btn_login
	private Button btn_login;
	private ViewGroup vsProgress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//����Ҫ��¼�Ľ���
		setContentView(R.layout.login_activity);
		initView();
		initViewListener();
	}
	//��ʼ����������
	private void initView() {
		img_back = (ImageView)findViewById(R.id.img_back);
		tgbtn_show_psw = (ToggleButton)findViewById(R.id.tgbtn_show_psw);
		edit_uid = (EditText)findViewById(R.id.edit_uid);
		img_login_clear_uid = (ImageView)findViewById(R.id.img_login_clear_uid);
		edit_psw = (EditText)findViewById(R.id.edit_psw);
		img_login_clear_psw = (ImageView)findViewById(R.id.img_login_clear_psw);
		btn_login = (Button)findViewById(R.id.btn_login);
		tv_quick_sign_up = (TextView)findViewById(R.id.tv_quick_sign_up);
	}

	private void initViewListener() {
		//���ؼ��ļ���
		img_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		tgbtn_show_psw.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton button, boolean isChecked) {
				if (isChecked) {
					//��ʾ����
					edit_psw.setTransformationMethod(
							HideReturnsTransformationMethod.getInstance());
				}else{
					//��������
					edit_psw.setTransformationMethod(
							PasswordTransformationMethod.getInstance());
				}
			}
		});
		
		//����������addTextChangeListener �������������ַ�ʱ����ɾ��ͼƬ����
		edit_uid.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				img_login_clear_uid.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			
			@Override
			public void afterTextChanged(Editable arg0) {}
		});
		//��userId����������ͼƬ��Ӽ��������û������ImageViewʱ���������е�����
		img_login_clear_uid.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				edit_uid.setText("");
			}
		});
		
		edit_psw.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				//�������������������ʱ������id���������ͼƬΪ����ʾ���������ͼƬ��ʾ�����õ�½��ť����
				img_login_clear_uid.setVisibility(View.INVISIBLE);
				img_login_clear_psw.setVisibility(View.VISIBLE);
				btn_login.setEnabled(true);
			}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			
			@Override
			public void afterTextChanged(Editable edit) {}
		
		});
		
		img_login_clear_psw.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				edit_psw.setText("");
			}
		});
		
		tv_quick_sign_up.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
		btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String username = edit_uid.getText()
						.toString().trim();
				String password = edit_psw.getText()
						.toString().trim();
				
				if ("".equals(username)) {
					Toast.makeText(LoginActivity.this, "�û�������Ϊ��",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if ("".equals(password)) {
					Toast.makeText(LoginActivity.this, "���벻��Ϊ��",
							Toast.LENGTH_SHORT).show();
					return;
				}
				//ִ�е�¼
				executeLogin(username, password);
			}
		});
	}
	
	private void onLoginComplete(Integer userId) {			
		if (userId == null || userId == 0) {
			Toast.makeText(LoginActivity.this, "�Բ����û��������������", Toast.LENGTH_SHORT)
					.show();
			if (vsProgress != null) {
				vsProgress.setVisibility(View.INVISIBLE);
			}
			return;
		}
		if (vsProgress != null) {
			vsProgress.setVisibility(View.INVISIBLE);
		}
		
		Toast.makeText(LoginActivity.this, "��¼�ɹ�������", Toast.LENGTH_SHORT).show();
		
		//�ڴ�����¼�ɹ���Ӧ���������ķ�Ӧ�����磬���û�ӭ���±ߵ��û���***************************************
	}
	
	//������������������룬�����첽������ִ�е�¼
	private void executeLogin(String username, String password) {
		new LoginTask().execute(username, password);
	}
	
	//ʹ���첽���ط�������
	private class LoginTask extends AsyncTask<String, Void, Integer> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			if (vsProgress == null) {
				ViewStub vs = (ViewStub) findViewById(R.id.vsProgress);
				vsProgress = (ViewGroup) vs.inflate();
			} else {
				vsProgress.setVisibility(View.VISIBLE);
			}
		}
			
		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub
			Integer result = null;
			JSONArray reqValue;
			try {
				//����post����
				reqValue = new JSONArray().put(new JSONObject().put("username",
						params[0]).put("password", params[1]));
				JSONArray rec = WebUtil.getJSONArrayByWeb(Config.METHOD_LOGIN,
						reqValue);		
				if (rec != null) {//������صĽ����Ϊ�գ����õ����ص�userid
					result = rec.getJSONObject(0).getInt("userId");
				} 
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stubd
			super.onPostExecute(result);
			//�õ������ص���¼��ɴ����ؽ���ķ���
			onLoginComplete(result);
		}
	}
	
}
