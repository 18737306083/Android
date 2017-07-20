package com.caiqiufang.vegetablesstyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.caiqiufang.mine.MyMenuActivity;
import com.example.caiqiufang.R;

import android.R.bool;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyMenu {
	 static String numafter;
	//����һ���˵�����
	public static List<Map<String, Object>> listItems = new ArrayList<Map<String,Object>>();

	public static List<Map<String, Object>> getListItems() {
		return listItems;
	}
	
	public static void setListItems(List<Map<String, Object>> listItems) {
		MyMenu.listItems = listItems;
	}
	
	public static  boolean isExistVs(String name){
		for (Map<String, Object> list :listItems) {
			if (list.get("name").equals(name)) {
				return true;
			}
		}
		return false;
	}
	public static void setExistVsNum(String name,String berforNum,String afternum){
		for(int i = 0;i<listItems.size();i++){
			listItems.get(i).get("name").equals(name);
			int num = Integer.parseInt(berforNum)+Integer.parseInt(afternum);
			listItems.get(i).remove("num");
			listItems.get(i).put("num", num+"");
		}
	}
	public static String getExistVsNum(String name){
		String num = null;
		for (Map<String, Object> list :listItems) {
			if (list.get("name").equals(name)) {
				num = (String) list.get("num");
			}
		}
		return num;
	}
	public static void deleteMenuItem(int position){
		listItems.remove(position);
	}
	
	public static void updateMenuItem(String name,String num){
		for(int i = 0;i<listItems.size();i++){
			if (listItems.get(i).get("name").equals(name)) {
				listItems.get(i).remove("num");
				listItems.get(i).put("num", num+"");
			}
		}
	}
	
	
	//�����޸ĶԻ���
	public static void creatPopuWindow(final Context context,String num,final String name){
		//���ض�Ӧ�Ľ���
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.popuwindow_activity, null);
		//������ʾҪ�޸�ǰ������
		TextView tvNum = (TextView) view.findViewById(R.id.berfor_num);
		tvNum.setText(num);
		
		final EditText et_num = (EditText)view.findViewById(R.id.update_num);
		
		
		new AlertDialog.Builder(context)
			//���öԻ���ı���
			.setTitle("�޸�����")
			.setView(view)
			.setPositiveButton("�޸�", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					numafter = et_num.getText().toString();
					Toast.makeText(context, numafter+"****************", Toast.LENGTH_SHORT).show();
					updateMenuItem(name,numafter);
					MyMenuActivity.calculateP();//�ص���ʾ�۸�
				}
			})
			.setNegativeButton("ȡ��", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//ȡ���޸���ص��߼�����
				}
			})
			.create()
			.show();
	}
	public static int getPriceBystr(String str){
		int price;
		price = Integer.parseInt(str.substring(1, str.length()));
		return price;
	}
	//�����ܼ�
	public static int calculatePrice(){
		int sum=0;
		int price,num;
		//�������ϣ�������ܼ۸�
		for (Map<String, Object> map : listItems) {
			price = getPriceBystr((String)(map.get("price")));
			num = Integer.parseInt((String)(map.get("num")));
			sum=sum+price*num;
		}
		//���ؽ��
		return sum;
	}
}
