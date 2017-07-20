package com.dachu.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

public class Asynck extends AsyncTask<String, Void, String> {
	Context context;
	JSONObject json;

	public Asynck(Context context, JSONObject json) {
		super();
		this.context = context;
		this.json = json;
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub

		StringEntity en;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(params[0]);

			en = new StringEntity(json.toString());
			post.setEntity(en);
			HttpResponse ponse = client.execute(post);
			if (ponse.getStatusLine().getStatusCode() == 200) {
				String entrity = EntityUtils.toString(ponse.getEntity());

				return entrity;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

}
