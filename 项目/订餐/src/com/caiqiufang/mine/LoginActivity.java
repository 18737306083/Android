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
		//加载要登录的界面
		setContentView(R.layout.login_activity);
		initView();
		initViewListener();
	}
	//初始化界面的组件
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
		//返回键的监听
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
				img_login_clear_uid.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			
			@Override
			public void afterTextChanged(Editable arg0) {}
		});
		//给userId输入框后的清除图片添加监听，当用户点击该ImageView时清空输入框中的内容
		img_login_clear_uid.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				edit_uid.setText("");
			}
		});
		
		edit_psw.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				//当密码输入框中有输入时，设置id输入框的清除图片为不显示，密码清除图片显示，设置登陆按钮可用
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
					Toast.makeText(LoginActivity.this, "用户名不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if ("".equals(password)) {
					Toast.makeText(LoginActivity.this, "密码不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				}
				//执行登录
				executeLogin(username, password);
			}
		});
	}
	
	private void onLoginComplete(Integer userId) {			
		if (userId == null || userId == 0) {
			Toast.makeText(LoginActivity.this, "对不起，用户名或者密码错误", Toast.LENGTH_SHORT)
					.show();
			if (vsProgress != null) {
				vsProgress.setVisibility(View.INVISIBLE);
			}
			return;
		}
		if (vsProgress != null) {
			vsProgress.setVisibility(View.INVISIBLE);
		}
		
		Toast.makeText(LoginActivity.this, "登录成功！！！", Toast.LENGTH_SHORT).show();
		
		//在此填充登录成功后应该做做出的反应，比如，设置欢迎您下边的用户名***************************************
	}
	
	//传入输入的姓名和密码，调用异步加载类执行登录
	private void executeLogin(String username, String password) {
		new LoginTask().execute(username, password);
	}
	
	//使用异步加载访问网络
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
				//发送post请求
				reqValue = new JSONArray().put(new JSONObject().put("username",
						params[0]).put("password", params[1]));
				JSONArray rec = WebUtil.getJSONArrayByWeb(Config.METHOD_LOGIN,
						reqValue);		
				if (rec != null) {//如果返回的结果不为空，则拿到返回的userid
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
			//拿到结果后回调登录完成处理返回结果的方法
			onLoginComplete(result);
		}
	}
	
}
