package com.dachu.activity;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.dachu.activity.Listmain.GetDataTask;
import com.future.dachu.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Xue extends Activity {
	TextView tv;
	Button bt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xue);
		tv = (TextView) findViewById(R.id.textView1);

		bt = (Button) findViewById(R.id.button1);
		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// new
				// Ascy().execute("http://wulian.10306.pc5s.cn/lianxis/cai");
			}
		});

	}

	class Ascy extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost posts = new HttpPost(params[0]);

			// 创建HttpGet对象

			// 发送get请求
			StringEntity enty;
			try {
				enty = new StringEntity("caide");

				posts.setEntity(enty);

				HttpResponse httpResponse = httpClient.execute(posts);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

	}

}
