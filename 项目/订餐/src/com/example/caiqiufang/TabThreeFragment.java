package com.example.caiqiufang;

import com.caiqiufang.mine.LoginActivity;
import com.caiqiufang.mine.MyCollectActivity;
import com.caiqiufang.mine.MyMenuActivity;
import com.caiqiufang.mine.MyOrderFormActivity;
import com.caiqiufang.mine.PersonalInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * @author 秋放
 *此类为板块中“我的”模块
 *主要处理订餐，菜单，收藏，个人信息等功能的类
 */
public class TabThreeFragment extends Fragment {
	private  View view;
	private Button login_register_bt;
	//my_dingdan,personal_infor,my_collect,my_menu
	private LinearLayout ll_my_dingdan,ll_personal_infor;
	private LinearLayout ll_my_collect,ll_my_menu;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		view =  inflater.inflate(R.layout.tab03,container,false);

		initView(view);
		initViewListener();
		return view;
	}
	
	private void initView(View view) {
		login_register_bt = (Button)view.findViewById(R.id.personal_login_button);
		//ll_my_dingdan,ll_personal_infor,ll_my_collect,ll_my_menu
		ll_my_dingdan = (LinearLayout)view.findViewById(R.id.my_dingdan);
		ll_personal_infor = (LinearLayout)view.findViewById(R.id.personal_infor);
		ll_my_collect = (LinearLayout)view.findViewById(R.id.my_collect);
		ll_my_menu = (LinearLayout)view.findViewById(R.id.my_menu);
	}
	//对所有的组件进行添加监听和事件处理
	private void initViewListener() {
		login_register_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//Toast.makeText(getActivity(), "您点击了登录界面", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getActivity(),LoginActivity.class);
				startActivity(intent);
			}
		});
		ll_my_dingdan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getActivity(), "您点击了我的订单", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getActivity(),MyOrderFormActivity.class);
				startActivity(intent);
			}
		});
		ll_personal_infor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getActivity(), "您点击了个人信息", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getActivity(),PersonalInfo.class);
				startActivity(intent);
			}
		});
		ll_my_collect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getActivity(), "您点击了我的收藏", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getActivity(),MyCollectActivity.class);
				startActivity(intent);
			}
		});
		ll_my_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Toast.makeText(getActivity(), "提交菜单服务人员即将为您上菜！", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getActivity(),MyMenuActivity.class);
				startActivity(intent);
			}
		});
	}
}
