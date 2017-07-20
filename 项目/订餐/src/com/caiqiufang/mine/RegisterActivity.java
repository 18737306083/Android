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
 * @author 秋放
 *功能：此类用于注册在某个店注册新的用户
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
	//初始化界面组件的监听
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
					//显示密码
					edit_psw.setTransformationMethod(
							HideReturnsTransformationMethod.getInstance());
					
				}else{
					//隐藏密码
					edit_psw.setTransformationMethod(
							PasswordTransformationMethod.getInstance());
				}
			}
		});
		
		//给输入框添加addTextChangeListener 监听，当输入字符时设置删除图片出现
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
		//给userId输入框后的清除图片添加监听，当用户点击该ImageView时清空输入框中的内容
		img_register_clear_uid.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				edit_uid.setText("");
			}
		});
		
		edit_psw.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				//当密码输入框中有输入时，设置id输入框的清除图片为不显示，密码清除图片显示，设置登陆按钮可用
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
		
		//给注册按钮添加监听
		btn_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Toast.makeText(getApplicationContext(), "您单击了注册按钮", Toast.LENGTH_SHORT).show();
				String rgusername = edit_uid.getText()
						.toString().trim();
				String rgpassword = edit_psw.getText()
						.toString().trim();
				//客户端自行校验
				if ("".equals(rgusername)) {
					Toast.makeText(RegisterActivity.this, "请您填写用户名",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if ("".equals(rgpassword)) {
					Toast.makeText(RegisterActivity.this, "请您填写密码",
							Toast.LENGTH_SHORT).show();
					return;
				}
				//如果已经填写了用户名和密码，执行登录操作
				executeRegister(rgusername, rgpassword);
			}
		});
	}
	private void executeRegister(String rgusername, String rgpassword) {
		
		new RegisterTask().execute(rgusername,rgpassword);		//执行异步任务
	}
	
private class RegisterTask extends AsyncTask<String, Void, 	Integer>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//点击注册按钮时显示任务执行提醒
			if (vsProgress==null) {
				ViewStub vs = (ViewStub)findViewById(R.id.vsProgress);
				vsProgress = (ViewGroup) vs.inflate();
			}else{
				vsProgress.setVisibility(View.VISIBLE);	//如果有此实例，直接进行显示
			}
		}
		
		@Override
		protected Integer doInBackground(String... params) {
			Integer result = null;	//发送请求后的返回码
			JSONArray reqValue;
			try {
				//将用户名和密码封装到JSONArray中，进行HTTP通信
				reqValue = new JSONArray().put(new JSONObject().put("username",
						params[0]).put("password", params[1]));
				JSONArray rec = WebUtil.getJSONArrayByWeb(Config.METHOD_REGISTER,
						reqValue);
				if (rec != null) {//如果返回的结果不为空
					result = rec.getJSONObject(0).getInt("id");
				}
			}catch(JSONException e){
				
			} 
			
			return result;
		}
		//耗时任务执行完毕时调用该方法，并把耗时任务获得的值传给此方法
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			//回调
			onRegisterComplete(result);
		}
	}
	
	public void onRegisterComplete(Integer id) {
		if (id == null||id ==-1) {//如果没有获取返回码，说明注册失败失败
			show("服务器错误！！！请您稍后重试！");
			if (vsProgress != null) {
				vsProgress.setVisibility(View.INVISIBLE);
			}
			return;
		}else if (id ==0) {
			show("此用户名存在，请您更改后尝试！");
			if (vsProgress != null) {
				vsProgress.setVisibility(View.INVISIBLE);
			}
		}else{
			show("注册成功！！！");
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
