package com.dachu.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.dachu.activity.PullToRefreshListView.OnRefreshListener;
import com.dachu.adapter.AddBaseAdapter;
import com.dachu.constant.Constant;
import com.dachu.http.Asynck;
import com.future.dachu.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Listmain<T> extends Activity {
	private PullToRefreshListView mPullRefreshListView;
	// public static PullToRefreshListView weiboListView;
	ArrayList<Constant> listss;
	LinkedList<Map<String, Object>> listItems;
	int counts = 7;
	AddBaseAdapter simple;
	ArrayList<ArrayList<String>> list;
	ImageView im;
	ArrayList<ArrayList<HashMap<String, String>>> sa;
	LayoutInflater layout;
	int num = 0;

	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		// weiboListView = (PullToRefreshListView) findViewById(R.id.weibolist);
		im = (ImageView) findViewById(R.id.im_list_back);
		 
		layout = getLayoutInflater().from(this);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setMode(Mode.BOTH);
		new GetDataTask(Listmain.this, 0)
				.execute("http://10306.5avpn.com/OrderServer/cook/Cook_getData");

		im.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Listmain.this.finish();
			}
		});
		final Handler handlers = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if (msg.what == 0x123) {

					new GetDataTask(Listmain.this, 0)
							.execute("http://10306.5avpn.com/OrderServer/cook/Cook_getData");
					simple.notifyDataSetChanged();
				}
			}
		};
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handlers.sendEmptyMessage(0x123);
			}
		}, 5000, 50003300);

		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				try {
					JSONObject sub = new JSONObject();
					sub.put("table", listss.get(position).name);// 桌号
					sub.put("progress", "1");// 接收
					new Asynck(Listmain.this, sub)
							.execute("http://10306.5avpn.com/OrderServer/cook/Cook_modifyProgress");
				}

				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Toast.makeText(Listmain.this, "sa",
				// Toast.LENGTH_SHORT).show();
				Bundle data = new Bundle();
				System.out.println("ddddddddddddddddddd" + sa.get(position));
				data.putSerializable("key", sa.get(position));
				data.putSerializable("table", listss.get(position).name);
				Intent in = new Intent(Listmain.this, AddItems.class);
				in.putExtras(data);
				startActivity(in);
			}
		});

		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						Log.e("TAG", "onPullDownToRefresh");
						// 杩欓噷鍐欎笅鎷夊埛鏂扮殑浠诲姟
						// new GetDataTask().execute();
						new GetDataTask(Listmain.this, 0)
								.execute("http://10306.5avpn.com/OrderServer/cook/Cook_getData");
						simple.notifyDataSetChanged();
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						Log.e("TAG", "onPullUpToRefresh");
						// 杩欓噷鍐欎笂鎷夊姞杞芥洿澶氱殑浠诲姟
						// new GetDataTask().execute();
						new GetDataTask(Listmain.this, 0)
								.execute("http://10306.5avpn.com/OrderServer/cook/Cook_getData");
						simple.notifyDataSetChanged();
					}
				});
	}

	class GetDataTask extends AsyncTask<String, Void, ArrayList<Constant>> {
		private Context context;
		private int index;

		public GetDataTask(Context context, int index) {
			this.context = context;
			this.index = index;
		}

		protected ArrayList<Constant> doInBackground(String... params) {
			// Simulates a background job.

			return getJSON(params[0]);
		}

		protected void onPostExecute(ArrayList<Constant> results) {

			if (index == 0) {
				// 将字符串“Added after refresh”添加到顶部
				simple = new AddBaseAdapter(Listmain.this, results);
				mPullRefreshListView.setAdapter(simple);
				// SimpleDateFormat format = new SimpleDateFormat(
				// "yyyy年MM月dd日  HH:mm");
				// String date = format.format(new Date());
				// Call onRefreshComplete when the list has been refreshed.
				simple.notifyDataSetChanged();
				// weiboListView.onRefreshComplete(date);
				mPullRefreshListView.onRefreshComplete();
				// weiboListView.onRefreshComplete();
			} // else if (index == 1) {

			super.onPostExecute(results);
		}

		@SuppressWarnings("unused")
		private ArrayList<Constant> getJSON(String url) {

			Constant constant;
			JSONArray jsonItems;
			JSONArray arrayy;
			String result;

			JSONArray dishNames;
			JSONArray dishnumber;
			String tableNumbers;
			String tableStatus;
			HttpClient httpClient;

			ArrayList<HashMap<String, String>> dishListname;
			HttpPost posts;

			try {
				httpClient = new DefaultHttpClient();
				// 创建HttpGet对象
				posts = new HttpPost(url);
				// 发送get请求
				StringEntity enty = new StringEntity("caide");
				posts.setEntity(enty);

				HttpResponse httpResponses = httpClient.execute(posts);

				// 如果请求服务器成功返回响应
				if (httpResponses.getStatusLine().getStatusCode() == 200) {

					result = EntityUtils.toString(httpResponses.getEntity(),
							"UTF-8");

					listss = new ArrayList<Constant>();

					sa = new ArrayList<ArrayList<HashMap<String, String>>>();
					arrayy = new JSONArray(result);
					System.out.println(arrayy);
					for (int i = 0; i < arrayy.length(); i++) {
						constant = new Constant();
						jsonItems = arrayy.getJSONArray(i); // 每一桌
						// scores = jsonItem.getJSONArray(1);
						tableNumbers = jsonItems.getString(0);// 桌号
						// status = jsonItems.getJSONArray(1);

						tableStatus = jsonItems.getString(1);// 状态
						dishNames = jsonItems.getJSONArray(2);

						dishnumber = jsonItems.getJSONArray(3);
						dishListname = new ArrayList<HashMap<String, String>>();

						for (int n = 0; n < dishNames.length(); n++) {
							HashMap<String, String> map = new HashMap<String, String>();

							map.put("names", dishNames.getString(n));

							// map.put("num", dishNames.getString(j));
							map.put("number", "+ "+dishnumber.getString(n));

							dishListname.add(map);
						}

						constant.name = tableNumbers;
						System.out.println(constant.name);
						constant.select = tableStatus;
						listss.add(constant);

						sa.add(dishListname);
						System.out.println(dishListname);
					}

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return listss;
		}
	}

}
