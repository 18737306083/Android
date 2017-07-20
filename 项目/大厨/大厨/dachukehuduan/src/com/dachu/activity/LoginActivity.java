package com.dachu.activity;

import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.dachu.http.Asynck;
import com.future.dachu.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	TextView register;
	TextView forget;
	Button login;
	EditText passWord;
	EditText name;
     ProgressBar bar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		passWord = (EditText) findViewById(R.id.inputPassWords);
		name = (EditText) findViewById(R.id.inputNames);
       
		register = (TextView) findViewById(R.id.beginRegister);
		//forget = (TextView) findViewById(R.id.fogivePassWord);
		login = (Button) findViewById(R.id.loginSucsuss);
		// bar=(ProgressBar)findViewById(R.id.proBar);
		register.setOnClickListener(this);
		//forget.setOnClickListener(this);
		login.setOnClickListener(this);
		
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.beginRegister: /* 开始注册 */
			
			Intent itent = new Intent(LoginActivity.this,
					RegisterActivity.class);
			startActivity(itent);
			break;
		/* 开始登陆 */
		case R.id.loginSucsuss:
		//	bar.setVisibility(View.VISIBLE);
			
			String pass = passWord.getText().toString();
			String names = name.getText().toString();

			if (names.equals("") || names == null) {
				Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT)
						.show();
				//bar.setVisibility(View.GONE);
				return;
			}

			if (pass.equals("") || pass == null) {
				Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT)
						.show();
				//bar.setVisibility(View.GONE);
				return;
			}

			try {
				JSONObject js = getJSONdata();
				if(js!=null){
				String result = js.getString("login");

				if (result.equals("succuss")) {
					//bar.setVisibility(View.GONE);
					// 登录成功
					Toast.makeText(LoginActivity.this, "登录成功",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(LoginActivity.this,
							Listmain.class);
					startActivity(intent);
				}

				// 登录失败
				else {
					//bar.setVisibility(View.GONE);
					Toast.makeText(LoginActivity.this, "你输入的密码不正确，请重新输入",
							Toast.LENGTH_SHORT).show();

				}
				}
				else{
					Toast.makeText(LoginActivity.this, "登录失败!",
							Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		 
		default:
			break;
		}

	}

	public JSONObject getJSONdata() {
 
		try {
			Asynck asynck = new Asynck(LoginActivity.this, sendJSON());
			asynck.execute("http://10306.5avpn.com/OrderServer/cook/Cook_login");
			String response = asynck.get();

			JSONObject 	jsonAuth = new JSONObject(response);

			return jsonAuth;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public JSONObject sendJSON() {
		String pass = passWord.getText().toString();
		String names = name.getText().toString();
		try {
			JSONObject person = new JSONObject();
			person.put("name", names);
			person.put("passWord", pass);
			return person;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

}
