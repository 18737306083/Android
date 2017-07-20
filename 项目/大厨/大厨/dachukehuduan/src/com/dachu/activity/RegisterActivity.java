package com.dachu.activity;

import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.dachu.http.Asynck;
import com.future.dachu.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {
	EditText etPone;
	EditText etPassWord;
	EditText etNextPass;
	ImageView exeit;
	Button complete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		exeit = (ImageView) findViewById(R.id.exeit);
		complete = (Button) findViewById(R.id.complete);
		exeit.setOnClickListener(this);
		complete.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		etPone = (EditText) findViewById(R.id.etPhone);
		String etpone = etPone.getText().toString().trim();
		etPassWord = (EditText) findViewById(R.id.etPassWord);
		String etpass = etPassWord.getText().toString().trim();
		etNextPass = (EditText) findViewById(R.id.etNextPassWord);
		String etnextPass = etNextPass.getText().toString().trim();
		switch (v.getId()) {

		case R.id.exeit:
			RegisterActivity.this.finish();
			break;
		case R.id.complete:
			if(etnextPass.equals("")||etpass.equals("")||etpone.equals("")){
				Toast.makeText(RegisterActivity.this, "请输入完整！",
						Toast.LENGTH_SHORT).show();
			}
			
			if (!etpass.equals(etnextPass)) {
				Toast.makeText(RegisterActivity.this, "你输入的密码不正确，请重新输入",
						Toast.LENGTH_SHORT).show();
				etPassWord.setText("");
				etNextPass.setText("");
				return;

			}
			String results = getJSON();
if(results!=null)
{
			if(results.equals("same")) {
				Toast.makeText(RegisterActivity.this, "已经注册!",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (results.equals("qq")) {
				Toast.makeText(RegisterActivity.this, "注册成功!",
						Toast.LENGTH_SHORT).show();
				return;
			}
			else {
				Toast.makeText(this, "注册失败!", Toast.LENGTH_SHORT).show();
			}
}else{
	Toast.makeText(this,"注册失败",Toast.LENGTH_SHORT).show();

}

		default:
			break;
		}

	}

	public String getJSON() {

		try {
			Asynck asynck = new Asynck(RegisterActivity.this, sendJSON());
			asynck.execute("http://10306.5avpn.com/OrderServer/cook/Cook_register");
			String response = asynck.get();

			JSONObject jsonAuth;

			jsonAuth = new JSONObject(response);

			String respons = jsonAuth.getString("regist");
			return respons;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public JSONObject sendJSON() {
		etPone = (EditText) findViewById(R.id.etPhone);
		String etpone = etPone.getText().toString().trim();
		etPassWord = (EditText) findViewById(R.id.etPassWord);
		String etpass = etPassWord.getText().toString().trim();

		try {
			JSONObject person = new JSONObject();
			person.put("name", etpone);
			person.put("passWord", etpass);
			return person;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
