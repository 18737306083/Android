package com.dachu.adapter;

import java.util.ArrayList;

import com.dachu.constant.Constant;
import com.future.dachu.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class AddBaseAdapter extends BaseAdapter {

	Context context;
	ArrayList<Constant> list;
	LayoutInflater lay;

	public AddBaseAdapter(Context context, ArrayList<Constant> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		lay = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder vh;

		if (arg1 == null) {
			arg1 = lay.inflate(R.layout.item_content, null);
			vh = new ViewHolder();
			vh.tv = (TextView) arg1.findViewById(R.id.item_content_tv);
			vh.bt = (TextView) arg1.findViewById(R.id.btStatus);
			arg1.setTag(vh);

		} else {
			vh = (ViewHolder) arg1.getTag();
		}
		vh.tv.setText("µÚ" + list.get(arg0).name + "ºÅ×À");
		vh.bt.setText(list.get(arg0).select);

		return arg1;
	}

	public class ViewHolder {
		TextView tv;
		TextView bt;
	}

}
