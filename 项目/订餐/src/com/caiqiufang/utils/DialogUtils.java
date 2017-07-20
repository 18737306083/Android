package com.caiqiufang.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import com.caiqiufang.config.Config;
import com.caiqiufang.scan.MipcaActivityCapture;
import com.example.caiqiufang.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

/**
 * @author ���
 *@function �Ի��򹤾��࣬������ʾɨ����ɨ�赽�Ĳ��ӷ�������ȡ��Ϣ�ĶԻ���
 */
public class DialogUtils {
	
	//vsProgress
	private static ViewGroup vsProgress;
	private static View view;
	private static TextView tv_dialog;
	private static String [] goodsInf ={"��Դ:","��������:","����:","����:"};
	private static StringBuffer buffer =null;
	/**
	 * @param url
	 * @param context
	 * @���� ��ʾ
	 */
	public static void DialogShow(String name,Context context){
		
		//������Ӧ�Ľ���
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.dialogutils_activity, null);
		TextView tv_dialog = (TextView)view.findViewById(R.id.tv_dialog);
		
		new FindSource().execute(name);
		
		new AlertDialog.Builder(context)
		//���öԻ���ı���
		.setTitle("��Դ����")
		.setView(view)
		.setPositiveButton("ȷ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				MipcaActivityCapture.getCurrentActivity().finish();
			}
		})
		.create()
		.show();
	}
	
	private  static class FindSource extends AsyncTask<String, Void, JSONArray>{
		
		@Override
		protected void onPreExecute() {
			
			if (vsProgress==null) {
				ViewStub vs = (ViewStub)view.findViewById(R.id.vsProgress);
				vsProgress = (ViewGroup) vs.inflate();
			}else{
				vsProgress.setVisibility(View.VISIBLE);	//����д�ʵ����ֱ�ӽ�����ʾ
			}
		}
		
		@Override
		protected JSONArray doInBackground(String... scanNames) {
			JSONObject jsonObject;
			JSONArray jsonArray;
			try {
				jsonArray = new JSONArray().put(new JSONObject().put("goodsId",
						scanNames[0]));
				//System.out.println("************"+scanNames[0]+"******************************************");
				JSONArray result = WebUtil.getJSONArrayByWeb(Config.METHOD_FINDSOURCE,
						jsonArray);
				
				if (result != null) {
					return result;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(JSONArray result) {
			//�жϽ��յĽ���������Ϊ�գ��������Ӧ�Ľ��ת��
			if (result != null) {
				if (vsProgress != null) {
					vsProgress.setVisibility(View.INVISIBLE);
				}
				
				buffer = new StringBuffer("");
				JSONObject jsonObject;
				try {
					for(int i = 0; i < result.length();i++){
						jsonObject = result.getJSONObject(i);
						buffer.append("����"+(i+1)+":").append(jsonObject.get("name")+"\n");
						buffer.append(goodsInf[0]).append(jsonObject.get("source")+"\n");
						buffer.append(goodsInf[1]).append(jsonObject.get("buydate")+"\n");
						buffer.append(goodsInf[2]).append(jsonObject.get("singleprice")+"\n");
						buffer.append(goodsInf[3]).append(jsonObject.get("num")+"\n");
					}
					tv_dialog.setText(buffer.toString());	//**********************************null
					//tv_dialog.setText("�Ҹ���һ����֪���Ķ���");
					System.out.println("****************************************************************");
					//System.out.println("*******************"+tv_dialog.toString()+"********************");
					//setViewText("�Ҹ���һ������");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				//tv_dialog.setText("�Բ����ά����ڻ����ӷ�����ʧ�ܣ�");
				if (vsProgress != null) {
					vsProgress.setVisibility(View.INVISIBLE);
				}
			}
		}
	}
	
	public static void setViewText(String string){
		tv_dialog.setText(string);
	}
}
