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
	private TextView tv_progress;	//��ʾ����
	private EditText place_num;		//���û�������λ��
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
		//������Ϣ���ݻ���
		progressHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if (msg.what==0x1233) {
					//�������˽�������������ķ�Ӧ
					tv_progress.setText((String) msg.obj);
				}else if (msg.what == 0x1234) {		//�ύ�ɹ�
					showMessage("�ύ�˵��ɹ��������Ե�Ƭ�̣�");
					tv_pay.setText("���ύ");
					tv_pay.setEnabled(false);	//ʹ�ύ��ť������
				}else if (msg.what == 0x1235) 	
				{	//mss.what==0x1235	�ύʧ��	
					showMessage("�ύʧ�ܣ��������³���!");
				}
			}
		};
	}

	/**
	 * ���ܣ���ʼ�����иý�������view
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
	 * ���ܣ�Ϊ�ý����е������� ��Ӧ�ļ���
	 */
	private void initViewListener() {
		
		img_menu_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();		//������ǰ�Ķ�Activity�����ص������ҳ��	
			}
		});
		
		tv_pay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//showMessage("�з�Ӧ��    ����������������");
				if (place_num.getText().toString().equals("")) {
					showMessage("�Բ���������д��λ��");
				}
				else if (tv_count_price.getText().toString() == null) {
					showMessage("�Բ���!����δѡ�˻�����Ҫ�ľ�ˮ�����ܽ����ύ");
				}else if (tv_count_price.getText().toString().equals("0")) {
					showMessage("�Բ�������δ��ѡ�κεĲ˻��ˮ�������ύ�˵���");
				}
				else{			//����ͻ���ѡ����������������������������
					final JSONArray jsonArray = new JSONArray();
					JSONObject jsonObject;
					new Thread(){
						@Override
						public void run() {
							listItems = (ArrayList<Map<String, Object>>) MyMenu.getListItems();
							//��װҪ���͵�����
							try {
								for (Map<String, Object> map :listItems) {
									//��ÿһ��
									JSONObject jsonObject = new JSONObject();
										jsonObject.put("name", map.get("name"));
										jsonObject.put("num", map.get("num"));
										jsonArray.put(jsonObject);
								}
							//���ܼۺ���λ�ŷ�װ���������󼯺ϵ����һ��������
							JSONObject jsonObject = new JSONObject();
							tablenum = Integer.parseInt(place_num.getText().toString());
							date = new Date();
							 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							 dateString = formatter.format(date);
							//��װ�ύ�����е��ܼۺ����ź�������ص���Ϣ
							jsonObject.put("count", tv_count_price.getText().toString());
							jsonObject.put("placenum", place_num.getText().toString());
							jsonObject.put("date", dateString);	//����ͻ��˴���ȥ��ʱ��ʱ����Ϣ���Ա��Ժ�������ں����Ų鿴��صĽ���
							jsonArray.put(jsonObject);
							} catch (JSONException e) {
								e.printStackTrace();
							}
							//����post����
							JSONArray respJsonArray	= WebUtil.getJSONArrayByWeb(Config.METHOD_SUBMIT, jsonArray);
							//showMessage("�Ѿ��ύ�˵�");
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
							//��ȡ�������еĽ��ȣ���������Ϣ,֪ͨ�����������
							//����get����
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
					}, 0, 60000);		//���   ����һ�������ȡ���Ȳ����ʹ�����Ϣ
					
				}	//if sententce(���) is over.
				//����һ����ʱ�����øü�ʱ�������Ե�ִ��ָ��������
			}
			//����һ����ʱ��
		});
		
		my_menu_list.setDelButtonClickListener(new DelButtonClickListener()
		{
			@Override
			public void clickHappend(final int position)
			{
				MyMenu.deleteMenuItem(position);		//ɾ��listItems�����ж�Ӧ������
				adapter.notifyDataSetChanged();			//ˢ��listview�����ݵĸı�
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
	 * @���� �����������������
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
	
	//�ṩ����۸�ʱ�Ļص�����
	public static void calculateP(){
		tv_count_price.setText(MyMenu.calculatePrice()+"");
	}
	
	//������ʾ��ʾ��Ϣ�ķ���
	public void showMessage(String message){
		Toast.makeText(MyMenuActivity.this, message, Toast.LENGTH_SHORT).show();;
	}
}
