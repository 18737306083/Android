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
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * @author ���
 *���ܣ���������ע����ĳ����ע���µ��û�
 *create time 2016-8-16
 */
public class RegisterActivity extends Activity {
	
	//img_back
	private ImageView img_back;
	//tgbtn_show_psw
	private ToggleButton tgbtn_show_psw;
	//edit_psw
	private EditText edit_psw;
	//edit_uid
	private EditText edit_uid;
	//img_register_clear_uid
	private ImageView img_register_clear_uid;
	//img_register_clear_psw
	private ImageView img_register_clear_psw;
	//btn_register
	private Button btn_register;
	//vsProgress
	private ViewGroup vsProgress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);
		initView();
		initViewListener();
	}
	
	private void initView() {
		img_back = (ImageView)findViewById(R.id.img_back);
		tgbtn_show_psw = (ToggleButton)findViewById(R.id.tgbtn_show_psw);
		edit_psw = (EditText)findViewById(R.id.edit_psw);
		edit_uid = (EditText)findViewById(R.id.edit_uid);
		img_register_clear_uid = (ImageView)findViewById(R.id.img_register_clear_uid);
		img_register_clear_psw = (ImageView)findViewById(R.id.img_register_clear_psw);
		btn_register = (Button)findViewById(R.id.btn_register);
		
	}
	//��ʼ����������ļ���
	private void initViewListener() {
		img_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tgbtn_show_psw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
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
				img_register_clear_uid.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			
			@Override
			public void afterTextChanged(Editable arg0) {}
		});
		//��userId����������ͼƬ��Ӽ��������û������ImageViewʱ���������е�����
		img_register_clear_uid.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				edit_uid.setText("");
			}
		});
		
		edit_psw.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				//�������������������ʱ������id���������ͼƬΪ����ʾ���������ͼƬ��ʾ�����õ�½��ť����
				img_register_clear_uid.setVisibility(View.INVISIBLE);
				img_register_clear_psw.setVisibility(View.VISIBLE);
				btn_register.setEnabled(true);
			}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			
			@Override
			public void afterTextChanged(Editable edit) {}
		
		});
		
		img_register_clear_psw.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				edit_psw.setText("");
			}
		});
		
		//��ע�ᰴť��Ӽ���
		btn_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Toast.makeText(getApplicationContext(), "��������ע�ᰴť", Toast.LENGTH_SHORT).show();
				String rgusername = edit_uid.getText()
						.toString().trim();
				String rgpassword = edit_psw.getText()
						.toString().trim();
				//�ͻ�������У��
				if ("".equals(rgusername)) {
					Toast.makeText(RegisterActivity.this, "������д�û���",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if ("".equals(rgpassword)) {
					Toast.makeText(RegisterActivity.this, "������д����",
							Toast.LENGTH_SHORT).show();
					return;
				}
				//����Ѿ���д���û��������룬ִ�е�¼����
				executeRegister(rgusername, rgpassword);
			}
		});
	}
	private void executeRegister(String rgusername, String rgpassword) {
		
		new RegisterTask().execute(rgusername,rgpassword);		//ִ���첽����
	}
	
private class RegisterTask extends AsyncTask<String, Void, 	Integer>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//���ע�ᰴťʱ��ʾ����ִ������
			if (vsProgress==null) {
				ViewStub vs = (ViewStub)findViewById(R.id.vsProgress);
				vsProgress = (ViewGroup) vs.inflate();
			}else{
				vsProgress.setVisibility(View.VISIBLE);	//����д�ʵ����ֱ�ӽ�����ʾ
			}
		}
		
		@Override
		protected Integer doInBackground(String... params) {
			Integer result = null;	//���������ķ�����
			JSONArray reqValue;
			try {
				//���û����������װ��JSONArray�У�����HTTPͨ��
				reqValue = new JSONArray().put(new JSONObject().put("username",
						params[0]).put("password", params[1]));
				JSONArray rec = WebUtil.getJSONArrayByWeb(Config.METHOD_REGISTER,
						reqValue);
				if (rec != null) {//������صĽ����Ϊ��
					result = rec.getJSONObject(0).getInt("id");
				}
			}catch(JSONException e){
				
			} 
			
			return result;
		}
		//��ʱ����ִ�����ʱ���ø÷��������Ѻ�ʱ�����õ�ֵ�����˷���
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			//�ص�
			onRegisterComplete(result);
		}
	}
	
	public void onRegisterComplete(Integer id) {
		if (id == null||id ==-1) {//���û�л�ȡ�����룬˵��ע��ʧ��ʧ��
			show("���������󣡣��������Ժ����ԣ�");
			if (vsProgress != null) {
				vsProgress.setVisibility(View.INVISIBLE);
			}
			return;
		}else if (id ==0) {
			show("���û������ڣ��������ĺ��ԣ�");
			if (vsProgress != null) {
				vsProgress.setVisibility(View.INVISIBLE);
			}
		}else{
			show("ע��ɹ�������");
			if (vsProgress != null) {
				vsProgress.setVisibility(View.INVISIBLE);
			}
			finish();
		}
		
		if (vsProgress != null) {
			vsProgress.setVisibility(View.INVISIBLE);
		}
	}
	public void show (String show){
		Toast.makeText(RegisterActivity.this, show, Toast.LENGTH_SHORT)
		.show();
	}
}
