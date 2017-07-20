package com.caiqiufang.mine;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.caiqiufang.config.Config;
import com.caiqiufang.mylistview.MyListView;
import com.caiqiufang.mylistview.MyListView.DelButtonClickListener;
import com.caiqiufang.utils.WebUtil;
import com.caiqiufang.vegetablesstyle.MyMenu;
import com.example.caiqiufang.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MyMenuActivity extends Activity {
	//img_menu_back
	private ImageView img_menu_back;
	//my_menu_list
	private MyListView my_menu_list;
	//tv_edit
	//private TextView tv_edit;
	private TextView tv_pay;
	private TextView tv_progress;	//显示进度
	private EditText place_num;		//让用户输入座位号
	private ArrayList<Map<String,Object>> listItems;
	private SimpleAdapter adapter;
	//tv_count_price
	private static TextView tv_count_price;
	private Handler progressHandler;
	private static int tablenum;
	private static Date date;
	private static String dateString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_menu_activity);
		initView();
		initViewListener();
		setViewData();
		//设置消息传递机制
		progressHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if (msg.what==0x1233) {
					//发送做菜进度请求后做出的反应
					tv_progress.setText((String) msg.obj);
				}else if (msg.what == 0x1234) {		//提交成功
					showMessage("提交菜单成功，请您稍等片刻！");
					tv_pay.setText("已提交");
					tv_pay.setEnabled(false);	//使提交按钮不可用
				}else if (msg.what == 0x1235) 	
				{	//mss.what==0x1235	提交失败	
					showMessage("提交失败，请您重新尝试!");
				}
			}
		};
	}

	/**
	 * 功能：初始化所有该界面的组件view
	 */
	private void initView() {
		img_menu_back = (ImageView)findViewById(R.id.img_menu_back);
		my_menu_list = (MyListView)findViewById(R.id.my_menu_list);
		tv_pay = (TextView)findViewById(R.id.tv_pay);
		tv_count_price = (TextView)findViewById(R.id.tv_count_price);
		tv_progress = (TextView)findViewById(R.id.tv_progress);
		place_num = (EditText)findViewById(R.id.place_num);
	}
	
	/**
	 * 功能：为该界面中的组件添加 相应的监听
	 */
	private void initViewListener() {
		
		img_menu_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();		//结束当前的饿Activity，返回到进入的页面	
			}
		});
		
		tv_pay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//showMessage("有反应啊    啊啊啊啊啊啊啊啊");
				if (place_num.getText().toString().equals("")) {
					showMessage("对不起！请您填写桌位号");
				}
				else if (tv_count_price.getText().toString() == null) {
					showMessage("对不起!您还未选菜或者需要的酒水，不能进行提交");
				}else if (tv_count_price.getText().toString().equals("0")) {
					showMessage("对不起您还未挑选任何的菜或酒水，不能提交菜单！");
				}
				else{			//如果客户有选所需餐饮，进行向服务器发送数据
					final JSONArray jsonArray = new JSONArray();
					JSONObject jsonObject;
					new Thread(){
						@Override
						public void run() {
							listItems = (ArrayList<Map<String, Object>>) MyMenu.getListItems();
							//封装要传送的数据
							try {
								for (Map<String, Object> map :listItems) {
									//把每一个
									JSONObject jsonObject = new JSONObject();
										jsonObject.put("name", map.get("name"));
										jsonObject.put("num", map.get("num"));
										jsonArray.put(jsonObject);
								}
							//将总价和座位号封装到发送请求集合的最后一个对象中
							JSONObject jsonObject = new JSONObject();
							tablenum = Integer.parseInt(place_num.getText().toString());
							date = new Date();
							 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							 dateString = formatter.format(date);
							//封装提交订单中的总价和桌号和日期相关的信息
							jsonObject.put("count", tv_count_price.getText().toString());
							jsonObject.put("placenum", place_num.getText().toString());
							jsonObject.put("date", dateString);	//必须客户端传过去此时的时间信息，以便稍后根据日期和桌号查看相关的进度
							jsonArray.put(jsonObject);
							} catch (JSONException e) {
								e.printStackTrace();
							}
							//发送post请求
							JSONArray respJsonArray	= WebUtil.getJSONArrayByWeb(Config.METHOD_SUBMIT, jsonArray);
							//showMessage("已经提交菜单");
							int s= 0;
							try {
								s =  respJsonArray.getJSONObject(0).getInt("key");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (s == 1) {
								progressHandler.sendEmptyMessage(0x1234);
							}else{
								progressHandler.sendEmptyMessage(0x1235);
							}
						}
					}.start();
					
					new Timer().schedule(new TimerTask() {
						JSONArray respJsonArray;
						JSONArray reqJsonArray;
						String result="";
						@Override
						public void run() {
							//获取服务器中的进度，并发送消息,通知组件更新内容
							//发送get请求
							//progressHandler.sendEmptyMessage(0x1233);
							reqJsonArray = new JSONArray();
							JSONObject jsonObject = new JSONObject();
							
							try {
								jsonObject.put("tablenum", tablenum);
								jsonObject.put("date", dateString);
								reqJsonArray.put(jsonObject);
								respJsonArray = WebUtil.getJSONArrayByWeb(Config.METHOD_PROGRESS, reqJsonArray);
								result = respJsonArray.getJSONObject(0).getString("result");	
								System.out.println("****Result******"+result+"*********");
							} catch (Exception e) {
								e.printStackTrace();
							}
							if (!result.equals("") && result != null) {			//***********************************
								Message message = new Message();
								message.obj = result;
								message.what = 0x1233;
								progressHandler.sendMessage(message);
							}
						}
					}, 0, 60000);		//相隔   发送一次请求获取进度并发送传递消息
					
				}	//if sententce(语句) is over.
				//定义一个计时器，让该计时器周期性的执行指定的任务
			}
			//开启一个计时器
		});
		
		my_menu_list.setDelButtonClickListener(new DelButtonClickListener()
		{
			@Override
			public void clickHappend(final int position)
			{
				MyMenu.deleteMenuItem(position);		//删除listItems集合中对应的数据
				adapter.notifyDataSetChanged();			//刷新listview中数据的改变
				tv_count_price.setText(MyMenu.calculatePrice()+"");
			}
			
			@Override
			public void updaHappend(int posion) {
				View view = my_menu_list.getChildAt(posion);
				TextView tv_name = (TextView)view.findViewById(R.id.menu_name);
				TextView tvNums = (TextView)view.findViewById(R.id.nums);
				MyMenu.creatPopuWindow(MyMenuActivity.this,
						tvNums.getText().toString(),tv_name.getText().toString());//*****************************
				adapter.notifyDataSetChanged();
				tv_count_price.setText(MyMenu.calculatePrice()+"");
			}
		});
	}
	/**
	 * @功能 设置所有组件的数据
	 */
	
	private void setViewData() {
		listItems = (ArrayList<Map<String, Object>>) MyMenu.getListItems();
		 adapter = new SimpleAdapter(this,listItems,
				R.layout.my_menu_list_items,
				new String []{"name","price","num"},
				new int []{R.id.menu_name,R.id.menu_price,R.id.nums}
				);
		my_menu_list.setAdapter(adapter);
		tv_count_price.setText(MyMenu.calculatePrice()+"");
	}
	
	//提供计算价格时的回调方法
	public static void calculateP(){
		tv_count_price.setText(MyMenu.calculatePrice()+"");
	}
	
	//创建显示提示消息的方法
	public void showMessage(String message){
		Toast.makeText(MyMenuActivity.this, message, Toast.LENGTH_SHORT).show();;
	}
}
